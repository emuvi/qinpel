import { QinBool, QinButton, QinColumn, QinCombo, QinLabel, QinLine, QinNumeric, QinChars, QinTabs, QinText, QinTitled } from "qin_case";
import { Module } from "./module";
import { JoinTies, FilterSeems, FilterLikes, FilterTies, Nature } from "qin_soul"
import { Delete, Filter, Insert, Join, Linked, Order, Select, Table, TableHead, ToGetID, Typed, Update, Valued } from "../../qin_soul/types/qin-type";

export class ModuleREG extends Module {
    private _bodyTabs = new QinTabs();
    private _topREG = new TopREG();
    private _seeREG = new SeeREG();
    private _canREG = new CanREG();
    private _newREG = new NewREG();
    private _askREG = new AskREG();
    private _setREG = new SetREG();
    private _delREG = new DelREG();
    private _auxREG = new AuxREG();

    public constructor() {
        super();
        this._bodyTabs.styleAsWhole();
        this._bodyTabs.addTab({title: "Top", viewer: this._topREG});
        this._bodyTabs.addTab({title: "See", viewer: this._seeREG});
        this._bodyTabs.addTab({title: "Can", viewer: this._canREG});
        this._bodyTabs.addTab({title: "New", viewer: this._newREG});
        this._bodyTabs.addTab({title: "Ask", viewer: this._askREG});
        this._bodyTabs.addTab({title: "Set", viewer: this._setREG});
        this._bodyTabs.addTab({title: "Del", viewer: this._delREG});
        this._bodyTabs.addTab({title: "Aux", viewer: this._auxREG});
        this._bodyTabs.install(this);
    }
}

class TopREG extends QinColumn {
    private _actLine = new QinLine();
    private _baseChars = new QinChars();
    private _baseTitled = new QinTitled({label: new QinLabel("Base"), items: [this._baseChars]});
    private _runButton = new QinButton({label: new QinLabel("Run")});
    private _resultText = new QinText({readOnly: true});

    public constructor() {
        super();
        this.styleAsWhole();
        this._resultText.styleAsWhole();
        this._runButton.addActionMain(_ => this.actRun());
        this._baseTitled.install(this._actLine);
        this._runButton.install(this._actLine);
        this._actLine.install(this);
        this._resultText.install(this);
    }

    private actRun() {
        const base = this._baseChars.value;
        this.qinpel.talk.reg.top(base)
                .then((heads) => this._resultText.value = JSON.stringify(heads, null, 2))
                .catch((err) => this.qinpel.frame.showError(err, "{qia_abdesk}(ErrCode-000007)"))
    }
}

class SeeREG extends QinColumn {
    private _actLine = new QinLine();
    private _baseChars = new QinChars();
    private _baseTitled = new QinTitled({label: new QinLabel("Base"), items: [this._baseChars]});
    private _tableHeadText = new QinText();
    private _tableHeadTitled = new QinTitled({label: new QinLabel("Table Head"), items: [this._tableHeadText]});
    private _runButton = new QinButton({label: new QinLabel("Run")});
    private _makeLine = new QinLine();
    private _makeLabel = new QinLabel("Make:");
    private _insertButton = new QinButton({label: new QinLabel("Insert")});
    private _selectButton = new QinButton({label: new QinLabel("Select")});
    private _updateButton = new QinButton({label: new QinLabel("Update")});
    private _deleteButton = new QinButton({label: new QinLabel("Delete")});
    private _resultText = new QinText({readOnly: true});

    public constructor() {
        super();
        this.styleAsWhole();
        this._resultText.styleAsWhole();
        this._runButton.addActionMain(_ => this.actRun());
        this._insertButton.addActionMain(_ => this.makeInsert());
        this._selectButton.addActionMain(_ => this.makeSelect());
        this._updateButton.addActionMain(_ => this.makeUpdate());
        this._deleteButton.addActionMain(_ => this.makeDelete());
        this._baseTitled.install(this._actLine);
        this._tableHeadTitled.install(this._actLine);
        this._runButton.install(this._actLine);
        this._makeLabel.install(this._makeLine);
        this._insertButton.install(this._makeLine);
        this._selectButton.install(this._makeLine);
        this._updateButton.install(this._makeLine);
        this._deleteButton.install(this._makeLine);
        this._actLine.install(this);
        this._makeLine.install(this);
        this._resultText.install(this);
    }

    private actRun() {
        const base = this._baseChars.value;
        const tableHead = JSON.parse(this._tableHeadText.value) as TableHead;
        const registry = this.qinpel.talk.reg.aux.newRegistry(base, tableHead);
        this.qinpel.talk.reg.see(registry)
                .then((table) => this._resultText.value = JSON.stringify(table, null, 2))
                .catch((err) => this.qinpel.frame.showError(err, "{qia_abdesk}(ErrCode-000008)"))
    }

