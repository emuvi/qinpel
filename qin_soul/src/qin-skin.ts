import { QinArms } from "./qin-arms";

export const QinStyles = {
    ColorForeground: "#180027ff",
    ColorBackground: "#fffcf9ff",
    ColorInactive: "#fff7ffff",
    ColorActive: "#fff0f0ff",
    ColorAccent: "#ae0000ff",
    ColorInactiveAct: "#f0f7ffff",
    ColorActiveAct: "#ddddffff",
    ColorAccentAct: "#0000aeff",
    ColorBlocked: "#f0f0f0ff",
    ColorEntered: "#e7f0e7ff",
    ColorAttend: "#496b49ff",
    ColorSelected: "#5d72de8f",
    ColorResize: "#dec9ffff",
    FontName: "SourceSansPro",
    FontSize: "16px",
};

export const QinStylesPicker = {
    ColorPickerForeground: "#180027ff",
    ColorPickerAccentAct: "#0000aeff",
    ColorUnPickedInactiveAct: "#f0f7ffff",
    ColorUnPickedActiveAct: "#ddddffff",
    ColorPickedInactiveAct: "#85c0ffff",
    ColorPickedActiveAct: "#8f8fffff",
};

function styleAsBody(el: HTMLElement) {
    document.body.style.position = "fixed";
    document.body.style.inset = "0";
    document.body.style.overflow = "auto";
    el.style.position = "fixed";
    el.style.inset = "7px";
    el.style.overflow = "auto";
    el.style.display = "flex";
    el.style.justifyContent = "stretch";
    el.style.alignItems = "stretch";
    styleAsSizeInitial(el);
}

function styleAsWhole(el: HTMLElement) {
    el.style.flex = "100 100 auto";
    styleAsSizeInitial(el);
}

function styleAsBase(el: HTMLElement) {
    el.style.minHeight = "fit-content";
    el.style.minWidth = "fit-content";
    el.style.maxHeight = "fit-content";
    el.style.maxWidth = "fit-content";
}

function styleAsSpaced(el: HTMLElement) {
    el.style.margin = "3px";
    el.style.padding = "4px";
}

function styleAsBordered(el: HTMLElement) {
    styleAsSpaced(el);
    el.style.border = "1px solid " + QinStyles.ColorForeground;
    el.style.borderRadius = "3px";
}

function styleAsEditable(el: HTMLElement) {
    styleAsBordered(el);
    el.tabIndex = 0;
    el.style.outline = "none";
    el.style.color = QinStyles.ColorForeground;
    el.style.backgroundColor = QinStyles.ColorInactive;
    el.addEventListener("focus", _styledAsEditableFocusEvent);
    el.addEventListener("focusout", _styledAsEditableFocusoutEvent);
    el.removeEventListener("focus", _styledAsReadOnlyFocusEvent);
    el.removeEventListener("focusout", _styledAsReadOnlyFocusoutEvent);
    el.removeEventListener("focus", _styledAsActionableFocusEvent);
    el.removeEventListener("focusout", _styledAsActionableFocusoutEvent);
}

function styleAsReadOnly(el: HTMLElement) {
    styleAsBordered(el);
    el.tabIndex = 0;
    el.style.outline = "none";
    el.style.color = QinStyles.ColorForeground;
    el.style.backgroundColor = QinStyles.ColorBlocked;
    el.removeEventListener("focus", _styledAsEditableFocusEvent);
    el.removeEventListener("focusout", _styledAsEditableFocusoutEvent);
    el.addEventListener("focus", _styledAsReadOnlyFocusEvent);
    el.addEventListener("focusout", _styledAsReadOnlyFocusoutEvent);
    el.removeEventListener("focus", _styledAsActionableFocusEvent);
    el.removeEventListener("focusout", _styledAsActionableFocusoutEvent);
}

function styleAsActionable(el: HTMLElement, styles: QinActionableStyles = QinStyles) {
    styleAsBordered(el);
    el.tabIndex = 0;
    el.style.outline = "none";
    el['actionableStyles'] = styles;
    el.style.color = styles.ColorForeground;
    el.style.backgroundColor = styles.ColorInactiveAct;
    el.removeEventListener("focus", _styledAsEditableFocusEvent);
    el.removeEventListener("focusout", _styledAsEditableFocusoutEvent);
    el.removeEventListener("focus", _styledAsReadOnlyFocusEvent);
    el.removeEventListener("focusout", _styledAsReadOnlyFocusoutEvent);
    el.addEventListener("focus", _styledAsActionableFocusEvent);
    el.addEventListener("focusout", _styledAsActionableFocusoutEvent);
}

export type QinActionableStyles = {
    ColorForeground: string;
    ColorInactiveAct: string;
    ColorActiveAct: string;
    ColorAccentAct: string;
};

function _styledAsEditableFocusEvent() {
    this.style.backgroundColor = QinStyles.ColorActive;
    this.style.border = "1px solid " + QinStyles.ColorAccent;
}

function _styledAsEditableFocusoutEvent() {
    this.style.backgroundColor = QinStyles.ColorInactive;
    this.style.border = "1px solid " + QinStyles.ColorForeground;
}

function _styledAsReadOnlyFocusEvent() {
    this.style.backgroundColor = QinSkin.styles.ColorEntered;
    this.style.border = "1px solid " + QinSkin.styles.ColorAttend;
}

function _styledAsReadOnlyFocusoutEvent() {
    this.style.backgroundColor = QinSkin.styles.ColorBlocked;
    this.style.border = "1px solid " + QinSkin.styles.ColorForeground;
}

function _styledAsActionableFocusEvent() {
    const styles = this['actionableStyles'] as QinActionableStyles;
    this.style.backgroundColor = styles.ColorActiveAct;
    this.style.border = "1px solid " + styles.ColorAccentAct;
}

function _styledAsActionableFocusoutEvent() {
    const styles = this['actionableStyles'] as QinActionableStyles;
    this.style.backgroundColor = styles.ColorInactiveAct;
    this.style.border = "1px solid " + styles.ColorForeground;
}

function styleMaxSizeForNotOverFlow(el: HTMLElement, parent?: HTMLElement) {
    if (!parent) {
        parent = el.parentElement;
    }
    if (parent) {
        let maxWidth = 0;
        let maxHeight = 0;
        let imediate = el;
        do {
            maxWidth = maxWidth + imediate.clientLeft;
            maxHeight = maxHeight + imediate.clientTop;
            imediate = imediate.parentElement;
        } while (imediate != null && imediate != parent);
        maxWidth = parent.clientWidth - maxWidth;
        maxHeight = parent.clientHeight - maxHeight;
        el.style.maxWidth = maxWidth + "px";
        el.style.maxHeight = maxHeight + "px";
    }
}

function styleAsScroll(el: HTMLElement) {
    el.style.overflow = "auto";
}

function styleAsMargin(el: HTMLElement, margin: number) {
    el.style.margin = getPixelsOrInitial(margin);
}

function styleAsMarginTop(el: HTMLElement, margin: number) {
    el.style.marginTop = getPixelsOrInitial(margin);
}

function styleAsMarginBottom(el: HTMLElement, margin: number) {
    el.style.marginBottom = getPixelsOrInitial(margin);
}

function styleAsMarginLeft(el: HTMLElement, margin: number) {
    el.style.marginLeft = getPixelsOrInitial(margin);
}

function styleAsMarginRight(el: HTMLElement, margin: number) {
    el.style.marginRight = getPixelsOrInitial(margin);
}

