import { QinColumn, QinFileView, QinLine, QinChars } from "qin_case";
import { Module } from "./module";

export class ModuleDIR extends Module {
    private _bodyColumn = new QinColumn();
    private _actionLine = new QinLine();
    private _pathChars = new QinChars();
    private _fileView = new QinFileView({canNavigate: true});
    public constructor() {
        super();
        this._fileView.addOnLoaded(folder => this._pathChars.value = folder);
        this._pathChars.styleAsWhole();
        this._fileView.styleAsWhole();
        this._bodyColumn.styleAsWhole();
        this._pathChars.install(this._actionLine);
        this._actionLine.install(this._bodyColumn);
        this._fileView.install(this._bodyColumn);
        this._bodyColumn.install(this);
        this._fileView.load("");
    }
}
