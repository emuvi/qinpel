import { Nature } from "qin_soul";
import { QinEditNumber, QinNumberSet } from "./qin-edit-number";

export class QinInt extends QinEditNumber {
    public constructor(options?: QinNumberSet, isQindred?: string) {
        super(options, (isQindred ? isQindred + "_" : "") + "int");
    }

    public override getNature(): Nature {
        return Nature.Int;
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinInt {
        super.styled(styles);
        return this;
    }
}