    private makeInsert() {
        const table = JSON.parse(this._resultText.value) as Table;
        const tableHead = table.tableHead;
        const valuedList = this.convertFieldsToValuedList(table);
        const insert = this.qinpel.talk.reg.aux
                .newInsert(tableHead, valuedList, null);
        this._resultText.value = JSON.stringify(insert, null, 2);
    }

    private makeSelect() {
        const table = JSON.parse(this._resultText.value) as Table;
        const tableHead = table.tableHead;
        const fieldList = this.convertFieldsToTypedList(table);
        const joinList = this.convertForeignToJoinList(table);
        const filterList = this.convertFieldsToFilterList(table);
        const orderList = this.convertPrimaryToOrderList(table);
        const select = this.qinpel.talk.reg.aux
                .newSelect(tableHead, fieldList, joinList, filterList, orderList, null, null);
        this._resultText.value = JSON.stringify(select, null, 2);
    }

    private makeUpdate() {
        const table = JSON.parse(this._resultText.value) as Table;
        const tableHead = table.tableHead;
        const valuedList = this.convertFieldsToValuedList(table);
        const filterList = this.convertFieldsToFilterList(table);
        const update = this.qinpel.talk.reg.aux
                .newUpdate(tableHead, valuedList, filterList, null);
        this._resultText.value = JSON.stringify(update, null, 2);
    }

    private makeDelete() {
        const table = JSON.parse(this._resultText.value) as Table;
        const tableHead = table.tableHead;
        const filterList = this.convertFieldsToFilterList(table);
        const delety = this.qinpel.talk.reg.aux
                .newDelete(tableHead, filterList);
        this._resultText.value = JSON.stringify(delety, null, 2);
    }

    private convertFieldsToTypedList(table: Table): Array<Typed> {
        const result = new Array<Typed>();
        for (const field of table.fieldList) {
            const typed = this.qinpel.talk.reg.aux
                    .newTyped(field.name, field.nature, null);
            result.push(typed);
        }
        return result;
    }

    private convertFieldsToValuedList(table: Table): Array<Valued> {
        const result = new Array<Valued>();
        for (const field of table.fieldList) {
            if (!field.keyPrimary) {
                const valued = this.qinpel.talk.reg.aux
                        .newValued(field.name, field.nature, null);
                result.push(valued);
            }
        }
        return result;
    }

    private convertFieldsToFilterList(table: Table): Array<Filter> {
        const result = new Array<Filter>();
        for (const field of table.fieldList) {
            if (field.keyPrimary) {
                const valued = this.qinpel.talk.reg.aux
                        .newValued(field.name, field.nature, null);
                const filter = this.qinpel.talk.reg.aux
                       .newFilter(FilterSeems.Is, FilterLikes.Equals, valued, null, FilterTies.And);
                result.push(filter);
            }
        }
        return result;
    }

    private convertForeignToJoinList(table: Table): Array<Join> {
        const result = new Array<Join>();
        for (const foreign of table.keyForeignList) {
            const filterList = new Array<Filter>;
            for (const match of foreign.matchList) {
                const linked = this.qinpel.talk.reg.aux
                        .newLinked(match.inColumn, match.outColumn);
                const filter = this.qinpel.talk.reg.aux
                        .newFilter(FilterSeems.Is, FilterLikes.Equals, null, linked, FilterTies.And);
                filterList.push(filter);
            }
            const join = this.qinpel.talk.reg.aux
                    .newJoin(foreign.outTableHead, null, filterList, JoinTies.Left);
            result.push(join);
        }
        return result;
    }

    private convertPrimaryToOrderList(table: Table): Array<Order> {
        const result = new Array<Order>();
        for (const primary of table.keyPrimaryList) {
            for (const primaryColumn of primary.columnList) {
                const order = this.qinpel.talk.reg.aux
                        .newOrder(primaryColumn.name, false);
                result.push(order);
            }
        }
        return result;
    }
}

class CanREG extends QinColumn {
    private _actLine = new QinLine();
    private _baseChars = new QinChars();
    private _baseTitled = new QinTitled({label: new QinLabel("Base"), items: [this._baseChars]});
    private _tableHeadText = new QinText();
    private _tableHeadTitled = new QinTitled({label: new QinLabel("Table Head"), items: [this._tableHeadText]});
    private _runButton = new QinButton({label: new QinLabel("Run")});
    private _resultText = new QinText({readOnly: true});

