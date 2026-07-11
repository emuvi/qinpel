import { Nature, NamedChars, QinHead } from "qin_soul";
import { QinEdit } from "./qin-edit";
import { QinLine } from "./qin-line";
import { QinChars } from "./qin-chars";
import { QinTitled } from "./qin-titled";
import { QinLabel } from "./qin-label";

export class QinNamedChars extends QinEdit<NamedChars> {
    
    private _nameLabel = new QinLabel("Name");
    private _nameChars = new QinChars();
    private _nameTitled = new QinTitled({label: this._nameLabel, items: [this._nameChars]});
    private _dataLabel = new QinLabel("Chars");
    private _dataChars = new QinChars();
    private _dataTitled = new QinTitled({label: this._dataLabel, items: [this._dataChars]});
    
    public constructor(options?: QinNamedCharsSet, isQindred?: string) {
        super(undefined, (isQindred ? isQindred + "_" : "") + "named-chars", new QinLine());
        if (options?.name) {
            this._nameChars.value = options.name;
        }
        if (options?.chars) {
            this._dataChars.value = options.chars;
        }
        if (options?.labels) {
            if (options?.labels?.name) {
                this._nameLabel.title = options.labels.name;
            }
            if (options?.labels?.chars) {
                this._dataLabel.title = options.labels.chars;
            }
        }
        this._nameTitled.install(this);
        this._dataTitled.install(this);
    }
    
    public override castedQine(): QinLine {
        return this.qinedBase as QinLine;
    }

    public override getNature(): Nature {
        return Nature.Object;
    }

    public override mayChange(): HTMLElement[] {
        return [...this._nameChars.mayChange(), ...this._dataChars.mayChange()];
    }

    public override turnReadOnly(): void {
        this._nameChars.turnReadOnly();
        this._dataChars.turnReadOnly();
    }

    public override turnEditable(): void {
        this._nameChars.turnEditable();
        this._dataChars.turnEditable();
    }

    public override isEditable(): boolean {
        return this._dataChars.isEditable();
    }

    protected override _getData(): NamedChars {
        return {
            name: this._nameChars.value,
            chars: this._dataChars.value
        };
    }

    protected override _setData(data: NamedChars) {
        this._nameChars.value = data?.name;
        this._dataChars.value = data?.chars;
    }
}

export type QinNamedCharsSet = {
    name?: string;
    chars?: string;
    labels?: {
        name?: string;
        chars?: string;
    };
};