import { QinBool, QinButton, QinChars, QinColumn, QinCombo, QinDouble, QinEdit, QinInt, QinLabel, QinLine, QinList, QinMap, QinPassword, QinSizer, QinTabs, QinTitled } from "qin_case";
import { Allow, Based, BasedJdbc, BasedLink, BasedWays, Group, Nature, Setup, User } from "qin_soul";
import { Module } from "./module";

export class ModuleWAY extends Module {

    private _bodyTabs = new QinTabs();
    private _setupWay = new SetupWAY();
    private _basesWay = new BasesWAY();
    private _usersWay = new UsersWAY();
    private _groupsWay = new GroupsWAY();

    public constructor() {
        super();
        this._bodyTabs.styleAsWhole();
        this._bodyTabs.addTab({title: "Setup", viewer: this._setupWay});
        this._bodyTabs.addTab({title: "Bases", viewer: this._basesWay});
        this._bodyTabs.addTab({title: "Users", viewer: this._usersWay});
        this._bodyTabs.addTab({title: "Groups", viewer: this._groupsWay});
        this._bodyTabs.install(this);
    }

}

class SetupWAY extends QinColumn {
    
    private _getButton = new QinButton({label: new QinLabel('Get')});
    private _setButton = new QinButton({label: new QinLabel('Set')});
    private _actionLine = new QinLine({items: [this._getButton, this._setButton]});

    private _serverNameChars = new QinChars();
    private _serverNameTitled = new QinTitled({label: new QinLabel('Server Name'), items: [this._serverNameChars]});
    private _serverLangChars = new QinChars();
    private _serverLangTitled = new QinTitled({label: new QinLabel('Server Lang'), items: [this._serverLangChars]});
    private _serverHostChars = new QinChars();
    private _serverHostTitled = new QinTitled({label: new QinLabel('Server Host'), items: [this._serverHostChars]});
    private _serverPortNumber = new QinInt();
    private _serverPortTitled = new QinTitled({label: new QinLabel('Server Port'), items: [this._serverPortNumber]});
    private _serverFolderChars = new QinChars();
    private _serverFolderTitled = new QinTitled({label: new QinLabel('Server Folder'), items: [this._serverFolderChars]});
    private _serverLine = new QinLine({items: [this._serverNameTitled, this._serverLangTitled, this._serverHostTitled, this._serverPortTitled, this._serverFolderTitled]});

    private _servesPubBool = new QinBool();
    private _servesPubTitled = new QinTitled({label: new QinLabel('Serves Pub'), items: [this._servesPubBool]});
    private _servesAppBool = new QinBool();
    private _servesAppTitled = new QinTitled({label: new QinLabel('Serves App'), items: [this._servesAppBool]});
    private _servesDirBool = new QinBool();
    private _servesDirTitled = new QinTitled({label: new QinLabel('Serves Dir'), items: [this._servesDirBool]});
    private _servesCmdBool = new QinBool();
    private _servesCmdTitled = new QinTitled({label: new QinLabel('Serves Cmd'), items: [this._servesCmdBool]});
    private _servesBasBool = new QinBool();
    private _servesBasTitled = new QinTitled({label: new QinLabel('Serves Bas'), items: [this._servesBasBool]});
    private _servesRegBool = new QinBool();
    private _servesRegTitled = new QinTitled({label: new QinLabel('Serves Reg'), items: [this._servesRegBool]});
    private _servesGizBool = new QinBool();
    private _servesGizTitled = new QinTitled({label: new QinLabel('Serves Giz'), items: [this._servesGizBool]});
    private _servesLine = new QinLine({items: [this._servesPubTitled, this._servesAppTitled, this._servesDirTitled, this._servesCmdTitled, this._servesBasTitled, this._servesRegTitled, this._servesGizTitled]});

    private _configMap = new QinMap<string, string>({editorK: new QinChars(), editorV: new QinChars()});
    private _configMapSizer = new QinSizer(this._configMap);
    private _configTitled = new QinTitled({label: new QinLabel('Config Map'), items: [this._configMapSizer]});
    private _redirectMap = new QinMap<string, string>({editorK: new QinChars(), editorV: new QinChars()});
    private _redirectMapSizer = new QinSizer(this._redirectMap);
    private _redirectTitled = new QinTitled({label: new QinLabel('Redirect Map'), items: [this._redirectMapSizer]});
    private _configLine = new QinLine({items: [this._configTitled, this._redirectTitled]});