    public constructor() {
        super();
        this.styleAsWhole();
        this._resultText.styleAsWhole();
        this._runButton.addActionMain(_ => this.actRun());
        this._baseTitled.install(this._actLine);
        this._tableHeadTitled.install(this._actLine);
        this._runButton.install(this._actLine);
        this._actLine.install(this);
        this._resultText.install(this);
    }

    private actRun() {
        const base = this._baseChars.value;
        const tableHead = JSON.parse(this._tableHeadText.value) as TableHead;
        const registry = this.qinpel.talk.reg.aux.newRegistry(base, tableHead);
        this.qinpel.talk.reg.can(registry)
                .then((allowReg) => this._resultText.value = JSON.stringify(allowReg, null, 2))
                .catch((err) => this.qinpel.frame.showError(err, "{qia_abdesk}(ErrCode-000009)"))
    }
}

class NewREG extends QinColumn {
    private _actLine = new QinLine();
    private _baseChars = new QinChars();
    private _baseTitled = new QinTitled({label: new QinLabel("Base"), items: [this._baseChars]});
    private _insertText = new QinText();
    private _insertTitled = new QinTitled({label: new QinLabel("Insert"), items: [this._insertText]});
    private _runButton = new QinButton({label: new QinLabel("Run")});
    private _resultText = new QinText({readOnly: true});

    public constructor() {
        super();
        this.styleAsWhole();
        this._resultText.styleAsWhole();
        this._runButton.addActionMain(_ => this.actRun());
        this._baseTitled.install(this._actLine);
        this._insertTitled.install(this._actLine);
        this._runButton.install(this._actLine);
        this._actLine.install(this);
        this._resultText.install(this);
    }

    private actRun() {
        const base = this._baseChars.value;
        const insert = JSON.parse(this._insertText.value) as Insert;
        const toInsert = this.qinpel.talk.reg.aux.newToInsert(base, insert);
        this.qinpel.talk.reg.new(toInsert)
                .then((inserted) => this._resultText.value = inserted)
                .catch((err) => this.qinpel.frame.showError(err, "{qia_abdesk}(ErrCode-000010)"))
    }
}

class AskREG extends QinColumn {
    private _actLine = new QinLine();
    private _baseChars = new QinChars();
    private _baseTitled = new QinTitled({label: new QinLabel("Base"), items: [this._baseChars]});
    private _selectText = new QinText();
    private _selectTitled = new QinTitled({label: new QinLabel("Select"), items: [this._selectText]});
    private _runButton = new QinButton({label: new QinLabel("Run")});
    private _resultText = new QinText({readOnly: true});

    public constructor() {
        super();
        this.styleAsWhole();
        this._resultText.styleAsWhole();
        this._runButton.addActionMain(_ => this.actRun());
        this._baseTitled.install(this._actLine);
        this._selectTitled.install(this._actLine);
        this._runButton.install(this._actLine);
        this._actLine.install(this);
        this._resultText.install(this);
    }

    private actRun() {
        const base = this._baseChars.value;
        const select = JSON.parse(this._selectText.value) as Select;
        const toSelect = this.qinpel.talk.reg.aux.newToSelect(base, select);
        this.qinpel.talk.reg.ask(toSelect)
                .then((selected) => this._resultText.value = selected)
                .catch((err) => this.qinpel.frame.showError(err, "{qia_abdesk}(ErrCode-000011)"))
    }
}

class SetREG extends QinColumn {
    private _actLine = new QinLine();
    private _baseChars = new QinChars();
    private _baseTitled = new QinTitled({label: new QinLabel("Base"), items: [this._baseChars]});
    private _updateText = new QinText();
    private _updateTitled = new QinTitled({label: new QinLabel("Update"), items: [this._updateText]});
    private _runButton = new QinButton({label: new QinLabel("Run")});
    private _resultText = new QinText({readOnly: true});

    public constructor() {
        super();
        this.styleAsWhole();
        this._resultText.styleAsWhole();
        this._runButton.addActionMain(_ => this.actRun());
        this._baseTitled.install(this._actLine);
        this._updateTitled.install(this._actLine);
        this._runButton.install(this._actLine);
        this._actLine.install(this);
        this._resultText.install(this);
    }

    private actRun() {
        const base = this._baseChars.value;
        const update = JSON.parse(this._updateText.value) as Update;
        const toUpdate = this.qinpel.talk.reg.aux.newToUpdate(base, update);
        this.qinpel.talk.reg.set(toUpdate)
                .then((updated) => this._resultText.value = updated)
                .catch((err) => this.qinpel.frame.showError(err, "{qia_abdesk}(ErrCode-000012)"))
    }
}

