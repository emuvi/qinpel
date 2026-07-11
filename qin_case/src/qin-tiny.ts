import { Nature } from "qin_soul";
import { QinEditNumber, QinNumberSet } from "./qin-edit-number";

export class QinTiny extends QinEditNumber {
    public constructor(options?: QinNumberSet, isQindred?: string) {
        super(options, (isQindred ? isQindred + "_" : "") + "tiny");
    }

    public override getNature(): Nature {
        return Nature.Tiny;
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinTiny {
        super.styled(styles);
        return this;
    }
}
