import { Nature } from "qin_soul";
import { QinEdit } from "./qin-edit";

export class QinCombo extends QinEdit<string> {
    
    private _optGroups = new Array<HTMLOptGroupElement>();

    public constructor(options?: QinComboSet, isQindred?: string) {
        super(undefined, (isQindred ? isQindred + "_" : "") + "combo", document.createElement("select"));
        if (options?.items) {
            for (let item of options.items) {
                this.addItem(item);
            }
        }
        if (options?.ofEnum) {
            for (const title in options.ofEnum) {
                const value = options.ofEnum[title];
                this.addItem({title, value});
            }
        }
        if (options?.selected) {
            this._setData(options.selected);
        }
        if (options?.readOnly) {
            this.turnReadOnly();
        }
    }

    public override castedQine(): HTMLSelectElement {
        return this.qinedHTML as HTMLSelectElement;
    }

    public override getNature(): Nature {
        return Nature.Chars;
    }

    public override mayChange(): HTMLElement[] {
        return [this.qinedHTML];
    }

    public override turnReadOnly(): void {
        this.castedQine().disabled = true;
        this.styleAsReadOnly();
    }

    public override turnEditable(): void {
        this.castedQine().disabled = false;
        this.styleAsEditable();
    }

    public override isEditable(): boolean {
        return !this.castedQine().disabled;
    }

    protected override _getData(): string {
        return this.castedQine().value;
    }

    protected override _setData(data: string) {
        this.castedQine().value = data;
    }

    public addSame(titleAndValue: string) {
        this.addItem({ title: titleAndValue, value: titleAndValue });
    }

    public addItem(item: QinComboItem): QinCombo {
        const option = document.createElement("option");
        option.text = item.title;
        option.value = item.value;
        if (item.selected != undefined && item.selected != null) {
            option.selected = item.selected;
        }
        let group = this.getGroup(item.group);
        if (group) {
            group.appendChild(option);
        } else {
            this.qinedHTML.appendChild(option);
        }
        return this;
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinCombo {
        super.styled(styles);
        return this;
    }

    private getGroup(label: string): HTMLOptGroupElement {
        if (!label) {
            return null;
        }
        for (let group of this._optGroups) {
            if (group.label == label) {
                return group;
            }
        }
        let newGroup = document.createElement("optgroup");
        newGroup.label = label;
        this._optGroups.push(newGroup);
        this.qinedHTML.appendChild(newGroup);
        return newGroup;
    }
}

export type QinComboSet = {
    items?: QinComboItem[];
    ofEnum?: object;
    selected?: string;
    readOnly?: boolean;
};

export type QinComboItem = {
    group?: string;
    title: string;
    value: string;
    selected?: boolean;
};
