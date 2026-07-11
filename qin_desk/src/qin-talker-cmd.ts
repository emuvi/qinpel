import { QinBody, IssuedToken, Execute } from "qin_soul";
import { QinTalker } from "./qin-talker";

export class QinTalkerCmd {
    private readonly _talker: QinTalker;

    public constructor(talker: QinTalker) {
        this._talker = talker;
    }

    public list(): Promise<string[]> {
        return new Promise<string[]>((resolve, reject) => {
            this._talker
                ._get<string>("/list/cmd")
                .then((text) => resolve(QinBody.getTextLines(text)))
                .catch((err) => reject(err));
        });
    }

    public run(execute: Execute): Promise<IssuedToken> {
        return new Promise<string>((resolve, reject) => {
            this._talker
                ._post<IssuedToken>("/cmd/run", execute)
                .then((token) => resolve(token))
                .catch((err) => reject(err));
        });
    }
}
