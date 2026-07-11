import { QinBool, QinButton, QinColumn, QinCombo, QinLabel, QinLine, QinChars, QinTabs, QinText, QinTitled } from "qin_case";
import { Module } from "./module";
import { Execute } from "../../qin_soul/types/qin-type";

export class ModuleGIZ extends Module {
    private _bodyTabs = new QinTabs();
    private _listPanel = new ListGIZ();
    private _runPanel = new RunGIZ();

    public constructor() {
        super();
        this._bodyTabs.styleAsWhole();
        this._bodyTabs.addTab({title: "List", viewer: this._listPanel});
        this._bodyTabs.addTab({title: "Run", viewer: this._runPanel});
        this._bodyTabs.install(this);
    }
}

class ListGIZ extends QinColumn {
    private _actionLine = new QinLine();
    private _listButton = new QinButton({label: new QinLabel("List")});
    private _resultText = new QinText();

    public constructor() {
        super();
        this.styleAsWhole();
        this._resultText.styleAsWhole();
        this._listButton.addActionMain(_ => this.actList());
        this._listButton.install(this._actionLine);
        this._actionLine.install(this);
        this._resultText.install(this);
    }

    private actList() {
        this.qinpel.talk.giz.list()
            .then((res) => this._resultText.value = res.join("\n"))
            .catch((err) => this.qinpel.frame.showError(err, "{qia_abdesk}(ErrCode-000005)"))
    }
}

class RunGIZ extends QinColumn {
    private _actionLine = new QinLine();
    private _nameChars = new QinChars();
    private _nameTitled = new QinTitled({label: new QinLabel("Name"), items: [this._nameChars]});
    private _argsText = new QinText();
    private _argsTitled = new QinTitled({label: new QinLabel("Args"), items: [this._argsText]});
    private _inputText = new QinText();
    private _inputTitled = new QinTitled({label: new QinLabel("Input"), items: [this._inputText]});
    private _joinErrsBoolean = new QinBool();
    private _joinErrsTitled = new QinTitled({label: new QinLabel("Join Errors"), items: [this._joinErrsBoolean]});
    private _logLevelCombo = new QinCombo({items: [{title: "Error", value: "0"}, {title: "Warn", value: "1"}, {title: "Info", value: "2"}, {title: "Debug", value: "3"}, {title: "Trace", value: "4"}]});
    private _logLevelTitled = new QinTitled({label: new QinLabel("Log Level"), items: [this._logLevelCombo]});
    private _runButton = new QinButton({label: new QinLabel("Run")});
    private _resultText = new QinText();

    public constructor() {
        super();
        this.styleAsWhole();
        this._resultText.styleAsWhole();
        this._nameTitled.install(this._actionLine);
        this._argsTitled.install(this._actionLine);
        this._inputTitled.install(this._actionLine);
        this._joinErrsTitled.install(this._actionLine);
        this._logLevelTitled.install(this._actionLine);
        this._runButton.addActionMain(_ => this.actRun());
        this._runButton.install(this._actionLine);
        this._actionLine.install(this);
        this._resultText.install(this);
    }

    private getExecute(): Execute {
        return {
            name: this._nameChars.value,
            argList: this._argsText.getLines(),
            inputList: this._inputText.getLines(),
            joinErrs: this._joinErrsBoolean.value,
            logLevel: parseInt(this._logLevelCombo.value, 10)
        };
    }

    private actRun() {
        const execute = this.getExecute();
        this.qinpel.talk.giz.run(execute)
            .then((token) => this._resultText.appendLine(token + "\n" + JSON.stringify(execute)))
            .catch((err) => this.qinpel.frame.showError(err, "{qia_abdesk}(ErrCode-000006)"))
    }

}
