import { Qinpel } from "qin_desk/types/qinpel";
import { QinAction, QinArms, QinBody, QinSkin } from "qin_soul";
import { QinBaseStyle } from "./qin-base-style";
import { Qine } from "./qin-tools";

export abstract class QinBase extends QinBaseStyle {
    
    private _qindred: string;
    private _qined: HTMLElement | QinBase;
    private _bodyBase: QinBase = null;

    public constructor(qindred: string, qined: HTMLElement | QinBase) {
        super(qined);
        this._qindred = qindred;
        if (qined instanceof QinBase) {
            qined.qinedHTML.id = qindred + "_" + qined.qinedHTML.id;
        } else {
            qined.id = "base_" + QinBody.makeQindredUID(qindred);
        }
        this._qined = qined;
        this.styleAsBase();
    }

    public abstract castedQine(): HTMLElement | QinBase;

    public get qinpel(): Qinpel {
        return Qine.qinpel;
    }

    public get qinedHTML(): HTMLElement {
        if (this._qined instanceof QinBase) {
            return this._qined.qinedHTML;
        } else {
            return this._qined;
        }
    }

    public get qinedBase(): QinBase {
        if (this._qined instanceof QinBase) {
            return this._qined;
        } else {
            return null;
        }
    }

    public get qindred(): string {
        return this._qindred;
    }

    public get bodyBase(): QinBase {
        return this._bodyBase;
    }

    public set bodyBase(base: QinBase) {
        this._bodyBase = base;
    }

    public get id(): string {
        return this.qinedHTML.id;
    }

    public set id(id: string) {
        this.qinedHTML.id = id;
    }

    public get tabIndex(): number {
        return this.qinedHTML.tabIndex;
    }

    public set tabIndex(index: number) {
        this.qinedHTML.tabIndex = index;
    }

    public mustId(): string {
        let result = this.id;
        if (!result) {
            result = QinBody.makeQinUID();
            this.id = result;
        }
        return result;
    }

    protected _baseParent: QinBase = null;
    protected _pastParent: QinBase = null;
    protected _baseChildren: QinBase[] = [];
    protected _baseDisplay: string = null;
    protected _baseVisibility: string = null;

    public put(item: QinBase): QinBase {
        item.install(this);
        return this;
    }

    public install(onBase: QinBase): QinBase {
        this.unInstall();
        this._baseParent = onBase;
        this._baseParent.addChild(this);
        return this;
    }

    public unInstall(): QinBase {
        if (this._baseParent != null) {
            this._baseParent.delChild(this);
            this._pastParent = this._baseParent;
            this._baseParent = null;
        }
        return this;
    }

    public reInstall(): QinBase {
        this.unInstall();
        if (this._pastParent != null) {
            this._pastParent.addChild(this);
            this._baseParent = this._pastParent;
        }
        return this;
    }

    public unInstallChildren(): QinBase {
        for (let i = this._baseChildren.length - 1; i >= 0; i--) {
            this._baseChildren[i].unInstall();
        }
        return this;
    }

    public unDisplay(): QinBase {
        if (this.qinedHTML.style.display !== "none") {
            this._baseDisplay = this.qinedHTML.style.display;
            this.qinedHTML.style.display = "none";
        }
        return this;
    }

    public reDisplay(): QinBase {
        if (this._baseDisplay != null) {
            this.qinedHTML.style.display = this._baseDisplay;
        }
        return this;
    }

    public unVisible(): QinBase {
        if (this.qinedHTML.style.display !== "hidden") {
            this._baseVisibility = this.qinedHTML.style.visibility;
            this.qinedHTML.style.visibility = "hidden";
        }
        return this;
    }

    public reVisible(): QinBase {
        if (this._baseVisibility != null) {
            this.qinedHTML.style.visibility = this._baseVisibility;
        }
        return this;
    }

    public get baseChildren(): QinBase[] {
        return this._baseChildren;
    }

    public addChild(child: QinBase): QinBase {
        if (this._bodyBase) {
            this._bodyBase.addChild(child);
        } else {
            this._baseChildren.push(child);
            this.qinedHTML.appendChild(child.qinedHTML);
        }
        return this;
    }

    public delChild(child: QinBase): QinBase {
        if (this._bodyBase) {
            this._bodyBase.addChild(child);
        } else {
            let index = this._baseChildren.indexOf(child);
            if (index > -1) {
                this._baseChildren.splice(index, 1);
            }
            this.qinedHTML.removeChild(child.qinedHTML);
        }
        return this;
    }

    public styled(styles: Partial<CSSStyleDeclaration>): QinBase {
        QinSkin.applyStyles(this.qinedHTML, styles);
        return this;
    }

    public focus(): QinBase {
        this.qinedHTML.focus();
        return this;
    }

    public hasFocus(): boolean {
        return this.qinedHTML == document.activeElement;
    }

    public addAction(action: QinAction): QinBase {
        QinArms.addAction(this.qinedHTML, action);
        return this;
    }

    public addActionMain(action: QinAction): QinBase {
        QinArms.addActionMain(this.qinedHTML, action);
        return this;
    }

    public addActionMainKey(action: QinAction): QinBase {
        QinArms.addActionMainKey(this.qinedHTML, action);
        return this;
    }

    public addActionMainMouse(action: QinAction): QinBase {
        QinArms.addActionMainMouse(this.qinedHTML, action);
        return this;
    }

    public addActionMainTouch(action: QinAction): QinBase {
        QinArms.addActionMainTouch(this.qinedHTML, action);
        return this;
    }

    public addActionMainPoint(action: QinAction): QinBase {
        QinArms.addActionMainPoint(this.qinedHTML, action);
        return this;
    }

    public addActionMidi(action: QinAction): QinBase {
        QinArms.addActionMidi(this.qinedHTML, action);
        return this;
    }

    public addActionMidiKey(action: QinAction): QinBase {
        QinArms.addActionMidiKey(this.qinedHTML, action);
        return this;
    }

    public addActionMidiMouse(action: QinAction): QinBase {
        QinArms.addActionMidiMouse(this.qinedHTML, action);
        return this;
    }

    public addActionMidiTouch(action: QinAction): QinBase {
        QinArms.addActionMidiTouch(this.qinedHTML, action);
        return this;
    }

    public addActionMidiPoint(action: QinAction): QinBase {
        QinArms.addActionMidiPoint(this.qinedHTML, action);
        return this;
    }

    public addActionMenu(action: QinAction): QinBase {
        QinArms.addActionMenu(this.qinedHTML, action);
        return this;
    }

    public addActionMenuKey(action: QinAction): QinBase {
        QinArms.addActionMenuKey(this.qinedHTML, action);
        return this;
    }

    public addActionMenuMouse(action: QinAction): QinBase {
        QinArms.addActionMenuMouse(this.qinedHTML, action);
        return this;
    }

    public addActionMenuTouch(action: QinAction): QinBase {
        QinArms.addActionMenuTouch(this.qinedHTML, action);
        return this;
    }

    public addActionMenuPoint(action: QinAction): QinBase {
        QinArms.addActionMenuPoint(this.qinedHTML, action);
        return this;
    }

    public addActionKey(keyList: string[], action: QinAction): QinBase {
        QinArms.addActionKey(keyList, this.qinedHTML, action);
        return this;
    }
}
