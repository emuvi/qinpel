import { AskConstantly, AskStream, IssuedAnswer, IssuedQuestion, StreamKind } from "qin_soul";
import { QinTalkerUtl } from "./qin-talker-utl";

export class QinTalkerUtlAux {
    private readonly _qinTalkerUtl: QinTalkerUtl;

    public constructor(qinTalkerUtl: QinTalkerUtl) {
        this._qinTalkerUtl = qinTalkerUtl;
    }

    public askWhenDone(question: IssuedQuestion): Promise<IssuedAnswer> {
        return new Promise<IssuedAnswer>((resolve, reject) => {
            const questionIsDone = {
                token: question.token,
                askIsDone: true,
            } as IssuedQuestion;
            const askIsDone = () => {
                this._qinTalkerUtl
                    .askIssued(questionIsDone)
                    .then((answer) => {
                        if (answer.isDone) {
                            this._qinTalkerUtl
                                .askIssued(question)
                                .then((res) => resolve(res))
                                .catch((err) => reject(err));
                        } else {
                            setTimeout((_) => askIsDone(), 700);
                        }
                    })
                    .catch((err) => reject(err));
            };
            askIsDone();
        });
    }

    public askConstantly(question: IssuedQuestion, process: AskConstantly) {
        const ask = () => {
            this._qinTalkerUtl
                .askIssued(question)
                .then((answer) => {
                    if (process.onReceive) {
                        process.onReceive(answer);
                    }
                    if (!process.stop) {
                        setTimeout((_) => ask(), 700);
                    }
                })
                .catch((err) => {
                    if (process.onError) {
                        process.onError(err);
                    }
                    if (!process.stop) {
                        setTimeout((_) => ask(), 700);
                    }
                });
        };
        ask();
    }

    public readStreamOut(reader: AskStream) {
        this.readStream(StreamKind.Out, reader);
    }

    public readStreamErr(reader: AskStream) {
        this.readStream(StreamKind.Err, reader);
    }

    public readStream(kind: StreamKind, reader: AskStream) {
        let from = 0;
        const ask = () => {
            let question: IssuedQuestion = null;
            if (kind === StreamKind.Err) {
                question = {
                    token: reader.token,
                    askIsDone: true,
                    askErrLinesFrom: from,
                    askErrLinesUntil: from + reader.chunks,
                    askErrLinesSize: true,
                };
            } else if (kind === StreamKind.Out) {
                question = {
                    token: reader.token,
                    askIsDone: true,
                    askOutLinesFrom: from,
                    askOutLinesUntil: from + reader.chunks,
                    askOutLinesSize: true,
                };
            }
            this._qinTalkerUtl
                .askIssued(question)
                .then((res) => {
                    let finished = false;
                    let got = 0;
                    if (res.outLinesFrom) {
                        if (reader.onReceive) {
                            res.outLinesFrom.forEach((line) => {
                                reader.onReceive(line);
                            });
                        }
                        got = res.outLinesFrom.length;
                    }
                    from = from + got;
                    console.log(res.isDone, from, res.outLinesSize);
                    if (res.isDone && from >= res.outLinesSize) {
                        finished = true;
                        if (reader.onFinish) {
                            reader.onFinish(res.outLinesSize);
                        }
                    }
                    if (!finished) {
                        setTimeout((_) => ask(), 70);
                    }
                })
                .catch((err) => {
                    if (reader.onError) {
                        reader.onError(err);
                    }
                });
        };
        ask();
    }
}
