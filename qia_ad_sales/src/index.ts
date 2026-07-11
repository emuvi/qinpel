import { AdMenuItem, adMenuStartUp, AdModules } from "admister";
import { AdClients } from "./ad-clients";
import { AdPaymentTerms } from "./ad-payment-terms";
import { AdPrices } from "./ad-prices";
import { AdProducts } from "./ad-products";
import { AdProductsGroup } from "./ad-products-group";
import { AdProductsSubGroup } from "./ad-products-subgroup";
import { AdSales } from "./ad-sales";
import { AdSalesItems } from "./ad-sales-items";

const items: AdMenuItem[] = [
    { module: AdModules.CLIENTS, action: AdClients },
    { module: AdModules.PRODUCTS, action: AdProducts },
    { module: AdModules.PRODUCTS_GROUP, action: AdProductsGroup },
    { module: AdModules.PRODUCTS_SUBGROUP, action: AdProductsSubGroup },
    { module: AdModules.PRICES, action: AdPrices },
    { module: AdModules.PAYMENT_TERMS, action: AdPaymentTerms },
    { module: AdModules.SALES, action: AdSales },
    { module: AdModules.SALES_ITEMS, action: AdSalesItems },
];

adMenuStartUp(items).putAsBody();
