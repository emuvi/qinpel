import { Nature } from "qin_soul";
import { QinEditNumber, QinNumberSet } from "./qin-edit-number";

export class QinDouble extends QinEditNumber {
    public constructor(options?: QinNumberSet, isQindred?: string) {
        super(options, (isQindred ? isQindred + "_" : "") + "double");
    }

    public override getNature(): Nature {
        return Nature.Double;
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinDouble {
        super.styled(styles);
        return this;
    }
}
