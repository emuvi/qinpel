import { Filter, Order, Registry, Typed } from "qin_soul";
import { AdJoined } from "./ad-joined";

export type AdSelect = {
    registry: Registry;
    fieldList?: Typed[];
    joinList?: AdJoined[];
    filterList?: Filter[];
    orderList?: Order[];
    offset?: number;
    limit?: number;
};
