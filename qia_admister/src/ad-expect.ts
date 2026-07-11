import { Filter, QinWaiters, Valued } from "qin_soul";
import { AdScope } from "./ad-tools";

export class AdExpect {
    private _scopeList: AdScope[];
    private _filterList: Filter[];
    private _fixedList: Valued[];
    private _waiters: QinWaiters<any>;

    public constructor(options: AdExpectSet) {
        this._scopeList = options.scopeList;
        this._filterList = options.filterList;
        this._fixedList = options.fixedList;
        this._waiters = options.waiters;
    }

    public get scopeList(): AdScope[] {
        return this._scopeList;
    }

    public get filterList(): Filter[] {
        return this._filterList;
    }

    public get fixedList(): Valued[] {
        return this._fixedList;
    }

    public get waiters(): QinWaiters<any> {
        return this._waiters;
    }

    private replaceAllScopeWithEachOne() {
        if (this._scopeList.findIndex((s) => s == AdScope.ALL) > -1) {
            let hasRelate = this._scopeList.findIndex((s) => s == AdScope.RELATE) > -1;
            this._scopeList = [
                AdScope.INSERT,
                AdScope.SEARCH,
                AdScope.NOTICE,
                AdScope.MUTATE,
                AdScope.DELETE,
            ];
            if (hasRelate) {
                this._scopeList.push(AdScope.RELATE);
            }
        }
    }

    public restrictInsert() {
        this.replaceAllScopeWithEachOne();
        this._scopeList = this._scopeList.filter((s) => s != AdScope.INSERT);
    }

    public restrictSelect() {
        this.replaceAllScopeWithEachOne();
        this._scopeList = this._scopeList.filter((s) => s != AdScope.SEARCH && s != AdScope.NOTICE);
    }

    public restrictUpdate() {
        this.replaceAllScopeWithEachOne();
        this._scopeList = this._scopeList.filter((s) => s != AdScope.MUTATE);
    }

    public restrictDelete() {
        this.replaceAllScopeWithEachOne();
        this._scopeList = this._scopeList.filter((s) => s != AdScope.DELETE);
    }

    public hasScope(scope: AdScope): boolean {
        return this._scopeList.findIndex((s) => s == AdScope.ALL || s == scope) > -1;
    }
}

export type AdExpectSet = {
    scopeList: AdScope[];
    filterList?: Filter[];
    fixedList?: Valued[];
    waiters?: QinWaiters<any>;
};
