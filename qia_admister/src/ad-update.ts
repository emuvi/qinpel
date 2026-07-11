import { Filter, Registry, Valued } from "qin_soul";

export type AdUpdate = {
    registry: Registry;
    valuedList: Valued[];
    filterList?: Filter[];
    limit?: number;
};