    private _threadsMinDouble = new QinDouble();
    private _threadsMinTitled = new QinTitled({label: new QinLabel('Threads Min'), items: [this._threadsMinDouble]});
    private _threadsMaxDouble = new QinDouble();
    private _threadsMaxTitled = new QinTitled({label: new QinLabel('Threads Max'), items: [this._threadsMaxDouble]});
    private _threadsIdleTimeoutDouble = new QinDouble();
    private _threadsIdleTimeoutTitled = new QinTitled({label: new QinLabel('Threads Idle Timeout'), items: [this._threadsIdleTimeoutDouble]});
    private _cleanIntervalDouble = new QinDouble();
    private _cleanIntervalTitled = new QinTitled({label: new QinLabel('Clean Interval'), items: [this._cleanIntervalDouble]});
    private _tokenValidityDouble = new QinDouble();
    private _tokenValidityTitled = new QinTitled({label: new QinLabel('Token Validity'), items: [this._tokenValidityDouble]});
    private _threadsLine = new QinLine({items: [this._threadsMinTitled, this._threadsMaxTitled, this._threadsIdleTimeoutTitled, this._cleanIntervalTitled, this._tokenValidityTitled]});

    public constructor() {
        super();
        this.initView();
        this._actionLine.install(this);
        this._serverLine.install(this);
        this._servesLine.install(this);
        this._configLine.install(this);
        this._threadsLine.install(this);
    }

    private initView() {
        this.styleAsWhole();
        this._getButton.addActionMain(_ => this.actGet());
        this._setButton.addActionMain(_ => this.actSet());
    }

    private actGet() {
        this.qinpel.talk.way
            .getSetup()
            .then(setup => this.viewSetup(setup))
            .catch(err => this.qinpel.frame.showError(err, '{qia_abdesk}(ErrCode-000021)'));
    }    

    private actSet() {
        this.qinpel.talk.way
            .setSetup(this.makeSetup())
            .then(res => this.qinpel.frame.showInfo(res, '{qia_abdesk}(ErrCode-000022)'))
            .catch(err => this.qinpel.frame.showError(err, '{qia_abdesk}(ErrCode-000023)'));
    }

    private viewSetup(setup: Setup) {
        this._serverNameChars.value = setup.serverName;
        this._serverLangChars.value = setup.serverLang;
        this._serverHostChars.value = setup.serverHost;
        this._serverPortNumber.value = setup.serverPort;
        this._serverFolderChars.value = setup.serverFolder;
        this._servesPubBool.value = setup.servesPub;
        this._servesAppBool.value = setup.servesApp;
        this._servesDirBool.value = setup.servesDir;
        this._servesCmdBool.value = setup.servesCmd;
        this._servesBasBool.value = setup.servesBas;
        this._servesRegBool.value = setup.servesReg;
        this._servesGizBool.value = setup.servesGiz;
        this._configMap.value = setup.configMap;
        this._redirectMap.value = setup.redirectMap;
        this._threadsMinDouble.value = setup.threadsMin;
        this._threadsMaxDouble.value = setup.threadsMax;
        this._threadsIdleTimeoutDouble.value = setup.threadsIdleTimeout;
        this._cleanIntervalDouble.value = setup.cleanInterval;
        this._tokenValidityDouble.value = setup.tokenValidity;
    }

    private makeSetup(): Setup {
        return {
            serverName: this._serverNameChars.value,
            serverLang: this._serverLangChars.value,
            serverHost: this._serverHostChars.value,
            serverPort: this._serverPortNumber.value,
            serverFolder: this._serverFolderChars.value,
            servesPub: this._servesPubBool.value,
            servesApp: this._servesAppBool.value,
            servesDir: this._servesDirBool.value,
            servesCmd: this._servesCmdBool.value,
            servesBas: this._servesBasBool.value,
            servesReg: this._servesRegBool.value,
            servesGiz: this._servesGizBool.value,
            configMap: this._configMap.valued,
            redirectMap: this._redirectMap.valued,
            threadsMin: this._threadsMinDouble.value,
            threadsMax: this._threadsMaxDouble.value,
            threadsIdleTimeout: this._threadsIdleTimeoutDouble.value,
            cleanInterval: this._cleanIntervalDouble.value,
            tokenValidity: this._tokenValidityDouble.value,
        }
    }

}

class BasesWAY extends QinColumn {

