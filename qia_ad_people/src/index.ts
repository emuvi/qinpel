import { AdMenuItem, adMenuStartUp, AdModules } from "admister";
import { AdBusiness } from "./ad-business";
import { AdCity } from "./ad-city";
import { AdDistrict } from "./ad-district";
import { AdNation } from "./ad-nation";
import { AdPeople } from "./ad-people";
import { AdPeopleGroup } from "./ad-people-group";
import { AdPeopleSubGroup } from "./ad-people-subgroup";
import { AdRegion } from "./ad-region";
import { AdState } from "./ad-state";

const items: AdMenuItem[] = [
    { module: AdModules.BUSINESS, action: AdBusiness },
    { module: AdModules.REGION, action: AdRegion },
    { module: AdModules.NATION, action: AdNation },
    { module: AdModules.STATE, action: AdState },
    { module: AdModules.CITY, action: AdCity },
    { module: AdModules.DISTRICT, action: AdDistrict },
    { module: AdModules.PEOPLE_GROUP, action: AdPeopleGroup },
    { module: AdModules.PEOPLE_SUBGROUP, action: AdPeopleSubGroup },
    { module: AdModules.PEOPLE, action: AdPeople },
];

adMenuStartUp(items).putAsBody();
