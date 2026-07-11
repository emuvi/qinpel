import { QinDimension, QinGrandeur } from "qin_soul";
import { QinPanel } from "./qin-panel";

export class QinSpacer extends QinPanel {
    public constructor(space: QinDimension | QinGrandeur, isQindred?: string) {
        super(null, (isQindred ? isQindred + "_" : "") + "spacer");
        this.styleAsSize(space);
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinSpacer {
        super.styled(styles);
        return this;
    }
}
