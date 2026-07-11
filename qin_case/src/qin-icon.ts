import { QinDimension, QinGrandeur, QinSkin } from "qin_soul";
import { QinAsset, getUrlOfAsset, getAssetOfUrl } from "./qin-assets";
import { QinBase } from "./qin-base";

export class QinIcon extends QinBase {
    public constructor(asset: QinAsset, size: QinDimension | QinGrandeur = QinGrandeur.SMALL, isQindred?: string) {
        super((isQindred ? isQindred + "_" : "") + "icon", document.createElement("img"));
        this.castedQine().src = getUrlOfAsset(asset);
        this.styleAsSize(size);
    }

    public override castedQine(): HTMLImageElement {
        return this.qinedHTML as HTMLImageElement;
    }

    public get asset(): QinAsset {
        return getAssetOfUrl(this.castedQine().src);
    }

    public set asset(asset: QinAsset) {
        this.castedQine().src = getUrlOfAsset(asset);
    }

    public get dimension(): QinDimension {
        return QinSkin.getDimension(this.qinedHTML);
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinIcon {
        super.styled(styles);
        return this;
    }
}
