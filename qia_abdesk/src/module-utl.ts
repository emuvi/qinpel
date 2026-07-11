import { QinBool, QinButton, QinColumn, QinLabel, QinLine, QinNumeric, QinPassword, QinChars, QinTabs, QinText, QinTitled } from "qin_case";
import { Module } from "./module";
import { IssuedQuestion, TryAuth } from "qin_soul";

export class ModuleUTL extends Module {
    
    private _bodyTabs = new QinTabs();
    private _basicUTL = new BasicUTL();
    private _enterUTL = new EnterUTL();
    private _issuedUTL = new IssuedUTL();
    
    public constructor() {
        super();
        this._bodyTabs.styleAsWhole();
        this._bodyTabs.addTab({title: "Basic", viewer: this._basicUTL});
        this._bodyTabs.addTab({title: "Enter", viewer: this._enterUTL});
        this._bodyTabs.addTab({title: "Issued", viewer: this._issuedUTL});
        this._bodyTabs.install(this);
    }
}

class BasicUTL extends QinColumn {
    private _basicLine = new QinLine();
    private _pingButton = new QinButton({label: new QinLabel("Ping")});
    private _langButton = new QinButton({label: new QinLabel("Lang")});
    private _configLine = new QinLine();
    private _configChars = new QinChars();
    private _configTitled = new QinTitled({label: new QinLabel("Config"), items: [this._configChars]});
    private _configButton = new QinButton({label: new QinLabel("Get")});
    private _resultText = new QinText();

    public constructor() {
        super();
        this.styleAsWhole();
        this._resultText.styleAsWhole();
        this._pingButton.addActionMain(_ => this.actPing());
        this._langButton.addActionMain(_ => this.actLang());
        this._configButton.addActionMain(_ => this.actConfig());
        this._pingButton.install(this._basicLine);
        this._langButton.install(this._basicLine);
        this._configTitled.install(this._configLine);
        this._configButton.install(this._configLine);
        this._basicLine.install(this);
        this._configLine.install(this);
        this._resultText.install(this);
    }

    private actPing() {
        this.qinpel.talk.utl
            .ping()
            .then((res) => this._resultText.value = res)
            .catch((err) => this.qinpel.frame.showError(err, "{qia_abdesk}(ErrCode-000014)"))
    }

    private actLang() {
        this.qinpel.talk.utl
            .getLang()
            .then((res) => this._resultText.value = res)
            .catch((err) => this.qinpel.frame.showError(err, "{qia_abdesk}(ErrCode-000015)"))
    }

    private actConfig() {
        this.qinpel.talk.utl
            .getConfig(this._configChars.value)
            .then((res) => this._resultText.value = res)
            .catch((err) => this.qinpel.frame.showError(err, "{qia_abdesk}(ErrCode-000019)"))
    }
}

class EnterUTL extends QinColumn {
    private _enterLine = new QinLine();
    private _nameChars = new QinChars();
    private _nameTitled = new QinTitled({label: new QinLabel("Name"), items: [this._nameChars]});
    private _passPassword = new QinPassword();
    private _passTitled = new QinTitled({label: new QinLabel("Pass"), items: [this._passPassword]});
    private _enterButton = new QinButton({label: new QinLabel("Enter")});
    private _loggedLine = new QinLine();
    private _isLoggedButton = new QinButton({label: new QinLabel("Is Logged")});
    private _getLoggedButton = new QinButton({label: new QinLabel("Get Logged")});
    private _resultText = new QinText();

    public constructor() {
        super();
        this.styleAsWhole();
        this._resultText.styleAsWhole();
        this._enterButton.addActionMain(_ => this.actEnter());
        this._isLoggedButton.addActionMain(_ => this.actIsLogged());
        this._getLoggedButton.addActionMain(_ => this.actGetLogged());
        this._nameTitled.install(this._enterLine);
        this._passTitled.install(this._enterLine);
        this._enterButton.install(this._enterLine);
        this._isLoggedButton.install(this._loggedLine);
        this._getLoggedButton.install(this._loggedLine);
        this._enterLine.install(this);
        this._loggedLine.install(this);
        this._resultText.install(this);
    }

    private actEnter() {
        const tryAuth: TryAuth = {
            name: this._nameChars.value,
            pass: this._passPassword.value
        }
        this.qinpel.talk.utl
            .tryEnter(tryAuth)
            .then((res) => this._resultText.value = JSON.stringify(res, null, 2))
            .catch((err) => this.qinpel.frame.showError(err, "{qia_abdesk}(ErrCode-000016)"))
    }

    private actIsLogged() {
        this.qinpel.talk.utl
            .isLogged()
            .then((res) => this._resultText.value = JSON.stringify(res, null, 2))
            .catch((err) => this.qinpel.frame.showError(err, "{qia_abdesk}(ErrCode-000017)"))
    }

    private actGetLogged() {
        this.qinpel.talk.utl
            .getLogged()
            .then((res) => this._resultText.value = JSON.stringify(res, null, 2))
            .catch((err) => this.qinpel.frame.showError(err, "{qia_abdesk}(ErrCode-000018)"))
    }
}

