import { BorderStyle, QinActionableStyles, QinDimension, QinGrandeur, QinSkin, QinStyles } from "qin_soul";
import { QinAsset, getUrlOfAsset } from "./qin-assets";
import { QinBase } from "./qin-base";

export class QinBaseStyle {
    private _el: HTMLElement;

    public constructor(root: HTMLElement | QinBase) {
        if (root instanceof QinBase) {
            this._el = root.qinedHTML;
        } else {
            this._el = root;
        }
    }

    public get rootEl() {
        return this._el;
    }

    public putAsBody() {
        document.body.appendChild(this._el);
        QinSkin.styleAsBody(this._el);
    }

    public delAsBody() {
        document.body.removeChild(this._el);
    }

    public styleAsWhole() {
        QinSkin.styleAsWhole(this._el)
    }

    public styleAsBase() {
        QinSkin.styleAsBase(this._el);
    }

    public styleAsSpaced() {
        QinSkin.styleAsSpaced(this._el);
    }

    public styleAsBordered() {
        QinSkin.styleAsBordered(this._el);
    }

    public styleAsEditable() {
        QinSkin.styleAsEditable(this._el);
    }

    public styleAsReadOnly() {
        QinSkin.styleAsReadOnly(this._el);
    }

    public styleAsActionable(styles: QinActionableStyles = QinStyles) {
        QinSkin.styleAsActionable(this._el, styles);
    }

    public styleMaxSizeForNotOverFlow() {
        QinSkin.styleMaxSizeForNotOverFlow(this._el);
    }

    public styleAsScroll() {
        QinSkin.styleAsScroll(this._el);
    }

    public styleAsMargin(margin: number) {
        QinSkin.styleAsMargin(this._el, margin);
    }

    public styleAsMarginTop(margin: number) {
        QinSkin.styleAsMarginTop(this._el, margin);
    }

    public styleAsMarginBottom(margin: number) {
        QinSkin.styleAsMarginBottom(this._el, margin);
    }

    public styleAsMarginLeft(margin: number) {
        QinSkin.styleAsMarginLeft(this._el, margin);
    }

    public styleAsMarginRight(margin: number) {
        QinSkin.styleAsMarginRight(this._el, margin);
    }

    public styleAsPadding(padding: number) {
        QinSkin.styleAsPadding(this._el, padding);
    }

    public styleAsPaddingTop(padding: number) {
        QinSkin.styleAsPaddingTop(this._el, padding);
    }

    public styleAsPaddingBottom(padding: number) {
        QinSkin.styleAsPaddingBottom(this._el, padding);
    }

    public styleAsPaddingLeft(padding: number) {
        QinSkin.styleAsPaddingLeft(this._el, padding);
    }

    public styleAsPaddingRight(padding: number) {
        QinSkin.styleAsPaddingRight(this._el, padding);
    }

    public styleAsBorder(thick?: number, color: string = QinSkin.styles.ColorForeground, style: BorderStyle = BorderStyle.SOLID) {
        QinSkin.styleAsBorder(this._el, thick, color, style);
    }

    public styleAsBorderTop(thick?: number, color: string = QinSkin.styles.ColorForeground, style: BorderStyle = BorderStyle.SOLID) {
        QinSkin.styleAsBorderTop(this._el, thick, color, style);
    }

    public styleAsBorderBottom(thick?: number, color: string = QinSkin.styles.ColorForeground, style: BorderStyle = BorderStyle.SOLID) {
        QinSkin.styleAsBorderBottom(this._el, thick, color, style);
    }

    public styleAsBorderLeft(thick?: number, color: string = QinSkin.styles.ColorForeground, style: BorderStyle = BorderStyle.SOLID) {
        QinSkin.styleAsBorderLeft(this._el, thick, color, style);
    }

    public styleAsBorderRight(thick?: number, color: string = QinSkin.styles.ColorForeground, style: BorderStyle = BorderStyle.SOLID) {
        QinSkin.styleAsBorderRight(this._el, thick, color, style);
    }