function styleAsPadding(el: HTMLElement, padding: number) {
    el.style.padding = getPixelsOrInitial(padding);
}

function styleAsPaddingTop(el: HTMLElement, padding: number) {
    el.style.paddingTop = getPixelsOrInitial(padding);
}

function styleAsPaddingBottom(el: HTMLElement, padding: number) {
    el.style.paddingBottom = getPixelsOrInitial(padding);
}

function styleAsPaddingLeft(el: HTMLElement, padding: number) {
    el.style.paddingLeft = getPixelsOrInitial(padding);
}

function styleAsPaddingRight(el: HTMLElement, padding: number) {
    el.style.paddingRight = getPixelsOrInitial(padding);
}

function styleAsBorder(el: HTMLElement, thick?: number, color: string = QinStyles.ColorForeground, style: BorderStyle = BorderStyle.SOLID) {
    if (thick) {
        el.style.border = thick + "px " + style + " " + color;
    } else {
        el.style.border = "none";
    }
}

function styleAsBorderTop(el: HTMLElement, thick?: number, color: string = QinStyles.ColorForeground, style: BorderStyle = BorderStyle.SOLID) {
    if (thick) {
        el.style.borderTop = thick + "px " + style + " " + color;
    } else {
        el.style.borderTop = "none";
    }
}

function styleAsBorderBottom(el: HTMLElement, thick?: number, color: string = QinStyles.ColorForeground, style: BorderStyle = BorderStyle.SOLID) {
    if (thick) {
        el.style.borderBottom = thick + "px " + style + " " + color;
    } else {
        el.style.borderBottom = "none";
    }
}

function styleAsBorderLeft(el: HTMLElement, thick?: number, color: string = QinStyles.ColorForeground, style: BorderStyle = BorderStyle.SOLID) {
    if (thick) {
        el.style.borderLeft = thick + "px " + style + " " + color;
    } else {
        el.style.borderLeft = "none";
    }
}

function styleAsBorderRight(el: HTMLElement, thick?: number, color: string = QinStyles.ColorForeground, style: BorderStyle = BorderStyle.SOLID) {
    if (thick) {
        el.style.borderRight = thick + "px " + style + " " + color;
    } else {
        el.style.borderRight = "none";
    }
}

function styleAsBorderRadius(el: HTMLElement, radius: number) {
    el.style.borderRadius = getPixelsOrInitial(radius);
}

function styleAsBorderTopLeftRadius(el: HTMLElement, radius: number) {
    el.style.borderTopLeftRadius = getPixelsOrInitial(radius);
}

function styleAsBorderTopRightRadius(el: HTMLElement, radius: number) {
    el.style.borderTopRightRadius = getPixelsOrInitial(radius);
}

function styleAsBorderBottomRightRadius(el: HTMLElement, radius: number) {
    el.style.borderBottomRightRadius = getPixelsOrInitial(radius);
}

function styleAsBorderBottomLeftRadius(el: HTMLElement, radius: number) {
    el.style.borderBottomLeftRadius = getPixelsOrInitial(radius);
}

function styleAsPositionStatic(el: HTMLElement) {
    el.style.position = "static";
}

function styleAsPositionRelative(el: HTMLElement) {
    el.style.position = "relative";
}

function styleAsPositionAbsolute(el: HTMLElement) {
    el.style.position = "absolute";
}

function styleAsPositionFixed(el: HTMLElement) {
    el.style.position = "fixed";
}

function styleAsPositionSticky(el: HTMLElement) {
    el.style.position = "sticky";
}

function styleAsPositionInherit(el: HTMLElement) {
    el.style.position = "inherit";
}

function styleAsPositionInitial(el: HTMLElement) {
    el.style.position = "initial";
}

function styleAsPositionRevert(el: HTMLElement) {
    el.style.position = "revert";
}

function styleAsPositionRevertLayer(el: HTMLElement) {
    el.style.position = "revert-layer";
}

function styleAsPositionUnset(el: HTMLElement) {
    el.style.position = "unset";
}

function styleAsDisplayBlock(el: HTMLElement) {
    el.style.display = "block";
}

function styleAsDisplayInline(el: HTMLElement) {
    el.style.display = "inline";
}

function styleAsDisplayInlineBlock(el: HTMLElement) {
    el.style.display = "inline-block";
}

function styleAsDisplayFlex(el: HTMLElement) {
    el.style.display = "flex";
}

function styleAsDisplayInlineFlex(el: HTMLElement) {
    el.style.display = "inline-flex";
}

function styleAsDisplayGrid(el: HTMLElement) {
    el.style.display = "grid";
}

function styleAsDisplayInlineGrid(el: HTMLElement) {
    el.style.display = "inline-grid";
}

function styleAsDisplayFlowRoot(el: HTMLElement) {
    el.style.display = "flow-root";
}

function styleAsDisplayNone(el: HTMLElement) {
    el.style.display = "none";
}

function styleAsDisplayContents(el: HTMLElement) {
    el.style.display = "contents";
}

function styleAsDisplayTable(el: HTMLElement) {
    el.style.display = "table";
}

function styleAsDisplayTableRow(el: HTMLElement) {
    el.style.display = "table-row";
}

function styleAsDisplayListItem(el: HTMLElement) {
    el.style.display = "list-item";
}

function styleAsDisplayInherit(el: HTMLElement) {
    el.style.display = "inherit";
}

function styleAsDisplayInitial(el: HTMLElement) {
    el.style.display = "initial";
}

function styleAsDisplayRevert(el: HTMLElement) {
    el.style.display = "revert";
}

function styleAsDisplayRevertLayer(el: HTMLElement) {
    el.style.display = "revert-layer";
}

function styleAsDisplayUnset(el: HTMLElement) {
    el.style.display = "unset";
}

function styleAsFlexDirectionRow(el: HTMLElement) {
    el.style.flexDirection = "row";
}

function styleAsFlexDirectionRowReverse(el: HTMLElement) {
    el.style.flexDirection = "row-reverse";
}

function styleAsFlexDirectionColumn(el: HTMLElement) {
    el.style.flexDirection = "column";
}

function styleAsFlexDirectionColumnReverse(el: HTMLElement) {
    el.style.flexDirection = "column-reverse";
}

function styleAsFlexWrap(el: HTMLElement) {
    el.style.flexWrap = "wrap";
}

function styleAsFlexWrapNot(el: HTMLElement) {
    el.style.flexWrap = "nowrap";
}

function styleAsFlexWrapReverse(el: HTMLElement) {
    el.style.flexWrap = "wrap-reverse";
}

function styleAsFlexMin(el: HTMLElement) {
    el.style.flex = "none";
}

function styleAsFlexMax(el: HTMLElement) {
    el.style.flex = "auto";
}

function styleAsFlexProp(el: HTMLElement, prop: number) {
    el.style.flex = prop + "";
}

function styleAsAllCentered(el: HTMLElement) {
    el.style.textAlign = "center";
    el.style.alignItems = "center";
    el.style.alignContent = "center";
    el.style.verticalAlign = "middle";
}

function styleAsJustifyContentFlexStart(el: HTMLElement) {
    el.style.justifyContent = "flex-start";
}

function styleAsJustifyContentFlexEnd(el: HTMLElement) {
    el.style.justifyContent = "flex-end";
}

function styleAsJustifyContentCenter(el: HTMLElement) {
    el.style.justifyContent = "center";
}

function styleAsJustifyContentSpaceBetween(el: HTMLElement) {
    el.style.justifyContent = "space-between";
}

