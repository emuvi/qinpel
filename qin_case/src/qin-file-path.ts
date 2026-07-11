import { QinFramePopup } from "qin_desk/types/qin-frame-popup";
import { FilesDescriptor, FilesNature, FilesOperation, Nature } from "qin_soul";
import { QinAsset } from "./qin-assets";
import { QinButton } from "./qin-button";
import { QinEdit } from "./qin-edit";
import { QinFilePick } from "./qin-file-pick";
import { QinIcon } from "./qin-icon";
import { QinLine } from "./qin-line";
import { QinChars } from "./qin-chars";

export class QinFilePath extends QinEdit<string> {
    
    private _pathString = new QinChars();
    private _searchButton = new QinButton({icon: new QinIcon(QinAsset.FaceFolder)});
    private _filePick: QinFilePick;
    private _framePopup: QinFramePopup;

    private _readOnly = false;

    public constructor(options?: QinFilePathSet, isQindred?: string) {
        super(undefined, (isQindred ? isQindred + "_" : "") + "file-path", new QinLine());
        this._filePick = new QinFilePick({
            filesNature: options?.nature,
            fileOperation: options?.operation,
            filesDescriptorList: options?.descriptors,
            singleSelection: true,
        });
        this._framePopup = this.qinpel.frame.newPopup(this._filePick.castedQine().castedQine());
        this._pathString.install(this.qinedBase);
        this._searchButton.install(this.qinedBase);
        this._searchButton.addActionMain((_) => this._framePopup.show());
        this._filePick.addOnChosen((chosen) => {
            if (chosen && chosen.length > 0) {
                this._pathString.value = chosen[0];
            }
            this._framePopup.close();
        });
        if (options?.initial) {
            this._setData(options.initial);
        }
        if (options?.readOnly) {
            this.turnReadOnly();
        }
    }

    public override castedQine(): QinLine {
        return this.qinedBase as QinLine;
    }

    public override getNature(): Nature {
        return Nature.Chars;
    }

    public override mayChange(): HTMLElement[] {
        return [...this._pathString.mayChange(), ...this._filePick.mayChange()];
    }

    public override turnReadOnly(): void {
        this._readOnly = true;
        this._pathString.turnReadOnly();
    }

    public override turnEditable(): void {
        this._readOnly = false;
        this._pathString.turnEditable();
    }

    public override isEditable(): boolean {
        return !this._readOnly;
    }

    protected override _getData(): string {
        return this._pathString.value;
    }

    protected override _setData(data: string) {
        this._pathString.value = data;
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinFilePath {
        super.styled(styles);
        return this;
    }
}

export type QinFilePathSet = {
    initial?: string;
    nature?: FilesNature;
    operation?: FilesOperation;
    descriptors?: FilesDescriptor[];
    readOnly?: boolean;
};