    private _getButton = new QinButton({label: new QinLabel('Get')});
    private _setButton = new QinButton({label: new QinLabel('Set')});
    private _actionLine = new QinLine({items: [this._getButton, this._setButton]});
    private _basesList = new QinList<BasedWays>({editor: new EditBasedWays()});

    public constructor() {
        super();
        this.initView();
        this._actionLine.install(this);
        this._basesList.install(this);
    }

    private initView() {
        this.styleAsWhole();
        this._basesList.styleAsWhole();
        this._getButton.addActionMain(_ => this.actGet());
        this._setButton.addActionMain(_ => this.actSet());
    }

    private actGet() {
        this.qinpel.talk.way
            .getBases()
            .then(bases => this._basesList.value = bases)
            .catch(err => this.qinpel.frame.showError(err, '{qia_abdesk}(ErrCode-000024)'));
    }    

    private actSet() {
        this.qinpel.talk.way
            .setBases(this._basesList.value)
            .then(res => this.qinpel.frame.showInfo(res, '{qia_abdesk}(ErrCode-000025)'))
            .catch(err => this.qinpel.frame.showError(err, '{qia_abdesk}(ErrCode-000026)'));
    }

}

class EditBasedWays extends QinEdit<BasedWays> {

    private _editKindTabs = new QinTabs();
    private _basedJdbcTab = new BasedJdbcTab();
    private _basedLinkTab = new BasedLinkTab();
    private _poolLine = new QinLine();
    private _poolMinIdleInt = new QinInt();
    private _poolMinIdleTitled = new QinTitled({label: new QinLabel("Min Idle"), items: [this._poolMinIdleInt]});
    private _poolMaxIdleInt = new QinInt();
    private _poolMaxIdleTitled = new QinTitled({label: new QinLabel("Max Idle"), items: [this._poolMaxIdleInt]});
    private _poolMaxTotalInt = new QinInt();
    private _poolMaxTotalTitled = new QinTitled({label: new QinLabel("Max Total"), items: [this._poolMaxTotalInt]});
    
    public constructor() {
        super(undefined, "based-ways", new QinColumn());
        this._editKindTabs.install(this);
        this._editKindTabs.addTab({title: "Jdbc", viewer: this._basedJdbcTab});
        this._editKindTabs.addTab({title: "Link", viewer: this._basedLinkTab});
        this._poolLine.install(this);
        this._poolMinIdleTitled.install(this._poolLine);
        this._poolMaxIdleTitled.install(this._poolLine);
        this._poolMaxTotalTitled.install(this._poolLine);
    }

    public override castedQine(): QinColumn {
        return this.qinedBase as QinColumn;
    }

    public override getNature(): Nature {
        return Nature.Object;
    }

    public override mayChange(): HTMLElement[] {
        return [];
    }

    public override turnReadOnly(): void {
        this.styleAsReadOnly();
    }

    public override turnEditable(): void {
        this.styleAsEditable();
    }

    public override isEditable(): boolean {
        return true;
    }

    protected override _getData(): BasedWays {
        const result = {
            dataJdbc: null,
            dataLink: null,
            poolMinIdle: this._poolMinIdleInt.value,
            poolMaxIdle: this._poolMaxIdleInt.value,
            poolMaxTotal: this._poolMaxTotalInt.value
        } as BasedWays;
        if (this._editKindTabs.selected === "Jdbc") {
            result.dataJdbc = this._basedJdbcTab.get();
        } else if (this._editKindTabs.selected === "Link") {
            result.dataLink = this._basedLinkTab.get();
        } 
        return result;
    }

    protected override _setData(data: BasedWays) {
        if (data) {
            if (data.dataJdbc) {
                this._editKindTabs.selected = "Jdbc";
                this._basedJdbcTab.set(data.dataJdbc);
            } else if (data.dataLink) {
                this._editKindTabs.selected = "Link";
                this._basedLinkTab.set(data.dataLink);
            }
            this._poolMinIdleInt.value = data.poolMinIdle ?? null;
            this._poolMaxIdleInt.value = data.poolMaxIdle ?? null;
            this._poolMaxTotalInt.value = data.poolMaxTotal ?? null;
            
        } else {
            this._editKindTabs.selected = "Jdbc";
            this._basedJdbcTab.new();
            this._basedLinkTab.new();
            this._poolMinIdleInt.value = null;
            this._poolMaxIdleInt.value = null;
            this._poolMaxTotalInt.value = null;
        }
    }

}