function styleAsJustifyContentSpaceAround(el: HTMLElement) {
    el.style.justifyContent = "space-around";
}

function styleAsJustifyContentSpaceEvenly(el: HTMLElement) {
    el.style.justifyContent = "space-evenly";
}

function styleAsJustifyContentInitial(el: HTMLElement) {
    el.style.justifyContent = "initial";
}

function styleAsJustifyContentInherit(el: HTMLElement) {
    el.style.justifyContent = "inherit";
}

function styleAsAlignItemsNormal(el: HTMLElement) {
    el.style.alignItems = "normal";
}

function styleAsAlignItemsStretch(el: HTMLElement) {
    el.style.alignItems = "stretch";
}

function styleAsAlignItemsCenter(el: HTMLElement) {
    el.style.alignItems = "center";
}

function styleAsAlignItemsStart(el: HTMLElement) {
    el.style.alignItems = "start";
}

function styleAsAlignItemsEnd(el: HTMLElement) {
    el.style.alignItems = "end";
}

function styleAsAlignItemsFlexStart(el: HTMLElement) {
    el.style.alignItems = "flex-start";
}

function styleAsAlignItemsFlexEnd(el: HTMLElement) {
    el.style.alignItems = "flex-end";
}

function styleAsAlignItemsSelfStart(el: HTMLElement) {
    el.style.alignItems = "self-start";
}

function styleAsAlignItemsSelfEnd(el: HTMLElement) {
    el.style.alignItems = "self-end";
}

function styleAsAlignItemsAnchorCenter(el: HTMLElement) {
    el.style.alignItems = "anchor-center";
}

function styleAsAlignItemsBaseline(el: HTMLElement) {
    el.style.alignItems = "baseline";
}

function styleAsAlignItemsFirstBaseline(el: HTMLElement) {
    el.style.alignItems = "first baseline";
}

function styleAsAlignItemsLastBaseline(el: HTMLElement) {
    el.style.alignItems = "last baseline";
}

function styleAsAlignItemsSafeCenter(el: HTMLElement) {
    el.style.alignItems = "safe center";
}

function styleAsAlignItemsUnsafeCenter(el: HTMLElement) {
    el.style.alignItems = "unsafe center";
}

function styleAsAlignItemsInherit(el: HTMLElement) {
    el.style.alignItems = "inherit";
}

function styleAsAlignItemsInitial(el: HTMLElement) {
    el.style.alignItems = "initial";
}

function styleAsAlignItemsRevert(el: HTMLElement) {
    el.style.alignItems = "stretch";
}

function styleAsAlignItemsRevertLayer(el: HTMLElement) {
    el.style.alignItems = "revert-layer";
}

function styleAsAlignItemsUnset(el: HTMLElement) {
    el.style.alignItems = "unset";
}

function styleAsBounds(el: HTMLElement, top: number, right: number, bottom: number, left: number) {
    el.style.top = getPixelsOrInitial(top);
    el.style.right = getPixelsOrInitial(right);
    el.style.bottom = getPixelsOrInitial(bottom);
    el.style.left = getPixelsOrInitial(left);
}

function styleAsTop(el: HTMLElement, top: number) {
    el.style.top = getPixelsOrInitial(top);
}

function styleAsLeft(el: HTMLElement, left: number) {
    el.style.left = getPixelsOrInitial(left);
}

function styleAsTopLeft(el: HTMLElement, top: number, left: number) {
    el.style.top = getPixelsOrInitial(top);
    el.style.left = getPixelsOrInitial(left);
}

function styleAsTopRight(el: HTMLElement, top: number, right: number) {
    el.style.top = getPixelsOrInitial(top);
    el.style.right = getPixelsOrInitial(right);
}

function styleAsBottomRight(el: HTMLElement, bottom: number, right: number) {
    el.style.bottom = getPixelsOrInitial(bottom);
    el.style.right = getPixelsOrInitial(right);
}

function styleAsBottomLeft(el: HTMLElement, bottom: number, left: number) {
    el.style.bottom = getPixelsOrInitial(bottom);
    el.style.left = getPixelsOrInitial(left);
}

function styleAsBottom(el: HTMLElement, bottom: number) {
    el.style.bottom = getPixelsOrInitial(bottom);
}

function styleAsRight(el: HTMLElement, right: number) {
    el.style.right = getPixelsOrInitial(right);
}

function styleAsWidth(el: HTMLElement, width: number | QinGrandeur) {
    let dim = width;
    if (typeof width === "string") {
        dim = getDimensionSize(width).width;
    }
    el.style.width = getPixelsOrInitial(dim as number);
    el.style.minWidth = getPixelsOrInitial(dim as number);
    el.style.maxWidth = getPixelsOrInitial(dim as number);
}

function styleAsWidthPer(el: HTMLElement, width: number) {
    el.style.width = getPercentOrInitial(width);
    el.style.minWidth = getPercentOrInitial(width);
    el.style.maxWidth = getPercentOrInitial(width);
}

function styleAsWidthMaxContent(el: HTMLElement) {
    el.style.width = 'max-content';
    el.style.minWidth = 'max-content';
    el.style.maxWidth = 'max-content';
}

function styleAsWidthMinContent(el: HTMLElement) {
    el.style.width = 'min-content';
    el.style.minWidth = 'min-content';
    el.style.maxWidth = 'min-content';
}

function styleAsWidthFitContent(el: HTMLElement) {
    el.style.width = 'fit-content';
    el.style.minWidth = 'fit-content';
    el.style.maxWidth = 'fit-content';
}

function styleAsWidthStretch(el: HTMLElement) {
    el.style.width = 'stretch';
    el.style.minWidth = 'stretch';
    el.style.maxWidth = 'stretch';
}

function styleAsWidthInherit(el: HTMLElement) {
    el.style.width = 'inherit';
    el.style.minWidth = 'inherit';
    el.style.maxWidth = 'inherit';
}

function styleAsWidthInitial(el: HTMLElement) {
    el.style.width = 'initial';
    el.style.minWidth = 'initial';
    el.style.maxWidth = 'initial';
}

function styleAsWidthRevert(el: HTMLElement) {
    el.style.width = 'revert';
    el.style.minWidth = 'revert';
    el.style.maxWidth = 'revert';
}

function styleAsWidthRevertLayer(el: HTMLElement) {
    el.style.width = 'revert-layer';
    el.style.minWidth = 'revert-layer';
    el.style.maxWidth = 'revert-layer';
}

function styleAsWidthUnset(el: HTMLElement) {
    el.style.width = 'unset';
    el.style.minWidth = 'unset';
    el.style.maxWidth = 'unset';
}

function styleAsHeight(el: HTMLElement, height: number | QinGrandeur) {
    let dim = height;
    if (typeof height === "string") {
        dim = getDimensionSize(height).height;
    }
    el.style.height = getPixelsOrInitial(dim as number);
    el.style.minHeight = getPixelsOrInitial(dim as number);
    el.style.maxHeight = getPixelsOrInitial(dim as number);
}

function styleAsHeightPer(el: HTMLElement, width: number) {
    el.style.height = getPercentOrInitial(width);
    el.style.minHeight = getPercentOrInitial(width);
    el.style.maxHeight = getPercentOrInitial(width);
}

function styleAsHeightMaxContent(el: HTMLElement) {
    el.style.height = 'max-content';
    el.style.minHeight = 'max-content';
    el.style.maxHeight = 'max-content';
}

