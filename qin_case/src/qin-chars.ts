import { Nature, QinSkin } from "qin_soul";
import { QinEdit } from "./qin-edit";

export class QinChars extends QinEdit<string> {
    
    public constructor(options?: QinCharsSet, isQindred?: string) {
        super(undefined, (isQindred ? isQindred + "_" : "") + "string", document.createElement("input"));
        this.castedQine().type = "text";
        this.castedQine().value = "";
        if (options?.maxLength) {
            this.qinedHTML.style.width = QinSkin.getWidthByMaxLength(options?.maxLength);
        }
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
        if (data === null || data === undefined) {
            this.castedQine().value = "";
        } else {
            this.castedQine().value = data;
        }
    }

    public insertAtCursor(data: string) {
        if (!data) return;
        let startPos = this.castedQine().selectionStart;
        let endPos = this.castedQine().selectionEnd;
        let oldVal = this.castedQine().value;
        let newVal =
            (startPos > 0 ? oldVal.substring(0, startPos) : "") +
            data +
            (endPos < oldVal.length ? oldVal.substring(endPos) : "");
        this.value = newVal;
        this.castedQine().selectionStart = startPos;
        this.castedQine().selectionEnd = startPos + data.length;
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinChars {
        super.styled(styles);
        return this;
    }
    
}

export type QinCharsSet = {
    initial?: string;
    maxLength?: number;
    readOnly?: boolean;
};
