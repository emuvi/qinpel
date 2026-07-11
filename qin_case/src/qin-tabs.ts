import { QinStyles } from "qin_soul";
import { QinBase } from "./qin-base";
import { QinButtonPick } from "./qin-button-pick";
import { QinColumn } from "./qin-column";
import { QinLabel } from "./qin-label";
import { QinLine } from "./qin-line";
import { QinPanel } from "./qin-panel";

export class QinTabs extends QinColumn {

    private _buttonsLine = new QinLine();
    private _bodyPanel = new QinPanel();
    private _tabsRef: QinTabRef[] = [];
    private _selected: string = null;

    public constructor(options?: QinTabsSet, isQindred?: string) {
        super(null, (isQindred ? isQindred + "_" : "") + "tabs");
        this._buttonsLine.styleAsMargin(0);
        this._buttonsLine.styleAsPaddingLeft(5);
        this._bodyPanel.styleAsMargin(0);
        this._bodyPanel.styleAsBorder(1, QinStyles.ColorForeground);
        this._bodyPanel.styleAsBorderRadius(3);
        this._bodyPanel.styleAsPadding(5);
        if (options?.initial) {
            for (const tab of options.initial) {
                this.addTab(tab);
            }
        }
        this._buttonsLine.install(this);
        this._bodyPanel.install(this);
    }

    public get buttonsLine(): QinLine {
        return this._buttonsLine;
    }

    public get bodyPanel(): QinPanel {
        return this._bodyPanel;
    }

    public get selected(): string {
        return this._selected;
    }

    public set selected(title: string) {
        if (this._selected !== title) {
            this.showTab(title);
        }
    }

    public addTab(tab: QinTab): QinTabs {
        const button = new QinButtonPick({label: new QinLabel(tab.title)});
        button.styleAsMargin(0);
        button.styleAsMarginRight(1);
        button.styleAsBorderBottomRightRadius(0);
        button.styleAsBorderBottomLeftRadius(0);
        button.addActionMain((_) => this.showViewer(tab.viewer));
        button.install(this._buttonsLine);
        let first = this._tabsRef.length === 0;
        let tabRef = {
            title: tab.title,
            viewer: tab.viewer,
            button,
        };
        this._tabsRef.push(tabRef);
        if (first) {
            this.showViewer(tab.viewer);
        }
        return this;
    }

    public showTab(title: string): QinTabs {
        for (const tab of this._tabsRef) {
            if (tab.title == title) {
                this.showViewer(tab.viewer);
                break;
            }
        }
        return this;
    }

    public showViewer(viewer: QinBase): QinTabs {
        this._bodyPanel.unInstallChildren();
        viewer.install(this._bodyPanel);
        this._selected = null;
        for (const tab of this._tabsRef) {
            if (tab.viewer === viewer) {
                tab.button.pick();
                this._selected = tab.title;
            } else {
                tab.button.unPick();
            }
        }
        return this;
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinTabs {
        super.styled(styles);
        return this;
    }

    public override styleAsWhole() {
        super.styleAsWhole();
        this._bodyPanel.styleAsWhole();
    }

}

export type QinTabsSet = {
    initial?: QinTab[];
};

export type QinTab = {
    title: string;
    viewer: QinBase;
};

type QinTabRef = {
    title: string;
    viewer: QinBase;
    button: QinButtonPick;
};
