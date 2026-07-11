import { Execute, IssuedToken, QinBody } from "qin_soul";
import { QinTalker } from "./qin-talker";

export class QinTalkerGiz {
    private readonly _talker: QinTalker;

    public constructor(talker: QinTalker) {
        this._talker = talker;
    }

    public list(): Promise<string[]> {
        return new Promise<string[]>((resolve, reject) => {
            this._talker
                ._get<string>("/list/giz")
                .then((text) => resolve(QinBody.getTextLines(text)))
                .catch((err) => reject(err));
        });
    }

    public run(execute: Execute): Promise<IssuedToken> {
        return new Promise<string>((resolve, reject) => {
            this._talker
                ._post<IssuedToken>("/giz/run", execute)
                .then((token) => resolve(token))
                .catch((err) => reject(err));
        });
    }
}