    public styleAsBorderRadius(radius: number) {
        QinSkin.styleAsBorderRadius(this._el, radius);
    }

    public styleAsBorderTopLeftRadius(radius: number) {
        QinSkin.styleAsBorderTopLeftRadius(this._el, radius);
    }

    public styleAsBorderTopRightRadius(radius: number) {
        QinSkin.styleAsBorderTopRightRadius(this._el, radius);
    }

    public styleAsBorderBottomRightRadius(radius: number) {
        QinSkin.styleAsBorderBottomRightRadius(this._el, radius);
    }

    public styleAsBorderBottomLeftRadius(radius: number) {
        QinSkin.styleAsBorderBottomLeftRadius(this._el, radius);
    }

    public styleAsPositionStatic() {
        QinSkin.styleAsPositionStatic(this._el);
    }

    public styleAsPositionRelative() {
        QinSkin.styleAsPositionRelative(this._el);
    }

    public styleAsPositionAbsolute() {
        QinSkin.styleAsPositionAbsolute(this._el);
    }

    public styleAsPositionFixed() {
        QinSkin.styleAsPositionFixed(this._el);
    }

    public styleAsPositionSticky() {
        QinSkin.styleAsPositionSticky(this._el);
    }

    public styleAsPositionInherit() {
        QinSkin.styleAsPositionInherit(this._el);
    }

    public styleAsPositionInitial() {
        QinSkin.styleAsPositionInitial(this._el);
    }

    public styleAsPositionRevert() {
        QinSkin.styleAsPositionRevert(this._el);
    }

    public styleAsPositionRevertLayer() {
        QinSkin.styleAsPositionRevertLayer(this._el);
    }

    public styleAsPositionUnset() {
        QinSkin.styleAsPositionUnset(this._el);
    }

    public styleAsDisplayBlock() {
        QinSkin.styleAsDisplayBlock(this._el);
    }

    public styleAsDisplayInline() {
        QinSkin.styleAsDisplayInline(this._el);
    }

    public styleAsDisplayInlineBlock() {
        QinSkin.styleAsDisplayInlineBlock(this._el);
    }

    public styleAsDisplayFlex() {
        QinSkin.styleAsDisplayFlex(this._el);
    }

    public styleAsDisplayInlineFlex() {
        QinSkin.styleAsDisplayInlineFlex(this._el);
    }

    public styleAsDisplayGrid() {
        QinSkin.styleAsDisplayGrid(this._el);
    }

    public styleAsDisplayInlineGrid() {
        QinSkin.styleAsDisplayInlineGrid(this._el);
    }

    public styleAsDisplayFlowRoot() {
        QinSkin.styleAsDisplayFlowRoot(this._el);
    }

    public styleAsDisplayNone() {
        QinSkin.styleAsDisplayNone(this._el);
    }

    public styleAsDisplayContents() {
        QinSkin.styleAsDisplayContents(this._el);
    }

    public styleAsDisplayTable() {
        QinSkin.styleAsDisplayTable(this._el);
    }

    public styleAsDisplayTableRow() {
        QinSkin.styleAsDisplayTableRow(this._el);
    }

    public styleAsDisplayListItem() {
        QinSkin.styleAsDisplayListItem(this._el);
    }

    public styleAsDisplayInherit() {
        QinSkin.styleAsDisplayInherit(this._el);
    }

    public styleAsDisplayInitial() {
        QinSkin.styleAsDisplayInitial(this._el);
    }

    public styleAsDisplayRevert() {
        QinSkin.styleAsDisplayRevert(this._el);
    }

    public styleAsDisplayRevertLayer() {
        QinSkin.styleAsDisplayRevertLayer(this._el);
    }

    public styleAsDisplayUnset() {
        QinSkin.styleAsDisplayUnset(this._el);
    }

    public styleAsFlexDirectionRow() {
        QinSkin.styleAsFlexDirectionRow(this._el);
    }

    public styleAsFlexDirectionRowReverse() {
        QinSkin.styleAsFlexDirectionRowReverse(this._el);
    }

    public styleAsFlexDirectionColumn() {
        QinSkin.styleAsFlexDirectionColumn(this._el);
    }

