import { Nature, QinSkin } from "qin_soul";
import { QinEdit } from "./qin-edit";

export class QinPassword extends QinEdit<string> {
    public constructor(options?: QinPasswordSet, isQindred?: string) {
        super(undefined, (isQindred ? isQindred + "_" : "") + "string", document.createElement("input"));
        this.castedQine().type = "password";
        this.castedQine().value = "";
        if (options?.maxLength) {
            this.qinedHTML.style.width = QinSkin.getWidthByMaxLength(options?.maxLength);
        }
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
        return Nature.Chars;
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

    protected override _getData(): string {
        let value = this.castedQine().value;
        if (value === null || value === undefined) {
            value = "";
        }
        return value;
    }

    protected override _setData(data: string) {
        this.castedQine().value = data;
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinPassword {
        super.styled(styles);
        return this;
    }
}

export type QinPasswordSet = {
    initial?: string;
    maxLength?: number;
    readOnly?: boolean;
};
