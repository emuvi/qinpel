import { Nature } from "qin_soul";
import { QinEditNumber, QinNumberSet } from "./qin-edit-number";

export class QinBigSerial extends QinEditNumber {
    public constructor(options?: QinNumberSet, isQindred?: string) {
        super(options, (isQindred ? isQindred + "_" : "") + "big-serial");
    }

    public override getNature(): Nature {
        return Nature.BigSerial;
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinBigSerial {
        super.styled(styles);
        return this;
    }
}
