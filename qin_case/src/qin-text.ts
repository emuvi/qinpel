import { Nature, QinSkin, QinSoul } from "qin_soul";
import { QinEdit } from "./qin-edit";

export class QinText extends QinEdit<string> {
    public constructor(options?: QinTextSet, isQindred?: string) {
        super(undefined, (isQindred ? isQindred + "_" : "") + "string", document.createElement("textarea"));
        if (options?.maxLength) {
            this.qinedHTML.style.width = QinSkin.getWidthByMaxLength(options?.maxLength);
        }
        if (options?.rows) {
            this.castedQine().rows = options?.rows;
        }
        if (options?.cols) {
            this.castedQine().cols = options?.cols;
        }
        if (options?.initial) {
            this._setData(options.initial);
        }
        if (options?.readOnly) {
            this.turnReadOnly();
        }
    }

    public override castedQine(): HTMLTextAreaElement {
        return this.qinedHTML as HTMLTextAreaElement;
    }

    public override getNature(): Nature {
        return Nature.Text;
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
        return this.castedQine().value;
    }

    protected override _setData(data: string) {
        this.castedQine().value = data;
    }

    public clear(): void {
        this.castedQine().value = "";
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

    public appendLine(line: string) {
        let buffer = this.castedQine().value;
        if (buffer) {
            buffer += "\n";
        }
        buffer += line;
        this.castedQine().value = buffer;
    }

    public getLines(): string[] {
        return QinSoul.body.getTextLines(this.castedQine().value);
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinText {
        super.styled(styles);
        return this;
    }
}

export type QinTextSet = {
    initial?: string;
    maxLength?: number;
    readOnly?: boolean;
    rows?: number;
    cols?: number;
};
