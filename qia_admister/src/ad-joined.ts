import { Filter, JoinTies, TableHead } from "qin_soul";
import { AdModule } from "./ad-tools";

export type AdJoined = {
    module: AdModule;
    tableHead?: TableHead;
    alias?: string;
    filterList?: Filter[];
    ties?: JoinTies;
};
