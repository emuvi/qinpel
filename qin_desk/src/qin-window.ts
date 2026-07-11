import { QinArms, QinConstants, QinSkin, QinSoul } from "qin_soul";
import { QinDesk, QinDeskSet } from "./qin-desk";
import { QinFrame } from "./qin-frame";
import { QinTalker } from "./qin-talker";
import { Qinpel } from "./qinpel";

import { TranslationsPtBR } from "./dics/pt-BR";

export class QinWindow {
    private _divBody = document.createElement("div");
    private _divMenu = document.createElement("div");
    private _imgMenu = document.createElement("img");
    private _frames: QinFrame[] = [];
    private _framesTopZ = 1;

    private _serverLang = null;
    private _userLang = null;
    private _userToken = null;

    private _talker = new QinTalker(this);

    private _menuFrame: QinFrame = null;

    public constructor() {
        this.initBody();
        this.initMenu();
        this.initScroll();
        this.initServerLang();
    }

    private initBody() {
        this._divBody.className = "QinpelWindowBody";
        this._divBody.style.backgroundImage = "url('./assets/background-normal.png')";
        this._divBody.style.backgroundAttachment = "local";
        this._divBody.style.fontWeight = "bold";
        this._divBody.style.fontSize = "12px";
        this._divBody.style.position = "relative";
        this._divBody.style.width = "100%";
        this._divBody.style.height = "100vh";
        this._divBody.style.overflow = "scroll";
        this._divBody.style.touchAction = "none";
    }

    private initMenu() {
        this._divMenu.id = "QinpelMenuID0";
        this._divMenu.style.backgroundColor = "#180027";
        this._divMenu.style.border = "2px solid #180027";
        this._divMenu.style.borderRadius = "4px";
        this._divMenu.style.position = "absolute";
        this._divMenu.style.overflow = "hidden";
        this._divMenu.style.top = "18px";
        this._divMenu.style.left = "18px";
        this._divMenu.style.width = "48px";
        this._divMenu.style.height = "48px";
        this._imgMenu.src = "./assets/qinpel.png";
        this._imgMenu.style.width = "48px";
        this._imgMenu.style.height = "48px";
        this._imgMenu.alt = "Menu";
        this._divMenu.appendChild(this._imgMenu);
        this._divBody.appendChild(this._divMenu);
        QinArms.addActionMain(this._divMenu, (ev) => {
            if (ev.hasShift) {
                document.body.requestFullscreen();
            } else if (this._menuFrame == null || this._menuFrame.wasClosed) {
                this._menuFrame = this.newFrame("Qinpel", "/pub/qin_desk/desk.html");
            } else {
                this._menuFrame.show();
            }
        });
    }

    private initScroll() {
        QinArms.addScroller(this._divBody, {
            onEnd: () => QinSkin.clearSelection()
        });
    }

    private initServerLang() {
        this.talk.utl
            .getLang()
            .then((res) => {
                this._serverLang = res;
                this.loadTranslations();
            })
            .catch((err) => {
                console.log("Could not get the server language. Error: " + err);
            });
    }

    public putInDocument() {
        document.body.appendChild(this._divBody);
        QinSoul.skin.disableSelection(document.body);
    }

    public addChild(child: HTMLElement) {
        this._divBody.appendChild(child);
    }

    public delChild(child: HTMLElement) {
        this._divBody.removeChild(child);
    }

    public hasChild(child: HTMLElement) {
        return this._divBody.contains(child);
    }

    public newDesk(qinpel: Qinpel, options?: QinDeskSet): QinDesk {
        return new QinDesk(qinpel, options);
    }

    public newFrame(title: string, appNameOrAddress: string, options?: any): QinFrame {
        let result = new QinFrame(this, title, appNameOrAddress, options);
        result.install();
        this._frames.push(result);
        return result;
    }

    public getFrame(withTitle: string): QinFrame {
        for (const frame of this._frames) {
            if (frame.title === withTitle) {
                return frame;
            }
        }
        return null;
    }

    public getFrameFromID(fromID: string): QinFrame {
        for (const jobber of this._frames) {
            if (jobber.getFrameID() === fromID) {
                return jobber;
            }
        }
        return null;
    }

    public getFrameIndexFromID(fromID: string): number {
        for (let i = 0; i < this._frames.length; i++) {
            if (this._frames[i].getFrameID() === fromID) {
                return i;
            }
        }
        return -1;
    }

    public delFrame(frame: QinFrame) {
        const index = this._frames.indexOf(frame);
        if (index > -1) {
            this._frames.splice(index, 1);
        }
    }

    public hasFrame(frame: QinFrame) {
        const index = this._frames.indexOf(frame);
        return index > -1;
    }

    public showElement(element: HTMLElement) {
        setTimeout(() => {
            element.style.zIndex = String(++this._framesTopZ);
            if (!QinSoul.skin.isElementVisibleInScroll(element)) {
                element.parentElement.scrollTo(element.offsetLeft, element.offsetTop);
            }
            if (element.id.indexOf("QinpelFrameID") === 0) {
                const index = this.getFrameIndexFromID(element.id);
                if (index > 0) {
                    const jobber = this._frames[index];
                    this._frames.splice(index, 1);
                    this._frames.unshift(jobber);
                }
            }
        }, 360);
    }

    public showMenu() {
        this._divBody.scrollTo(0, 0);
        this.showElement(this._divMenu);
    }

    public getBodyWidth() {
        return this._divBody.clientWidth;
    }

    public getBodyHeight() {
        return this._divBody.clientHeight;
    }

    public hasToken() {
        return !!this._userToken;
    }

    public async needToEnter(): Promise<boolean> {
        return new Promise<boolean>((resolve) => {
            this.talk.utl
                .isLogged()
                .then((res) => {
                    resolve(!res);
                })
                .catch((_) => {
                    resolve(true);
                });
        });
    }

    public getAxiosConfig(headers: any) {
        if (!headers) {
            headers = {};
        }
        headers["Qinpel-Token"] = this._userToken;
        headers["Accept-Language"] = this.getLang();
        let configs = {
            headers,
        };
        return configs;
    }

    public saveConfig(key: string, value: string) {
        window.localStorage.setItem(key, value);
    }

    public loadConfig(key: string, orDefault?: string): string {
        return window.localStorage.getItem(key) || orDefault;
    }

    public get talk(): QinTalker {
        return this._talker;
    }

    public tryEnter(name: string, pass: string): Promise<string> {
        return new Promise((resolve, reject) => {
            this.talk.utl
                .tryEnter({ name, pass })
                .then((res) => {
                    this._userLang = res.lang;
                    this._userToken = res.token;
                    this.loadTranslations();
                    resolve(this._userLang);
                })
                .catch((err) => reject(err));
        });
    }

    public getLang(): string {
        if (this._userLang) {
            return this._userLang;
        } else if (this._serverLang) {
            return this._serverLang;
        } else {
            return navigator.language;
        }
    }

    public loadTranslations() {
        const lang = this.getLang();
        if (lang == QinConstants.LANG_PT_BR) {
            QinSoul.head.loadDictionary(TranslationsPtBR);
        }
    }

    public exit() {
        this._userLang = "";
        this._userToken = "";
        QinSoul.head.delCookie("Qinpel-Lang");
        QinSoul.head.delCookie("Qinpel-Token");
    }
}
