
import { PathKind, QinActionableStyles, FilesNature, Nature, QinSoul, QinStylesPicker} from "qin_soul";
import { QinEdit } from "./qin-edit";
import { QinLine } from "./qin-line";
import { QinPanel } from "./qin-panel";
import { QinColumn } from "./qin-column";

export class QinFileView extends QinEdit<string[]> {
    
    private _bodyLine = new QinLine();
    private _stretchPanel = new QinPanel();

    private _filesNature: FilesNature;
    private _filesExtensionList: string[];
    private _singleSelection: boolean;
    private _canNavigate: boolean;
    private _readOnly = false;

    private _onLoaded: OnFileViewLoaded[] = [];

    private _folderSelected: string = "";

    private _items: Item[] = [];

    public constructor(options?: QinFileViewSet, isQindred?: string) {
        super(undefined, (isQindred ? isQindred + "_" : "") + "file-view", new QinColumn());
        this._filesNature = options?.filesNature ? options.filesNature : FilesNature.Both;
        this._filesExtensionList = options?.filesExtensionList ? options.filesExtensionList : [];
        this._singleSelection = options?.singleSelection ?? false;
        this._canNavigate = options?.canNavigate ?? false;
        this.initMain();
        if (options?.readOnly) {
            this.turnReadOnly();
        }
        this._stretchPanel.styleAsWhole();
        this._bodyLine.install(this);
        this._stretchPanel.install(this);
        this.bodyBase = this._bodyLine;
    }

    public override castedQine(): QinPanel {
        return this.qinedBase as QinPanel;
    }

    public override getNature(): Nature {
        return Nature.Chars;
    }

    public override mayChange(): HTMLElement[] {
        return [];
    }

    public override turnReadOnly(): void {
        this._readOnly = true;
        this.styleAsReadOnly();
    }

    public override turnEditable(): void {
        this._readOnly = false;
        this.styleAsEditable();
    }

    public override isEditable(): boolean {
        return !this._readOnly;
    }

    protected override _getData(): string[] {
        let result = [];
        this._items.forEach((item) => {
            if (item.isPicked()) {
                result.push(QinSoul.foot.getPathJoin(this._folderSelected, item.getName()));
            }
        });
        return result;
    }

    protected override _setData(data: string[]) {
        this.clean();
        if (data && data.length > 0) {
            let dataFolder = QinSoul.foot.getParent(data[0]);
            this.load(dataFolder, (_) => {
                for (const itemPath of data) {
                    let itemRoot = QinSoul.foot.getParent(itemPath);
                    let itemName = QinSoul.foot.getStem(itemPath);
                    if (itemRoot !== dataFolder) {
                        this.qinpel.frame.statusError(
                            `The item '${itemPath}' is not on the root '${dataFolder}'.`,
                            "{qin_case}(ErrCode-000001)"
                        );
                    } else {
                        if (!this.select(itemName)) {
                            this.qinpel.frame.statusError(
                                `Does not have the item '${itemName}' on the folder '${dataFolder}'`,
                                "{qin_case}(ErrCode-000002)"
                            );
                        }
                    }
                }
            });
        }
    }

    public get filesNature(): FilesNature {
        return this._filesNature;
    }

    public set filesNature(value: FilesNature) {
        this._filesNature = value;
    }

    public get filesExtensionList(): string[] {
        return this._filesExtensionList;
    }

    public set filesExtensionList(value: string[]) {
        this._filesExtensionList = value;
    }

    public get singleSelection(): boolean {
        return this._singleSelection;
    }

    public set singleSelection(value: boolean) {
        this._singleSelection = value;
        this.updateSingleSelection();
    }

    public get canNavigate(): boolean {
        return this._canNavigate;
    }

    public set canNavigate(value: boolean) {
        this._canNavigate = value;
    }

    public get folderSelected(): string {
        return this._folderSelected;
    }

    public addOnLoaded(onLoaded: OnFileViewLoaded): QinFileView {
        this._onLoaded.push(onLoaded);
        return this;
    }

    public load(folder: string, onLoaded?: OnFileViewLoaded) {
        this.clean();
        this.qinpel.talk.dir
            .dirList({ path: folder })
            .then((res) => {
                this._folderSelected = res.path;
                if (this._canNavigate) {
                    this.newDir("..");
                }
                for (let inside of res.list) {
                    if (inside.kind === PathKind.Folder) {
                        if (this._filesNature == FilesNature.Both || this._filesNature == FilesNature.Directories) {
                            this.newDir(inside.name);
                        }
                    } else if (inside.kind === PathKind.File) {
                        if (this._filesNature == FilesNature.Both || this._filesNature == FilesNature.Files) {
                            let extension = QinSoul.foot.getFileExtension(inside.name);
                            let passedExtension = true;
                            if (this._filesExtensionList && this._filesExtensionList.length > 0) {
                                passedExtension = this._filesExtensionList.indexOf(extension) > -1;
                            }
                            if (passedExtension) {
                                this.newFile(inside.name, extension);
                            }
                        }
                    }
                }
                this.sendOnLoaded(onLoaded);
            })
            .catch((err) => this.qinpel.frame.statusError(err, "{qin_case}(ErrCode-000003)"));
    }

