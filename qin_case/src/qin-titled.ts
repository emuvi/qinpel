import { QinBase } from "./qin-base";
import { QinColumn } from "./qin-column";
import { QinLabel } from "./qin-label";
import { QinLine } from "./qin-line";

export class QinTitled extends QinColumn {
    private _titleLabel: QinLabel;
    private _headLine = new QinLine();
    private _bodyLine = new QinLine();

    public constructor(options?: QinTitledSet, isQindred?: string) {
        super(null, (isQindred ? isQindred + "_" : "") + "titled");
        if (options?.label) {
            this._titleLabel = options.label;
        } else {
            this._titleLabel = new QinLabel();
        }
        if (options?.items) {
            options.items.forEach((item) => {
                item.install(this._bodyLine);
                this._titleLabel.qinLink(item);
            });
        }
        this.styleAsMarginRight(3);
        this._titleLabel.install(this._headLine);
        this._headLine.install(this);
        this._bodyLine.install(this);
        this.bodyBase = this._bodyLine;
    }

    public get title(): string {
        return this._titleLabel.title;
    }

    public set title(title: string) {
        this._titleLabel.title = title;
    }

    public override put(item: QinBase): QinTitled {
        item.install(this._bodyLine);
        this._titleLabel.qinLink(item);
        return this;
    }

    public override addChild(child: QinBase): QinTitled {
        super.addChild(child);
        this._titleLabel.qinLink(child);
        return this;
    }

    public override delChild(child: QinBase): QinTitled  {
        if (child === this._bodyLine || child === this._headLine) {
            super.delChild(child);
        } else {
            this._bodyLine.delChild(child);
        }
        return this;
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinTitled {
        super.styled(styles);
        return this;
    }
}

export type QinTitledSet = {
    label?: QinLabel;
    items?: QinBase[];
};
