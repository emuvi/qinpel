import { Nature } from "qin_soul";
import { QinEdit } from "./qin-edit";
import { QinStack } from "./qin-stack";
import { QinColumn } from "./qin-column";
import { QinPanel } from "./qin-panel";
import { QinButton } from "./qin-button";
import { QinIcon } from "./qin-icon";
import { QinAsset } from "./qin-assets";
import { QinIconPick } from "./qin-icon-pick";
import { QinTitled } from "./qin-titled";

export class QinMap<K, V> extends QinEdit<Map<K, V>> {
    
    private _actListNewButton = new QinButton({icon: new QinIcon(QinAsset.FaceAdd)});
    private _actListEditIcon = new QinIcon(QinAsset.FacePencil);
    private _actListDelIcon = new QinIcon(QinAsset.FaceTrash);
    private _actListIconPick = new QinIconPick({icons: [this._actListEditIcon, this._actListDelIcon]});
    private _actListPanel = new QinPanel({items: [this._actListNewButton, this._actListIconPick]});
    private _listColumn = new QinColumn();

    private _itemsPanelList = new Array<QinPanel>();

    private _actEditConfirmButton = new QinButton({icon: new QinIcon(QinAsset.FaceConfirm)});
    private _actEditCancelButton = new QinButton({icon: new QinIcon(QinAsset.FaceCancel)});
    private _actEditPanel = new QinPanel({items: [this._actEditConfirmButton, this._actEditCancelButton]});
    private _editorKPanel = new QinPanel();
    private _editorVPanel = new QinPanel();
    private _editorColumn = new QinColumn({items: [this._actEditPanel, this._editorKPanel, this._editorVPanel]});

    private _readOnly = false;
    private readonly _editorK?: QinEdit<K>;
    private readonly _editorV?: QinEdit<V>;
    private readonly _titledK?: QinTitled;
    private readonly _titledV?: QinTitled;
    private readonly getKey?: (oldKey: K, newKey: K, newValue: V) => K;
    private readonly getValue?: (oldKey: K, newKey: K, newValue: V) => V;
    
    private _mode = Mode.VIEW;
    private _value = new Map<K, V>();
    private _editKey: K = null;
    
    public constructor(options?: QinMapSet<K, V>, isQindred?: string) {
        super(undefined, (isQindred ? isQindred + "_" : "") + "list", new QinStack());
        if ((!options?.editorK && !options?.editorV) || options?.readonly) {
            this.turnReadOnly();
        }
        this._titledK = options?.titledK;
        this._titledV = options?.titledV;
        this.getKey = options?.getKey;
        this.getValue = options?.getValue;
        if (options?.editorK || options?.editorV) {
            if (options?.editorK) {
                this._editorK = options.editorK;
                if (options.titledK) {
                    options.titledK.install(this._editorKPanel);
                } else {
                    this._editorK.install(this._editorKPanel);
                }
            }
            if (options?.editorV) {
                this._editorV = options.editorV;
                if (options.titledV) {
                    options.titledV.install(this._editorVPanel);
                } else {
                    this._editorV.install(this._editorVPanel);
                }
            }
            this._initEditor();
            this._actListPanel.install(this._listColumn);
            this.castedQine().stack(this._editorColumn);
        }
        this.castedQine().stack(this._listColumn);
        if (options?.initial) {
            this._setData(options?.initial);
            this._updateList();
        }
    }

    public override castedQine(): QinStack {
        return this.qinedBase as QinStack;
    }

    public override getNature(): Nature {
        return Nature.Object;
    }

    public override mayChange(): HTMLElement[] {
        return [];
    }

    public override turnReadOnly(): void {
        this._readOnly = true;
        if (this._editorK) {
            this._editorK.turnReadOnly();
        }
        if (this._editorV) {
            this._editorV.turnReadOnly();
        }
        this.styleAsReadOnly();
    }

    public override turnEditable(): void {
        this._readOnly = false;
        if (this._editorK) {
            this._editorK.turnEditable();
        }
        if (this._editorV) {
            this._editorV.turnEditable();
        }
        this.styleAsEditable();
    }

    public override isEditable(): boolean {
        return !this._readOnly;
    }

    protected override _getData(): Map<K, V> {
        return this._value;
    }

