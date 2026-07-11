import { FilesDescriptor, FilesNature, FilesOperation, Nature } from "qin_soul";
import { QinAsset } from "./qin-assets";
import { QinButton } from "./qin-button";
import { QinColumn } from "./qin-column";
import { QinCombo } from "./qin-combo";
import { QinEdit } from "./qin-edit";
import { QinFileView } from "./qin-file-view";
import { QinIcon } from "./qin-icon";
import { QinLine } from "./qin-line";
import { QinPanel } from "./qin-panel";
import { QinChars } from "./qin-chars";

export class QinFilePick extends QinEdit<string[]> {
    
    private _upperLine = new QinLine();
    private _confirmButton = new QinButton({icon: new QinIcon(QinAsset.FaceConfirm)});
    private _folderString = new QinChars();
    private _extensionsCombo = new QinCombo();
    private _searchButton = new QinButton({icon: new QinIcon(QinAsset.FaceSearch)});
    private _underPanel = new QinPanel();
    private _fileView = new QinFileView();

    private _filesNature: FilesNature;
    private _filesOperation: FilesOperation;
    private _fileDescriptorList: FilesDescriptor[];
    private _singleSelection: boolean;
    private _readOnly = false;

    private _onChosen: QinFilePickChosen[] = [];

    public constructor(options?: QinFilePickSet, isQindred?: string) {
        super(undefined, (isQindred ? isQindred + "_" : "") + "file-pick", new QinColumn());
        this._filesNature = options?.filesNature ? options.filesNature : FilesNature.Both;
        this._filesOperation = options?.fileOperation ? options.fileOperation : FilesOperation.Open;
        this._fileDescriptorList = options?.filesDescriptorList ? options.filesDescriptorList : [];
        this._singleSelection = options?.singleSelection ?? false;
        this.initMain();
        this.initUpper();
        this.initUnder();
        if (options?.readOnly) {
            this.turnReadOnly();
        }
    }

    public override castedQine(): QinColumn {
        return this.qinedBase as QinColumn;
    }

    public override getNature(): Nature {
        return Nature.Chars;
    }

    public override mayChange(): HTMLElement[] {
        return [...this._fileView.mayChange()];
    }

    public override turnReadOnly(): void {
        this._readOnly = true;
        this._folderString.turnReadOnly();
        this._extensionsCombo.turnReadOnly();
        this._fileView.turnReadOnly();
    }

    public override turnEditable(): void {
        this._readOnly = false;
        this._folderString.turnEditable();
        this._extensionsCombo.turnEditable();
        this._fileView.turnEditable();
    }

    public override isEditable(): boolean {
        return !this._readOnly;
    }

    protected override _getData(): string[] {
        return this._fileView.value;
    }

    protected override _setData(data: string[]) {
        this._fileView.value = data;
    }

    public get filesNature(): FilesNature {
        return this._filesNature;
    }

    public set filesNature(value: FilesNature) {
        this._filesNature = value;
        this._fileView.filesNature = value;
    }

    public get filesOperation(): FilesOperation {
        return this._filesOperation;
    }

    public set filesOperation(value: FilesOperation) {
        this._filesOperation = value;
    }

    public get filesDescriptorList(): FilesDescriptor[] {
        return this._fileDescriptorList;
    }

    public set filesDescriptorList(value: FilesDescriptor[]) {
        this._fileDescriptorList = value;
    }

    public get singleSelection(): boolean {
        return this._singleSelection;
    }

    public set singleSelection(value: boolean) {
        this._singleSelection = value;
        this._fileView.singleSelection = value;
    }

    public addOnChosen(onChosen: QinFilePickChosen): QinFilePick {
        this._onChosen.push(onChosen);
        return this;
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinFilePick {
        super.styled(styles);
        return this;
    }

    private initMain() {
        this._upperLine.install(this.qinedBase);
        this._underPanel.install(this.qinedBase);
    }

    private initUpper() {
        this._upperLine.styleAsFlexMin();
        this._confirmButton.install(this._upperLine);
        this._confirmButton.addActionMain((_) => {
            let data = this._getData();
            for (const chosen of this._onChosen) {
                chosen(data);
            }
        });
        this._folderString.install(this._upperLine);
        this._folderString.styleAsMinWidth(100);
        this._folderString.styleAsFlexMax();
        this._folderString.addActionMain((_) => {
            if (this.isEditable()) {
                this.loadFolder();
            }
        });
        this._extensionsCombo.install(this._upperLine);
        this._extensionsCombo.styleAsMinWidth(100);
        this.initExtensions();
        this._searchButton.install(this._upperLine);
        this._searchButton.addAction((_) => {
            if (this.isEditable()) {
                this.loadFolder();
            }
        });
    }

    private initUnder() {
        this._underPanel.styleAsScroll();
        this._underPanel.styleAsFlexMax();
        this._fileView.install(this._underPanel);
        this._fileView.filesNature = this._filesNature;
        this._fileView.singleSelection = this._singleSelection;
    }

    private initExtensions() {
        if (this._fileDescriptorList.length == 0) {
            this._extensionsCombo.addItem({
                title: this.qinpel.tr("All files") + " (*.*)",
                value: "*",
                selected: true,
            });
            this._fileView.filesExtensionList = [];
        } else {
            for (let index = 0; index < this._fileDescriptorList.length; index++) {
                const descriptor = this._fileDescriptorList[index];
                this._extensionsCombo.addItem({
                    title: descriptor.description,
                    value: descriptor.extensions.join(";"),
                    selected: index == 0,
                });
            }
            this._fileView.filesExtensionList = this._fileDescriptorList[0].extensions;
        }
    }

    private loadFolder() {
        this._fileView.load(this._folderString.value, (loaded) => {
            this._folderString.value = loaded;
        });
    }
}

export type QinFilePickSet = {
    filesNature?: FilesNature;
    fileOperation?: FilesOperation;
    filesDescriptorList?: FilesDescriptor[];
    singleSelection?: boolean;
    readOnly?: boolean;
};

export type QinFilePickChosen = (chosenPaths: string[]) => void;
