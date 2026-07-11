import { QinFrame } from "./qin-frame";
import { QinOurs } from "./qin-ours";
import { QinTalker } from "./qin-talker";
import { QinWindow } from "./qin-window";

export class Qinpel {
    private _qinWindow: QinWindow;
    private _qinFrame: QinFrame;

    public constructor(qinWindow: QinWindow, qinFrame: QinFrame) {
        this._qinWindow = qinWindow;
        this._qinFrame = qinFrame;
    }

    public get window(): QinWindow {
        return this._qinWindow;
    }

    public get frame(): QinFrame {
        return this._qinFrame;
    }

    public get talk(): QinTalker {
        return this._qinWindow.talk;
    }

    public get ours() {
        return QinOurs;
    }

    public tr(of: string): string {
        return this.ours.tr(of);
    }
}
