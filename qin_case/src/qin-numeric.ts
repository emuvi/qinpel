import { Nature } from "qin_soul";
import { QinEditNumber, QinNumberSet } from "./qin-edit-number";

export class QinNumeric extends QinEditNumber {
    public constructor(options?: QinNumberSet, isQindred?: string) {
        super(options, (isQindred ? isQindred + "_" : "") + "numeric");
    }

    public override getNature(): Nature {
        return Nature.Numeric;
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinNumeric {
        super.styled(styles);
        return this;
    }
}