    public styleAsFlexDirectionColumnReverse() {
        QinSkin.styleAsFlexDirectionColumnReverse(this._el);
    }

    public styleAsFlexWrap() {
        QinSkin.styleAsFlexWrap(this._el);
    }

    public styleAsFlexWrapNot() {
        QinSkin.styleAsFlexWrapNot(this._el);
    }

    public styleAsFlexWrapReverse() {
        QinSkin.styleAsFlexWrapReverse(this._el);
    }

    public styleAsFlexMin() {
        QinSkin.styleAsFlexMin(this._el);
    }

    public styleAsFlexMax() {
        QinSkin.styleAsFlexMax(this._el);
    }

    public styleAsAllCentered() {
        QinSkin.styleAsAllCentered(this._el);
    }

    public styleAsJustifyContentFlexStart() {
        QinSkin.styleAsJustifyContentFlexStart(this._el);
    }

    public styleAsJustifyContentFlexEnd() {
        QinSkin.styleAsJustifyContentFlexEnd(this._el);
    }

    public styleAsJustifyContentCenter() {
        QinSkin.styleAsJustifyContentCenter(this._el);
    }

    public styleAsJustifyContentSpaceBetween() {
        QinSkin.styleAsJustifyContentSpaceBetween(this._el);
    }

    public styleAsJustifyContentSpaceAround() {
        QinSkin.styleAsJustifyContentSpaceAround(this._el);
    }

    public styleAsJustifyContentSpaceEvenly() {
        QinSkin.styleAsJustifyContentSpaceEvenly(this._el);
    }

    public styleAsJustifyContentInitial() {
        QinSkin.styleAsJustifyContentInitial(this._el);
    }

    public styleAsJustifyContentInherit() {
        QinSkin.styleAsJustifyContentInherit(this._el);
    }

    public styleAsAlignItemsNormal() {
        QinSkin.styleAsAlignItemsNormal(this._el);
    }
    public styleAsAlignItemsStretch() {
        QinSkin.styleAsAlignItemsStretch(this._el);
    }
    public styleAsAlignItemsCenter() {
        QinSkin.styleAsAlignItemsCenter(this._el);
    }
    public styleAsAlignItemsStart() {
        QinSkin.styleAsAlignItemsStart(this._el);
    }
    public styleAsAlignItemsEnd() {
        QinSkin.styleAsAlignItemsEnd(this._el);
    }
    public styleAsAlignItemsFlexStart() {
        QinSkin.styleAsAlignItemsFlexStart(this._el);
    }
    public styleAsAlignItemsFlexEnd() {
        QinSkin.styleAsAlignItemsFlexEnd(this._el);
    }
    public styleAsAlignItemsSelfStart() {
        QinSkin.styleAsAlignItemsSelfStart(this._el);
    }
    public styleAsAlignItemsSelfEnd() {
        QinSkin.styleAsAlignItemsSelfEnd(this._el);
    }
    public styleAsAlignItemsAnchorCenter() {
        QinSkin.styleAsAlignItemsAnchorCenter(this._el);
    }
    public styleAsAlignItemsBaseline() {
        QinSkin.styleAsAlignItemsBaseline(this._el);
    }
    public styleAsAlignItemsFirstBaseline() {
        QinSkin.styleAsAlignItemsFirstBaseline(this._el);
    }
    public styleAsAlignItemsLastBaseline() {
        QinSkin.styleAsAlignItemsLastBaseline(this._el);
    }
    public styleAsAlignItemsSafeCenter() {
        QinSkin.styleAsAlignItemsSafeCenter(this._el);
    }
    public styleAsAlignItemsUnsafeCenter() {
        QinSkin.styleAsAlignItemsUnsafeCenter(this._el);
    }
    public styleAsAlignItemsInherit() {
        QinSkin.styleAsAlignItemsInherit(this._el);
    }
    public styleAsAlignItemsInitial() {
        QinSkin.styleAsAlignItemsInitial(this._el);
    }
    public styleAsAlignItemsRevert() {
        QinSkin.styleAsAlignItemsRevert(this._el);
    }
    public styleAsAlignItemsRevertLayer() {
        QinSkin.styleAsAlignItemsRevertLayer(this._el);
    }
    public styleAsAlignItemsUnset() {
        QinSkin.styleAsAlignItemsUnset(this._el);
    }

