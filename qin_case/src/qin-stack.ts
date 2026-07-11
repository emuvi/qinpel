import { QinBase } from "./qin-base";
import { QinPanel, QinPanelSet } from "./qin-panel";

export class QinStack extends QinPanel {
    public constructor(options?: QinPanelSet, isQindred?: string) {
        super(options, (isQindred ? isQindred + "_" : "") + "stack");
    }

    public stack(child: QinBase): QinStack {
        return this.put(child);
    }

    public show(child: QinBase) {
        this.baseChildren.forEach((inChild) => {
            if (inChild === child) {
                inChild.reDisplay();
            } else {
                inChild.unDisplay();
            }
        });
    }

    public override put(item: QinBase): QinStack {
        item.install(this);
        return this;
    }

    public override addChild(child: QinBase): QinStack {
        this.baseChildren.forEach((inChild) => {
            inChild.unDisplay();
        });
        super.addChild(child);
        return this;
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinStack {
        super.styled(styles);
        return this;
    }
}
