import { QinButton, Qine, QinLabel, QinColumn } from "qin_case";
import { Modules } from "./consts";

const qinWindow = Qine.qinpel.window;

export class Menu extends QinColumn {
    private _buttonAPP = new QinButton({label: new QinLabel("APP")});
    private _buttonBAS = new QinButton({label: new QinLabel("BAS")});
    private _buttonCMD = new QinButton({label: new QinLabel("CMD")});
    private _buttonDIR = new QinButton({label: new QinLabel("DIR")});
    private _buttonGIZ = new QinButton({label: new QinLabel("GIZ")});
    private _buttonREG = new QinButton({label: new QinLabel("REG")});
    private _buttonUTL = new QinButton({label: new QinLabel("UTL")});
    private _buttonWAY = new QinButton({label: new QinLabel("WAY")});
    
    public constructor() {
        super();
        this._buttonAPP
            .addActionMain(_ => qinWindow.newFrame("AbDesk APP", "abdesk", {Module: Modules.APP}))
            .install(this);
        this._buttonBAS
            .addActionMain(_ => qinWindow.newFrame("AbDesk BAS", "abdesk", {Module: Modules.BAS}))
            .install(this);
        this._buttonCMD
            .addActionMain(_ => qinWindow.newFrame("AbDesk CMD", "abdesk", {Module: Modules.CMD}))
            .install(this);
        this._buttonDIR
            .addActionMain(_ => qinWindow.newFrame("AbDesk DIR", "abdesk", {Module: Modules.DIR}))
            .install(this);
        this._buttonGIZ
            .addActionMain(_ => qinWindow.newFrame("AbDesk GIZ", "abdesk", {Module: Modules.GIZ}))
            .install(this);
        this._buttonREG
            .addActionMain(_ => qinWindow.newFrame("AbDesk REG", "abdesk", {Module: Modules.REG}))
            .install(this);
        this._buttonUTL
            .addActionMain(_ => qinWindow.newFrame("AbDesk UTL", "abdesk", {Module: Modules.UTL}))
            .install(this);
        this._buttonWAY
            .addActionMain(_ => qinWindow.newFrame("AbDesk WAY", "abdesk", {Module: Modules.WAY}))
            .install(this);
    }
}