class BasedJdbcTab extends QinLine {

    public _nameChars = new QinChars();
    public _nameTitled = new QinTitled({label: new QinLabel("Name"), items: [this._nameChars]});
    public _urlChars = new QinChars();
    public _urlTitled = new QinTitled({label: new QinLabel("Url"), items: [this._urlChars]});
    public _userChars = new QinChars();
    public _userTitled = new QinTitled({label: new QinLabel("User"), items: [this._userChars]});
    public _passWord = new QinPassword();
    public _passTitled = new QinTitled({label: new QinLabel("Pass"), items: [this._passWord]});

    public constructor() {
        super();
        this._nameTitled.install(this);
        this._urlTitled.install(this);
        this._userTitled.install(this);
        this._passTitled.install(this);
    }

    public new() {
        this._nameChars.value = "";
        this._urlChars.value = "";
        this._userChars.value = "";
        this._passWord.value = "";
    }

    public get(): BasedJdbc {
        return {
            name: this._nameChars.value,
            url: this._urlChars.value,
            user: this._userChars.value,
            pass: this._passWord.value
        };
    }

    public set(value: BasedJdbc) {
        this._nameChars.value = value?.name ?? "";
        this._urlChars.value = value?.url ?? "";
        this._userChars.value = value?.user ?? "";
        this._passWord.value = value?.pass ?? "";
    }

}

class BasedLinkTab extends QinLine {

    public _nameChars = new QinChars();
    public _nameTitled = new QinTitled({label: new QinLabel("Name"), items: [this._nameChars]});
    public _baseCombo = new QinCombo({ofEnum: Based});
    public _baseTitled = new QinTitled({label: new QinLabel("Base"), items: [this._baseCombo]});
    public _pathChars = new QinChars();
    public _pathTitled = new QinTitled({label: new QinLabel("Path"), items: [this._pathChars]});
    public _portInt = new QinInt();
    public _portTitled = new QinTitled({label: new QinLabel("Port"), items: [this._portInt]});
    public _dataChars = new QinChars();
    public _dataTitled = new QinTitled({label: new QinLabel("Data"), items: [this._dataChars]});
    public _userChars = new QinChars();
    public _userTitled = new QinTitled({label: new QinLabel("User"), items: [this._userChars]});
    public _passWord = new QinPassword();
    public _passTitled = new QinTitled({label: new QinLabel("Pass"), items: [this._passWord]});

    public constructor() {
        super();
        this._nameTitled.install(this);
        this._baseTitled.install(this);
        this._pathTitled.install(this);
        this._portTitled.install(this);
        this._dataTitled.install(this);
        this._userTitled.install(this);
        this._passTitled.install(this);
    }

    public new() {
        this._nameChars.value = "";
        this._baseCombo.value = null;
        this._pathChars.value = "";
        this._portInt.value = null;
        this._dataChars.value = "";
        this._userChars.value = "";
        this._passWord.value = "";
    }

    public get(): BasedLink {
        return {
            name: this._nameChars.value,
            base: this._baseCombo.value as Based,
            path: this._pathChars.value,
            port: this._portInt.value,
            data: this._dataChars.value,
            user: this._userChars.value,
            pass: this._passWord.value
        };
    }

    public set(value: BasedLink) {
        this._nameChars.value = value?.name ?? "";
        this._baseCombo.value = value?.base ?? null;
        this._pathChars.value = value?.path ?? "";
        this._portInt.value = value?.port ?? null;
        this._dataChars.value = value?.data ?? "";
        this._userChars.value = value?.user ?? "";
        this._passWord.value = value?.pass ?? "";
    }

}

class UsersWAY extends QinColumn {

    private _getButton = new QinButton({label: new QinLabel('Get')});
    private _setButton = new QinButton({label: new QinLabel('Set')});
    private _actionLine = new QinLine({items: [this._getButton, this._setButton]});
    private _usersList = new QinList<User>({editor: new EditUser()});

    public constructor() {
        super();
        this.initView();
        this._actionLine.install(this);
        this._usersList.install(this);
    }

    private initView() {
        this.styleAsWhole();
        this._usersList.styleAsWhole();
        this._getButton.addActionMain(_ => this.actGet());
        this._setButton.addActionMain(_ => this.actSet());
    }

    private actGet() {
        this.qinpel.talk.way
            .getUsers()
            .then(users => this._usersList.value = users)
            .catch(err => this.qinpel.frame.showError(err, '{qia_abdesk}(ErrCode-000027)'));
    }

