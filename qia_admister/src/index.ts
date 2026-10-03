import { Qine, QinPanel } from "qin_case";
import { AdMenu, AdMenuItem, adMenuStartUp } from "./ad-menu";
import { AdModules } from "./ad-modules";
import { AdNames } from "./ad-names";
import { AdSetup } from "./ad-tools";
import { AdBusiness } from "./people/ad-business";
import { AdCity } from "./people/ad-city";
import { AdDistrict } from "./people/ad-district";
import { AdNation } from "./people/ad-nation";
import { AdPeople } from "./people/ad-people";
import { AdPeopleGroup } from "./people/ad-people-group";
import { AdPeopleSubGroup } from "./people/ad-people-subgroup";
import { AdRegion } from "./people/ad-region";
import { AdState } from "./people/ad-state";
import { AdClients } from "./sales/ad-clients";
import { AdPaymentTerms } from "./sales/ad-payment-terms";
import { AdPrices } from "./sales/ad-prices";
import { AdProducts } from "./sales/ad-products";
import { AdProductsGroup } from "./sales/ad-products-group";
import { AdProductsSubGroup } from "./sales/ad-products-subgroup";
import { AdSales } from "./sales/ad-sales";
import { AdSalesItems } from "./sales/ad-sales-items";

const PEOPLE_GROUP = "Pessoas";
const SALES_GROUP = "Vendas";

const items: AdMenuItem[] = [
    { group: PEOPLE_GROUP, module: AdModules.BUSINESS, action: AdBusiness },
    { group: PEOPLE_GROUP, module: AdModules.REGION, action: AdRegion },
    { group: PEOPLE_GROUP, module: AdModules.NATION, action: AdNation },
    { group: PEOPLE_GROUP, module: AdModules.STATE, action: AdState },
    { group: PEOPLE_GROUP, module: AdModules.CITY, action: AdCity },
    { group: PEOPLE_GROUP, module: AdModules.DISTRICT, action: AdDistrict },
    { group: PEOPLE_GROUP, module: AdModules.PEOPLE_GROUP, action: AdPeopleGroup },
    { group: PEOPLE_GROUP, module: AdModules.PEOPLE_SUBGROUP, action: AdPeopleSubGroup },
    { group: PEOPLE_GROUP, module: AdModules.PEOPLE, action: AdPeople },
    { group: SALES_GROUP, module: AdModules.CLIENTS, action: AdClients },
    { group: SALES_GROUP, module: AdModules.PRODUCTS, action: AdProducts },
    { group: SALES_GROUP, module: AdModules.PRODUCTS_GROUP, action: AdProductsGroup },
    { group: SALES_GROUP, module: AdModules.PRODUCTS_SUBGROUP, action: AdProductsSubGroup },
    { group: SALES_GROUP, module: AdModules.PRICES, action: AdPrices },
    { group: SALES_GROUP, module: AdModules.PAYMENT_TERMS, action: AdPaymentTerms },
    { group: SALES_GROUP, module: AdModules.SALES, action: AdSales },
    { group: SALES_GROUP, module: AdModules.SALES_ITEMS, action: AdSalesItems },
];

class AdMister extends QinPanel {
    public constructor() {
        super();
        this.put(new AdMenu(items));
        const qinDesk = this.qinpel.window.newDesk(this.qinpel, {
            shouldAddApp: (manifest) => manifest.group == AdNames.AdMister,
            shouldAddCfg: (manifest) => manifest.title == this.qinpel.ours.consts.QIN_BASES,
        });
        this.castedQine().appendChild(qinDesk.getMain());
    }
}

const adSetup = Qine.qinpel.frame.getOption(AdNames.AdSetup) as AdSetup;
if (adSetup?.module) {
    adMenuStartUp(items).putAsBody();
} else {
    new AdMister().putAsBody();
}
