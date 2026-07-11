import { QinArms, QinBody, QinBounds, QinGrandeur, QinHead, QinLegs, QinSkin, QinSoul, QinWaiter, QinWaiters} from "qin_soul";
import { QinFrameDialog } from "./qin-frame-dialog";
import { QinFramePopup } from "./qin-frame-popup";
import { QinWindow } from "./qin-window";
import { Qinpel } from "./qinpel";

export { QinFrameDialog } from "./qin-frame-dialog";
export { QinFramePopup } from "./qin-frame-popup";

export class QinFrame {
    private _qinWindow: QinWindow;
    private _title: string;
    private _appNameOrAddress: string;
    private _appName: string;
    private _options: any;
    private _waiters = new QinWaiters<any>();
    private _rootID = Math.floor(Math.random() * 1000000);
    private _divFrame = document.createElement("div");
    private _divHead = document.createElement("div");
    private _imgMenu = document.createElement("img");
    private _divTitle = document.createElement("div");
    private _imgMinimize = document.createElement("img");
    private _imgMaximize = document.createElement("img");
    private _imgClose = document.createElement("img");
    private _divBody = document.createElement("div");
    private _iframeBody = document.createElement("iframe");
    private _statusBody = document.createElement("div");
    private _divFoot = document.createElement("div");
    private _footStatusType = document.createElement("img");
    private _footStatusText = document.createElement("div");
    private _footResize = document.createElement("img");

    private _seeingStatus = false;
    private _minimized = false;
    private _maximized = false;
    private _lastWidth = -1;
    private _lastHeight = -1;

    private _wasClosed = false;

    private _onFocusGain: Array<Function> = null;
    private _onFocusLost: Array<Function> = null;

    public constructor(qinWindow: QinWindow, title: string, appNameOrAddress: string, options?: any) {
        this._qinWindow = qinWindow;
        this._title = this.initFrameTitle(title);
        this._appNameOrAddress = appNameOrAddress;
        this._options = options ? options : {};
        this.initDivFrame();
        this.initDivHead();
        this.initDivBody();
        this.initIFrameBody();
        this.initStatusBody();
        this.initDivFoot();
        this.initDraggable();
    }

    private initFrameTitle(title: string): string {
        let result = title;
        let attempt = 1;
        while (true) {
            if (this._qinWindow.getFrame(result) != null) {
                result = title + " (" + ++attempt + ")";
            } else {
                break;
            }
        }
        return result;
    }

    private initDivFrame() {
        this._divFrame.id = "QinpelFrameID" + this._rootID;
        styles.applyOnDivFrame(this._divFrame);
        const frameInitBounds = this.loadFrameInitBounds();
        this._divFrame.style.left = frameInitBounds.posX + "px";
        this._divFrame.style.top = frameInitBounds.posY + "px";
        this._divFrame.style.width = frameInitBounds.width + "px";
        this._divFrame.style.height = frameInitBounds.height + "px";
        this._lastWidth = frameInitBounds.width;
        this._lastHeight = frameInitBounds.height;
    }

    private loadFrameInitBounds(): QinBounds {
        const result = {
            posX: 64,
            posY: 64,
            width: 800,
            height: 600,
        };
        let windowSizeStyle = QinSoul.skin.getWindowSizeStyle();
        const windowStyleConfigName = this.getFrameWindowStyleConfigName(windowSizeStyle);
        const frameBoundsSaved = this._qinWindow.loadConfig(windowStyleConfigName);
        if (frameBoundsSaved) {
            let parts = frameBoundsSaved.split(",");
            result.posX = Number(parts[0]);
            result.posY = Number(parts[1]);
            result.width = Number(parts[2]);
            result.height = Number(parts[3]);
        } else {
            if (windowSizeStyle === QinGrandeur.SMALL) {
                result.posX = 0;
                result.posY = 0;
                const size = QinSoul.skin.getWindowSize();
                result.width = size.width - 4;
                result.height = size.height - 4;
            } else if (windowSizeStyle === QinGrandeur.MEDIUM) {
                result.posX = 48;
                result.posY = 48;
                result.width = 500;
                result.height = 375;
            }
        }
        return result;
    }

