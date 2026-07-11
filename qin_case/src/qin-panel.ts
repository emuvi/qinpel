import { QinBase } from "./qin-base";

export class QinPanel extends QinBase {
    public constructor(options?: QinPanelSet, isQindred?: string) {
        super((isQindred ? isQindred + "_" : "") + "panel", document.createElement("div"));
        if (options?.items) {
            for (const item of options.items) {
                item.install(this);
            }
        }
        this.styleAsPositionRelative();
        this.styleAsDisplayFlex();
        this.styleAsFlexDirectionRow();
        this.styleAsFlexWrapNot();
    }

    public override castedQine(): HTMLDivElement {
        return this.qinedHTML as HTMLDivElement;
    }

    public override put(item: QinBase): QinPanel {
        item.install(this);
        return this;
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinPanel {
        super.styled(styles);
        return this;
    }
}

export type QinPanelSet = {
    items?: QinBase[];
};
