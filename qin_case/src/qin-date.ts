import { Nature } from "qin_soul";
import { QinEdit } from "./qin-edit";

export class QinDate extends QinEdit<Date> {
    public constructor(options?: QinDateSet, isQindred?: string) {
        super(undefined, (isQindred ? isQindred + "_" : "") + "date", document.createElement("input"));
        this.castedQine().type = "date";
        if (options?.initial) {
            this._setData(options.initial);
        }
        if (options?.readOnly) {
            this.turnReadOnly();
        }
    }

    public override castedQine(): HTMLInputElement {
        return this.qinedHTML as HTMLInputElement;
    }

    public override getNature(): Nature {
        return Nature.Date;
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

    protected override _getData(): Date | null {
        if (!this.castedQine().value) {
            return null;
        }
        return new Date(this.castedQine().value);
    }

    protected override _setData(data: Date | null) {
        if (data == null || data == undefined) {
            this.castedQine().value = "";
        }
        else {
            this.castedQine().value = data.toString();
        }
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinDate {
        super.styled(styles);
        return this;
    }
}

export type QinDateSet = {
    initial?: Date;
    readOnly?: boolean;
};