    private getFrameWindowStyleConfigName(windowSizeStyle: QinGrandeur): string {
        return "On: [" + windowSizeStyle + "] Of: [" + this._title + "]";
    }

    private initDivHead() {
        styles.applyOnDivHead(this._divHead);
        this._imgMenu.src = "./assets/frame-menu.png";
        styles.applyOnDivEdgeIcon(this._imgMenu);
        this._imgMenu.alt = "o";
        QinArms.addActionMain(this._imgMenu, () => this.showWindowMenu());
        this._divHead.appendChild(this._imgMenu);
        styles.applyOnDivHeadTitle(this._divTitle);
        this._divTitle.innerText = this._title;
        this._divHead.appendChild(this._divTitle);
        this._imgMinimize.src = "./assets/frame-minimize.png";
        styles.applyOnDivEdgeIcon(this._imgMinimize);
        this._imgMinimize.alt = "-";
        QinArms.addActionMain(this._imgMinimize, () => this.minimize());
        this._divHead.appendChild(this._imgMinimize);
        this._imgMaximize.src = "./assets/frame-maximize.png";
        styles.applyOnDivEdgeIcon(this._imgMaximize);
        this._imgMaximize.alt = "+";
        QinArms.addActionMain(this._imgMaximize, () => this.maximize());
        this._divHead.appendChild(this._imgMaximize);
        this._imgClose.src = "./assets/frame-close.png";
        styles.applyOnDivEdgeIcon(this._imgClose);
        this._imgClose.alt = "x";
        QinArms.addActionMain(this._imgClose, () => this.close());
        this._divHead.appendChild(this._imgClose);
        this._divFrame.appendChild(this._divHead);
    }

    private initDivBody() {
        this._divBody.id = "QinpelDivBodyID" + this._rootID;
        styles.applyOnDivBody(this._divBody);
        this._divFrame.appendChild(this._divBody);
    }

    private initIFrameBody() {
        this._iframeBody.id = "QinpelIFrameBodyID" + this._rootID;
        styles.applyOnIFrameBody(this._iframeBody);
        this._iframeBody.style.display = "initial";
        let address = this._appNameOrAddress;
        if (!(address.startsWith("/app/") || address.startsWith("/pub/"))) {
            this._appName = address;
            address = "/app/" + address + "/index.html";
        } else {
            const parts = address.split("/");
            this._appName = parts.length > 2 ? parts[2] : "";
        }
        this._iframeBody.src = address;
        this._iframeBody.onload = (_) => {
            styles.applyOnIFrameLoad(this._iframeBody);
            QinSoul.head.stopBrowserShortcuts(this._iframeBody.contentWindow);
        };
        this._divBody.appendChild(this._iframeBody);
    }

    private initFocusVerifier() {
        let isFocused = false;
        const checkFocus = () => {
            if (document.activeElement == this._iframeBody) {
                if (!isFocused) {
                    isFocused = true;
                    this._qinWindow.showElement(this._divFrame);
                    if (this._onFocusGain) {
                        for (const toCall of this._onFocusGain) {
                            toCall();
                        }
                    }
                }
            } else {
                if (isFocused) {
                    isFocused = false;
                    if (this._onFocusLost) {
                        for (const toCall of this._onFocusLost) {
                            toCall();
                        }
                    }
                }
            }
            if (this._qinWindow.hasChild(this._divFrame)) {
                window.setTimeout(checkFocus, 1000);
            }
        };
        checkFocus();
    }

    private initStatusBody() {
        this._statusBody.id = "QinpelStatusBodyID" + this._rootID;
        styles.applyOnStatusBody(this._statusBody);
        this._statusBody.style.display = "none";
        this._divBody.appendChild(this._statusBody);
    }

