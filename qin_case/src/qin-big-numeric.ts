import { Nature } from "qin_soul";
import { QinEditNumber, QinNumberSet } from "./qin-edit-number";

export class QinBigNumeric extends QinEditNumber {
    public constructor(options?: QinNumberSet, isQindred?: string) {
        super(options, (isQindred ? isQindred + "_" : "") + "big-numeric");
    }

    public override getNature(): Nature {
        return Nature.BigNumeric;
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinBigNumeric {
        super.styled(styles);
        return this;
    }
}
