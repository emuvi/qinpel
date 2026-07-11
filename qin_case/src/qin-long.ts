import { Nature } from "qin_soul";
import { QinEditNumber, QinNumberSet } from "./qin-edit-number";

export class QinLong extends QinEditNumber {
    public constructor(options?: QinNumberSet, isQindred?: string) {
        super(options, (isQindred ? isQindred + "_" : "") + "long");
    }

    public override getNature(): Nature {
        return Nature.Long;
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinLong {
        super.styled(styles);
        return this;
    }
}
