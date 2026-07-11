import { AllowReg, Heads, Registry, Table, ToDelete, ToInsert, ToSelect, ToUpdate } from "qin_soul";
import { QinTalker } from "./qin-talker";
import { QinTalkerRegAux } from "./qin-talker-reg-aux";

export class QinTalkerReg {
    private readonly _talker: QinTalker;
    private readonly _qinTalkerRegAux: QinTalkerRegAux;

    public constructor(talker: QinTalker) {
        this._talker = talker;
        this._qinTalkerRegAux = new QinTalkerRegAux();
    }

    public get aux(): QinTalkerRegAux {
        return this._qinTalkerRegAux;
    }

    public top(base: string): Promise<Heads> {
        return new Promise<Heads>((resolve, reject) => {
            this._talker
                ._post<Heads>("/reg/top", base)
                .then((res) => resolve(res))
                .catch((err) => reject(err));
        });
    }

    public see(registry: Registry): Promise<Table> {
        return new Promise<Table>((resolve, reject) => {
            this._talker
                ._post<Table>("/reg/see", registry)
                .then((res) => resolve(res))
                .catch((err) => reject(err));
        });
    }

    public can(registry: Registry): Promise<AllowReg> {
        return new Promise<AllowReg>((resolve, reject) => {
            this._talker
                ._post<AllowReg>("/reg/can", registry)
                .then((res) => resolve(res))
                .catch((err) => reject(err));
        });
    }

    public new(toInsert: ToInsert): Promise<string> {
        return new Promise<string>((resolve, reject) => {
            this._talker
                ._post<string>("/reg/new", toInsert)
                .then((res) => resolve(res))
                .catch((err) => reject(err));
        });
    }

    public ask(toSelect: ToSelect): Promise<string> {
        return new Promise<string>((resolve, reject) => {
            this._talker
                ._post<string>("/reg/ask", toSelect)
                .then((res) => resolve(res))
                .catch((err) => reject(err));
        });
    }

    public set(toUpdate: ToUpdate): Promise<string> {
        return new Promise<string>((resolve, reject) => {
            this._talker
                ._post<string>("/reg/set", toUpdate)
                .then((res) => resolve(res))
                .catch((err) => reject(err));
        });
    }

    public del(toDelete: ToDelete): Promise<string> {
        return new Promise<string>((resolve, reject) => {
            this._talker
                ._post<string>("/reg/del", toDelete)
                .then((res) => resolve(res))
                .catch((err) => reject(err));
        });
    }
}
