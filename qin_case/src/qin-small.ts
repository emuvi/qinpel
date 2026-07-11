import { Nature } from "qin_soul";
import { QinEditNumber, QinNumberSet } from "./qin-edit-number";

export class QinSmall extends QinEditNumber {
    public constructor(options?: QinNumberSet, isQindred?: string) {
        super(options, (isQindred ? isQindred + "_" : "") + "small");
    }

    public override getNature(): Nature {
        return Nature.Small;
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinSmall {
        super.styled(styles);
        return this;
    }
}