class DelREG extends QinColumn {
    private _actLine = new QinLine();
    private _baseChars = new QinChars();
    private _baseTitled = new QinTitled({label: new QinLabel("Base"), items: [this._baseChars]});
    private _deleteText = new QinText();
    private _deleteTitled = new QinTitled({label: new QinLabel("Delete"), items: [this._deleteText]});
    private _runButton = new QinButton({label: new QinLabel("Run")});
    private _resultText = new QinText({readOnly: true});

    public constructor() {
        super();
        this.styleAsWhole();
        this._resultText.styleAsWhole();
        this._runButton.addActionMain(_ => this.actRun());
        this._baseTitled.install(this._actLine);
        this._deleteTitled.install(this._actLine);
        this._runButton.install(this._actLine);
        this._actLine.install(this);
        this._resultText.install(this);
    }

    private actRun() {
        const base = this._baseChars.value;
        const delety = JSON.parse(this._deleteText.value) as Delete;
        const toDelete = this.qinpel.talk.reg.aux.newToDelete(base, delety);
        this.qinpel.talk.reg.del(toDelete)
                .then((deleted) => this._resultText.value = deleted)
                .catch((err) => this.qinpel.frame.showError(err, "{qia_abdesk}(ErrCode-000013)"))
    }
}

class AuxREG extends QinColumn {
    private _bodyTabs = new QinTabs();
    private _tableHeadAux = new RegAuxTableHead();
    private _registryAux = new RegAuxRegistry();
    private _insertAux = new RegAuxInsert();
    private _selectAux = new RegAuxSelect();
    private _updateAux = new RegAuxUpdate();
    private _deleteAux = new RegAuxDelete();
    private _toGetIDAux = new RegAuxToGetID();
    private _joinAux = new RegAuxJoin();
    private _filterAux = new RegAuxFilter();
    private _linkedAux = new RegAuxLinked();
    private _orderAux = new RegAuxOrder();
    private _valuedAux = new RegAuxValued();
    private _typedAux = new RegAuxTyped();

    public constructor() {
        super();
        this.styleAsWhole();
        this._bodyTabs.styleAsWhole();
        this._bodyTabs.addTab({title: "Table Head", viewer: this._tableHeadAux});
        this._bodyTabs.addTab({title: "Registry", viewer: this._registryAux});
        this._bodyTabs.addTab({title: "Insert", viewer: this._insertAux});
        this._bodyTabs.addTab({title: "Select", viewer: this._selectAux});
        this._bodyTabs.addTab({title: "Update", viewer: this._updateAux});
        this._bodyTabs.addTab({title: "Delete", viewer: this._deleteAux});
        this._bodyTabs.addTab({title: "ToGetID", viewer: this._toGetIDAux});
        this._bodyTabs.addTab({title: "Join", viewer: this._joinAux});
        this._bodyTabs.addTab({title: "Filter", viewer: this._filterAux});
        this._bodyTabs.addTab({title: "Linked", viewer: this._linkedAux});
        this._bodyTabs.addTab({title: "Order", viewer: this._orderAux});
        this._bodyTabs.addTab({title: "Valued", viewer: this._valuedAux});
        this._bodyTabs.addTab({title: "Typed", viewer: this._typedAux});
        this._bodyTabs.install(this);
    }
}

class RegAuxTableHead extends QinColumn {
    private _newLine = new QinLine();
    private _catalogChars = new QinChars();
    private _catalogTitled = new QinTitled({label: new QinLabel("Catalog"), items: [this._catalogChars]});
    private _schemaChars = new QinChars();
    private _schemaTitled = new QinTitled({label: new QinLabel("Schema"), items: [this._schemaChars]});
    private _nameChars = new QinChars();
    private _nameTitled = new QinTitled({label: new QinLabel("Name"), items: [this._nameChars]});
    private _aliasChars = new QinChars();
    private _aliasTitled = new QinTitled({label: new QinLabel("Alias"), items: [this._aliasChars]});
    private _newButton = new QinButton({label: new QinLabel("New")});
    private _resultText = new QinText({readOnly: true});

    public constructor() {
        super();
        this.styleAsWhole();
        this._resultText.styleAsWhole();
        this._newButton.addActionMain(_ => this.actNew());
        this._catalogTitled.install(this._newLine);
        this._schemaTitled.install(this._newLine);
        this._nameTitled.install(this._newLine);
        this._aliasTitled.install(this._newLine);
        this._newButton.install(this._newLine);
        this._newLine.install(this);
        this._resultText.install(this);
    }