    public styleAsBounds(top: number, right: number, bottom: number, left: number) {
        QinSkin.styleAsBounds(this._el, top, right, bottom, left);
    }

    public styleAsTop(top: number) {
        QinSkin.styleAsTop(this._el, top);
    }

    public styleAsLeft(left: number) {
        QinSkin.styleAsLeft(this._el, left);
    }

    public styleAsBottom(bottom: number) {
        QinSkin.styleAsBottom(this._el, bottom);
    }

    public styleAsRight(right: number) {
        QinSkin.styleAsRight(this._el, right);
    }

    public styleAsTopLeft(top: number, left: number) {
        QinSkin.styleAsTopLeft(this._el, top, left);
    }

    public styleAsTopRight(top: number, right: number) {
        QinSkin.styleAsTopRight(this._el, top, right);
    }

    public styleAsBottomRight(bottom: number, right: number) {
        QinSkin.styleAsBottomRight(this._el, bottom, right);
    }

    public styleAsBottomLeft(bottom: number, left: number) {
        QinSkin.styleAsBottomLeft(this._el, bottom, left);
    }

    public styleAsWidth(width: number | QinGrandeur) {
        QinSkin.styleAsWidth(this._el, width);
    }

    public styleAsWidthPer(width: number) {
        QinSkin.styleAsWidthPer(this._el, width);
    }

    public styleAsWidthMaxContent() {
        QinSkin.styleAsWidthMaxContent(this._el);
    }

    public styleAsWidthMinContent() {
        QinSkin.styleAsWidthMinContent(this._el);
    }

    public styleAsWidthFitContent() {
        QinSkin.styleAsWidthFitContent(this._el);
    }

    public styleAsWidthStretch() {
        QinSkin.styleAsWidthStretch(this._el);
    }

    public styleAsWidthInherit() {
        QinSkin.styleAsWidthInherit(this._el);
    }

    public styleAsWidthInitial() {
        QinSkin.styleAsWidthInitial(this._el);
    }

    public styleAsWidthRevert() {
        QinSkin.styleAsWidthRevert(this._el);
    }

    public styleAsWidthRevertLayer() {
        QinSkin.styleAsWidthRevertLayer(this._el);
    }

    public styleAsWidthUnset() {
        QinSkin.styleAsWidthUnset(this._el);
    }

    public styleAsHeight(height: number | QinGrandeur) {
        QinSkin.styleAsHeight(this._el, height);
    }

    public styleAsHeightPer(height: number) {
        QinSkin.styleAsHeightPer(this._el, height);
    }

    public styleAsHeightMaxContent() {
        QinSkin.styleAsHeightMaxContent(this._el);
    }

    public styleAsHeightMinContent() {
        QinSkin.styleAsHeightMinContent(this._el);
    }

    public styleAsHeightFitContent() {
        QinSkin.styleAsHeightFitContent(this._el);
    }

    public styleAsHeightStretch() {
        QinSkin.styleAsHeightStretch(this._el);
    }

    public styleAsHeightInherit() {
        QinSkin.styleAsHeightInherit(this._el);
    }

    public styleAsHeightInitial() {
        QinSkin.styleAsHeightInitial(this._el);
    }

    public styleAsHeightRevert() {
        QinSkin.styleAsHeightRevert(this._el);
    }

    public styleAsHeightRevertLayer() {
        QinSkin.styleAsHeightRevertLayer(this._el);
    }

    public styleAsHeightUnset() {
        QinSkin.styleAsHeightUnset(this._el);
    }

    public styleAsSize(size: QinDimension | QinGrandeur) {
        QinSkin.styleAsSize(this._el, size);
    }

    public styleAsSizePer(size: QinDimension) {
        QinSkin.styleAsSizePer(this._el, size);
    }

