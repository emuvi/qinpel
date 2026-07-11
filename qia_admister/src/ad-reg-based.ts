import { AdJoined } from "./ad-joined";
import { Filter, Order, Registry } from "qin_soul";

export class AdRegBased {
    registry: Registry;
    joinList?: AdJoined[];
    filterList?: Filter[];
    orderList?: Order[];
}
