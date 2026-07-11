import { Nature } from "qin_soul";
import { QinEditNumber, QinNumberSet } from "./qin-edit-number";

export class QinReal extends QinEditNumber {
    public constructor(options?: QinNumberSet, isQindred?: string) {
        super(options, (isQindred ? isQindred + "_" : "") + "real");
    }

    public override getNature(): Nature {
        return Nature.Real;
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinReal {
        super.styled(styles);
        return this;
    }
}
