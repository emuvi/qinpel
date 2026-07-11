import { Nature } from "qin_soul";
import { QinAsset } from "./qin-assets";
import { QinEdit } from "./qin-edit";
import { QinIcon } from "./qin-icon";
import { QinIconCell } from "./qin-icon-cell";
import { QinLine } from "./qin-line";

export class QinIconPick extends QinEdit<QinAsset> {
    
    private _readOnly = false;

    public constructor(options?: QinIconPickSet, isQindred?: string) {
        super(undefined, (isQindred ? isQindred + "_" : "") + "icon-pick", new QinLine());
        if (options?.icons) {
            for (const icon of options.icons) {
                this.addIcon(icon);
            }
        }
        if (options?.cells) {
            for (const cell of options.cells) {
                this.addCell(cell);
            }
        }
        if (options?.readOnly) {
            this.turnReadOnly();
        }
        if (options?.initial) {
            this._setData(options?.initial);
        }
    }

    public override castedQine(): QinLine {
        return this.qinedBase as QinLine;
    }

    public override getNature(): Nature {
        return Nature.Chars;
    }

    public override mayChange(): HTMLElement[] {
        return [];
    }

    public override turnReadOnly(): void {
        this._readOnly = true;
        this.styleAsReadOnly();
    }

    public override turnEditable(): void {
        this._readOnly = false;
        this.styleAsEditable();
    }

    public override isEditable(): boolean {
        return !this._readOnly;
    }

    protected override _getData(): QinAsset {
        for (let child of this.baseChildren) {
            if (child instanceof QinIconCell) {
                if (child.selected) {
                    return child.icon.asset;
                }
            }
        }
        return null;
    }

    protected override _setData(asset: QinAsset) {
        for (const child of this.qinedBase.baseChildren) {
            if (child instanceof QinIconCell) {
                if (child.icon.asset == asset) {
                    child.selected = true;
                } else {
                    child.selected = false;
                }
            }
        }
    }

    public addIcon(icon: QinIcon) {
        this.addCell(new QinIconCell(icon));
    }

    public addCell(cell: QinIconCell) {
        cell.addActionMain(_ => {
            if (this.isEditable()) {
                this._setData(cell.icon.asset);
            }
        });
        cell.install(this.qinedBase);
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinIconPick {
        super.styled(styles);
        return this;
    }
}

export type QinIconPickSet = {
    initial?: QinAsset;
    icons?: QinIcon[];
    cells?: QinIconCell[];
    readOnly?: boolean;
};
