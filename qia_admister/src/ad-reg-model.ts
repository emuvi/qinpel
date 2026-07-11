import { Registry, Typed, ToGetID, Valued, FilterSeems, FilterLikes, FilterTies, Filter } from "qin_soul";
import { AdDelete } from "./ad-delete";
import { AdField } from "./ad-field";
import { AdInsert } from "./ad-insert";
import { AdRegCalls } from "./ad-reg-calls";
import { AdRegister } from "./ad-register";
import { AdUpdate } from "./ad-update";

export class AdRegModel {
    private _reg: AdRegister;
    private _fieldList: AdField[] = [];
    private _typedList: Typed[] = null;

    public constructor(register: AdRegister) {
        this._reg = register;
    }

    public get fieldList(): AdField[] {
        return this._fieldList;
    }

    public get typedList(): Typed[] {
        if (this._typedList == null) {
            this._typedList = [];
            for (let field of this._fieldList) {
                this._typedList.push(field.typed);
            }
        }
        return this._typedList;
    }

    public addField(field: AdField) {
        this._fieldList.push(field);
    }

    public getFieldByName(name: string): AdField {
        for (let field of this._fieldList) {
            if (field.name === name) {
                return field;
            }
        }
        return null;
    }

    public getFieldIndexByName(name: string): number {
        for (let i = 0; i < this._fieldList.length; i++) {
            if (this._fieldList[i].name === name) {
                return i;
            }
        }
        return -1;
    }

    public setValue(index: number, value: any) {
        this._fieldList[index].value = value;
    }

    public getValue(index: number): any {
        return this._fieldList[index].value;
    }

    public setValues(values: any[]) {
        for (let i = 0; i < values.length; i++) {
            this.setValue(i, values[i]);
        }
    }

    public getValues(): any[] {
        let result = [];
        for (const field of this._fieldList) {
            result.push(field.value);
        }
        return result;
    }

    public clean() {
        for (let field of this._fieldList) {
            field.clean();
        }
    }

    public turnReadOnly() {
        for (let field of this._fieldList) {
            field.turnReadOnly();
        }
    }

    public turnEditable() {
        for (let field of this._fieldList) {
            if (!field.readOnly) {
                field.turnEditable();
            } else {
                field.turnReadOnly();
            }
        }
    }

    public hasMutations(): string[] {
        let result: Array<string> = null;
        for (let field of this._fieldList) {
            if (field.hasMutations()) {
                if (result == null) {
                    result = [];
                }
                result.push(field.title);
            }
        }
        return result;
    }

    public undoMutations() {
        for (let field of this._fieldList) {
            field.undoMutations();
        }
    }

    public async insert(): Promise<AdRegKeys> {
        return new Promise<AdRegKeys>((resolve, reject) => {
            let valuedList = new Array<Valued>();
            let regKeyList = new Array<Valued>();
            let toGetID: ToGetID = {
                name: null,
                filter: null
            };
            for (let field of this._fieldList) {
                let valued = field.valued;
                if (valued.name.indexOf(".") === -1) {
                    if (valued.data || field.key) {
                        valuedList.push(valued);
                    }
                    if (field.key) {
                        regKeyList.push(valued);
                        if (!valued.data) {
                            toGetID.name = field.name;
                        } else {
                            toGetID.filter = valued;
                        }
                    }
                }
            }
            let query: AdInsert = {
                registry: this._reg.registry,
                valuedList,
                toGetID,
            };
            AdRegCalls.insert(query)
                .then((id) => {
                    if (toGetID && toGetID.name) {
                        for (let valued of regKeyList) {
                            if (valued.name === toGetID.name) {
                                valued.data = id;
                                break;
                            }
                        }
                    }
                    resolve(regKeyList);
                })
                .catch((err) => reject(err));
        });
    }

    public async update(): Promise<AdRegKeys> {
        return new Promise<AdRegKeys>((resolve, reject) => {
            let regKeyList = new Array<Valued>();
            for (let field of this._fieldList) {
                if (field.key) {
                    regKeyList.push(field.valued);
                }
            }
            let query: AdUpdate = {
                registry: this._reg.registry,
                valuedList: this.getMutationValuedList(),
                filterList: this.getKeyFieldsFilter(),
            };
            AdRegCalls.update(query)
                .then((_) => {
                    for (let field of this._fieldList) {
                        field.saved();
                    }
                    resolve(regKeyList);
                })
                .catch((err) => reject(err));
        });
    }

    public async delete(): Promise<void> {
        return new Promise<void>((resolve, reject) => {
            let detailsPromise = new Array<Promise<number>>();
            for (const detail of this._reg.details) {
                let registry: Registry = {
                    base: this._reg.registry.base,
                    tableHead: detail.setup.module.tableHead,
                };
                let deleteDetail: AdDelete = {
                    registry: registry,
                    filterList: [],
                };
                if (detail.setup.filterList) {
                    for (const filter of detail.setup.filterList) {
                        if (filter.linked) {
                            let indexField = this._reg.model.getFieldIndexByName(filter.linked.upon);
                            let fixedValue = this._reg.selectedValues[indexField];
                            deleteDetail.filterList.push({
                                seems: FilterSeems.Is,
                                likes: FilterLikes.Equals,
                                valued: {
                                    name: filter.linked.name,
                                    type: this._reg.model.fieldList[indexField].typed.type,
                                    data: fixedValue,
                                },
                                ties: FilterTies.And,
                            });
                        } else {
                            deleteDetail.filterList.push(filter);
                        }
                    }
                }
                if (deleteDetail.filterList.length > 0) {
                    detailsPromise.push(AdRegCalls.delete(deleteDetail));
                }
            }
            let deleteQuery: AdDelete = {
                registry: this._reg.registry,
                filterList: this.getKeyFieldsFilter(),
            };
            Promise.all(detailsPromise)
                .then((_) => {
                    AdRegCalls.delete(deleteQuery)
                        .then((_) => {
                            this.clean();
                            resolve();
                        })
                        .catch((err) => reject(err));
                })
                .catch((err) => reject(err));
        });
    }

    private getMutationValuedList(): Valued[] {
        let result = [];
        for (let field of this._fieldList) {
            if (field.hasMutations() && !field.key) {
                result.push(field.valued);
            }
        }
        return result;
    }

    private getKeyFieldsFilter(): Filter[] {
        let result: Filter[] = [];
        for (let field of this._fieldList) {
            if (field.key) {
                let filter = {
                    seems: FilterSeems.Is,
                    likes: FilterLikes.Equals,
                    valued: field.valued,
                    ties: FilterTies.And,
                };
                result.push(filter);
            }
        }
        return result;
    }
}

export type AdRegKeys = Valued[];