    private actNew() {
        const tableHead = this.qinpel.talk.reg.aux
                .newTableHead(
                    this._catalogChars.value,
                    this._schemaChars.value,
                    this._nameChars.value,
                    this._aliasChars.value
                );
        this._resultText.value = JSON.stringify(tableHead, null, 2);
    }
}

class RegAuxRegistry extends QinColumn {
    private _newLine = new QinLine();
    private _baseChars = new QinChars();
    private _baseTitled = new QinTitled({label: new QinLabel("Base"), items: [this._baseChars]});
    private _tableHeadText = new QinText();
    private _tableHeadTitled = new QinTitled({label: new QinLabel("Table Head"), items: [this._tableHeadText]});
    private _newButton = new QinButton({label: new QinLabel("New")});
    private _resultText = new QinText({readOnly: true});

    public constructor() {
        super();
        this.styleAsWhole();
        this._resultText.styleAsWhole();
        this._newButton.addActionMain(_ => this.actNew());
        this._baseTitled.install(this._newLine);
        this._tableHeadTitled.install(this._newLine);
        this._newButton.install(this._newLine);
        this._newLine.install(this);
        this._resultText.install(this);
    }

    private actNew() {
        const base = this._baseChars.value;
        const tableHead = JSON.parse(this._tableHeadText.value) as TableHead;
        const registry = this.qinpel.talk.reg.aux.newRegistry(base, tableHead);
        this._resultText.value = JSON.stringify(registry, null, 2);
    }
}

class RegAuxInsert extends QinColumn {
    private _newLine = new QinLine();
    private _tableHeadText = new QinText();
    private _tableHeadTitled = new QinTitled({label: new QinLabel("Table Head"), items: [this._tableHeadText]});
    private _valuedListText = new QinText();
    private _valuedListTitled = new QinTitled({label: new QinLabel("Valued List"), items: [this._valuedListText]});
    private _toGetIDText = new QinText();
    private _toGetIDTitled = new QinTitled({label: new QinLabel("To Get ID"), items: [this._toGetIDText]});
    private _newButton = new QinButton({label: new QinLabel("New")});
    private _resultText = new QinText({readOnly: true});

    public constructor() {
        super();
        this.styleAsWhole();
        this._resultText.styleAsWhole();
        this._newButton.addActionMain(_ => this.actNew());
        this._tableHeadTitled.install(this._newLine);
        this._valuedListTitled.install(this._newLine);
        this._toGetIDTitled.install(this._newLine);
        this._newButton.install(this._newLine);
        this._newLine.install(this);
        this._resultText.install(this);
    }

    private actNew() {
        const tableHead = JSON.parse(this._tableHeadText.value) as TableHead;
        const valuedList = JSON.parse(this._valuedListText.value) as Array<Valued>;
        const toGetID = JSON.parse(this._toGetIDText.value) as ToGetID;
        const insert = this.qinpel.talk.reg.aux
                .newInsert(tableHead, valuedList, toGetID);
        this._resultText.value = JSON.stringify(insert, null, 2);
    }
}

class RegAuxSelect extends QinColumn {
    private _newLine = new QinLine();
    private _tableHeadText = new QinText();
    private _tableHeadTitled = new QinTitled({label: new QinLabel("Table Head"), items: [this._tableHeadText]});
    private _fieldListText = new QinText();
    private _fieldListTitled = new QinTitled({label: new QinLabel("Field List"), items: [this._fieldListText]});
    private _joinListText = new QinText();
    private _joinListTitled = new QinTitled({label: new QinLabel("Join List"), items: [this._joinListText]});
    private _filterListText = new QinText();
    private _filterListTitled = new QinTitled({label: new QinLabel("Filter List"), items: [this._filterListText]});
    private _orderListText = new QinText();
    private _orderListTitled = new QinTitled({label: new QinLabel("Order List"), items: [this._orderListText]});
    private _offsetNumeric = new QinNumeric();
    private _offsetTitled = new QinTitled({label: new QinLabel("Offset"), items: [this._offsetNumeric]});
    private _limitNumeric = new QinNumeric();
    private _limitTitled = new QinTitled({label: new QinLabel("Limit"), items: [this._limitNumeric]});
    private _newButton = new QinButton({label: new QinLabel("New")});
    private _resultText = new QinText({readOnly: true});

    public constructor() {
        super();
        this.styleAsWhole();
        this._resultText.styleAsWhole();
        this._newButton.addActionMain(_ => this.actNew());
        this._tableHeadTitled.install(this._newLine);
        this._fieldListTitled.install(this._newLine);
        this._joinListTitled.install(this._newLine);
        this._filterListTitled.install(this._newLine);
        this._orderListTitled.install(this._newLine);
        this._offsetTitled.install(this._newLine);
        this._limitTitled.install(this._newLine);
        this._newButton.install(this._newLine);
        this._newLine.install(this);
        this._resultText.install(this);
    }

