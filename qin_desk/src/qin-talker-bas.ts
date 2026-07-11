import { QinBody } from "qin_soul";
import { QinTalker } from "./qin-talker";

export class QinTalkerBas {
    private readonly _talker: QinTalker;

    public constructor(talker: QinTalker) {
        this._talker = talker;
    }

    public list(): Promise<string[]> {
        return new Promise<string[]>((resolve, reject) => {
            this._talker
                ._get<string>("/list/base")
                .then((text) => resolve(QinBody.getTextLines(text)))
                .catch((err) => reject(err));
        });
    }
}
