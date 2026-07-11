import { Filter, FilterLikes, FilterSeems, FilterTies, JoinTies, Valued } from "qin_soul";
import { AdApprise } from "./ad-apprise";
import { AdRegCalls } from "./ad-reg-calls";
import { AdRegister } from "./ad-register";
import { AdSelect } from "./ad-select";

export class AdRegLoader {
    private _reg: AdRegister;

    public constructor(register: AdRegister) {
        this._reg = register;
    }

    public refresh(): Promise<void> {
        return new Promise<void>((resolve, reject) => {
            const query = this.mountSelect(false);
            if (!query.filterList) {
                query.filterList = [];
            }
            for (const field of this._reg.model.fieldList) {
                if (field.key) {
                    let filter: Filter = {
                        seems: FilterSeems.Is,
                        likes: FilterLikes.Equals,
                        valued: field.valued,
                        ties: FilterTies.And
                    };
                    query.filterList.push(filter);
                }
            }
            AdRegCalls.select(query)
                .then((rows) => {
                    if (rows.length == 0) {
                        this._reg.displayInfo(AdApprise.NO_RESULTS_FOUND, "{qia_admister}(ErrCode-000018)");
                    } else {
                        this._reg.refreshSelected(rows[0]);
                    }
                    resolve();
                })
                .catch((err) => reject(err));
        });
    }

    public load(): Promise<void> {
        return new Promise<void>((resolve, reject) => {
            const query = this.mountSelect();
            AdRegCalls.select(query)
                .then((rows) => {
                    this._reg
                        .unselectAll()
                        .then(() => {
                            this._reg.table.delLines();
                            if (rows.length == 0) {
                                this._reg.displayInfo(AdApprise.NO_RESULTS_FOUND, "{qia_admister}(ErrCode-000008)");
                            } else {
                                for (let row of rows) {
                                    this._reg.table.addLine(row);
                                }
                            }
                            resolve();
                        })
                        .catch((err) => reject(err));
                })
                .catch((err) => reject(err));
        });
    }

    public mountSelect(addSearchFilters: boolean = true, plusFilters: Valued[] = null): AdSelect {
        let registry = this._reg.registry;
        let fieldList = this._reg.model.typedList;
        let joinList = this._reg.based.joinList;
        if (joinList) {
            for (let join of joinList) {
                if (!join.tableHead) {
                    join.tableHead = join.module.tableHead;
                }
                if (!join.ties) {
                    join.ties = JoinTies.Left;
                }
            }
        }
        let filterList: Filter[] = [];
        if (this._reg.based.filterList) {
            filterList.push(...this._reg.based.filterList);
        }
        if (this._reg.expect.filterList) {
            filterList.push(...this._reg.expect.filterList);
        }
        if (addSearchFilters) {
            let searchFilters = this._reg.search.getFilters();
            if (searchFilters) {
                filterList.push(...searchFilters);
            }
        }
        if (plusFilters) {
            for (const valued of plusFilters) {
                let filter: Filter = {
                    seems: FilterSeems.Is,
                    likes: FilterLikes.Equals,
                    valued: valued,
                    ties: FilterTies.And,
                };
                filterList.push(filter);
            }
        }
        let orderList = this._reg.based.orderList;
        return { registry, fieldList,  joinList, filterList, orderList, limit: 300 };
    }
}
