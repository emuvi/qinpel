import { QinSkin } from "qin_soul";
import { QinBase } from "./qin-base";
import { QinIcon } from "./qin-icon";
import { QinLabel } from "./qin-label";

export class QinButton extends QinBase {
    
    private _qinIcon: QinIcon = null;
    private _qinLabel: QinLabel = null;

    public constructor(options?: QinButtonSet, isQindred?: string) {
        super((isQindred ? isQindred + "_" : "") + "button", document.createElement("button"));
        styles.applyOnButton(this.qinedHTML);
        if (options?.icon) {
            this._qinIcon = options.icon;
            this._qinIcon.install(this);
        }
        if (options?.label) {
            this._qinLabel = options.label;
            this._qinLabel.install(this);
        }
    }

    public override castedQine(): HTMLButtonElement {
        return this.qinedHTML as HTMLButtonElement;
    }

    public get qinIcon(): QinIcon {
        return this._qinIcon;
    }

    public get qinLabel(): QinLabel {
        return this._qinLabel;
    }

    public putAsRow() {
        this.styleAsFlexDirectionRow();
    }

    public putAsRowReverse() {
        this.styleAsFlexDirectionRowReverse();
    }

    public putAsColumn() {
        this.styleAsFlexDirectionColumn();
    }

    public putAsColumnReverse() {
        this.styleAsFlexDirectionColumnReverse();
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinButton {
        super.styled(styles);
        return this;
    }
    
}

export type QinButtonSet = {
    icon?: QinIcon;
    label?: QinLabel;
};

const styles = {
    applyOnButton: (el: HTMLElement) => {
        QinSkin.styleAsActionable(el);
        el.style.display = "inline-flex";
        el.style.flexDirection = "row";
        el.style.alignItems = "center";
        el.style.justifyContent = "center";
    },
};