    public styleAsSizeMaxContent() {
        QinSkin.styleAsSizeMaxContent(this._el);
    }

    public styleAsSizeMinContent() {
        QinSkin.styleAsSizeMinContent(this._el);
    }

    public styleAsSizeFitContent() {
        QinSkin.styleAsSizeFitContent(this._el);
    }

    public styleAsSizeStretch() {
        QinSkin.styleAsSizeStretch(this._el);
    }

    public styleAsSizeInherit() {
        QinSkin.styleAsSizeInherit(this._el);
    }

    public styleAsSizeInitial() {
        QinSkin.styleAsSizeInitial(this._el);
    }

    public styleAsSizeRevert() {
        QinSkin.styleAsSizeRevert(this._el);
    }

    public styleAsSizeRevertLayer() {
        QinSkin.styleAsSizeRevertLayer(this._el);
    }

    public styleAsSizeUnset() {
        QinSkin.styleAsSizeUnset(this._el);
    }

    public styleAsMinWidth(width: number | QinGrandeur) {
        QinSkin.styleAsMinWidth(this._el, width);
    }

    public styleAsMinWidthPer(width: number) {
        QinSkin.styleAsMinWidthPer(this._el, width);
    }

    public styleAsMinWidthMaxContent() {
        QinSkin.styleAsMinWidthMaxContent(this._el);
    }

    public styleAsMinWidthMinContent() {
        QinSkin.styleAsMinWidthMinContent(this._el);
    }

    public styleAsMinWidthFitContent() {
        QinSkin.styleAsMinWidthFitContent(this._el);
    }

    public styleAsMinWidthStretch() {
        QinSkin.styleAsMinWidthStretch(this._el);
    }

    public styleAsMinWidthInherit() {
        QinSkin.styleAsMinWidthInherit(this._el);
    }

    public styleAsMinWidthInitial() {
        QinSkin.styleAsMinWidthInitial(this._el);
    }

    public styleAsMinWidthRevert() {
        QinSkin.styleAsMinWidthRevert(this._el);
    }

    public styleAsMinWidthRevertLayer() {
        QinSkin.styleAsMinWidthRevertLayer(this._el);
    }

    public styleAsMinWidthUnset() {
        QinSkin.styleAsMinWidthUnset(this._el);
    }

    public styleAsMinHeight(height: number | QinGrandeur) {
        QinSkin.styleAsMinHeight(this._el, height);
    }

    public styleAsMinHeightPer(height: number) {
        QinSkin.styleAsMinHeightPer(this._el, height);
    }

    public styleAsMinHeightMaxContent() {
        QinSkin.styleAsMinHeightMaxContent(this._el);
    }

    public styleAsMinHeightMinContent() {
        QinSkin.styleAsMinHeightMinContent(this._el);
    }

    public styleAsMinHeightFitContent() {
        QinSkin.styleAsMinHeightFitContent(this._el);
    }

    public styleAsMinHeightStretch() {
        QinSkin.styleAsMinHeightStretch(this._el);
    }

    public styleAsMinHeightInherit() {
        QinSkin.styleAsMinHeightInherit(this._el);
    }

    public styleAsMinHeightInitial() {
        QinSkin.styleAsMinHeightInitial(this._el);
    }

    public styleAsMinHeightRevert() {
        QinSkin.styleAsMinHeightRevert(this._el);
    }

    public styleAsMinHeightRevertLayer() {
        QinSkin.styleAsMinHeightRevertLayer(this._el);
    }

    public styleAsMinHeightUnset() {
        QinSkin.styleAsMinHeightUnset(this._el);
    }

    public styleAsMinSize(size: QinDimension | QinGrandeur) {
        QinSkin.styleAsMinSize(this._el, size);
    }

    public styleAsMinSizePer(size: QinDimension) {
        QinSkin.styleAsMinSizePer(this._el, size);
    }

    public styleAsMinSizeMaxContent() {
        QinSkin.styleAsMinSizeMaxContent(this._el);
    }