    private actSet() {
        this.qinpel.talk.way
            .setUsers(this._usersList.value)
            .then(res => this.qinpel.frame.showInfo(res, '{qia_abdesk}(ErrCode-000028)'))
            .catch(err => this.qinpel.frame.showError(err, '{qia_abdesk}(ErrCode-000029)'));
    }

}

class EditUser extends QinEdit<User> {
    
    private _nameChars = new QinChars();
    private _nameTitled = new QinTitled({label: new QinLabel("Name"), items: [this._nameChars]});
    private _passChars = new QinPassword();
    private _passTitled = new QinTitled({label: new QinLabel("Pass"), items: [this._passChars]});
    private _homeChars = new QinChars();
    private _homeTitled = new QinTitled({label: new QinLabel("Home"), items: [this._homeChars]});
    private _langChars = new QinChars();
    private _langTitled = new QinTitled({label: new QinLabel("Lang"), items: [this._langChars]});
    private _masterBool = new QinBool();
    private _masterTitled = new QinTitled({label: new QinLabel("Master"), items: [this._masterBool]});
    private _groupChars = new QinChars();
    private _groupTitled = new QinTitled({label: new QinLabel("Group"), items: [this._groupChars]});
    private _mainLine = new QinLine({items: [this._nameTitled, this._passTitled, this._homeTitled, this._langTitled, this._masterTitled, this._groupTitled]});
    private _allowList = new QinList<Allow>({ editor: new EditAllow() });
    private _allowListSizer = new QinSizer(this._allowList);
    private _allowListTitled = new QinTitled({label: new QinLabel("Allow List"), items: [this._allowListSizer]});
    private _configMap = new QinMap<string, string>({editorK: new QinChars(), editorV: new QinChars()});
    private _configMapSizer = new QinSizer(this._configMap);
    private _configMapTitled = new QinTitled({label: new QinLabel("Config Map"), items: [this._configMapSizer]});
    private _otherLine = new QinLine({items: [this._allowListTitled, this._configMapTitled]});

    public constructor() {
        super(undefined, "user", new QinColumn());
        this._mainLine.install(this);
        this._otherLine.install(this);
    }

    public override castedQine(): QinColumn {
        return this.qinedBase as QinColumn;
    }

    public override getNature(): Nature {
        return Nature.Object;
    }

    public override mayChange(): HTMLElement[] {
        return [];
    }

    public override turnReadOnly(): void {
        this.styleAsReadOnly();
    }

    public override turnEditable(): void {
        this.styleAsEditable();
    }

    public override isEditable(): boolean {
        return true;
    }

    protected override _getData(): User {
        return {
            name: this._nameChars.value,
            pass: this._passChars.value,
            home: this._homeChars.value,
            lang: this._langChars.value,
            master: this._masterBool.value,
            allowList: this._allowList.value,
            configMap: this._configMap.valued,
            group: this._groupChars.value
        };
    }

    protected override _setData(data: User) {
        this._nameChars.value = data?.name ?? "";
        this._passChars.value = data?.pass ?? "";
        this._homeChars.value = data?.home ?? "";
        this._langChars.value = data?.lang ?? "";
        this._masterBool.value = data?.master ?? false;
        this._groupChars.value = data?.group ?? "";
        this._allowList.value = data?.allowList ?? [];
        this._configMap.value = data?.configMap ?? new Map<string, string>();
    }
}

class GroupsWAY extends QinColumn {

    private _getButton = new QinButton({label: new QinLabel('Get')});
    private _setButton = new QinButton({label: new QinLabel('Set')});
    private _actionLine = new QinLine({items: [this._getButton, this._setButton]});
    private _groupsList = new QinList<Group>({editor: new EditGroup()});

    public constructor() {
        super();
        this.initView();
        this._actionLine.install(this);
        this._groupsList.install(this);
    }

    private initView() {
        this.styleAsWhole();
        this._groupsList.styleAsWhole();
        this._getButton.addActionMain(_ => this.actGet());
        this._setButton.addActionMain(_ => this.actSet());
    }

    private actGet() {
        this.qinpel.talk.way
            .getGroups()
            .then(groups => this._groupsList.value = groups)
            .catch(err => this.qinpel.frame.showError(err, '{qia_abdesk}(ErrCode-000030)'));
    }

