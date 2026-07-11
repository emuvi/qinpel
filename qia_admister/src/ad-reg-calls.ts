import { Qine } from "qin_case";
import { AdDelete } from "./ad-delete";
import { AdInsert } from "./ad-insert";
import { AdSelect } from "./ad-select";
import { AdUpdate } from "./ad-update";
import { Join, JoinTies, QinBody, ToDelete, ToInsert, ToSelect, ToUpdate } from "qin_soul";

export class AdRegCalls {
    public static selectOne(query: AdSelect): Promise<string> {
        return new Promise<string>((resolve, reject) => {
            AdRegCalls.select(query)
                .then((res) => {
                    if (res && res.length > 0 && res[0] && res[0].length > 0) {
                        resolve(res[0][0]);
                    } else {
                        resolve(null);
                    }
                })
                .catch((err) => reject(err));
        });
    }

    public static selectRow(query: AdSelect): Promise<string[]> {
        return new Promise<string[]>((resolve, reject) => {
            AdRegCalls.select(query)
                .then((res) => {
                    if (res && res.length > 0) {
                        resolve(res[0]);
                    } else {
                        resolve(null);
                    }
                })
                .catch((err) => reject(err));
        });
    }

    public static select(query: AdSelect): Promise<string[][]> {
        return new Promise<string[][]>((resolve, reject) => {
            let toSelectJoinList: Join[] = null;
            if (query.joinList) {
                toSelectJoinList = [];
                for (let join of query.joinList) {
                    if (!join.tableHead) {
                        join.tableHead = join.module.tableHead;
                    }
                    if (!join.ties) {
                        join.ties = JoinTies.Left;
                    }
                    toSelectJoinList.push({
                        tableHead: join.tableHead,
                        alias: join.alias,
                        filterList: join.filterList,
                        ties: join.ties
                    });
                }
            }
            let toSelect: ToSelect = {
                base: query.registry.base,
                select: {
                    tableHead: query.registry.tableHead,
                    fieldList: query.fieldList,
                    joinList: toSelectJoinList,
                    filterList: query.filterList,
                    orderList: query.orderList,
                    offset: query.offset,
                    limit: query.limit
                }
            }
            Qine.qinpel.talk.reg
                .ask(toSelect)
                .then((data) => resolve(QinBody.getCSVRows(data)))
                .catch((err) => reject(err));
        });
    }

    public static insert(query: AdInsert): Promise<any> {
        return new Promise<any>((resolve, reject) => {
            let toInsert: ToInsert = {
                base: query.registry.base,
                insert: {
                    tableHead: query.registry.tableHead,
                    valuedList: query.valuedList,
                    toGetID: query.toGetID
                }
            }
            Qine.qinpel.talk.reg
                .new(toInsert)
                .then((res) => resolve(res))
                .catch((err) => reject(err));
        });
    }

    public static update(query: AdUpdate): Promise<number> {
        return new Promise<number>((resolve, reject) => {
            let toUpdate: ToUpdate = {
                base: query.registry.base,
                update: {
                    tableHead: query.registry.tableHead,
                    valuedList: query.valuedList,
                    filterList: query.filterList,
                    limit: query.limit
                }
            }
            Qine.qinpel.talk.reg
                .set(toUpdate)
                .then((res) => resolve(parseInt(res)))
                .catch((err) => reject(err));
        });
    }

    public static delete(query: AdDelete): Promise<number> {
        return new Promise<number>((resolve, reject) => {
            let toDelete: ToDelete = {
                base: query.registry.base,
                delete: {
                    tableHead: query.registry.tableHead,
                    filterList: query.filterList
                }
            }
            Qine.qinpel.talk.reg
                .del(toDelete)
                .then((res) => resolve(parseInt(res)))
                .catch((err) => reject(err));
        });
    }
}
