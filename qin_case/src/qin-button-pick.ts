import { QinActionableStyles, QinSkin, QinStylesPicker } from "qin_soul";
import { QinBase } from "./qin-base";
import { QinButtonSet } from "./qin-button";
import { QinIcon } from "./qin-icon";
import { QinLabel } from "./qin-label";

export class QinButtonPick extends QinBase {
    private _qinIcon: QinIcon = null;
    private _qinLabel: QinLabel = null;
    private _styles: QinActionableStyles;
    private _picked: boolean = false;

    public constructor(options?: QinButtonSet, isQindred?: string) {
        super(
            (isQindred ? isQindred + "_" : "") + "button-pick",
            document.createElement("button")
        );
        this._styles = {
            ColorForeground: QinStylesPicker.ColorPickerForeground,
            ColorAccentAct: QinStylesPicker.ColorPickerAccentAct,
            ColorInactiveAct: QinStylesPicker.ColorUnPickedInactiveAct,
            ColorActiveAct: QinStylesPicker.ColorUnPickedActiveAct,
        };
        styles.applyOnButton(this.qinedHTML, this._styles);
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

    public override styled(styles: Partial<CSSStyleDeclaration>): QinButtonPick {
        super.styled(styles);
        return this;
    }

    public get qinIcon(): QinIcon {
        return this._qinIcon;
    }

    public get qinLabel(): QinLabel {
        return this._qinLabel;
    }

    public toggle() {
        this._picked = !this._picked;
        this.updateStyles();
    }

    public pick() {
        this._picked = true;
        this.updateStyles();
    }

    public unPick() {
        this._picked = false;
        this.updateStyles();
    }

    private updateStyles() {
        this._styles.ColorInactiveAct = this._picked
            ? QinStylesPicker.ColorPickedInactiveAct
            : QinStylesPicker.ColorUnPickedInactiveAct;
        this._styles.ColorActiveAct = this._picked
            ? QinStylesPicker.ColorPickedActiveAct
            : QinStylesPicker.ColorUnPickedActiveAct;
        if (this.hasFocus()) {
            this.qinedHTML.style.backgroundColor = this._styles.ColorActiveAct;
        } else {
            this.qinedHTML.style.backgroundColor = this._styles.ColorInactiveAct;
        }
    }

    public isPicked(): boolean {
        return this._picked;
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
}

const styles = {
    applyOnButton: (el: HTMLElement, styles: QinActionableStyles) => {
        QinSkin.styleAsActionable(el, styles);
        el.style.display = "flex";
        el.style.flexDirection = "row";
        el.style.alignItems = "center";
        el.style.justifyContent = "center";
    },
};
