import { QinFramePopup } from "qin_desk/types/qin-frame-popup";
import { QinBounds } from "qin_soul";
import { QinBase } from "./qin-base";
import { QinPanel } from "./qin-panel";
import { Qine } from "./qin-tools";

export class QinPopup {
    private _qinMain: QinFramePopup;

    public constructor(contents: QinPanel) {
        this._qinMain = Qine.qinpel.frame.newPopup(contents.castedQine());
    }

    public show() {
        this._qinMain.show();
    }

    public showOnParent(parent: QinBase) {
        this._qinMain.showOnParent(parent.qinedHTML);
    }

    public showOnBounds(bounds: QinBounds) {
        this._qinMain.showOnBounds(bounds);
    }

    public close() {
        this._qinMain.close();
    }
}
