import { QinHead } from "qin_soul";
import { QinBase } from "./qin-base";

export class QinLabel extends QinBase {
    
    public constructor(title?: string, isQindred?: string) {
        super((isQindred ? isQindred + "_" : "") + "label", document.createElement("label"));
        if (title) {
            this.qinedHTML.textContent = QinHead.tr(title);
        }
    }

    public override castedQine(): HTMLLabelElement {
        return this.qinedHTML as HTMLLabelElement;
    }

    public get title(): string {
        return this.qinedHTML.textContent;
    }

    public set title(title: string) {
        this.qinedHTML.textContent = QinHead.tr(title);
    }

    public get link(): string {
        return this.qinedHTML.getAttribute("for");
    }

    public set link(name: string) {
        this.qinedHTML.setAttribute("for", name);
    }

    public qinLink(qinComp: QinBase) {
        this.link = qinComp.mustId();
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinLabel {
        super.styled(styles);
        return this;
    }

}
