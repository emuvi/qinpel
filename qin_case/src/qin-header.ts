import { QinBase } from "./qin-base";

export class QinHeader extends QinBase {
    public constructor(level: number, title?: string, isQindred?: string) {
        super((isQindred ? isQindred + "_" : "") + "label", document.createElement("h" + level));
        if (title) {
            this.qinedHTML.textContent = title;
        }
    }

    public override castedQine(): HTMLHeadingElement {
        return this.qinedHTML as HTMLHeadingElement;
    }

    public get title(): string | null {
        return this.qinedHTML.textContent;
    }

    public set title(title: string | null) {
        this.qinedHTML.textContent = title;
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinHeader {
        super.styled(styles);
        return this;
    }
}
