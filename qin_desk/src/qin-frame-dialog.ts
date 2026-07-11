import { QinSoul, QinStyles } from "qin_soul";
import { QinFrame } from "./qin-frame";

export class QinFrameDialog {
    private _qinFrame: QinFrame;
    private _title: string;
    private _divContent: HTMLDivElement;
    private _divDialog = document.createElement("div");
    private _divTop = document.createElement("div");
    private _spanTitle = document.createElement("span");
    private _spanClose = document.createElement("span");
    private _imgClose = document.createElement("img");
    private _divPack = document.createElement("div");

    private _showing = false;
    private _docNodes: ChildNode[] = [];

    public constructor(qinFrame: QinFrame, title: string, divContent: HTMLDivElement) {
        this._qinFrame = qinFrame;
        this._title = title;
        this._divContent = divContent;
        this.initDialog();
        this.initTop();
        this.initPack();
    }

    private initDialog() {
        styles.applyOnDialog(this._divDialog);
    }

    private initTop() {
        styles.applyOnDialogTop(this._divTop);
        this._divDialog.appendChild(this._divTop);
        styles.applyOnDialogTitle(this._spanTitle);
        this._spanTitle.innerText = this._title;
        this._divTop.appendChild(this._spanTitle);
        styles.applyOnDialogClose(this._spanClose);
        this._divTop.appendChild(this._spanClose);
        styles.applyOnDialogImage(this._imgClose);
        this._imgClose.src = "/pub/qin_desk/assets/frame-close.png";
        this._spanClose.appendChild(this._imgClose);
        QinSoul.arms.addAction(this._spanClose, (_) => {
            this.close();
        });
    }

    private initPack() {
        this._divDialog.appendChild(this._divPack);
        styles.applyOnDialogPack(this._divPack);
        this._divPack.appendChild(this._divContent);
    }

    public show() {
        if (this._showing) {
            return;
        }
        this._docNodes = [];
        for (let i = 0; i < this._qinFrame.getIFrameDoc().body.childNodes.length; i++) {
            const child = this._qinFrame.getIFrameDoc().body.childNodes[i];
            this._docNodes.push(child);
        }
        for (const child of this._docNodes) {
            this._qinFrame.getIFrameDoc().body.removeChild(child);
        }
        this._qinFrame.getIFrameDoc().body.appendChild(this._divDialog);
        this._showing = true;
    }

    public close() {
        if (!this._showing) {
            return;
        }
        this._qinFrame.getIFrameDoc().body.removeChild(this._divDialog);
        for (const child of this._docNodes) {
            this._qinFrame.getIFrameDoc().body.appendChild(child);
        }
        this._docNodes = [];
        this._showing = false;
    }
}

const styles = {
    applyOnDialog: (el: HTMLDivElement) => {
        el.style.position = "absolute";
        el.style.top = "0px";
        el.style.right = "0px";
        el.style.bottom = "0px";
        el.style.left = "0px";
        el.style.display = "flex";
        el.style.flexDirection = "column";
    },
    applyOnDialogTop: (el: HTMLDivElement) => {
        el.style.flex = "0";
        el.style.padding = "3px";
        el.style.margin = "0px";
        el.style.border = "0px";
        el.style.display = "flex";
        el.style.flexDirection = "row";
        el.style.flexWrap = "wrap";
        el.style.alignItems = "center";
        el.style.backgroundColor = QinStyles.ColorForeground;
        el.style.color = QinStyles.ColorBackground;
    },
    applyOnDialogPack: (el: HTMLDivElement) => {
        el.style.flex = "1";
        el.style.overflow = "auto";
        el.style.display = "flex";
        el.style.justifyContent = "center";
        el.style.alignItems = "center";
        el.style.padding = "0px";
        el.style.margin = "0px";
        el.style.border = "0px";
    },
    applyOnDialogTitle: (el: HTMLSpanElement) => {
        el.style.flex = "1";
        el.style.textAlign = "center";
        el.style.fontWeight = "bold";
    },
    applyOnDialogClose: (el: HTMLSpanElement) => {
        el.style.flex = "0";
        el.style.padding = "0px";
        el.style.margin = "0px";
        el.style.border = "0px";
        el.style.display = "flex";
        el.style.justifyContent = "center";
        el.style.alignItems = "center";
    },
    applyOnDialogImage: (el: HTMLImageElement) => {
        el.style.padding = "0px";
        el.style.margin = "0px";
        el.style.border = "0px";
    },
};