function styleAsHeightMinContent(el: HTMLElement) {
    el.style.height = 'min-content';
    el.style.minHeight = 'min-content';
    el.style.maxHeight = 'min-content';
}

function styleAsHeightFitContent(el: HTMLElement) {
    el.style.height = 'fit-content';
    el.style.minHeight = 'fit-content';
    el.style.maxHeight = 'fit-content';
}

function styleAsHeightStretch(el: HTMLElement) {
    el.style.height = 'stretch';
    el.style.minHeight = 'stretch';
    el.style.maxHeight = 'stretch';
}

function styleAsHeightInherit(el: HTMLElement) {
    el.style.height = 'inherit';
    el.style.minHeight = 'inherit';
    el.style.maxHeight = 'inherit';
}

function styleAsHeightInitial(el: HTMLElement) {
    el.style.height = 'initial';
    el.style.minHeight = 'initial';
    el.style.maxHeight = 'initial';
}

function styleAsHeightRevert(el: HTMLElement) {
    el.style.height = 'revert';
    el.style.minHeight = 'revert';
    el.style.maxHeight = 'revert';
}

function styleAsHeightRevertLayer(el: HTMLElement) {
    el.style.height = 'revert-layer';
    el.style.minHeight = 'revert-layer';
    el.style.maxHeight = 'revert-layer';
}

function styleAsHeightUnset(el: HTMLElement) {
    el.style.height = 'unset';
    el.style.minHeight = 'unset';
    el.style.maxHeight = 'unset';
}

function styleAsSize(el: HTMLElement, size: QinDimension | QinGrandeur) {
    let dim = size;
    if (typeof size === "string") {
        dim = getDimensionSize(size);
    }
    if (!dim) {
        dim = { width: null, height: null };
    }
    el.style.width = getPixelsOrInitial((dim as QinDimension).width);
    el.style.height = getPixelsOrInitial((dim as QinDimension).height);
    el.style.minWidth = getPixelsOrInitial((dim as QinDimension).width);
    el.style.minHeight = getPixelsOrInitial((dim as QinDimension).height);
    el.style.maxWidth = getPixelsOrInitial((dim as QinDimension).width);
    el.style.maxHeight = getPixelsOrInitial((dim as QinDimension).height);
}

function styleAsSizePer(el: HTMLElement, size: QinDimension) {
    if (!size) {
        size = { width: null, height: null };
    }
    el.style.width = getPercentOrInitial(size.width);
    el.style.height = getPercentOrInitial(size.height);
    el.style.minWidth = getPercentOrInitial(size.width);
    el.style.minHeight = getPercentOrInitial(size.height);
    el.style.maxWidth = getPercentOrInitial(size.width);
    el.style.maxHeight = getPercentOrInitial(size.height);
}

function styleAsSizeMaxContent(el: HTMLElement) {
    el.style.width = 'max-content';
    el.style.height = 'max-content';
    el.style.minWidth = 'max-content';
    el.style.minHeight = 'max-content';
    el.style.maxWidth = 'max-content';
    el.style.maxHeight = 'max-content';
}

function styleAsSizeMinContent(el: HTMLElement) {
    el.style.width = 'min-content';
    el.style.height = 'min-content';
    el.style.minWidth = 'min-content';
    el.style.minHeight = 'min-content';
    el.style.maxWidth = 'min-content';
    el.style.maxHeight = 'min-content';
}

function styleAsSizeFitContent(el: HTMLElement) {
    el.style.width = 'fit-content';
    el.style.height = 'fit-content';
    el.style.minWidth = 'fit-content';
    el.style.minHeight = 'fit-content';
    el.style.maxWidth = 'fit-content';
    el.style.maxHeight = 'fit-content';
}

function styleAsSizeStretch(el: HTMLElement) {
    el.style.width = 'stretch';
    el.style.height = 'stretch';
    el.style.minWidth = 'stretch';
    el.style.minHeight = 'stretch';
    el.style.maxWidth = 'stretch';
    el.style.maxHeight = 'stretch';
}

function styleAsSizeInherit(el: HTMLElement) {
    el.style.width = 'inherit';
    el.style.height = 'inherit';
    el.style.minWidth = 'inherit';
    el.style.minHeight = 'inherit';
    el.style.maxWidth = 'inherit';
    el.style.maxHeight = 'inherit';
}

function styleAsSizeInitial(el: HTMLElement) {
    el.style.width = 'initial';
    el.style.height = 'initial';
    el.style.minWidth = 'initial';
    el.style.minHeight = 'initial';
    el.style.maxWidth = 'initial';
    el.style.maxHeight = 'initial';
}

function styleAsSizeRevert(el: HTMLElement) {
    el.style.width = 'revert';
    el.style.height = 'revert';
    el.style.minWidth = 'revert';
    el.style.minHeight = 'revert';
    el.style.maxWidth = 'revert';
    el.style.maxHeight = 'revert';
}

function styleAsSizeRevertLayer(el: HTMLElement) {
    el.style.width = 'revert-layer';
    el.style.height = 'revert-layer';
    el.style.minWidth = 'revert-layer';
    el.style.minHeight = 'revert-layer';
    el.style.maxWidth = 'revert-layer';
    el.style.maxHeight = 'revert-layer';
}

function styleAsSizeUnset(el: HTMLElement) {
    el.style.width = 'unset';
    el.style.height = 'unset';
    el.style.minWidth = 'unset';
    el.style.minHeight = 'unset';
    el.style.maxWidth = 'unset';
    el.style.maxHeight = 'unset';
}

function styleAsMinWidth(el: HTMLElement, width: number | QinGrandeur) {
    let dim = width;
    if (typeof width === "string") {
        dim = getDimensionSize(width).width;
    }
    el.style.minWidth = getPixelsOrInitial(dim as number);
}

function styleAsMinWidthPer(el: HTMLElement, width: number) {
    el.style.minWidth = getPercentOrInitial(width);
}

function styleAsMinWidthMaxContent(el: HTMLElement) {
    el.style.minWidth = 'max-content';
}

function styleAsMinWidthMinContent(el: HTMLElement) {
    el.style.minWidth = 'min-content';
}

function styleAsMinWidthFitContent(el: HTMLElement) {
    el.style.minWidth = 'fit-content';
}

function styleAsMinWidthStretch(el: HTMLElement) {
    el.style.minWidth = 'stretch';
}

function styleAsMinWidthInherit(el: HTMLElement) {
    el.style.minWidth = 'inherit';
}

function styleAsMinWidthInitial(el: HTMLElement) {
    el.style.minWidth = 'initial';
}

function styleAsMinWidthRevert(el: HTMLElement) {
    el.style.minWidth = 'revert';
}

function styleAsMinWidthRevertLayer(el: HTMLElement) {
    el.style.minWidth = 'revert-layer';
}

function styleAsMinWidthUnset(el: HTMLElement) {
    el.style.minWidth = 'unset';
}

function styleAsMinHeight(el: HTMLElement, height: number | QinGrandeur) {
    let dim = height;
    if (typeof height === "string") {
        dim = getDimensionSize(height).height;
    }
    el.style.minHeight = getPixelsOrInitial(dim as number);
}

function styleAsMinHeightPer(el: HTMLElement, height: number) {
    el.style.minHeight = getPercentOrInitial(height);
}

function styleAsMinHeightMaxContent(el: HTMLElement) {
    el.style.minHeight = 'max-content';
}

function styleAsMinHeightMinContent(el: HTMLElement) {
    el.style.minHeight = 'min-content';
}

function styleAsMinHeightFitContent(el: HTMLElement) {
    el.style.minHeight = 'fit-content';
}