class IssuedUTL extends QinColumn {
    private _questionLine = new QinLine();
    private _tokenChars = new QinChars();
    private _tokenTitled = new QinTitled({label: new QinLabel("Token"), items: [this._tokenChars]});
    private _createdAtBoolean = new QinBool();
    private _createdAtTitled = new QinTitled({label: new QinLabel("Created At"), items: [this._createdAtBoolean]});
    private _outLinesBoolean = new QinBool();
    private _outLinesTitled = new QinTitled({label: new QinLabel("Out Lines"), items: [this._outLinesBoolean]});
    private _outLinesFromNumber = new QinNumeric();
    private _outLinesFromTitled = new QinTitled({label: new QinLabel("Out Lines From"), items: [this._outLinesFromNumber]});
    private _outLinesUntilNumber = new QinNumeric();
    private _outLinesUntilTitled = new QinTitled({label: new QinLabel("Out Lines Until"), items: [this._outLinesUntilNumber]});
    private _outLinesSizeBoolean = new QinBool();
    private _outLinesSizeTitled = new QinTitled({label: new QinLabel("Out Lines Size"), items: [this._outLinesSizeBoolean]});
    private _errLinesBoolean = new QinBool();
    private _errLinesTitled = new QinTitled({label: new QinLabel("Err Lines"), items: [this._errLinesBoolean]});
    private _errLinesFromNumber = new QinNumeric();
    private _errLinesFromTitled = new QinTitled({label: new QinLabel("Err Lines From"), items: [this._errLinesFromNumber]});
    private _errLinesUntilNumber = new QinNumeric();
    private _errLinesUntilTitled = new QinTitled({label: new QinLabel("Err Lines Until"), items: [this._errLinesUntilNumber]});
    private _errLinesSizeBoolean = new QinBool();
    private _errLinesSizeTitled = new QinTitled({label: new QinLabel("Err Lines Size"), items: [this._errLinesSizeBoolean]});
    private _resultCodeBoolean = new QinBool();
    private _resultCodeTitled = new QinTitled({label: new QinLabel("Result Code"), items: [this._resultCodeBoolean]});
    private _isDoneBoolean = new QinBool();
    private _isDoneTitled = new QinTitled({label: new QinLabel("Is Done"), items: [this._isDoneBoolean]});
    private _hasOutBoolean = new QinBool();
    private _hasOutTitled = new QinTitled({label: new QinLabel("Has Out"), items: [this._hasOutBoolean]});
    private _hasErrBoolean = new QinBool();
    private _hasErrTitled = new QinTitled({label: new QinLabel("Has Err"), items: [this._hasErrBoolean]});
    private _finishedAtBoolean = new QinBool();
    private _finishedAtTitled = new QinTitled({label: new QinLabel("Finished At"), items: [this._finishedAtBoolean]});
    private _askButton = new QinButton({label: new QinLabel("Ask")});
    private _resultText = new QinText();

    public constructor() {
        super();
        this.styleAsWhole();
        this._resultText.styleAsWhole();
        this._askButton.addActionMain(_ => this.actAsk());
        this._tokenTitled.install(this._questionLine);
        this._createdAtTitled.install(this._questionLine);
        this._outLinesTitled.install(this._questionLine);
        this._outLinesFromTitled.install(this._questionLine);
        this._outLinesUntilTitled.install(this._questionLine);
        this._outLinesSizeTitled.install(this._questionLine);
        this._errLinesTitled.install(this._questionLine);
        this._errLinesFromTitled.install(this._questionLine);
        this._errLinesUntilTitled.install(this._questionLine);
        this._errLinesSizeTitled.install(this._questionLine);
        this._resultCodeTitled.install(this._questionLine);
        this._isDoneTitled.install(this._questionLine);
        this._hasOutTitled.install(this._questionLine);
        this._hasErrTitled.install(this._questionLine);
        this._finishedAtTitled.install(this._questionLine);
        this._askButton.install(this._questionLine);
        this._questionLine.install(this);
        this._resultText.install(this);
    }

    private actAsk() {
        const question: IssuedQuestion = {
            token: this._tokenChars.value,
            askCreatedAt: this._createdAtBoolean.value,
            askOutLines: this._outLinesBoolean.value,
            askOutLinesFrom: this._outLinesFromNumber.value,
            askOutLinesUntil: this._outLinesUntilNumber.value,
            askOutLinesSize: this._outLinesSizeBoolean.value,
            askErrLines: this._errLinesBoolean.value,
            askErrLinesFrom: this._errLinesFromNumber.value,
            askErrLinesUntil: this._errLinesUntilNumber.value,
            askErrLinesSize: this._errLinesSizeBoolean.value,
            askResultCode: this._resultCodeBoolean.value,
            askIsDone: this._isDoneBoolean.value,
            askHasOut: this._hasOutBoolean.value,
            askHasErr: this._hasErrBoolean.value,
            askFinishedAt: this._finishedAtBoolean.value
        };
        this.qinpel.talk.utl
            .askIssued(question)
            .then((res) => this._resultText.value = JSON.stringify(res, null, 2))
            .catch((err) => this.qinpel.frame.showError(err, "{qia_abdesk}(ErrCode-000020)"))
    }
}