    private initDivFoot() {
        styles.applyOnDivFoot(this._divFoot);
        this._footStatusType.src = "./assets/frame-status-info.png";
        styles.applyOnDivEdgeIcon(this._footStatusType);
        QinSoul.arms.addActionMain(this._footStatusType, () => this.switchStatus());
        this._divFoot.appendChild(this._footStatusType);
        styles.applyOnStatusText(this._footStatusText);
        this._footStatusText.innerText = "StatusBar";
        this._divFoot.appendChild(this._footStatusText);
        this._footResize.src = "./assets/frame-resize.png";
        styles.applyOnDivEdgeIcon(this._footResize);
        this._footResize.alt = "/";
        this._divFoot.appendChild(this._footResize);
        this._divFrame.appendChild(this._divFoot);
    }

    private initDraggable() {
        QinSoul.arms.addMover([this._divTitle, this._footStatusText], this._divFrame, {
            onEnd: () => {
                this.show();
                this.saveFrameBounds();
                QinSoul.skin.clearSelection();

            }
        });
        QinArms.addResizer([this._footResize], this._divFrame, {
            onEnd: () => {
                this._maximized = false;
                this._lastWidth = parseInt(this._divFrame.style.width, 10);
                this._lastHeight = parseInt(this._divFrame.style.height, 10);
                this.show();
                this.saveFrameBounds();
                QinSoul.skin.clearSelection();
            }
        });
    }

    private switchStatus() {
        if (this._seeingStatus) {
            this._statusBody.style.display = "none";
            this._iframeBody.style.display = "initial";
            this._seeingStatus = false;
        } else {
            this._iframeBody.style.display = "none";
            this._statusBody.style.display = "initial";
            this._statusBody.scrollTop = this._statusBody.scrollHeight;
            this._seeingStatus = true;
        }
    }

    public get window(): QinWindow {
        return this._qinWindow;
    }

    public get title(): string {
        return this._title;
    }

    public get appName(): string {
        return this._appName;
    }

    public putOption(name: string, value: any): QinFrame {
        this._options[name] = value;
        return this;
    }

    public delOption(name: string): QinFrame {
        delete this._options[name];
        return this;
    }

    public hasOption(name: string): boolean {
        return Object.prototype.hasOwnProperty.call(this._options, name);
    }

    public putOptions(options: any): QinFrame {
        if (options) {
            Object.assign(this._options, options);
        } else {
            this._options = options;
        }
        return this;
    }

    public delOptions(...names: string[]): QinFrame {
        for (const name of names) {
            delete this._options[name];
        }
        return this;
    }

    public hasOptions(...names: string[]): boolean {
        return names.every(name => Object.prototype.hasOwnProperty.call(this._options, name));
    }

    public cleanOptions(): QinFrame {
        this._options = {};
        return this;
    }

    public getOption(name: string): any {
        return this._options[name];
    }

    public putWaiter(waiter: QinWaiter<any>): QinFrame {
        this._waiters.put(waiter);
        return this;
    }

    public delWaiter(waiter: QinWaiter<any>): QinFrame {
        this._waiters.del(waiter);
        return this;
    }

    public hasWaiter(waiter: QinWaiter<any>): boolean {
        return this._waiters.has(waiter);
    }

    public cleanWaiters(): QinFrame {
        this._waiters.clean();
        return this;
    }

    public sendWaiters(result: any): QinFrame {
        this._waiters.send(result);
        return this;
    }

    public getFrameID(): string {
        return this._divFrame.id;
    }

    public install() {
        //@ts-ignore
        this._iframeBody.qinpel = new Qinpel(this._qinWindow, this);
        this._qinWindow.addChild(this._divFrame);
        this.initFocusVerifier();
        this.show();
    }

    public statusInfo(info: any, origin: string) {
        let message = QinHead.getInfoMessage(info, origin);
        this._footStatusText.innerText = this.getDisplayStatusMessage(message);
        let divInfo = document.createElement("div");
        divInfo.innerText = message;
        styles.applyOnStatusBodyItem(divInfo);
        divInfo.style.backgroundColor = "#0f9d5827";
        this._statusBody.appendChild(divInfo);
    }