    public styleAsMinSizeMinContent() {
        QinSkin.styleAsMinSizeMinContent(this._el);
    }

    public styleAsMinSizeFitContent() {
        QinSkin.styleAsMinSizeFitContent(this._el);
    }

    public styleAsMinSizeStretch() {
        QinSkin.styleAsMinSizeStretch(this._el);
    }

    public styleAsMinSizeInherit() {
        QinSkin.styleAsMinSizeInherit(this._el);
    }

    public styleAsMinSizeInitial() {
        QinSkin.styleAsMinSizeInitial(this._el);
    }

    public styleAsMinSizeRevert() {
        QinSkin.styleAsMinSizeRevert(this._el);
    }

    public styleAsMinSizeRevertLayer() {
        QinSkin.styleAsMinSizeRevertLayer(this._el);
    }

    public styleAsMinSizeUnset() {
        QinSkin.styleAsMinSizeUnset(this._el);
    }

    public styleAsMaxWidth(width: number | QinGrandeur) {
        QinSkin.styleAsMaxWidth(this._el, width);
    }

    public styleAsMaxWidthPer(width: number) {
        QinSkin.styleAsMaxWidthPer(this._el, width);
    }

    public styleAsMaxWidthMaxContent() {
        QinSkin.styleAsMaxWidthMaxContent(this._el);
    }

    public styleAsMaxWidthMinContent() {
        QinSkin.styleAsMaxWidthMinContent(this._el);
    }

    public styleAsMaxWidthFitContent() {
        QinSkin.styleAsMaxWidthFitContent(this._el);
    }

    public styleAsMaxWidthStretch() {
        QinSkin.styleAsMaxWidthStretch(this._el);
    }

    public styleAsMaxWidthInherit() {
        QinSkin.styleAsMaxWidthInherit(this._el);
    }

    public styleAsMaxWidthInitial() {
        QinSkin.styleAsMaxWidthInitial(this._el);
    }

    public styleAsMaxWidthRevert() {
        QinSkin.styleAsMaxWidthRevert(this._el);
    }

    public styleAsMaxWidthRevertLayer() {
        QinSkin.styleAsMaxWidthRevertLayer(this._el);
    }

    public styleAsMaxWidthUnset() {
        QinSkin.styleAsMaxWidthUnset(this._el);
    }

    public styleAsMaxHeight(height: number | QinGrandeur) {
        QinSkin.styleAsMaxHeight(this._el, height);
    }

    public styleAsMaxHeightPer(height: number) {
        QinSkin.styleAsMaxHeightPer(this._el, height);
    }

    public styleAsMaxHeightMaxContent() {
        QinSkin.styleAsMaxHeightMaxContent(this._el);
    }

    public styleAsMaxHeightMinContent() {
        QinSkin.styleAsMaxHeightMinContent(this._el);
    }

    public styleAsMaxHeightFitContent() {
        QinSkin.styleAsMaxHeightFitContent(this._el);
    }

    public styleAsMaxHeightStretch() {
        QinSkin.styleAsMaxHeightStretch(this._el);
    }

    public styleAsMaxHeightInherit() {
        QinSkin.styleAsMaxHeightInherit(this._el);
    }

    public styleAsMaxHeightInitial() {
        QinSkin.styleAsMaxHeightInitial(this._el);
    }

    public styleAsMaxHeightRevert() {
        QinSkin.styleAsMaxHeightRevert(this._el);
    }

    public styleAsMaxHeightRevertLayer() {
        QinSkin.styleAsMaxHeightRevertLayer(this._el);
    }

    public styleAsMaxHeightUnset() {
        QinSkin.styleAsMaxHeightUnset(this._el);
    }

    public styleAsMaxSize(size: QinDimension | QinGrandeur) {
        QinSkin.styleAsMaxSize(this._el, size);
    }

    public styleAsMaxSizePer(size: QinDimension) {
        QinSkin.styleAsMaxSizePer(this._el, size);
    }

    public styleAsMaxSizeMaxContent() {
        QinSkin.styleAsMaxSizeMaxContent(this._el);
    }