function styleAsMinHeightStretch(el: HTMLElement) {
    el.style.minHeight = 'stretch';
}

function styleAsMinHeightInherit(el: HTMLElement) {
    el.style.minHeight = 'inherit';
}

function styleAsMinHeightInitial(el: HTMLElement) {
    el.style.minHeight = 'initial';
}

function styleAsMinHeightRevert(el: HTMLElement) {
    el.style.minHeight = 'revert';
}

function styleAsMinHeightRevertLayer(el: HTMLElement) {
    el.style.minHeight = 'revert-layer';
}

function styleAsMinHeightUnset(el: HTMLElement) {
    el.style.minHeight = 'unset';
}

function styleAsMinSize(el: HTMLElement, size: QinDimension | QinGrandeur) {
    let dim = size;
    if (typeof size === "string") {
        dim = getDimensionSize(size);
    }
    if (!dim) {
        dim = { width: null, height: null }
    }
    el.style.minWidth = getPixelsOrInitial((dim as QinDimension).width);
    el.style.minHeight = getPixelsOrInitial((dim as QinDimension).height);
}

function styleAsMinSizePer(el: HTMLElement, size: QinDimension) {
    if (!size) {
        size = { width: null, height: null }
    }
    el.style.minWidth = getPercentOrInitial(size.width);
    el.style.minHeight = getPercentOrInitial(size.height);
}

function styleAsMinSizeMaxContent(el: HTMLElement) {
    el.style.minWidth = 'max-content';
    el.style.minHeight = 'max-content';
}

function styleAsMinSizeMinContent(el: HTMLElement) {
    el.style.minWidth = 'min-content';
    el.style.minHeight = 'min-content';
}

function styleAsMinSizeFitContent(el: HTMLElement) {
    el.style.minWidth = 'fit-content';
    el.style.minHeight = 'fit-content';
}

function styleAsMinSizeStretch(el: HTMLElement) {
    el.style.minWidth = 'stretch';
    el.style.minHeight = 'stretch';
}

function styleAsMinSizeInherit(el: HTMLElement) {
    el.style.minWidth = 'inherit';
    el.style.minHeight = 'inherit';
}

function styleAsMinSizeInitial(el: HTMLElement) {
    el.style.minWidth = 'initial';
    el.style.minHeight = 'initial';
}

function styleAsMinSizeRevert(el: HTMLElement) {
    el.style.minWidth = 'revert';
    el.style.minHeight = 'revert';
}

function styleAsMinSizeRevertLayer(el: HTMLElement) {
    el.style.minWidth = 'revert-layer';
    el.style.minHeight = 'revert-layer';
}

function styleAsMinSizeUnset(el: HTMLElement) {
    el.style.minWidth = 'unset';
    el.style.minHeight = 'unset';
}

function styleAsMaxWidth(el: HTMLElement, width: number | QinGrandeur) {
    let dim = width;
    if (typeof width === "string") {
        dim = getDimensionSize(width).width;
    }
    el.style.maxWidth = getPixelsOrInitial(dim as number);
}

function styleAsMaxWidthPer(el: HTMLElement, width: number) {
    el.style.maxWidth = getPercentOrInitial(width);
}

function styleAsMaxWidthMaxContent(el: HTMLElement) {
    el.style.maxWidth = 'max-content';
}

function styleAsMaxWidthMinContent(el: HTMLElement) {
    el.style.maxWidth = 'min-content';
}

function styleAsMaxWidthFitContent(el: HTMLElement) {
    el.style.maxWidth = 'fit-content';
}

function styleAsMaxWidthStretch(el: HTMLElement) {
    el.style.maxWidth = 'stretch';
}

function styleAsMaxWidthInherit(el: HTMLElement) {
    el.style.maxWidth = 'inherit';
}

function styleAsMaxWidthInitial(el: HTMLElement) {
    el.style.maxWidth = 'initial';
}

function styleAsMaxWidthRevert(el: HTMLElement) {
    el.style.maxWidth = 'revert';
}

function styleAsMaxWidthRevertLayer(el: HTMLElement) {
    el.style.maxWidth = 'revert-layer';
}

function styleAsMaxWidthUnset(el: HTMLElement) {
    el.style.maxWidth = 'unset';
}

function styleAsMaxHeight(el: HTMLElement, height: number | QinGrandeur) {
    let dim = height;
    if (typeof height === "string") {
        dim = getDimensionSize(height).height;
    }
    el.style.maxHeight = getPixelsOrInitial(dim as number);
}

function styleAsMaxHeightPer(el: HTMLElement, height: number) {
    el.style.maxHeight = getPercentOrInitial(height);
}

function styleAsMaxHeightMaxContent(el: HTMLElement) {
    el.style.maxHeight = 'max-content';
}

function styleAsMaxHeightMinContent(el: HTMLElement) {
    el.style.maxHeight = 'min-content';
}

function styleAsMaxHeightFitContent(el: HTMLElement) {
    el.style.maxHeight = 'fit-content';
}

function styleAsMaxHeightStretch(el: HTMLElement) {
    el.style.maxHeight = 'stretch';
}

function styleAsMaxHeightInherit(el: HTMLElement) {
    el.style.maxHeight = 'inherit';
}

function styleAsMaxHeightInitial(el: HTMLElement) {
    el.style.maxHeight = 'initial';
}

function styleAsMaxHeightRevert(el: HTMLElement) {
    el.style.maxHeight = 'revert';
}

function styleAsMaxHeightRevertLayer(el: HTMLElement) {
    el.style.maxHeight = 'revert-layer';
}

function styleAsMaxHeightUnset(el: HTMLElement) {
    el.style.maxHeight = 'unset';
}

function styleAsMaxSize(el: HTMLElement, size: QinDimension | QinGrandeur) {
    let dim = size;
    if (typeof size === "string") {
        dim = getDimensionSize(size);
    }
    if (!dim) {
        dim = { width: null, height: null };
    }
    el.style.maxWidth = getPixelsOrInitial((dim as QinDimension).width);
    el.style.maxHeight = getPixelsOrInitial((dim as QinDimension).height);
}

function styleAsMaxSizePer(el: HTMLElement, size: QinDimension) {
    if (!size) {
        size = { width: null, height: null };
    }
    el.style.maxWidth = getPercentOrInitial(size.width);
    el.style.maxHeight = getPercentOrInitial(size.height);
}

function styleAsMaxSizeMaxContent(el: HTMLElement) {
    el.style.maxWidth = 'max-content';
    el.style.maxHeight = 'max-content';
}

function styleAsMaxSizeMinContent(el: HTMLElement) {
    el.style.maxWidth = 'min-content';
    el.style.maxHeight = 'min-content';
}

function styleAsMaxSizeFitContent(el: HTMLElement) {
    el.style.maxWidth = 'fit-content';
    el.style.maxHeight = 'fit-content';
}

function styleAsMaxSizeStretch(el: HTMLElement) {
    el.style.maxWidth = 'stretch';
    el.style.maxHeight = 'stretch';
}

function styleAsMaxSizeInherit(el: HTMLElement) {
    el.style.maxWidth = 'inherit';
    el.style.maxHeight = 'inherit';
}

function styleAsMaxSizeInitial(el: HTMLElement) {
    el.style.maxWidth = 'initial';
    el.style.maxHeight = 'initial';
}

function styleAsMaxSizeRevert(el: HTMLElement) {
    el.style.maxWidth = 'revert';
    el.style.maxHeight = 'revert';
}