    public statusError(error: any, origin: string) {
        let message = QinHead.getErrorMessage(error, origin);
        this._footStatusText.innerText = this.getDisplayStatusMessage(message);
        this._footStatusType.src = "./assets/frame-status-error.png";
        let divError = document.createElement("div");
        divError.innerText = message;
        styles.applyOnStatusBodyItem(divError);
        divError.style.backgroundColor = "#e5091427";
        this._statusBody.appendChild(divError);
        console.log(error);
    }

    private getDisplayStatusMessage(message: string): string {
        let firstBreak = message.indexOf("\n");
        if (firstBreak > -1) {
            return message.substring(0, firstBreak);
        } else {
            return message;
        }
    }

    public saveFrameBounds() {
        let windowSizeStyle = QinSoul.skin.getWindowSizeStyle();
        const windowStyleConfigName = this.getFrameWindowStyleConfigName(windowSizeStyle);
        const frameBounds =
            parseInt(this._divFrame.style.left, 10) +
            "," +
            parseInt(this._divFrame.style.top, 10) +
            "," +
            parseInt(this._divFrame.style.width, 10) +
            "," +
            parseInt(this._divFrame.style.height, 10);
        this._qinWindow.saveConfig(windowStyleConfigName, frameBounds);
    }

    public show() {
        this._qinWindow.showElement(this._divFrame);
        this._iframeBody.focus();
    }

    public showWindowMenu() {
        this._qinWindow.showMenu();
    }

    public minimize() {
        if (this._minimized) {
            this._divFrame.style.width = this._lastWidth + "px";
            this._divFrame.style.height = this._lastHeight + "px";
            this._iframeBody.style.display = "initial";
            this._divFoot.style.display = "initial";
            this._minimized = false;
        } else {
            if (this._maximized) {
                this.maximize();
            }
            this._lastWidth = parseInt(this._divFrame.style.width, 10);
            this._lastHeight = parseInt(this._divFrame.style.height, 10);
            this._iframeBody.style.display = "none";
            this._divFoot.style.display = "none";
            this._divFrame.style.width = FrameSetup.MINIMIZED_WIDTH + "px";
            this._divFrame.style.height = this._divHead.clientHeight + "px";
            this._minimized = true;
        }
        this._qinWindow.showElement(this._divFrame);
    }

    public maximize() {
        if (this._maximized) {
            this._divFrame.style.width = this._lastWidth + "px";
            this._divFrame.style.height = this._lastHeight + "px";
            this._maximized = false;
        } else {
            if (this._minimized) {
                this.minimize();
            }
            this._lastWidth = parseInt(this._divFrame.style.width, 10);
            this._lastHeight = parseInt(this._divFrame.style.height, 10);
            this._divFrame.style.width = this._qinWindow.getBodyWidth() - 4 + "px";
            this._divFrame.style.height = this._qinWindow.getBodyHeight() - 4 + "px";
            this._maximized = true;
        }
        this._qinWindow.showElement(this._divFrame);
    }

    public getIFrame(): HTMLIFrameElement {
        return this._iframeBody;
    }

    public getIFrameDoc(): Document {
        return this._iframeBody.contentWindow.document;
    }

    public newDialog(title: string, divContent: HTMLDivElement): QinFrameDialog {
        return new QinFrameDialog(this, title, divContent);
    }

    public newPopup(divContent: HTMLDivElement): QinFramePopup {
        return new QinFramePopup(this, divContent);
    }

    public showAlert(message: string) {
        const divBody = document.createElement("div");
        const popup = this.newPopup(divBody);
        divBody.style.display = "flex";
        divBody.style.flexDirection = "column";
        divBody.style.padding = "12px";
        const divMessage = this.newMessageLines(message);
        divBody.appendChild(divMessage);
        const divButton = document.createElement("div");
        divBody.appendChild(divButton);
        divButton.style.display = "flex";
        divButton.style.justifyContent = "center";
        divButton.style.marginTop = "6px";
        const button = document.createElement("button");
        divButton.appendChild(button);
        QinSkin.styleAsEditable(button);
        button.innerText = "Ok";
        button.onclick = () => {
            popup.close();
        };
        popup.show();
    }

