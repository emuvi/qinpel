import { QinArms, QinDimension, QinGrandeur, QinSkin } from "qin_soul";
import { QinAsset } from "./qin-assets";
import { QinBase } from "./qin-base";
import { QinIcon } from "./qin-icon";
import { QinPanel } from "./qin-panel";
import { QinScroll } from "./qin-scroll";

export class QinSizer extends QinPanel {
    
    private _bodyScroll = new QinScroll();
    private _sizerIcon = new QinIcon(QinAsset.CaseSizer, QinGrandeur.MICRO);
    
    public constructor(child?: QinBase, options?: QinSizerSet, isQindred?: string) {
        super(undefined, (isQindred ? isQindred + "_" : "") + "sizer");
        if (child) {
            child.install(this._bodyScroll);
        }
        this.styleAsBordered();
        this.styleAsDisplayInitial();
        this.styleAsSize({width: 180, height: 90});
        if (options?.size) {
            this.styleAsSize(options?.size);
        }
        this._bodyScroll.styleAsSizeInitial();
        this._bodyScroll.styleAsPositionAbsolute();
        this._bodyScroll.styleAsBounds(0, 0, 0, 0);
        this._sizerIcon.styleAsPositionAbsolute();
        this._sizerIcon.styleAsBottomRight(3, 3);
        this._bodyScroll.install(this);
        this._sizerIcon.install(this);
        QinArms.addResizer([this._sizerIcon.qinedHTML], this.qinedHTML);
        this.bodyBase = this._bodyScroll;
    }

    public override put(item: QinBase): QinSizer {
        item.install(this._bodyScroll);
        return this;
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinSizer {
        super.styled(styles);
        return this;
    }
}

export type QinSizerSet = {
    size?: QinDimension | QinGrandeur;
};
