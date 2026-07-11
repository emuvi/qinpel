import { Registry, ToGetID, Valued } from "qin_soul";

export type AdInsert = {
    registry: Registry;
    valuedList: Valued[];
    toGetID: ToGetID;
};