function styleAsMaxSizeRevertLayer(el: HTMLElement) {
    el.style.maxWidth = 'revert-layer';
    el.style.maxHeight = 'revert-layer';
}

function styleAsMaxSizeUnset(el: HTMLElement) {
    el.style.maxWidth = 'unset';
    el.style.maxHeight = 'unset';
}

function styleAsForeground(el: HTMLElement, foreground: string) {
    el.style.color = foreground;
}

function styleAsBackground(el: HTMLElement, background: string) {
    el.style.background = background;
}

function styleAsBackgroundImage(el: HTMLElement, imageURL: string) {
    if (imageURL) {
        el.style.backgroundImage = "url('" + imageURL + "')";
    } else {
        styleAsBackgroundImageInitial(el);
    }
}

function styleAsBackgroundImageInitial(el: HTMLElement) {
    el.style.backgroundImage = "initial";
}

function styleAsZIndex(el: HTMLElement, index: number) {
    if (index == null || index == undefined) {
        el.style.zIndex = "initial";
    } else {
        el.style.zIndex = index.toString();
    }
}

function styleAsCursorAuto(el: HTMLElement) {
    el.style.cursor = "auto";
}

function styleAsCursorDefault(el: HTMLElement) {
    el.style.cursor = "default";
}

function styleAsCursorNone(el: HTMLElement) {
    el.style.cursor = "none";
}

function styleAsCursorContextMenu(el: HTMLElement) {
    el.style.cursor = "context-menu";
}

function styleAsCursorHelp(el: HTMLElement) {
    el.style.cursor = "help";
}

function styleAsCursorPointer(el: HTMLElement) {
    el.style.cursor = "pointer";
}

function styleAsCursorProgress(el: HTMLElement) {
    el.style.cursor = "progress";
}

function styleAsCursorWait(el: HTMLElement) {
    el.style.cursor = "wait";
}

function styleAsCursorCell(el: HTMLElement) {
    el.style.cursor = "cell";
}

function styleAsCursorCrosshair(el: HTMLElement) {
    el.style.cursor = "crosshair";
}

function styleAsCursorText(el: HTMLElement) {
    el.style.cursor = "text";
}

function styleAsCursorVerticalText(el: HTMLElement) {
    el.style.cursor = "vertical-text";
}

function styleAsCursorAlias(el: HTMLElement) {
    el.style.cursor = "alias";
}

function styleAsCursorCopy(el: HTMLElement) {
    el.style.cursor = "copy";
}

function styleAsCursorMove(el: HTMLElement) {
    el.style.cursor = "move";
}

function styleAsCursorNoDrop(el: HTMLElement) {
    el.style.cursor = "no-drop";
}

function styleAsCursorNotAllowed(el: HTMLElement) {
    el.style.cursor = "not-allowed";
}

function styleAsCursorGrab(el: HTMLElement) {
    el.style.cursor = "grab";
}

function styleAsCursorGrabbing(el: HTMLElement) {
    el.style.cursor = "grabbing";
}

function styleAsCursorAllScroll(el: HTMLElement) {
    el.style.cursor = "all-scroll";
}

function styleAsCursorColResize(el: HTMLElement) {
    el.style.cursor = "col-resize";
}

function styleAsCursorRowResize(el: HTMLElement) {
    el.style.cursor = "row-resize";
}

function styleAsCursorNResize(el: HTMLElement) {
    el.style.cursor = "n-resize";
}

function styleAsCursorEResize(el: HTMLElement) {
    el.style.cursor = "e-resize";
}

function styleAsCursorSResize(el: HTMLElement) {
    el.style.cursor = "s-resize";
}

function styleAsCursorWResize(el: HTMLElement) {
    el.style.cursor = "w-resize";
}

function styleAsCursorNEResize(el: HTMLElement) {
    el.style.cursor = "ne-resize";
}

function styleAsCursorNWResize(el: HTMLElement) {
    el.style.cursor = "nw-resize";
}

function styleAsCursorSEResize(el: HTMLElement) {
    el.style.cursor = "se-resize";
}

function styleAsCursorSWResize(el: HTMLElement) {
    el.style.cursor = "sw-resize";
}

function styleAsCursorEWResize(el: HTMLElement) {
    el.style.cursor = "ew-resize";
}

function styleAsCursorNSResize(el: HTMLElement) {
    el.style.cursor = "ns-resize";
}

function styleAsCursorNEWSResize(el: HTMLElement) {
    el.style.cursor = "nesw-resize";
}

function styleAsCursorNWSEResize(el: HTMLElement) {
    el.style.cursor = "nwse-resize";
}

function styleAsCursorZoomIn(el: HTMLElement) {
    el.style.cursor = "zoom-in";
}

function styleAsCursorZoomOut(el: HTMLElement) {
    el.style.cursor = "zoom-out";
}

function styleAsCursorInherit(el: HTMLElement) {
    el.style.cursor = "inherit";
}

function styleAsCursorInitial(el: HTMLElement) {
    el.style.cursor = "initial";
}

function styleAsCursorRevert(el: HTMLElement) {
    el.style.cursor = "revert";
}

function styleAsCursorRevertLayer(el: HTMLElement) {
    el.style.cursor = "revert-layer";
}

function styleAsCursorUnset(el: HTMLElement) {
    el.style.cursor = "unset";
}

function styleAsWhiteSpaceNormal(el: HTMLElement) {
    el.style.whiteSpace = "normal";
}

function styleAsWhiteSpacePre(el: HTMLElement) {
    el.style.whiteSpace = "pre";
}

function styleAsWhiteSpacePreWrap(el: HTMLElement) {
    el.style.whiteSpace = "pre-wrap";
}

function styleAsWhiteSpacePreLine(el: HTMLElement) {
    el.style.whiteSpace = "pre-line";
}

function styleAsWhiteSpaceNoWrap(el: HTMLElement) {
    el.style.whiteSpace = "nowrap";
}

function styleAsWhiteSpaceWrap(el: HTMLElement) {
    el.style.whiteSpace = "wrap";
}

function styleAsWhiteSpaceBreakSpaces(el: HTMLElement) {
    el.style.whiteSpace = "break-spaces";
}

function styleAsWhiteSpaceCollapse(el: HTMLElement) {
    el.style.whiteSpace = "collapse";
}

function styleAsWhiteSpacePreserveNowrap(el: HTMLElement) {
    el.style.whiteSpace = "preserve nowrap";
}

function styleAsWhiteSpaceInherit(el: HTMLElement) {
    el.style.whiteSpace = "inherit";
}

function styleAsWhiteSpaceInitial(el: HTMLElement) {
    el.style.whiteSpace = "initial";
}

function styleAsWhiteSpaceRevert(el: HTMLElement) {
    el.style.whiteSpace = "revert";
}

function styleAsWhiteSpaceRevertLayer(el: HTMLElement) {
    el.style.whiteSpace = "revert-layer";
}

function styleAsWhiteSpaceUnset(el: HTMLElement) {
    el.style.whiteSpace = "unset";
}

function applyStyles(element: HTMLElement, styles: Partial<CSSStyleDeclaration>) {
    if (element && styles) {
        for (const key in styles) {
            element.style[key] = styles[key];
        }
    }
}

function getPixelsOrInitial(value: number): string {
    if (value == null || value == undefined) {
        return "initial";
    }
    return value + "px";
}

function getPercentOrInitial(value: number): string {
    if (value == null || value == undefined) {
        return "initial";
    }
    return value + "%";
}

