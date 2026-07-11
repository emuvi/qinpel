import { QinFoot } from "qin_soul";
import { QinEdit } from "./qin-edit";

export abstract class QinEditNumber extends QinEdit<number> {
    
    public constructor(options?: QinNumberSet, isQindred?: string) {
        super(options?.specs, (isQindred ? isQindred + "_" : "") + "number", document.createElement("input"));
        this.castedQine().type = "number";
        this.castedQine().style.width = "90px";
        this.castedQine().addEventListener("focusout", () => {
            this._setData(this._getData());
        });
        if (options?.readOnly) {
            this.turnReadOnly();
        }
        if (options?.initial) {
            this._setData(options.initial);
        }
    }

    public override castedQine(): HTMLInputElement {
        return this.qinedHTML as HTMLInputElement;
    }

    public override mayChange(): HTMLElement[] {
        return [this.qinedHTML];
    }

    public override turnReadOnly(): void {
        this.castedQine().readOnly = true;
        this.styleAsReadOnly();
    }

    public override turnEditable(): void {
        this.castedQine().readOnly = false;
        this.styleAsEditable();
    }

    public override isEditable(): boolean {
        return !this.castedQine().readOnly;
    }

    protected override _getData(): number {
        return QinFoot.getValued(this.getNature(), this.castedQine().value, this.specs);
    }

    protected override _setData(data: number) {
        if (data == null || data == undefined) {
            this.castedQine().value = "";
        } else {
            this.castedQine().value = data.toString();
        }
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinEditNumber {
        super.styled(styles);
        return this;
    }
}

export type QinNumberSet = {
    initial?: number;
    readOnly?: boolean;
    specs?: {
        precision?: number;
    }
};
