import { QinBase } from "./qin-base";
import { QinColumn } from "./qin-column";
import { QinPanel, QinPanelSet } from "./qin-panel";

export class QinRows extends QinColumn {
    
    private _panelList: QinPanel[];

    public constructor(options?: QinRowsSet & QinPanelSet, isQindred?: string) {
        super(options, (isQindred ? isQindred + "_" : "") + "rows");
        if (options?.rows) {
            this._panelList = options.rows;
        } else {
            this._panelList = [];
        }
        if (options?.size) {
            while (this._panelList.length < options.size) {
                this.newRow();
            }
        }
    }

    public newRow(): QinPanel {
        let row = new QinPanel();
        row.install(this);
        this._panelList.push(row);
        return row;
    }

    public putRow(row: QinPanel): QinRows {
        row.install(this);
        this._panelList.push(row);
        return this;
    }

    public delRow(row: number): QinRows {
        if (row < 0 || row >= this._panelList.length) {
            return this;
        }
        let panel = this._panelList[row];
        panel.unInstall();
        this._panelList.splice(row, 1);
        return this;
    }

    public clearRows(): QinRows {
        while (this._panelList.length > 0) {
            this.delRow(this._panelList.length - 1);
        }
        return this;
    }

    public lastRow(): QinPanel {
        if (this._panelList.length === 0) {
            return this.newRow();
        }
        return this._panelList[this._panelList.length - 1];
    }

    public putOn(row: number, item: QinBase): QinRows {
        while (row >= this._panelList.length) {
            this.newRow();
        }
        this._panelList[row].put(item);
        return this;
    }

    public override put(item: QinBase): QinRows {
        item.install(this.lastRow());
        return this;
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinRows {
        super.styled(styles);
        return this;
    }
}

export type QinRowsSet = {
    rows?: QinPanel[];
    size?: number;
};
