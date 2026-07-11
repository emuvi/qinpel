import { QinAction, QinConstants, QinSoul, QinWaiter } from "qin_soul";
import { Qinpel } from "./qinpel";

export class QinDesk {
    private _divMain = document.createElement("div");
    private _divApps: HTMLDivElement = null;
    private _divCfgs: HTMLDivElement = null;

    private qinpel: Qinpel;
    private options: QinDeskSet;

    public constructor(qinpel: Qinpel, options?: QinDeskSet) {
        this.qinpel = qinpel;
        this.options = options || {};
        this.initMain();
        if (!(this.options?.shouldShowApps === false)) {
            this.initApps();
        }
        if (!(this.options?.shouldShowCfgs === false)) {
            this.initCfgs();
        }
    }

    private initMain() {
        styles.applyOnDivMain(this._divMain);
    }

    public initApps() {
        this._divApps = document.createElement("div");
        this._divMain.appendChild(this._divApps);
        styles.applyOnDivLine(this._divApps);
        this.qinpel.talk.app
            .list()
            .then((names) => {
                for (let name of names) {
                    this.tryAddApp(name);
                }
            })
            .catch((err) => {
                if (err.response?.status === 403) {
                    this.qinpel.window.exit();
                }
                this.qinpel.frame.statusError(err, "{qin_desk}(ErrCode-000002)");
            });
    }

    private tryAddApp(name: string) {
        if (name && name !== "qin_desk") {
            this.qinpel.talk.app
                .manifest(name)
                .then((manifest) => {
                    if (!shouldAdd(this.options.shouldAddApp, manifest)) {
                        return;
                    }
                    const title = manifest.title;
                    const iconUrl = "/app/" + name + "/favicon.ico";
                    this.addMenu(
                        this._divApps,
                        this.newMenu(title, iconUrl, (ev) => {
                            if (ev.isMain) {
                                this.qinpel.window.newFrame(title, name);
                            }
                        })
                    );
                })
                .catch((err) => {
                    this.qinpel.frame.statusError(err, "{qin_desk}(ErrCode-000001)");
                });
        }
    }

    private initCfgs() {
        this._divCfgs = document.createElement("div");
        this._divMain.appendChild(this._divCfgs);
        styles.applyOnDivLine(this._divCfgs);
        if (shouldAdd(this.options.shouldAddCfg, { title: QinConstants.QIN_BASES })) {
            this.qinpel.talk.bas
            .list()
            .then((bases) => this.addQinBases(bases))
            .catch((err) => this.qinpel.frame.statusError(err, "{qin_desk}(ErrCode-000006)"));
        }
    }

    private addQinBases(bases: string[]) {
        if (!bases || bases.length === 0) {
            return;
        }
        let actual = this.qinpel.window.loadConfig(QinConstants.QIN_BASE_SELECTED);
        if (bases.indexOf(actual) == -1) {
            actual = null;
        }
        if (!actual) {
            actual = bases[0];
            this.qinpel.window.saveConfig(QinConstants.QIN_BASE_SELECTED, actual);
        }
        if (bases.length === 1) {
            return;
        }
        let items = new Array<ComboItem>();
        items.push({
            title: "",
            selected: false,
        });
        for (let base of bases) {
            items.push({
                title: base,
                selected: base === actual,
            });
        }
        this.addMenu(
            this._divCfgs,
            this.newCombo(QinConstants.QIN_BASES, items, (base) => {
                this.qinpel.window.saveConfig(QinConstants.QIN_BASE_SELECTED, base);
            })
        );
    }

    private newMenu(title: string, iconUrl: string, action: QinAction): HTMLDivElement {
        const menuBody = document.createElement("div");
        styles.applyOnMenuBody(menuBody);
        const menuIcon = document.createElement("img");
        styles.applyOnMenuIcon(menuIcon);
        menuIcon.src = iconUrl;
        const menuText = document.createElement("span");
        styles.applyOnMenuText(menuText);
        menuText.innerText = title;
        menuBody.appendChild(menuIcon);
        menuBody.appendChild(menuText);
        QinSoul.arms.addActionMain(menuBody, action);
        return menuBody;
    }

    private newCombo(title: string, items: ComboItem[], action: QinWaiter<string>): HTMLDivElement {
        const menuBody = document.createElement("div");
        styles.applyOnMenuBody(menuBody);
        const menuText = document.createElement("span");
        styles.applyOnMenuText(menuText);
        menuText.innerText = title;
        menuBody.appendChild(menuText);
        const menuCombo = document.createElement("select");
        styles.applyOnMenuCombo(menuCombo);
        for (const item of items) {
            const menuComboItem = document.createElement("option");
            menuComboItem.value = item.title;
            menuComboItem.innerText = item.title;
            menuComboItem.selected = item.selected;
            menuCombo.appendChild(menuComboItem);
        }
        menuBody.appendChild(menuCombo);
        if (action) {
            menuBody.onchange = () => {
                action(menuCombo.value);
            };
        }
        return menuBody;
    }

    private addMenu(divContainer: HTMLDivElement, divContent: HTMLDivElement) {
        const divMenu = document.createElement("div");
        styles.applyOnDivMenu(divMenu);
        divMenu.appendChild(divContent);
        divContainer.appendChild(divMenu);
    }

    public putInDocBody() {
        document.body.appendChild(this._divMain);
    }

    public getMain(): HTMLDivElement {
        return this._divMain;
    }
}

export type QinDeskSet = {
    shouldShowApps?: boolean;
    shouldAddApp?: QinAuthorize;
    shouldShowCfgs?: boolean;
    shouldAddCfg?: QinAuthorize;
};

function shouldAdd(authorizer: QinAuthorize, manifest: QinManifest): boolean {
    if (!authorizer) {
        return true;
    }
    return authorizer(manifest);
}

export type QinAuthorize = (manifest: QinManifest) => boolean;

export type QinManifest = {
    title: string;
    group?: string;
};

type ComboItem = {
    title: string;
    selected: boolean;
};

const styles = {
    applyOnDivMain: (el: HTMLDivElement) => {
        el.style.margin = "18px 3px";
        styles.applyOnDivColumn(el);
    },
    applyOnDivColumn: (el: HTMLDivElement) => {
        el.style.padding = "0px";
        el.style.display = "flex";
        el.style.flexDirection = "column";
        el.style.flexWrap = "nowrap";
    },
    applyOnDivLine: (el: HTMLDivElement) => {
        el.style.padding = "3px";
        el.style.display = "flex";
        el.style.flexDirection = "row";
        el.style.flexWrap = "wrap";
    },
    applyOnDivMenu: (el: HTMLDivElement) => {
        el.style.margin = "3px";
        el.style.minWidth = "96px";
        el.style.maxWidth = "96px";
        el.style.cursor = "pointer";
    },
    applyOnMenuBody: (el: HTMLDivElement) => {
        el.style.display = "flex";
        el.style.flexDirection = "column";
        el.style.alignItems = "center";
    },
    applyOnMenuIcon: (el: HTMLImageElement) => {
        el.style.width = "48px";
        el.style.height = "48px";
        el.style.margin = "3px";
    },
    applyOnMenuCombo: (el: HTMLSelectElement) => {
        QinSoul.skin.styleAsEditable(el);
        el.style.margin = "3px";
    },
    applyOnMenuText: (el: HTMLSpanElement) => {
        el.style.margin = "3px";
        el.style.fontWeight = "bold";
    },
};
