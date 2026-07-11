import { Nature } from "qin_soul";
import { QinEditNumber, QinNumberSet } from "./qin-edit-number";

export class QinSerial extends QinEditNumber {
    public constructor(options?: QinNumberSet, isQindred?: string) {
        super(options, (isQindred ? isQindred + "_" : "") + "serial");
    }

    public override getNature(): Nature {
        return Nature.Serial;
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinSerial {
        super.styled(styles);
        return this;
    }
}