    private actSet() {
        this.qinpel.talk.way
            .setGroups(this._groupsList.value)
            .then(res => this.qinpel.frame.showInfo(res, '{qia_abdesk}(ErrCode-000031)'))
            .catch(err => this.qinpel.frame.showError(err, '{qia_abdesk}(ErrCode-000032)'));
    }

}

class EditGroup extends QinEdit<Group> {
    
    private _nameChars = new QinChars();
    private _nameTitled = new QinTitled({label: new QinLabel("Name"), items: [this._nameChars]});
    private _homeChars = new QinChars();
    private _homeTitled = new QinTitled({label: new QinLabel("Home"), items: [this._homeChars]});
    private _langChars = new QinChars();
    private _langTitled = new QinTitled({label: new QinLabel("Lang"), items: [this._langChars]});
    private _masterBool = new QinBool();
    private _masterTitled = new QinTitled({label: new QinLabel("Master"), items: [this._masterBool]});
    private _mainLine = new QinLine({ items: [this._nameTitled, this._homeTitled, this._langTitled, this._masterTitled]});
    private _allowList = new QinList<Allow>({editor: new EditAllow()});
    private _allowListSizer = new QinSizer(this._allowList);
    private _allowListTitled = new QinTitled({label: new QinLabel("Allow List"), items: [this._allowListSizer]});
    private _configMap = new QinMap<string, string>({editorK: new QinChars(), editorV: new QinChars()});
    private _configMapSizer = new QinSizer(this._configMap);
    private _configMapTitled = new QinTitled({label: new QinLabel("Config Map"), items: [this._configMapSizer]});
    private _otherLine = new QinLine({items: [this._allowListTitled, this._configMapTitled]});

    public constructor() {
        super(undefined, "group", new QinColumn());
        this._mainLine.install(this);
        this._otherLine.install(this);
    }

    public override castedQine(): QinColumn {
        return this.qinedBase as QinColumn;
    }

    public override getNature(): Nature {
        return Nature.Object;
    }

    public override mayChange(): HTMLElement[] {
        return [];
    }

    public override turnReadOnly(): void {
        this.styleAsReadOnly();
    }

    public override turnEditable(): void {
        this.styleAsEditable();
    }

    public override isEditable(): boolean {
        return true;
    }

    protected override _getData(): Group {
        return {
            name: this._nameChars.value,
            home: this._homeChars.value,
            lang: this._langChars.value,
            master: this._masterBool.value,
            allowList: this._allowList.value,
            configMap: this._configMap.valued
        };
    }

    protected override _setData(data: Group) {
        this._nameChars.value = data?.name ?? "";
        this._homeChars.value = data?.home ?? "";
        this._langChars.value = data?.lang ?? "";
        this._masterBool.value = data?.master ?? false;
        this._allowList.value = data?.allowList ?? [];
        this._configMap.value = data?.configMap ?? new Map<string, string>();
    }
}

class EditAllow extends QinEdit<Allow> {