    protected override _setData(data: Map<K, V>) {
        if (data && !(data instanceof Map)) {
            data = new Map(Object.keys(data).map(key => [key as K, data[key] as V]));
        }
        this._value = data;
        this._updateList();
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinMap<K, V> {
        super.styled(styles);
        return this;
    }
    
    private _initEditor() {
        this._actListPanel.styleAsAlignItemsFlexEnd();
        this._actListPanel.styleAsBorderBottom(1);
        this._actListNewButton.addActionMain(_ => this._actListNew());
        this._actListEditIcon.addActionMain(_ => this._actListEdit());
        this._actListDelIcon.addActionMain(_ => this._actListDel());
        this._actEditConfirmButton.addActionMain(_ => this._actEditConfirm());
        this._actEditCancelButton.addActionMain(_ => this._actEditCancel());
    }

    private _updateList() {
        this._clearList();
        if (!this._value) return;
        for (const [key, value] of this._value) {
            const panelItem = new QinPanel();
            const display = {};
            const displayKTitle = this._titledK?.title ? this._titledK?.title : 'key';
            const displayVTitle = this._titledV?.title ? this._titledV?.title : 'value';
            display[displayKTitle] = key;
            display[displayVTitle] = value;
            panelItem.castedQine().innerText = JSON.stringify(display);
            panelItem.styleAsWhiteSpaceNoWrap();
            if (this._readOnly) {
                panelItem.styleAsReadOnly();
            } else {
                panelItem.styleAsEditable();
            }
            panelItem.addActionMain(_ => this._actItemMain(key));
            panelItem.install(this._listColumn);
            this._itemsPanelList.push(panelItem);
        }
        this._updateListMode();
    }

    private _clearList() {
        this._itemsPanelList.forEach(panelItem => panelItem.unInstall());
        this._itemsPanelList = [];
    }

    private _updateListMode() {
        for (const panelItem of this._itemsPanelList) {
            if (this._mode === Mode.EDIT || this._mode === Mode.DEL) {
                panelItem.styleAsCursorPointer();
            } else {
                panelItem.styleAsCursorAuto();
            }
        }
    }

    private _setMode(mode: Mode) {
        this._mode = mode;
        this._actListIconPick.value = null;
        if (mode === Mode.EDIT) {
            this._actListIconPick.value = this._actListEditIcon.asset;
        } else if (mode === Mode.DEL) {
            this._actListIconPick.value = this._actListDelIcon.asset;
        }
        this._updateListMode();
    }

    private _actItemMain(key: K) {
        if (this._readOnly) return;
        switch (this._mode) {
            case Mode.EDIT: return this._actItemEdit(key);
            case Mode.DEL: return this._actItemDel(key);
        }
    }

    private _actItemEdit(key: K) {
        if (this._readOnly) return;
        const itemValue = this._value.get(key);
        if (this._editorK) {
            this._editorK.value = key;
        } else {
            this._editorKPanel.qinedHTML.innerText = JSON.stringify(key);
        }
        if (this._editorV) {
            this._editorV.value = itemValue;
        } else {
            this._editorVPanel.qinedHTML.innerText = JSON.stringify(itemValue);
        }
        this._editKey = key;
        this.castedQine().show(this._editorColumn);
    }

    private _actItemDel(key: K) {
        if (this._readOnly) return;
        this._value.delete(key);
        this._updateList();
    }

    private _actListNew() {
        this._setMode(Mode.NEW);
        this._editKey = undefined;
        if (this._editorK) {
            this._editorK.value = null;
        }
        if (this._editorV) {
            this._editorV.value = null;
        }
        this.castedQine().show(this._editorColumn);
    }

    private _actListEdit() {
        this._setMode(this._mode === Mode.EDIT ? Mode.VIEW : Mode.EDIT);
        this.castedQine().show(this._listColumn);
    }

    private _actListDel() {
        this._setMode(this._mode === Mode.DEL ? Mode.VIEW : Mode.DEL);
        this.castedQine().show(this._listColumn);
    }

    private _actEditConfirm() {
        let editedKey: K = this._editorK ? this._editorK.value : null;
        let editedValue: V = this._editorV ? this._editorV.value : null;
        if (this.getKey) {
            editedKey = this.getKey(this._editKey, editedKey, editedValue);
        }
        if (this.getValue) {
            editedValue = this.getValue(this._editKey, editedKey, editedValue);
        }
        if (this._mode === Mode.NEW) {
            if (editedKey !== undefined && editedKey !== null) {
                this._value.set(editedKey, editedValue);
            }
        } else if (this._mode === Mode.EDIT && this._editKey !== undefined) {
            if (this._editKey !== editedKey) {
                this._value.delete(this._editKey);
            }
            if (editedKey !== undefined && editedKey !== null) {
                this._value.set(editedKey, editedValue);
            }
        }
        this._updateList();
        this.castedQine().show(this._listColumn);
    }

    private _actEditCancel() {
        if (this._editorK) {
            this._editorK.value = null;
        }
        if (this._editorV) {
            this._editorV.value = null;
        }
        this.castedQine().show(this._listColumn);
    }
}

export type QinMapSet<K, V> = {
    initial?: Map<K, V>;
    editorK?: QinEdit<K>;
    titledK?: QinTitled;
    editorV?: QinEdit<V>;
    titledV?: QinTitled;
    readonly?: boolean;
    getKey?: (oldKey: K, newKey: K, newValue: V) => K;
    getValue?: (oldKey: K, newKey: K, newValue: V) => V;
}

enum Mode {
    VIEW,
    NEW,
    EDIT,
    DEL,
}