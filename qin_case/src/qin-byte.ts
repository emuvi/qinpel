import { Nature } from "qin_soul";
import { QinEditNumber, QinNumberSet } from "./qin-edit-number";

export class QinByte extends QinEditNumber {
    public constructor(options?: QinNumberSet, isQindred?: string) {
        super(options, (isQindred ? isQindred + "_" : "") + "byte");
    }

    public override getNature(): Nature {
        return Nature.Byte;
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinByte {
        super.styled(styles);
        return this;
    }
}
