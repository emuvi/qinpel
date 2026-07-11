import { IssuedAnswer, IssuedQuestion, Logged, TryAuth } from "qin_soul";
import { QinTalker } from "./qin-talker";
import { QinTalkerUtlAux } from "./qin-talker-utl-aux";
import { QinOurs } from "./qin-ours";

export class QinTalkerUtl {
    private readonly _qinTalker: QinTalker;
    private readonly _qinTalkerUtlAux: QinTalkerUtlAux;

    public constructor(qinTalker: QinTalker) {
        this._qinTalker = qinTalker;
        this._qinTalkerUtlAux = new QinTalkerUtlAux(this);
    }

    public get aux(): QinTalkerUtlAux {
        return this._qinTalkerUtlAux;
    }

    public ping(): Promise<string> {
        return new Promise<string>((resolve, reject) => {
            this._qinTalker
                ._get<string>("/ping")
                .then((res) => resolve(res))
                .catch((err) => reject(err));
        });
    }

    public getLang(): Promise<string> {
        return new Promise<string>((resolve, reject) => {
            this._qinTalker
                ._get<string>("/lang")
                .then((res) => resolve(res))
                .catch((err) => reject(err));
        });
    }

    public tryEnter(tryAuth: TryAuth): Promise<Logged> {
        tryAuth.pass = QinOurs.crypto.sha1(tryAuth.pass);
        return new Promise<Logged>((resolve, reject) => {
            this._qinTalker
                ._post<Logged>("/enter", tryAuth)
                .then((res) => resolve(res))
                .catch((err) => reject(err));
        });
    }

    public isLogged(): Promise<boolean> {
        return new Promise<boolean>((resolve, reject) => {
            this._qinTalker
                ._get<string>("/logged")
                .then((res) => resolve(res !== "<--NO_USER_LOGGED-->"))
                .catch((err) => reject(err));
        });
    }

    public getLogged(): Promise<string> {
        return new Promise<string>((resolve, reject) => {
            this._qinTalker
                ._get<string>("/logged")
                .then((res) => {
                    if (res !== "<--NO_USER_LOGGED-->") {
                        resolve(res);
                    } else {
                        reject(new Error("No user is logged."));
                    }
                })
                .catch((err) => reject(err));
        });
    }

    public getConfig(name: string, orDefault: string = ""): Promise<string> {
        return new Promise<string>((resolve, reject) => {
            this._qinTalker
                ._get<string>("/config/" + encodeURIComponent(name))
                .then((config) => resolve(config ?? orDefault))
                .catch((err) => reject(err));
        });
    }

    public askIssued(question: IssuedQuestion): Promise<IssuedAnswer> {
        return new Promise<IssuedAnswer>((resolve, reject) => {
            this._qinTalker
                ._post<IssuedAnswer>("/issued", question)
                .then((answer) => resolve(answer))
                .catch((err) => reject(err));
        });
    }
}