    public showDialog(message: string): Promise<boolean> {
        return new Promise((resolve) => {
            const divBody = document.createElement("div");
            const popup = this.newPopup(divBody);
            divBody.style.display = "flex";
            divBody.style.flexDirection = "column";
            divBody.style.padding = "12px";
            const divMessage = this.newMessageLines(message);
            divBody.appendChild(divMessage);
            const divButton = document.createElement("div");
            divBody.appendChild(divButton);
            divButton.style.display = "flex";
            divButton.style.justifyContent = "center";
            divButton.style.marginTop = "6px";
            const btnOk = document.createElement("button");
            divButton.appendChild(btnOk);
            QinSkin.styleAsEditable(btnOk);
            btnOk.innerText = "Ok";
            var confirmed = false;
            btnOk.onclick = () => {
                confirmed = true;
                popup.close();
            };
            const btnCancel = document.createElement("button");
            divButton.appendChild(btnCancel);
            QinSkin.styleAsEditable(btnCancel);
            btnCancel.innerText = "Cancel";
            btnCancel.onclick = () => {
                popup.close();
            };
            popup.addOnClose(() => {
                resolve(confirmed);
            });
            popup.show();
        });
    }

    public showInfo(info: any, origin: string) {
        const divBody = document.createElement("div");
        const popup = this.newPopup(divBody);
        divBody.style.display = "flex";
        divBody.style.flexDirection = "column";
        divBody.style.padding = "12px";
        const divIcon = document.createElement("div");
        divBody.appendChild(divIcon);
        divIcon.style.display = "flex";
        divIcon.style.justifyContent = "center";
        divIcon.style.marginBottom = "6px";
        const icon = document.createElement("img");
        icon.src = "/pub/qin_desk/assets/frame-status-info.png";
        icon.style.width = "24px";
        icon.style.height = "24px";
        divIcon.appendChild(icon);
        const divMessage = this.newMessageLines(QinHead.getInfoMessage(info, origin));
        divBody.appendChild(divMessage);
        const divButton = document.createElement("div");
        divBody.appendChild(divButton);
        divButton.style.display = "flex";
        divButton.style.justifyContent = "center";
        divButton.style.marginTop = "6px";
        const button = document.createElement("button");
        divButton.appendChild(button);
        QinSkin.styleAsEditable(button);
        button.innerText = "Ok";
        button.onclick = () => {
            popup.close();
        };
        popup.show();
    }

    public showError(error: any, origin: string) {
        const divBody = document.createElement("div");
        const popup = this.newPopup(divBody);
        divBody.style.display = "flex";
        divBody.style.flexDirection = "column";
        divBody.style.padding = "12px";
        const divIcon = document.createElement("div");
        divBody.appendChild(divIcon);
        divIcon.style.display = "flex";
        divIcon.style.justifyContent = "center";
        divIcon.style.marginBottom = "6px";
        const icon = document.createElement("img");
        icon.src = "/pub/qin_desk/assets/frame-status-error.png";
        icon.style.width = "24px";
        icon.style.height = "24px";
        divIcon.appendChild(icon);
        const divMessage = this.newMessageLines(QinHead.getErrorMessage(error, origin));
        divBody.appendChild(divMessage);
        const divButton = document.createElement("div");
        divBody.appendChild(divButton);
        divButton.style.display = "flex";
        divButton.style.justifyContent = "center";
        divButton.style.marginTop = "6px";
        const button = document.createElement("button");
        divButton.appendChild(button);
        QinSkin.styleAsEditable(button);
        button.innerText = "Ok";
        button.onclick = () => {
            popup.close();
        };
        popup.show();
    }

    private newMessageLines(message: string): HTMLDivElement {
        return QinLegs.newColumn(
            QinBody.getTextLines(message).map((line) => QinLegs.newSpan(line))
        );
    }

    public navigate(url: string) {
        this._iframeBody.src = url;
    }

    public close() {
        this.saveFrameBounds();
        this._qinWindow.delChild(this._divFrame);
        this._qinWindow.delFrame(this);
        this._wasClosed = true;
    }

    public get wasClosed() {
        return this._wasClosed;
    }