    private _appNameChars = new QinChars();
    private _appNameTitled = new QinTitled({label: new QinLabel("Name"), items:[this._appNameChars]});
    private _appLine = new QinLine({items: [this._appNameTitled]});
    private _appLineTitled = new QinTitled({label: new QinLabel("App"), items: [this._appLine]});
    private _dirPathChars = new QinChars();
    private _dirPathTitled = new QinTitled({label: new QinLabel("Path"), items: [this._dirPathChars]});
    private _dirMutateBool = new QinBool();
    private _dirMutateTitled = new QinTitled({label: new QinLabel("Mutate"), items: [this._dirMutateBool]});
    private _dirLine = new QinLine({items: [this._dirPathTitled, this._dirMutateTitled]});
    private _dirLineTitled = new QinTitled({label: new QinLabel("Dir"), items: [this._dirLine]});
    private _cmdNameChars = new QinChars();
    private _cmdNameTitled = new QinTitled({label: new QinLabel("Name"), items: [this._cmdNameChars]});
    private _cmdArgList = new QinList<string>({editor: new QinChars()});
    private _cmdArgListSizer = new QinSizer(this._cmdArgList);
    private _cmdArgListTitled = new QinTitled({label: new QinLabel("Arg List"), items: [this._cmdArgListSizer]});
    private _cmdLine = new QinLine({items: [this._cmdNameTitled, this._cmdArgListTitled]});
    private _cmdLineTitled = new QinTitled({label: new QinLabel("Cmd"), items: [this._cmdLine]});
    private _basNameChars = new QinChars();
    private _basNameTitled = new QinTitled({label: new QinLabel("Name"), items: [this._basNameChars]});
    private _basMutateBool = new QinBool();
    private _basMutateTitled = new QinTitled({label: new QinLabel("Mutate"), items: [this._basMutateBool]});
    private _basLine = new QinLine({items: [this._basNameTitled, this._basMutateTitled]});
    private _basLineTitled = new QinTitled({label: new QinLabel("Bas"), items: [this._basLine]});
    private _regRegistryBaseChars = new QinChars();
    private _regRegistryBaseTitled = new QinTitled({label: new QinLabel("Base"), items: [this._regRegistryBaseChars]});
    private _regRegistryTableHeadCatalogChars = new QinChars();
    private _regRegistryTableHeadCatalogTitled = new QinTitled({label: new QinLabel("Catalog"), items: [this._regRegistryTableHeadCatalogChars]});
    private _regRegistryTableHeadSchemaChars = new QinChars();
    private _regRegistryTableHeadSchemaTitled = new QinTitled({label: new QinLabel("Schema"), items: [this._regRegistryTableHeadSchemaChars]});
    private _regRegistryTableHeadNameChars = new QinChars();
    private _regRegistryTableHeadNameTitled = new QinTitled({label: new QinLabel("Table"), items: [this._regRegistryTableHeadNameChars]});
    private _regRegistryTableHeadAliasChars = new QinChars();
    private _regRegistryTableHeadAliasTitled = new QinTitled({label: new QinLabel("Alias"), items: [this._regRegistryTableHeadAliasChars]});
    private _regRegistryTableHeadLine = new QinLine({items: [this._regRegistryTableHeadCatalogTitled, this._regRegistryTableHeadSchemaTitled, this._regRegistryTableHeadNameTitled, this._regRegistryTableHeadAliasTitled]});
    private _regRegistryTableHeadTitled = new QinTitled({label: new QinLabel("Table Head"), items: [this._regRegistryTableHeadLine]});
    private _regRegistryLine = new QinLine({items: [this._regRegistryBaseTitled, this._regRegistryTableHeadTitled]});
    private _regRegistryTitled = new QinTitled({label: new QinLabel("Registry"), items: [this._regRegistryLine]});
    private _regOpsAllBool = new QinBool();
    private _regOpsAllTitled = new QinTitled({label: new QinLabel("All"), items: [this._regOpsAllBool]});
    private _regOpsInsertBool = new QinBool();
    private _regOpsInsertTitled = new QinTitled({label: new QinLabel("Insert"), items: [this._regOpsInsertBool]});
    private _regOpsSelectBool = new QinBool();
    private _regOpsSelectTitled = new QinTitled({label: new QinLabel("Select"), items: [this._regOpsSelectBool]});
    private _regOpsUpdateBool = new QinBool();
    private _regOpsUpdateTitled = new QinTitled({label: new QinLabel("Update"), items: [this._regOpsUpdateBool]});
    private _regOpsDeleteBool = new QinBool();
    private _regOpsDeleteTitled = new QinTitled({label: new QinLabel("Delete"), items: [this._regOpsDeleteBool]});
    private _regOpsLine = new QinLine({items: [this._regOpsAllTitled, this._regOpsInsertTitled, this._regOpsSelectTitled, this._regOpsUpdateTitled, this._regOpsDeleteTitled]});
    private _regOpsTitled = new QinTitled({label: new QinLabel("Ops"), items: [this._regOpsLine]});
    private _regStrainRestrictChars = new QinChars();
    private _regStrainRestrictTitled = new QinTitled({label: new QinLabel("Restrict"), items: [this._regStrainRestrictChars]});
    private _regStrainModifyChars = new QinChars();
    private _regStrainModifyTitled = new QinTitled({label: new QinLabel("Modify"), items: [this._regStrainModifyChars]});
    private _regStrainIncludeChars = new QinChars();
    private _regStrainIncludeTitled = new QinTitled({label: new QinLabel("Include"), items: [this._regStrainIncludeChars]});
    private _regStrainLine = new QinLine({items: [this._regStrainRestrictTitled, this._regStrainModifyTitled, this._regStrainIncludeTitled]});
    private _regStrainTitled = new QinTitled({label: new QinLabel("Strain"), items: [this._regStrainLine]});
    private _regLineTitled = new QinTitled({label: new QinLabel("Reg"), items: [this._regRegistryTitled, this._regOpsTitled, this._regStrainTitled]});
    private _gizPathChars = new QinChars();
    private _gizPathTitled = new QinTitled({label: new QinLabel("Path"), items: [this._gizPathChars]});
    private _gizLine = new QinLine({items: [this._gizPathTitled]});
    private _gizLineTitled = new QinTitled({label: new QinLabel("Giz"), items: [this._gizLine]});

