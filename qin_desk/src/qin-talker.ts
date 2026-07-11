import axios from "axios";
import { QinTalkerApp } from "./qin-talker-app";
import { QinTalkerBas } from "./qin-talker-bas";
import { QinTalkerCmd } from "./qin-talker-cmd";
import { QinTalkerDir } from "./qin-talker-dir";
import { QinTalkerGiz } from "./qin-talker-giz";
import { QinTalkerReg } from "./qin-talker-reg";
import { QinTalkerUtl } from "./qin-talker-utl";
import { QinTalkerWay } from "./qin-talker-way";
import { QinWindow } from "./qin-window";

export class QinTalker {
    private readonly _qinWindow: QinWindow;
    private readonly _qinTalkerApp: QinTalkerApp;
    private readonly _qinTalkerBas: QinTalkerBas;
    private readonly _qinTalkerCmd: QinTalkerCmd;
    private readonly _qinTalkerDir: QinTalkerDir;
    private readonly _qinTalkerGiz: QinTalkerGiz;
    private readonly _qinTalkerReg: QinTalkerReg;
    private readonly _qinTalkerUtl: QinTalkerUtl;
    private readonly _qinTalkerWay: QinTalkerWay;

    public constructor(qinWindow: QinWindow) {
        this._qinWindow = qinWindow;
        this._qinTalkerApp = new QinTalkerApp(this);
        this._qinTalkerBas = new QinTalkerBas(this);
        this._qinTalkerCmd = new QinTalkerCmd(this);
        this._qinTalkerDir = new QinTalkerDir(this);
        this._qinTalkerGiz = new QinTalkerGiz(this);
        this._qinTalkerReg = new QinTalkerReg(this);
        this._qinTalkerUtl = new QinTalkerUtl(this);
        this._qinTalkerWay = new QinTalkerWay(this);
    }

    public get app() {
        return this._qinTalkerApp;
    }

    public get bas() {
        return this._qinTalkerBas;
    }

    public get cmd() {
        return this._qinTalkerCmd;
    }

    public get dir() {
        return this._qinTalkerDir;
    }

    public get giz() {
        return this._qinTalkerGiz;
    }

    public get reg() {
        return this._qinTalkerReg;
    }

    public get utl() {
        return this._qinTalkerUtl;
    }

    public get way() {
        return this._qinTalkerWay;
    }

    public _get<T = any>(address: string, headers?: any): Promise<T> {
        let configs = this._qinWindow.getAxiosConfig(headers);
        return new Promise<T>((resolve, reject) => {
            axios.get<T>(address, configs)
                .then(res => resolve(res.data))
                .catch(err => reject(err));
        });
    }

    public _post<T = any>(address: string, data: any, headers?: any): Promise<T> {
        let configs = this._qinWindow.getAxiosConfig(headers);
        if (!configs.headers["Content-Type"]) {
            if (typeof data === "string" || data instanceof String) {
                configs.headers["Content-Type"] = "text/plain";
            } else if (data instanceof FormData) {
                configs.headers["Content-Type"] = "multipart/form-data";
            } else {
                configs.headers["Content-Type"] = "application/json";
            }
        }
        return new Promise<T>((resolve, reject) => {
            axios.post<T>(address, data, configs)
                .then(res => resolve(res.data))
                .catch(err => reject(err));
        });
    }
}