    public styleAsMaxSizeMinContent() {
        QinSkin.styleAsMaxSizeMinContent(this._el);
    }

    public styleAsMaxSizeFitContent() {
        QinSkin.styleAsMaxSizeFitContent(this._el);
    }

    public styleAsMaxSizeStretch() {
        QinSkin.styleAsMaxSizeStretch(this._el);
    }

    public styleAsMaxSizeInherit() {
        QinSkin.styleAsMaxSizeInherit(this._el);
    }

    public styleAsMaxSizeInitial() {
        QinSkin.styleAsMaxSizeInitial(this._el);
    }

    public styleAsMaxSizeRevert() {
        QinSkin.styleAsMaxSizeRevert(this._el);
    }

    public styleAsMaxSizeRevertLayer() {
        QinSkin.styleAsMaxSizeRevertLayer(this._el);
    }

    public styleAsMaxSizeUnset() {
        QinSkin.styleAsMaxSizeUnset(this._el);
    }

    public styleAsForeground(foreground: string) {
        QinSkin.styleAsForeground(this._el, foreground);
    }

    public styleAsBackground(background: string) {
        QinSkin.styleAsBackground(this._el, background);
    }

    public styleAsBackgroundImage(imageURL: string) {
        QinSkin.styleAsBackgroundImage(this._el, imageURL)
    }

    public styleAsBackgroundImageAsset(asset: QinAsset) {
        QinSkin.styleAsBackgroundImage(this._el, getUrlOfAsset(asset))
    }

    public styleAsBackgroundImageInitial() {
        QinSkin.styleAsBackgroundImageInitial(this._el)
    }

    public styleAsZIndex(index: number) {
        QinSkin.styleAsZIndex(this._el, index);
    }

    public styleAsCursorAuto() {
        QinSkin.styleAsCursorAuto(this._el);
    }
    public styleAsCursorDefault() {
        QinSkin.styleAsCursorDefault(this._el);
    }
    public styleAsCursorNone() {
        QinSkin.styleAsCursorNone(this._el);
    }
    public styleAsCursorContextMenu() {
        QinSkin.styleAsCursorContextMenu(this._el);
    }
    public styleAsCursorHelp() {
        QinSkin.styleAsCursorHelp(this._el);
    }
    public styleAsCursorPointer() {
        QinSkin.styleAsCursorPointer(this._el);
    }
    public styleAsCursorProgress() {
        QinSkin.styleAsCursorProgress(this._el);
    }
    public styleAsCursorWait() {
        QinSkin.styleAsCursorWait(this._el);
    }
    public styleAsCursorCell() {
        QinSkin.styleAsCursorCell(this._el);
    }
    public styleAsCursorCrosshair() {
        QinSkin.styleAsCursorCrosshair(this._el);
    }
    public styleAsCursorText() {
        QinSkin.styleAsCursorText(this._el);
    }
    public styleAsCursorVerticalText() {
        QinSkin.styleAsCursorVerticalText(this._el);
    }
    public styleAsCursorAlias() {
        QinSkin.styleAsCursorAlias(this._el);
    }
    public styleAsCursorCopy() {
        QinSkin.styleAsCursorCopy(this._el);
    }
    public styleAsCursorMove() {
        QinSkin.styleAsCursorMove(this._el);
    }
    public styleAsCursorNoDrop() {
        QinSkin.styleAsCursorNoDrop(this._el);
    }
    public styleAsCursorNotAllowed() {
        QinSkin.styleAsCursorNotAllowed(this._el);
    }
    public styleAsCursorGrab() {
        QinSkin.styleAsCursorGrab(this._el);
    }
    public styleAsCursorGrabbing() {
        QinSkin.styleAsCursorGrabbing(this._el);
    }
    public styleAsCursorAllScroll() {
        QinSkin.styleAsCursorAllScroll(this._el);
    }
    public styleAsCursorColResize() {
        QinSkin.styleAsCursorColResize(this._el);
    }
    public styleAsCursorRowResize() {
        QinSkin.styleAsCursorRowResize(this._el);
    }
    public styleAsCursorNResize() {
        QinSkin.styleAsCursorNResize(this._el);
    }
    public styleAsCursorEResize() {
        QinSkin.styleAsCursorEResize(this._el);
    }
    public styleAsCursorSResize() {
        QinSkin.styleAsCursorSResize(this._el);
    }
    public styleAsCursorWResize() {
        QinSkin.styleAsCursorWResize(this._el);
    }
    public styleAsCursorNEResize() {
        QinSkin.styleAsCursorNEResize(this._el);
    }
    public styleAsCursorNWResize() {
        QinSkin.styleAsCursorNWResize(this._el);
    }
    public styleAsCursorSEResize() {
        QinSkin.styleAsCursorSEResize(this._el);
    }
    public styleAsCursorSWResize() {
        QinSkin.styleAsCursorSWResize(this._el);
    }
    public styleAsCursorEWResize() {
        QinSkin.styleAsCursorEWResize(this._el);
    }
    public styleAsCursorNSResize() {
        QinSkin.styleAsCursorNSResize(this._el);
    }
    public styleAsCursorNEWSResize() {
        QinSkin.styleAsCursorNEWSResize(this._el);
    }
    public styleAsCursorNWSEResize() {
        QinSkin.styleAsCursorNWSEResize(this._el);
    }
    public styleAsCursorZoomIn() {
        QinSkin.styleAsCursorZoomIn(this._el);
    }
    public styleAsCursorZoomOut() {
        QinSkin.styleAsCursorZoomOut(this._el);
    }
    public styleAsCursorInherit() {
        QinSkin.styleAsCursorInherit(this._el);
    }
    public styleAsCursorInitial() {
        QinSkin.styleAsCursorInitial(this._el);
    }
    public styleAsCursorRevert() {
        QinSkin.styleAsCursorRevert(this._el);
    }
    public styleAsCursorRevertLayer() {
        QinSkin.styleAsCursorRevertLayer(this._el);
    }
    public styleAsCursorUnset() {
        QinSkin.styleAsCursorUnset(this._el);
    }