    public addOnFocusGain(func: Function) {
        if (!this._onFocusGain) {
            this._onFocusGain = [];
        }
        this._onFocusGain.push(func);
    }

    public delOnFocusGain(func: Function) {
        if (!this._onFocusGain) {
            return;
        }
        const index = this._onFocusGain.indexOf(func);
        if (index > -1) {
            this._onFocusGain.splice(index, 1);
        }
    }

    public addOnFocusLost(func: Function) {
        if (!this._onFocusLost) {
            this._onFocusLost = [];
        }
        this._onFocusLost.push(func);
    }

    public delOnFocusLost(func: Function) {
        if (!this._onFocusLost) {
            return;
        }
        const index = this._onFocusLost.indexOf(func);
        if (index > -1) {
            this._onFocusLost.splice(index, 1);
        }
    }
}

const FrameSetup = {
    POP_MENU_MAX_HEIGHT: 270,
    POP_MENU_WIDTH: 180,
    MINIMIZED_WIDTH: 180,
};

const styles = {
    applyOnDivFrame: (el: HTMLDivElement) => {
        el.style.backgroundColor = "#878787";
        el.style.border = "2px solid #6c6c6c";
        el.style.borderStyle = "outset";
        el.style.borderRadius = "7px";
        el.style.position = "absolute";
        el.style.display = "flex";
        el.style.flexDirection = "column";
        el.style.overflow = "hidden";
    },
    applyOnDivHead: (el: HTMLDivElement) => {
        el.style.padding = "3px";
        el.style.backgroundColor = "#545454";
        el.style.color = "white";
        el.style.display = "flex";
        el.style.alignItems = "center";
        el.style.flexWrap = "wrap";
        el.style.cursor = "default";
    },
    applyOnDivHeadTitle: (el: HTMLSpanElement) => {
        el.style.flex = "1";
        el.style.fontSize = "16px";
    },
    applyOnDivEdgeIcon: (el: HTMLImageElement) => {
        el.style.width = "15px";
        el.style.height = "15px";
        el.style.margin = "4px";
    },
    applyOnDivBody: (el: HTMLDivElement) => {
        el.style.flex = "1";
        el.style.display = "flex";
        el.style.backgroundColor = "#f1f1f1";
        el.style.minWidth = "10px";
        el.style.minHeight = "10px";
    },
    applyOnIFrameBody: (el: HTMLIFrameElement) => {
        el.style.flex = "1";
        el.style.backgroundColor = "#f1f1f1";
    },
    applyOnStatusBody: (el: HTMLDivElement) => {
        el.style.flex = "1";
        el.style.backgroundColor = "#3b599827";
        el.style.padding = "9px";
        el.style.fontSize = "16px";
        el.style.display = "flex";
        el.style.flexDirection = "column";
        el.style.overflow = "scroll";
    },
    applyOnStatusBodyItem: (el: HTMLDivElement) => {
        el.style.margin = "9px";
        el.style.padding = "9px";
        el.style.borderRadius = "7px";
    },
    applyOnIFrameLoad: (el: HTMLIFrameElement) => {
        const head = el.contentWindow.document.head;
        const defaultCSS = document.createElement("link");
        defaultCSS.id = "QinpelIFrameDefaultCSS";
        defaultCSS.rel = "stylesheet";
        defaultCSS.type = "text/css";
        defaultCSS.href = "/pub/qin_desk/default.css";
        defaultCSS.media = "all";
        head.appendChild(defaultCSS);
    },
    applyOnDivFoot: (el: HTMLDivElement) => {
        el.style.padding = "3px";
        el.style.backgroundColor = "#545454";
        el.style.color = "#cfcfcf";
        el.style.display = "flex";
        el.style.alignItems = "flex-end";
        el.style.flexWrap = "wrap";
        el.style.cursor = "default";
    },
    applyOnStatusText: (el: HTMLDivElement) => {
        el.style.flex = "1";
        el.style.whiteSpace = "nowrap";
        el.style.overflow = "hidden";
        el.style.fontSize = "15px";
    },
};
