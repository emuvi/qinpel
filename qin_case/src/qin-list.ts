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

export class QinList<T> extends QinEdit<Array<T>> {
    
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
    private _editorPanel = new QinPanel();
    private _editorColumn = new QinColumn({items: [this._actEditPanel, this._editorPanel]});

    private _readOnly = false;
    private readonly _editor?: QinEdit<T>;
    
    private _mode = Mode.VIEW;
    private _value = new Array<T>();
    private _editIndex = -1;
    
    public constructor(options?: QinListSet<T>, isQindred?: string) {
        super(undefined, (isQindred ? isQindred + "_" : "") + "list", new QinStack());
        if (!options?.editor || options?.readonly) {
            this.turnReadOnly();
        }
        if (options?.editor) {
            this._editor = options.editor;
            if (options.titled) {
                options.titled.install(this._editorPanel);
            } else {
                this._editor.install(this._editorPanel);
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
        if (this._editor) {
            this._editor.turnReadOnly();
        }
        this.styleAsReadOnly();
    }

    public override turnEditable(): void {
        this._readOnly = false;
        if (this._editor) {
            this._editor.turnEditable();
        }
        this.styleAsEditable();
    }

    public override isEditable(): boolean {
        return !this._readOnly;
    }

    protected override _getData(): Array<T> {
        return this._value;
    }

    protected override _setData(data: Array<T>) {
        this._value = data;
        this._updateList();
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinList<T> {
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
        for (let i = 0; i < this._value.length; i++) {
            const valueItem = this._value[i];
            const panelItem = new QinPanel();
            panelItem.castedQine().innerText = JSON.stringify(valueItem);
            panelItem.styleAsWhiteSpaceNoWrap();
            if (this._readOnly) {
                panelItem.styleAsReadOnly();
            } else {
                panelItem.styleAsEditable();
            }
            panelItem.addActionMain(_ => this._actItemMain(i));
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

    private _actItemMain(index: number) {
        if (this._readOnly) return;
        switch (this._mode) {
            case Mode.EDIT: return this._actItemEdit(index);
            case Mode.DEL: return this._actItemDel(index);
        }
    }

    private _actItemEdit(index: number) {
        if (this._readOnly) return;
        const itemValue = this._value[index];
        this._editor.value = itemValue;
        this._editIndex = index;
        this.castedQine().show(this._editorColumn);
    }

    private _actItemDel(index: number) {
        if (this._readOnly) return;
        this._value.splice(index, 1);
        this._updateList();
    }

    private _actListNew() {
        this._setMode(Mode.NEW);
        this._editor.value = null;
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
        const editedValue = this._editor.value;
        if (this._mode === Mode.NEW) {
            this._value.push(editedValue);
        } else if (this._mode === Mode.EDIT && this._editIndex >= 0) {
            this._value[this._editIndex] = editedValue;
        }
        this._updateList();
        this.castedQine().show(this._listColumn);
    }

    private _actEditCancel() {
        this._editor.value = null;
        this.castedQine().show(this._listColumn);
    }
}

export type QinListSet<T> = {
    initial?: Array<T>;
    editor?: QinEdit<T>;
    titled?: QinTitled;
    readonly?: boolean;
}

enum Mode {
    VIEW,
    NEW,
    EDIT,
    DEL,
}