function getWindowSize(): QinDimension {
    return {
        width: document.body.clientWidth,
        height: document.body.clientHeight,
    };
}

function getWindowSizeStyle(): QinGrandeur {
    const width = getWindowSize().width;
    if (width < 600) {
        return QinGrandeur.SMALL;
    } else if (width < 1000) {
        return QinGrandeur.MEDIUM;
    } else {
        return QinGrandeur.LARGE;
    }
}

function hideAllIFrames() {
    let docIFrames = document.getElementsByTagName("iframe");
    for (const doc_iframe of docIFrames) {
        doc_iframe.style.visibility = "hidden";
    }
}

function showAllIFrames() {
    let docIFrames = document.getElementsByTagName("iframe");
    for (const doc_iframe of docIFrames) {
        doc_iframe.style.visibility = "visible";
    }
}

function disableSelection(element: HTMLElement) {
    element.style.userSelect = "none";
    element.style.webkitUserSelect = "none";
    element.onselectstart = QinArms.stopEvent;
}

function clearSelection() {
    setTimeout(() => {
        if (window.getSelection) {
            window.getSelection().removeAllRanges();
        }
    }, 360);
}

function isElementVisibleInScroll(element: HTMLElement) {
    if (element.parentElement) {
        if (element.offsetTop < element.parentElement.scrollTop) {
            return false;
        }
        if (element.offsetLeft < element.parentElement.scrollLeft) {
            return false;
        }
        if (
            element.clientWidth >
            element.parentElement.clientWidth -
                (element.offsetLeft - element.parentElement.scrollLeft)
        ) {
            return false;
        }
        if (
            element.clientHeight >
            element.parentElement.clientHeight -
                (element.offsetTop - element.parentElement.scrollTop)
        ) {
            return false;
        }
    }
    return true;
}

function getWidthByMaxLength(maxLength: number): string {
    let positions = Math.min(Math.max(maxLength - 10, 0), 90);
    let width = Math.floor(90 + (positions * 7) / 3);
    return width + "px";
}

function getDimension(el: HTMLElement): QinDimension {
    return {
        width: parseInt(el.style.width),
        height: parseInt(el.style.height),
    };
}

function getDimensionSize(size: QinGrandeur): QinDimension {
    switch (size) {
        case QinGrandeur.HUGE:
            return getDimensionHuge();
        case QinGrandeur.GRAND:
            return getDimensionGrand();
        case QinGrandeur.LARGE:
            return getDimensionLarge();
        case QinGrandeur.MEDIUM:
            return getDimensionMedium();
        case QinGrandeur.SMALL:
            return getDimensionSmall();
        case QinGrandeur.MICRO:
            return getDimensionMicro();
        case QinGrandeur.PICO:
            return getDimensionPico();
        default:
            return getDimensionSmall();
    }
}

const dimensionHuge: QinDimension = {
    width: 240,
    height: 240,
};

function getDimensionHuge(): QinDimension {
    return dimensionHuge;
}

const dimensionGrand: QinDimension = {
    width: 120,
    height: 120,
};

function getDimensionGrand(): QinDimension {
    return dimensionGrand;
}

const dimensionLarge: QinDimension = {
    width: 60,
    height: 60,
};

function getDimensionLarge(): QinDimension {
    return dimensionLarge;
}

const dimensionMedium: QinDimension = {
    width: 40,
    height: 40,
};

function getDimensionMedium(): QinDimension {
    return dimensionMedium;
}

const dimensionSmall: QinDimension = {
    width: 20,
    height: 20,
};
function getDimensionSmall(): QinDimension {
    return dimensionSmall;
}

const dimensionMicro: QinDimension = {
    width: 15,
    height: 15,
};
function getDimensionMicro(): QinDimension {
    return dimensionMicro;
}

const dimensionPico: QinDimension = {
    width: 10,
    height: 10,
};

function getDimensionPico(): QinDimension {
    return dimensionPico;
}

export type QinPoint = {
    posX: number;
    posY: number;
}

export type QinDimension = {
    width: number;
    height: number;
}

export type QinBounds = {
    posX: number;
    posY: number;
    width: number;
    height: number;
}

export enum QinGrandeur {
    HUGE = "HUGE",
    GRAND = "GRAND",
    LARGE = "LARGE",
    MEDIUM = "MEDIUM",
    SMALL = "SMALL",
    MICRO = "MICRO",
    PICO = "PICO",
}

export enum BorderStyle {
    NONE = "none",
    HIDDEN = "hidden",
    DOTTED = "dotted",
    DASHED = "dashed",
    SOLID = "solid",
    DOUBLE = "double",
    GROOVE = "groove",
    RIDGE = "ridge",
    INSET = "inset",
    OUTSET = "outset",
}