    private actNew() {
        const tableHead = JSON.parse(this._tableHeadText.value) as TableHead;
        const fieldList = JSON.parse(this._fieldListText.value) as Array<Typed>;
        const joinList = JSON.parse(this._joinListText.value) as Array<Join>;
        const filterList = JSON.parse(this._filterListText.value) as Array<Filter>;
        const orderList = JSON.parse(this._orderListText.value) as Array<Order>;
        const offset = this._offsetNumeric.value;
        const limit = this._limitNumeric.value;
        const select = this.qinpel.talk.reg.aux
                .newSelect(tableHead, fieldList, joinList, filterList, orderList, offset, limit);
        this._resultText.value = JSON.stringify(select, null, 2);
    }
}

class RegAuxUpdate extends QinColumn {
    private _newLine = new QinLine();
    private _tableHeadText = new QinText();
    private _tableHeadTitled = new QinTitled({label: new QinLabel("Table Head"), items: [this._tableHeadText]});
    private _valuedListText = new QinText();
    private _valuedListTitled = new QinTitled({label: new QinLabel("Valued List"), items: [this._valuedListText]});
    private _filterListText = new QinText();
    private _filterListTitled = new QinTitled({label: new QinLabel("Filter List"), items: [this._filterListText]});
    private _limitNumeric = new QinNumeric();
    private _limitTitled = new QinTitled({label: new QinLabel("Limit"), items: [this._limitNumeric]});
    private _newButton = new QinButton({label: new QinLabel("New")});
    private _resultText = new QinText({readOnly: true});

    public constructor() {
        super();
        this.styleAsWhole();
        this._resultText.styleAsWhole();
        this._newButton.addActionMain(_ => this.actNew());
        this._tableHeadTitled.install(this._newLine);
        this._valuedListTitled.install(this._newLine);
        this._filterListTitled.install(this._newLine);
        this._limitTitled.install(this._newLine);
        this._newButton.install(this._newLine);
        this._newLine.install(this);
        this._resultText.install(this);
    }

    private actNew() {
        const tableHead = JSON.parse(this._tableHeadText.value) as TableHead;
        const valuedList = JSON.parse(this._valuedListText.value) as Array<Valued>;
        const filterList = JSON.parse(this._filterListText.value) as Array<Filter>;
        const limit = this._limitNumeric.value;
        const update = this.qinpel.talk.reg.aux
                .newUpdate(tableHead, valuedList, filterList, limit);
        this._resultText.value = JSON.stringify(update, null, 2);
    }
}

class RegAuxDelete extends QinColumn {
    private _newLine = new QinLine();
    private _tableHeadText = new QinText();
    private _tableHeadTitled = new QinTitled({label: new QinLabel("Table Head"), items: [this._tableHeadText]});
    private _filterListText = new QinText();
    private _filterListTitled = new QinTitled({label: new QinLabel("Filter List"), items: [this._filterListText]});
    private _newButton = new QinButton({label: new QinLabel("New")});
    private _resultText = new QinText({readOnly: true});

    public constructor() {
        super();
        this.styleAsWhole();
        this._resultText.styleAsWhole();
        this._newButton.addActionMain(_ => this.actNew());
        this._tableHeadTitled.install(this._newLine);
        this._filterListTitled.install(this._newLine);
        this._newButton.install(this._newLine);
        this._newLine.install(this);
        this._resultText.install(this);
    }

    private actNew() {
        const tableHead = JSON.parse(this._tableHeadText.value) as TableHead;
        const filterList = JSON.parse(this._filterListText.value) as Array<Filter>;
        const delety = this.qinpel.talk.reg.aux
                .newDelete(tableHead, filterList);
        this._resultText.value = JSON.stringify(delety, null, 2);
    }
}

class RegAuxToGetID extends QinColumn {
    private _newLine = new QinLine();
    private _nameChars = new QinChars();
    private _nameTitled = new QinTitled({label: new QinLabel("Name"), items: [this._nameChars]});
    private _filterText = new QinText();
    private _filterTitled = new QinTitled({label: new QinLabel("Filter: Valued"), items: [this._filterText]});
    private _newButton = new QinButton({label: new QinLabel("New")});
    private _resultText = new QinText({readOnly: true});