    public reload(onLoaded?: OnFileViewLoaded) {
        this.load(this._folderSelected, onLoaded);
    }

    public goFolder(name: string, onLoaded?: OnFileViewLoaded) {
        if (name == "..") {
            this.goFolderUp();
        } else {
            this.load(QinSoul.foot.getPathJoin(this._folderSelected, name), onLoaded);
        }
    }

    public goFolderUp(onLoaded?: OnFileViewLoaded) {
        this.load(QinSoul.foot.getParent(this._folderSelected), onLoaded);
    }

    public clean() {
        this._bodyLine.qinedHTML.innerHTML = "";
        this._items = [];
        this._folderSelected = "";
    }

    public cleanSelection() {
        for (const item of this._items) {
            item.unPick();
        }
    }

    public select(itemName: string): boolean {
        let item = this._items.find((inside) => inside.getName() == itemName);
        if (item) {
            item.pick();
            return true;
        } else {
            return false;
        }
    }

    public unselect(itemName: string): boolean {
        let item = this._items.find((inside) => inside.getName() == itemName);
        if (item) {
            item.unPick();
            return true;
        } else {
            return false;
        }
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinFileView {
        super.styled(styles);
        return this;
    }

    private initMain() {
        this.styleAsEditable();
        styles.applyOnMain(this.qinedHTML);
        this.qinedBase.addActionMain((_) => {
            if (!this._readOnly) {
                this.cleanSelection();
            }
        });
        this.qinedBase.disabledSelection();
    }

    private newDir(name: string) {
        this.newItem(name, IconName.EXPLORER_DIR);
    }

    private newFile(name: string, extension: string) {
        this.newItem(name, getIconName(extension));
    }

    private newItem(name: string, icon: IconName) {
        const item = new Item(this, name, icon);
        item.install(this._bodyLine.qinedHTML);
        this._items.push(item);
    }

    private sendOnLoaded(onLoaded?: OnFileViewLoaded) {
        if (onLoaded) {
            onLoaded(this._folderSelected);
        }
        for (const funOnLoaded of this._onLoaded) {
            funOnLoaded(this._folderSelected);
        }
    }

    private updateSingleSelection() {
        if (this._singleSelection) {
            let alreadyHas = false;
            for (const item of this._items) {
                if (item.isPicked()) {
                    if (alreadyHas) {
                        item.unPick();
                    } else {
                        alreadyHas = true;
                    }
                }
            }
        }
    }
}

export type QinFileViewSet = {
    filesNature?: FilesNature;
    filesExtensionList?: string[];
    singleSelection?: boolean;
    readOnly?: boolean;
    canNavigate?: boolean;
};

export type OnFileViewLoaded = (loadedPath: string) => void;

class Item {
    private _view: QinFileView;
    private _styles: QinActionableStyles;
    private _itemDiv = document.createElement("div");
    private _bodyDiv = document.createElement("div");
    private _iconSpan = document.createElement("span");
    private _iconImg = document.createElement("img");
    private _textSpan = document.createElement("span");
    private _fileName: string;
    private _iconName: IconName;
    private _isFolder: boolean;
    private _picked: boolean = false;

    public constructor(view: QinFileView, fileName: string, iconName: IconName) {
        this._view = view;
        this._styles = {
            ColorForeground: QinStylesPicker.ColorPickerForeground,
            ColorAccentAct: QinStylesPicker.ColorPickerAccentAct,
            ColorInactiveAct: QinStylesPicker.ColorUnPickedInactiveAct,
            ColorActiveAct: QinStylesPicker.ColorUnPickedActiveAct,
        };
        this._fileName = fileName;
        this._iconName = iconName;
        this._isFolder = this._iconName == IconName.EXPLORER_DIR;
        this.initItem();
    }