export const QinSkin = {
    styles: QinStyles,
    styleAsBody,
    styleAsWhole,
    styleAsBase,
    styleAsSpaced,
    styleAsBordered,
    styleAsEditable,
    styleAsReadOnly,
    styleAsActionable,
    styleMaxSizeForNotOverFlow,
    styleAsScroll,
    styleAsMargin,
    styleAsMarginTop,
    styleAsMarginBottom,
    styleAsMarginLeft,
    styleAsMarginRight,
    styleAsPadding,
    styleAsPaddingTop,
    styleAsPaddingBottom,
    styleAsPaddingLeft,
    styleAsPaddingRight,
    styleAsBorder,
    styleAsBorderTop,
    styleAsBorderBottom,
    styleAsBorderLeft,
    styleAsBorderRight,
    styleAsBorderRadius,
    styleAsBorderTopLeftRadius,
    styleAsBorderTopRightRadius,
    styleAsBorderBottomRightRadius,
    styleAsBorderBottomLeftRadius,
    styleAsPositionStatic,
    styleAsPositionRelative,
    styleAsPositionAbsolute,
    styleAsPositionFixed,
    styleAsPositionSticky,
    styleAsPositionInherit,
    styleAsPositionInitial,
    styleAsPositionRevert,
    styleAsPositionRevertLayer,
    styleAsPositionUnset,
    styleAsDisplayBlock,
    styleAsDisplayInline,
    styleAsDisplayInlineBlock,
    styleAsDisplayFlex,
    styleAsDisplayInlineFlex,
    styleAsDisplayGrid,
    styleAsDisplayInlineGrid,
    styleAsDisplayFlowRoot,
    styleAsDisplayNone,
    styleAsDisplayContents,
    styleAsDisplayTable,
    styleAsDisplayTableRow,
    styleAsDisplayListItem,
    styleAsDisplayInherit,
    styleAsDisplayInitial,
    styleAsDisplayRevert,
    styleAsDisplayRevertLayer,
    styleAsDisplayUnset,
    styleAsFlexDirectionRow,
    styleAsFlexDirectionRowReverse,
    styleAsFlexDirectionColumn,
    styleAsFlexDirectionColumnReverse,
    styleAsFlexWrap,
    styleAsFlexWrapNot,
    styleAsFlexWrapReverse,
    styleAsFlexMin,
    styleAsFlexMax,
    styleAsFlexProp,
    styleAsAllCentered,
    styleAsJustifyContentFlexStart,
    styleAsJustifyContentFlexEnd,
    styleAsJustifyContentCenter,
    styleAsJustifyContentSpaceBetween,
    styleAsJustifyContentSpaceAround,
    styleAsJustifyContentSpaceEvenly,
    styleAsJustifyContentInitial,
    styleAsJustifyContentInherit,
    styleAsAlignItemsNormal,
    styleAsAlignItemsStretch,
    styleAsAlignItemsCenter,
    styleAsAlignItemsStart,
    styleAsAlignItemsEnd,
    styleAsAlignItemsFlexStart,
    styleAsAlignItemsFlexEnd,
    styleAsAlignItemsSelfStart,
    styleAsAlignItemsSelfEnd,
    styleAsAlignItemsAnchorCenter,
    styleAsAlignItemsBaseline,
    styleAsAlignItemsFirstBaseline,
    styleAsAlignItemsLastBaseline,
    styleAsAlignItemsSafeCenter,
    styleAsAlignItemsUnsafeCenter,
    styleAsAlignItemsInherit,
    styleAsAlignItemsInitial,
    styleAsAlignItemsRevert,
    styleAsAlignItemsRevertLayer,
    styleAsAlignItemsUnset,
    styleAsBounds,
    styleAsTop,
    styleAsLeft,
    styleAsBottom,
    styleAsRight,
    styleAsTopLeft,
    styleAsTopRight,
    styleAsBottomRight,
    styleAsBottomLeft,
    styleAsWidth,
    styleAsWidthPer,
    styleAsWidthMaxContent,
    styleAsWidthMinContent,
    styleAsWidthFitContent,
    styleAsWidthStretch,
    styleAsWidthInherit,
    styleAsWidthInitial,
    styleAsWidthRevert,
    styleAsWidthRevertLayer,
    styleAsWidthUnset,
    styleAsHeight,
    styleAsHeightPer,
    styleAsHeightMaxContent,
    styleAsHeightMinContent,
    styleAsHeightFitContent,
    styleAsHeightStretch,
    styleAsHeightInherit,
    styleAsHeightInitial,
    styleAsHeightRevert,
    styleAsHeightRevertLayer,
    styleAsHeightUnset,
    styleAsSize,
    styleAsSizePer,
    styleAsSizeMaxContent,
    styleAsSizeMinContent,
    styleAsSizeFitContent,
    styleAsSizeStretch,
    styleAsSizeInherit,
    styleAsSizeInitial,
    styleAsSizeRevert,
    styleAsSizeRevertLayer,
    styleAsSizeUnset,
    styleAsMinWidth,
    styleAsMinWidthPer,
    styleAsMinWidthMaxContent,
    styleAsMinWidthMinContent,
    styleAsMinWidthFitContent,
    styleAsMinWidthStretch,
    styleAsMinWidthInherit,
    styleAsMinWidthInitial,
    styleAsMinWidthRevert,
    styleAsMinWidthRevertLayer,
    styleAsMinWidthUnset,
    styleAsMinHeight,
    styleAsMinHeightPer,
    styleAsMinHeightMaxContent,
    styleAsMinHeightMinContent,
    styleAsMinHeightFitContent,
    styleAsMinHeightStretch,
    styleAsMinHeightInherit,
    styleAsMinHeightInitial,
    styleAsMinHeightRevert,
    styleAsMinHeightRevertLayer,
    styleAsMinHeightUnset,
    styleAsMinSize,
    styleAsMinSizePer,
    styleAsMinSizeMaxContent,
    styleAsMinSizeMinContent,
    styleAsMinSizeFitContent,
    styleAsMinSizeStretch,
    styleAsMinSizeInherit,
    styleAsMinSizeInitial,
    styleAsMinSizeRevert,
    styleAsMinSizeRevertLayer,
    styleAsMinSizeUnset,
    styleAsMaxWidth,
    styleAsMaxWidthPer,
    styleAsMaxWidthMaxContent,
    styleAsMaxWidthMinContent,
    styleAsMaxWidthFitContent,
    styleAsMaxWidthStretch,
    styleAsMaxWidthInherit,
    styleAsMaxWidthInitial,
    styleAsMaxWidthRevert,
    styleAsMaxWidthRevertLayer,
    styleAsMaxWidthUnset,
    styleAsMaxHeight,
    styleAsMaxHeightPer,
    styleAsMaxHeightMaxContent,
    styleAsMaxHeightMinContent,
    styleAsMaxHeightFitContent,
    styleAsMaxHeightStretch,
    styleAsMaxHeightInherit,
    styleAsMaxHeightInitial,
    styleAsMaxHeightRevert,
    styleAsMaxHeightRevertLayer,
    styleAsMaxHeightUnset,
    styleAsMaxSize,
    styleAsMaxSizePer,
    styleAsMaxSizeMaxContent,
    styleAsMaxSizeMinContent,
    styleAsMaxSizeFitContent,
    styleAsMaxSizeStretch,
    styleAsMaxSizeInherit,
    styleAsMaxSizeInitial,
    styleAsMaxSizeRevert,
    styleAsMaxSizeRevertLayer,
    styleAsMaxSizeUnset,
    styleAsForeground,
    styleAsBackground,
    styleAsBackgroundImage,
    styleAsBackgroundImageInitial,
    styleAsZIndex,
    styleAsCursorAuto,
    styleAsCursorDefault,
    styleAsCursorNone,
    styleAsCursorContextMenu,
    styleAsCursorHelp,
    styleAsCursorPointer,
    styleAsCursorProgress,
    styleAsCursorWait,
    styleAsCursorCell,
    styleAsCursorCrosshair,
    styleAsCursorText,
    styleAsCursorVerticalText,
    styleAsCursorAlias,
    styleAsCursorCopy,
    styleAsCursorMove,
    styleAsCursorNoDrop,
    styleAsCursorNotAllowed,
    styleAsCursorGrab,
    styleAsCursorGrabbing,
    styleAsCursorAllScroll,
    styleAsCursorColResize,
    styleAsCursorRowResize,
    styleAsCursorNResize,
    styleAsCursorEResize,
    styleAsCursorSResize,
    styleAsCursorWResize,
    styleAsCursorNEResize,
    styleAsCursorNWResize,
    styleAsCursorSEResize,
    styleAsCursorSWResize,
    styleAsCursorEWResize,
    styleAsCursorNSResize,
    styleAsCursorNEWSResize,
    styleAsCursorNWSEResize,
    styleAsCursorZoomIn,
    styleAsCursorZoomOut,
    styleAsCursorInherit,
    styleAsCursorInitial,
    styleAsCursorRevert,
    styleAsCursorRevertLayer,
    styleAsCursorUnset,
    styleAsWhiteSpaceNormal,
    styleAsWhiteSpacePre,
    styleAsWhiteSpacePreWrap,
    styleAsWhiteSpacePreLine,
    styleAsWhiteSpaceNoWrap,
    styleAsWhiteSpaceWrap,
    styleAsWhiteSpaceBreakSpaces,
    styleAsWhiteSpaceCollapse,
    styleAsWhiteSpacePreserveNowrap,
    styleAsWhiteSpaceInitial,
    styleAsWhiteSpaceInherit,
    styleAsWhiteSpaceRevert,
    styleAsWhiteSpaceRevertLayer,
    styleAsWhiteSpaceUnset,
    applyStyles,
    getPixelsOrInitial,
    getPercentOrInitial,
    getWindowSize,
    getWindowSizeStyle,
    hideAllIFrames,
    showAllIFrames,
    disableSelection,
    clearSelection,
    isElementVisibleInScroll,
    getWidthByMaxLength,
    getDimension,
    getDimensionSize,
    getDimensionHuge,
    getDimensionGrand,
    getDimensionLarge,
    getDimensionMedium,
    getDimensionSmall,
    getDimensionMicro,
    getDimensionPico,
};