    public constructor() {
        super();
        this.styleAsWhole();
        this._resultText.styleAsWhole();
        this._newButton.addActionMain(_ => this.actNew());
        this._nameTitled.install(this._newLine);
        this._filterTitled.install(this._newLine);
        this._newButton.install(this._newLine);
        this._newLine.install(this);
        this._resultText.install(this);
    }

    private actNew() {
        const name = this._nameChars.value;
        const filter = JSON.parse(this._filterText.value) as Valued;
        const join = this.qinpel.talk.reg.aux
                .newToGetID(name, filter);
        this._resultText.value = JSON.stringify(join, null, 2);
    }
}

class RegAuxJoin extends QinColumn {
    private _newLine = new QinLine();
    private _tableHeadText = new QinText();
    private _tableHeadTitled = new QinTitled({label: new QinLabel("Table Head"), items: [this._tableHeadText]});
    private _aliasChars = new QinChars();
    private _aliasTitled = new QinTitled({label: new QinLabel("Alias"), items: [this._aliasChars]});
    private _filterListText = new QinText();
    private _filterListTitled = new QinTitled({label: new QinLabel("Filter List"), items: [this._filterListText]});
    private _tiesCombo = new QinCombo({ofEnum: JoinTies});
    private _tiesTitled = new QinTitled({label: new QinLabel("Ties"), items: [this._tiesCombo]});
    private _newButton = new QinButton({label: new QinLabel("New")});
    private _resultText = new QinText({readOnly: true});

    public constructor() {
        super();
        this.styleAsWhole();
        this._resultText.styleAsWhole();
        this._newButton.addActionMain(_ => this.actNew());
        this._tableHeadTitled.install(this._newLine);
        this._aliasTitled.install(this._newLine);
        this._filterListTitled.install(this._newLine);
        this._tiesTitled.install(this._newLine);
        this._newButton.install(this._newLine);
        this._newLine.install(this);
        this._resultText.install(this);
    }

    private actNew() {
        const tableHead = JSON.parse(this._tableHeadText.value) as TableHead;
        const alias = this._aliasChars.value;
        const filterList = JSON.parse(this._filterListText.value) as Array<Filter>;
        const ties = this._tiesCombo.value as JoinTies;  
        const join = this.qinpel.talk.reg.aux
                .newJoin(tableHead, alias, filterList, ties);
        this._resultText.value = JSON.stringify(join, null, 2);
    }
}

class RegAuxFilter extends QinColumn {
    private _newLine = new QinLine();
    private _seemsCombo = new QinCombo({ofEnum: FilterSeems});
    private _seemsTitled = new QinTitled({label: new QinLabel("Seems"), items: [this._seemsCombo]});
    private _likesCombo = new QinCombo({ofEnum: FilterLikes});
    private _likesTitled = new QinTitled({label: new QinLabel("Likes"), items: [this._likesCombo]});
    private _valuedText = new QinText();
    private _valuedTitled = new QinTitled({label: new QinLabel("Valued"), items: [this._valuedText]});
    private _linkedText = new QinText();
    private _linkedTitled = new QinTitled({label: new QinLabel("Linked"), items: [this._linkedText]});
    private _tiesCombo = new QinCombo({ofEnum: FilterTies});
    private _tiesTitled = new QinTitled({label: new QinLabel("Ties"), items: [this._tiesCombo]});
    private _newButton = new QinButton({label: new QinLabel("New")});
    private _resultText = new QinText({readOnly: true});

    public constructor() {
        super();
        this.styleAsWhole();
        this._resultText.styleAsWhole();
        this._newButton.addActionMain(_ => this.actNew());
        this._seemsTitled.install(this._newLine);
        this._likesTitled.install(this._newLine);
        this._valuedTitled.install(this._newLine);
        this._linkedTitled.install(this._newLine);
        this._tiesTitled.install(this._newLine);
        this._newButton.install(this._newLine);
        this._newLine.install(this);
        this._resultText.install(this);
    }

    private actNew() {
        const seems = this._seemsCombo.value as FilterSeems;  
        const likes = this._likesCombo.value as FilterLikes;  
        const valued = JSON.parse(this._valuedText.value) as Valued;
        const linked = JSON.parse(this._linkedText.value) as Linked;
        const ties = this._tiesCombo.value as FilterTies;  
        const filter = this.qinpel.talk.reg.aux
                .newFilter(seems, likes, valued, linked, ties);
        this._resultText.value = JSON.stringify(filter, null, 2);
    }
}

class RegAuxLinked extends QinColumn {
    private _newLine = new QinLine();
    private _nameChars = new QinChars();
    private _nameTitled = new QinTitled({label: new QinLabel("Name"), items: [this._nameChars]});
    private _uponChars = new QinChars();
    private _uponTitled = new QinTitled({label: new QinLabel("Upon"), items: [this._uponChars]});
    private _newButton = new QinButton({label: new QinLabel("New")});
    private _resultText = new QinText({readOnly: true});

