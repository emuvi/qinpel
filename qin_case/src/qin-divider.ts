import { QinBase } from "./qin-base";

export class QinDivider extends QinBase {
    private _isHorizontal = true;

    public constructor(options?: QinDividerSet, isQindred?: string) {
        super((isQindred ? isQindred + "_" : "") + "divider", document.createElement("div"));
        if (options?.horizontal) {
            this.setHorizontal();
        } else {
            this.setVertical();
        }
    }

    public override castedQine(): HTMLDivElement {
        return this.qinedHTML as HTMLDivElement;
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinDivider {
        super.styled(styles);
        return this;
    }

    public setHorizontal() {
        this.qinedHTML.style.minWidth = "initial";
        this.qinedHTML.style.maxWidth = "initial";
        this.qinedHTML.style.minHeight = "6px";
        this.qinedHTML.style.maxHeight = "6px";
        this.qinedHTML.style.height = "6px";
        this.qinedHTML.style.background = "linear-gradient(180deg, rgba(255,250,239,0.1) 0%, rgba(255,250,239,0.1) 26%, rgba(24,0,39,0.8) 42%, rgba(24,0,39,0.8) 58%, rgba(255,250,239,0.1) 74%, rgba(255,250,239,0.1) 100%)";
        this._isHorizontal = true;
    }

    public setVertical() {
        this.qinedHTML.style.flexDirection = "row";
        this.qinedHTML.style.minWidth = "6px";
        this.qinedHTML.style.maxWidth = "6px";
        this.qinedHTML.style.minHeight = "initial";
        this.qinedHTML.style.maxHeight = "initial";
        this.qinedHTML.style.width = "6px";
        this.qinedHTML.style.background = "linear-gradient(90deg, rgba(255,250,239,0.1) 0%, rgba(255,250,239,0.1) 26%, rgba(24,0,39,0.8) 42%, rgba(24,0,39,0.8) 58%, rgba(255,250,239,0.1) 74%, rgba(255,250,239,0.1) 100%)";
        this._isHorizontal = false;
    }

    public get isHorizontal(): boolean {
        return this._isHorizontal;
    }
}

export type QinDividerSet = {
    horizontal?: boolean;
};
