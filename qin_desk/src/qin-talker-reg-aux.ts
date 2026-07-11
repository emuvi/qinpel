import { AllowReg, Delete, Filter, FilterLikes, FilterSeems, FilterTies, Insert, Join, JoinTies, Linked, Nature, Order, Registry, Select, Strain, TableHead, ToDelete, ToGetID, ToInsert, ToSelect, ToUpdate, Typed, Update, Valued } from "qin_soul";

export class QinTalkerRegAux {

    public newTableHead(catalog: string, schema: string, name: string, alias: string): TableHead {
        return {catalog, schema, name, alias};
    }

    public newRegistry(base: string, tableHead: TableHead): Registry {
        return {base, tableHead};
    }

    public newAllowReg(registry: Registry, all: boolean, insert: boolean, select: boolean, update: boolean, delety: boolean, strain: Strain): AllowReg {
        return {registry, all, insert, select, update, delete: delety, strain};
    }
    
    public newStrain(restrict: string, modify: string, include: string): Strain {
        return {restrict, modify, include};
    }

    public newToInsert(base: string, insert: Insert): ToInsert {
        return {base, insert};
    }
    
    public newInsert(tableHead: TableHead, valuedList: Array<Valued>, toGetID: ToGetID): Insert {
        return {tableHead, valuedList, toGetID};
    }
    
    public newToSelect(base: string, select: Select): ToSelect {
        return {base, select};
    }
    
    public newSelect(tableHead: TableHead, fieldList: Array<Typed>, joinList: Array<Join>, filterList: Array<Filter>, orderList: Array<Order>, offset: number, limit: number): Select {
        return {tableHead, fieldList, joinList, filterList, orderList, offset, limit};
    }

    public newToUpdate(base: string, update: Update): ToUpdate {
        return {base, update};
    }
    
    public newUpdate(tableHead: TableHead, valuedList: Array<Valued>, filterList: Array<Filter>, limit: number): Update {
        return {tableHead, valuedList, filterList, limit};
    }

    public newToDelete(base: string, delety: Delete): ToDelete {
        return {base, delete: delety};
    }

    public newDelete(tableHead: TableHead, filterList: Array<Filter>): Delete {
        return {tableHead, filterList};
    }
    
    public newToGetID(name: string, filter: Valued): ToGetID {
        return {name, filter};
    }

    public newJoin(tableHead: TableHead, alias: string, filterList: Array<Filter>, ties: JoinTies): Join {
        return {tableHead, alias, filterList, ties};
    }

    public newFilter(seems: FilterSeems, likes: FilterLikes, valued: Valued, linked: Linked, ties: FilterTies): Filter {
        return {seems, likes, valued, linked, ties};
    }

    public newLinked(name: string, upon: string): Linked {
        return {name, upon};
    }

    public newOrder(name: string, desc: boolean): Order {
        return {name, desc};
    }

    public newValued(name: string, type: Nature, data: any): Valued {
        return {name, type, data};
    }

    public newTyped(name: string, type: Nature, alias: string): Typed {
        return {name, type, alias};
    }

}