    private initItem() {
        this._itemDiv.tabIndex = 0;
        styles.applyOnItemDiv(this._itemDiv, this._styles);
        styles.applyOnItemBodyDiv(this._bodyDiv);
        this._itemDiv.appendChild(this._bodyDiv);
        styles.applyOnItemIconSpan(this._iconSpan);
        this._bodyDiv.appendChild(this._iconSpan);
        this._iconImg.src = "/pub/qin_desk/assets/" + this._iconName;
        this._iconSpan.appendChild(this._iconImg);
        this._textSpan.innerText = this._fileName;
        styles.applyOnItemTextSpan(this._textSpan);
        this._bodyDiv.appendChild(this._textSpan);
        QinSoul.arms.addActionMain(this._itemDiv, (ev) => {
            if (this._view.isEditable()) {
                this._itemDiv.focus();
                this.toggle();
                if (ev.isMaster) {
                    this.pick();
                    if (this._isFolder && this._view.canNavigate) {
                        this._view.goFolder(this._fileName);
                    }
                }
            }
        });
    }

    public install(on: HTMLElement) {
        on.appendChild(this._itemDiv);
    }

    public toggle() {
        if (this._view.singleSelection) {
            this._view.cleanSelection();
        }
        this._picked = !this._picked;
        this.updateStyles();
    }

    public pick() {
        if (this._view.singleSelection) {
            this._view.cleanSelection();
        }
        this._picked = true;
        this.updateStyles();
    }

    public unPick() {
        this._picked = false;
        this.updateStyles();
    }

    private updateStyles() {
        this._styles.ColorInactiveAct = this._picked ? QinStylesPicker.ColorPickedInactiveAct : QinStylesPicker.ColorUnPickedInactiveAct;
        this._styles.ColorActiveAct = this._picked ? QinStylesPicker.ColorPickedActiveAct : QinStylesPicker.ColorUnPickedActiveAct;
        if (this._itemDiv == document.activeElement) {
            this._itemDiv.style.backgroundColor = this._styles.ColorActiveAct;
        } else {
            this._itemDiv.style.backgroundColor = this._styles.ColorInactiveAct;
        }
    }

    public isPicked(): boolean {
        return this._picked;
    }

    public getName(): string {
        return this._fileName;
    }
}

enum IconName {
    EXPLORER_DIR = "explorer-dir.png",
    EXPLORER_FILE = "explorer-file.png",
    EXPLORER_APPS = "explorer-apps.png",
    EXPLORER_CMDS = "explorer-cmds.png",
    EXPLORER_EXEC = "explorer-exec.png",
    EXPLORER_IMAGE = "explorer-image.png",
    EXPLORER_MUSIC = "explorer-music.png",
    EXPLORER_MOVIE = "explorer-movie.png",
    EXPLORER_ZIPPED = "explorer-zipped.png"
}

function getIconName(fromExtension: string): IconName {
    let result = IconName.EXPLORER_FILE;
    if (QinSoul.foot.isFileApp(fromExtension)) {
        result = IconName.EXPLORER_APPS;
    } else if (QinSoul.foot.isFileCmd(fromExtension)) {
        result = IconName.EXPLORER_CMDS;
    } else if (QinSoul.foot.isFileExec(fromExtension)) {
        result = IconName.EXPLORER_EXEC;
    } else if (QinSoul.foot.isFileImage(fromExtension)) {
        result = IconName.EXPLORER_IMAGE;
    } else if (QinSoul.foot.isFileVector(fromExtension)) {
        result = IconName.EXPLORER_IMAGE;
    } else if (QinSoul.foot.isFileMusic(fromExtension)) {
        result = IconName.EXPLORER_MUSIC;
    } else if (QinSoul.foot.isFileMovie(fromExtension)) {
        result = IconName.EXPLORER_MOVIE;
    } else if (QinSoul.foot.isFileZipped(fromExtension)) {
        result = IconName.EXPLORER_ZIPPED;
    }
    return result;
}

const styles = {
    applyOnMain: (el: HTMLElement) => {
        el.style.overflow = "auto";
        el.style.minWidth = "160px";
        el.style.minHeight = "160px";
        el.tabIndex = 0;
    },
    applyOnItemDiv: (el: HTMLElement, styles: QinActionableStyles) => {
        QinSoul.skin.styleAsActionable(el, styles);
        el.style.margin = "2px";
        el.style.padding = "9px";
        el.style.maxHeight = "fit-content";
        el.style.display = "inline-block";
    },
    applyOnItemBodyDiv: (el: HTMLElement) => {
        el.style.display = "flex";
        el.style.flexDirection = "column";
        el.style.width = "96px";
        el.style.maxHeight = "fit-content";
    },
    applyOnItemIconSpan: (el: HTMLElement) => {
        el.style.textAlign = "center";
    },
    applyOnItemTextSpan: (el: HTMLElement) => {
        el.style.textAlign = "center";
        el.style.wordWrap = "break-word";
    },
};
