import { QinBase } from "./qin-base";
import { QinPanel } from "./qin-panel";

export class QinScroll extends QinPanel {
    public constructor(child?: QinBase, isQindred?: string) {
        super(child ? { items: [child] } : undefined, (isQindred ? isQindred + "_" : "") + "scroll");
        this.styleAsScroll();
    }

    public hasScroll() {
        return this.qinedHTML.scrollHeight > this.qinedHTML.clientHeight;
    }

    public get scrollTop() {
        return this.qinedHTML.scrollTop;
    }

    public get scrollLeft() {
        return this.qinedHTML.scrollLeft;
    }

    public get scrollHeight() {
        return this.qinedHTML.scrollHeight;
    }

    public get scrollWidth() {
        return this.qinedHTML.scrollWidth;
    }

    public override put(item: QinBase): QinScroll {
        item.install(this);
        return this;
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinScroll {
        super.styled(styles);
        return this;
    }
}