    public styleAsWhiteSpaceNormal() {
        QinSkin.styleAsWhiteSpaceNormal(this._el);
    }
    public styleAsWhiteSpacePre() {
        QinSkin.styleAsWhiteSpacePre(this._el);
    }
    public styleAsWhiteSpacePreWrap() {
        QinSkin.styleAsWhiteSpacePreWrap(this._el);
    }
    public styleAsWhiteSpacePreLine() {
        QinSkin.styleAsWhiteSpacePreLine(this._el);
    }
    public styleAsWhiteSpaceNoWrap() {
        QinSkin.styleAsWhiteSpaceNoWrap(this._el);
    }
    public styleAsWhiteSpaceWrap() {
        QinSkin.styleAsWhiteSpaceWrap(this._el);
    }
    public styleAsWhiteSpaceBreakSpaces() {
        QinSkin.styleAsWhiteSpaceBreakSpaces(this._el);
    }
    public styleAsWhiteSpaceCollapse() {
        QinSkin.styleAsWhiteSpaceCollapse(this._el);
    }
    public styleAsWhiteSpacePreserveNowrap() {
        QinSkin.styleAsWhiteSpacePreserveNowrap(this._el);
    }
    public styleAsWhiteSpaceInitial() {
        QinSkin.styleAsWhiteSpaceInitial(this._el);
    }
    public styleAsWhiteSpaceInherit() {
        QinSkin.styleAsWhiteSpaceInherit(this._el);
    }
    public styleAsWhiteSpaceRevert() {
        QinSkin.styleAsWhiteSpaceRevert(this._el);
    }
    public styleAsWhiteSpaceRevertLayer() {
        QinSkin.styleAsWhiteSpaceRevertLayer(this._el);
    }
    public styleAsWhiteSpaceUnset() {
        QinSkin.styleAsWhiteSpaceUnset(this._el);
    }

    public disabledSelection() {
        QinSkin.disableSelection(this._el);
    }

    public clearSelection() {
        QinSkin.clearSelection();
    }
}