    public constructor() {
        super(undefined, "allow", new QinColumn());
        this._appLineTitled.install(this);
        this._dirLineTitled.install(this);
        this._cmdLineTitled.install(this);
        this._basLineTitled.install(this);
        this._regLineTitled.install(this);
        this._gizLineTitled.install(this);
    }

    public override castedQine(): QinColumn {
        return this.qinedBase as QinColumn;
    }

    public override getNature(): Nature {
        return Nature.Chars;
    }

    public override mayChange(): HTMLElement[] {
        return [];
    }

    public override turnReadOnly(): void {
        this.styleAsReadOnly();
    }

    public override turnEditable(): void {
        this.styleAsEditable();
    }

    public override isEditable(): boolean {
        return true;
    }

    protected override _getData(): Allow {
        return {
            allowApp: {
                name: this._appNameChars.value
            },
            allowDir: {
                path: this._dirPathChars.value,
                mutate: this._dirMutateBool.value
            },
            allowCmd: {
                name: this._cmdNameChars.value,
                argList: this._cmdArgList.value
            },
            allowBas: {
                name: this._basNameChars.value,
                mutate: this._basMutateBool.value
            },
            allowReg: {
                registry: {
                    base: this._regRegistryBaseChars.value,
                    tableHead: {
                        catalog: this._regRegistryTableHeadCatalogChars.value,
                        schema: this._regRegistryTableHeadSchemaChars.value,
                        name: this._regRegistryTableHeadNameChars.value,
                        alias: this._regRegistryTableHeadAliasChars.value
                    }
                },
                all: this._regOpsAllBool.value,
                insert: this._regOpsInsertBool.value,
                select: this._regOpsSelectBool.value,
                update: this._regOpsUpdateBool.value,
                delete: this._regOpsDeleteBool.value,
                strain: {
                    restrict: this._regStrainRestrictChars.value,
                    modify: this._regStrainModifyChars.value,
                    include: this._regStrainIncludeChars.value
                }
            },
            allowGiz: {
                path: this._gizPathChars.value
            }
        };
    }

    protected override _setData(data: Allow) {
        this._appNameChars.value = data?.allowApp?.name ?? "";
        this._dirPathChars.value = data?.allowDir?.path ?? "";
        this._dirMutateBool.value = data?.allowDir?.mutate ?? false;
        this._cmdNameChars.value = data?.allowCmd?.name ?? "";
        this._cmdArgList.value = data?.allowCmd?.argList ?? [];
        this._basNameChars.value = data?.allowBas?.name ?? "";
        this._basMutateBool.value = data?.allowBas?.mutate ?? false;
        this._regRegistryBaseChars.value = data?.allowReg?.registry?.base ?? "";
        this._regRegistryTableHeadCatalogChars.value = data?.allowReg?.registry?.tableHead?.catalog ?? "";
        this._regRegistryTableHeadSchemaChars.value = data?.allowReg?.registry?.tableHead?.schema ?? "";
        this._regRegistryTableHeadNameChars.value = data?.allowReg?.registry?.tableHead?.name ?? "";
        this._regRegistryTableHeadAliasChars.value = data?.allowReg?.registry?.tableHead?.alias ?? "";
        this._regOpsAllBool.value = data?.allowReg?.all ?? false;
        this._regOpsInsertBool.value = data?.allowReg?.insert ?? false;
        this._regOpsSelectBool.value = data?.allowReg?.select ?? false;
        this._regOpsUpdateBool.value = data?.allowReg?.update ?? false;
        this._regOpsDeleteBool.value = data?.allowReg?.delete ?? false;
        this._regStrainRestrictChars.value = data?.allowReg?.strain?.restrict ?? "";
        this._regStrainModifyChars.value = data?.allowReg?.strain?.modify ?? "";
        this._regStrainIncludeChars.value = data?.allowReg?.strain?.include ?? "";
        this._gizPathChars.value = data?.allowGiz?.path ?? "";
    }

}