    public constructor() {
        super();
        this.styleAsWhole();
        this._resultText.styleAsWhole();
        this._newButton.addActionMain(_ => this.actNew());
        this._nameTitled.install(this._newLine);
        this._uponTitled.install(this._newLine);
        this._newButton.install(this._newLine);
        this._newLine.install(this);
        this._resultText.install(this);
    }

    private actNew() {
        const name = this._nameChars.value;
        const upon = this._uponChars.value;
        const linked = this.qinpel.talk.reg.aux
                .newLinked(name, upon);
        this._resultText.value = JSON.stringify(linked, null, 2);
    }
}

class RegAuxOrder extends QinColumn {
    private _newLine = new QinLine();
    private _nameChars = new QinChars();
    private _nameTitled = new QinTitled({label: new QinLabel("Name"), items: [this._nameChars]});
    private _descBoolean = new QinBool();
    private _descTitled = new QinTitled({label: new QinLabel("Desc"), items: [this._descBoolean]});
    private _newButton = new QinButton({label: new QinLabel("New")});
    private _resultText = new QinText({readOnly: true});

    public constructor() {
        super();
        this.styleAsWhole();
        this._resultText.styleAsWhole();
        this._newButton.addActionMain(_ => this.actNew());
        this._nameTitled.install(this._newLine);
        this._descTitled.install(this._newLine);
        this._newButton.install(this._newLine);
        this._newLine.install(this);
        this._resultText.install(this);
    }

    private actNew() {
        const name = this._nameChars.value;
        const desc = this._descBoolean.value;
        const order = this.qinpel.talk.reg.aux
                .newOrder(name, desc);
        this._resultText.value = JSON.stringify(order, null, 2);
    }
}

class RegAuxValued extends QinColumn {
    private _newLine = new QinLine();
    private _nameChars = new QinChars();
    private _nameTitled = new QinTitled({label: new QinLabel("Name"), items: [this._nameChars]});
    private _typeCombo = new QinCombo({ofEnum: Nature});
    private _typeTitled = new QinTitled({label: new QinLabel("Type"), items: [this._typeCombo]});
    private _dataChars = new QinChars();
    private _dataTitled = new QinTitled({label: new QinLabel("Data"), items: [this._dataChars]});
    private _newButton = new QinButton({label: new QinLabel("New")});
    private _resultText = new QinText({readOnly: true});

    public constructor() {
        super();
        this.styleAsWhole();
        this._resultText.styleAsWhole();
        this._newButton.addActionMain(_ => this.actNew());
        this._nameTitled.install(this._newLine);
        this._typeTitled.install(this._newLine);
        this._dataTitled.install(this._newLine);
        this._newButton.install(this._newLine);
        this._newLine.install(this);
        this._resultText.install(this);
    }

    private actNew() {
        const name = this._nameChars.value;
        const type = this._typeCombo.value as Nature;  
        const data = this._dataChars.value;
        const valued = this.qinpel.talk.reg.aux
                .newValued(name, type, data);
        this._resultText.value = JSON.stringify(valued, null, 2);
    }
}

class RegAuxTyped extends QinColumn {
    private _newLine = new QinLine();
    private _nameChars = new QinChars();
    private _nameTitled = new QinTitled({label: new QinLabel("Name"), items: [this._nameChars]});
    private _typeCombo = new QinCombo({ofEnum: Nature});
    private _typeTitled = new QinTitled({label: new QinLabel("Type"), items: [this._typeCombo]});
    private _aliasChars = new QinChars();
    private _aliasTitled = new QinTitled({label: new QinLabel("Alias"), items: [this._aliasChars]});
    private _newButton = new QinButton({label: new QinLabel("New")});
    private _resultText = new QinText({readOnly: true});

    public constructor() {
        super();
        this.styleAsWhole();
        this._resultText.styleAsWhole();
        this._newButton.addActionMain(_ => this.actNew());
        this._nameTitled.install(this._newLine);
        this._typeTitled.install(this._newLine);
        this._aliasTitled.install(this._newLine);
        this._newButton.install(this._newLine);
        this._newLine.install(this);
        this._resultText.install(this);
    }

    private actNew() {
        const name = this._nameChars.value;
        const type = this._typeCombo.value as Nature;  
        const alias = this._aliasChars.value;
        const typed = this.qinpel.talk.reg.aux
                .newTyped(name, type, alias);
        this._resultText.value = JSON.stringify(typed, null, 2);
    }
}