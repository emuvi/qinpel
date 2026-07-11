import { QinBase } from "./qin-base";
import { QinPanel, QinPanelSet } from "./qin-panel";

export class QinColumn extends QinPanel {
    public constructor(options?: QinPanelSet, isQindred?: string) {
        super(options, (isQindred ? isQindred + "_" : "") + "column");
        this.styleAsFlexDirectionColumn();
        this.styleAsAlignItemsStretch();
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinColumn {
        super.styled(styles);
        return this;
    }

    public override put(item: QinBase): QinColumn {
        item.install(this);
        return this;
    }
}
