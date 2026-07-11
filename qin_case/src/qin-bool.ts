import { Nature } from "qin_soul";
import { QinAsset } from "./qin-assets";
import { QinEdit } from "./qin-edit";
import { QinIcon } from "./qin-icon";

export class QinBool extends QinEdit<boolean> {
    
    private _icon = new QinIcon(QinAsset.FaceCheckRadio);
    private _value = false;
    private _readOnly = false;

    public constructor(options?: QinBooleanSet, isQindred?: string) {
        super(undefined, (isQindred ? isQindred + "_" : "") + "boolean", document.createElement("div"));
        if (options?.initial) {
            this._setData(options.initial);
        }
        if (options?.readOnly) {
            this.turnReadOnly();
        }
        this.addActionMain((_) => this.toggle());
        this._icon.install(this);
    }

    public override castedQine(): HTMLDivElement {
        return this.qinedHTML as HTMLDivElement;
    }

    public override getNature(): Nature {
        return Nature.Bool;
    }

    public override mayChange(): HTMLElement[] {
        return [];
    }

    public override turnReadOnly(): void {
        this._readOnly = true;
        this.styleAsReadOnly();
    }

    public override turnEditable(): void {
        this._readOnly = false;
        this.styleAsEditable();
    }

    public override isEditable(): boolean {
        return !this._readOnly;
    }

    protected override _getData(): boolean {
        return this._value;
    }

    protected override _setData(data: boolean) {
        this._value = data;
        this.updateIcon();
    }

    private updateIcon() {
        if (this._value) {
            this._icon.asset = QinAsset.FaceCheckedRadio;
        } else {
            this._icon.asset = QinAsset.FaceCheckRadio;
        }
    }

    public toggle() {
        this.value = !this.value;
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinBool {
        super.styled(styles);
        return this;
    }
}

export type QinBooleanSet = {
    initial?: boolean;
    readOnly?: boolean;
};
