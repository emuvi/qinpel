import { Nature } from "qin_soul";
import { QinEditNumber, QinNumberSet } from "./qin-edit-number";

export class QinFloat extends QinEditNumber {
    public constructor(options?: QinNumberSet, isQindred?: string) {
        super(options, (isQindred ? isQindred + "_" : "") + "float");
    }

    public override getNature(): Nature {
        return Nature.Float;
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinFloat {
        super.styled(styles);
        return this;
    }
}
