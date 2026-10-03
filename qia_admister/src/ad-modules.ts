import { QinAsset } from "qin_case";
import { AdModule } from "./ad-tools";

export class AdModules {

    static BUSINESS: AdModule = {
        appName: "admister",
        title: "Negócios",
        icon: QinAsset.FaceBusiness,
        tableHead: { name: "negocios" },
    };

    static REGION: AdModule = {
        appName: "admister",
        title: "Região",
        icon: QinAsset.FaceRegion,
        tableHead: { name: "regioes" },
    };

    static NATION: AdModule = {
        appName: "admister",
        title: "Países",
        icon: QinAsset.FaceGlobe,
        tableHead: { name: "paises" },
    };

    static STATE: AdModule = {
        appName: "admister",
        title: "Estados",
        icon: QinAsset.FaceState,
        tableHead: { name: "estados" },
    };

    static CITY: AdModule = {
        appName: "admister",
        title: "Cidades",
        icon: QinAsset.FaceCity,
        tableHead: { name: "cidades" },
    };

    static DISTRICT: AdModule = {
        appName: "admister",
        title: "Bairros",
        icon: QinAsset.FaceDistrict,
        tableHead: { name: "bairros" },
    };

    static PEOPLE: AdModule = {
        appName: "admister",
        title: "Pessoas",
        icon: QinAsset.FacePeople,
        tableHead: { name: "pessoas" },
    };

    static PEOPLE_GROUP: AdModule = {
        appName: "admister",
        title: "Grupos de Pessoas",
        icon: QinAsset.FacePeopleGroup,
        tableHead: { name: "grupos_pessoas" },
    };

    static PEOPLE_SUBGROUP: AdModule = {
        appName: "admister",
        title: "SubGrupos de Pessoas",
        icon: QinAsset.FacePeopleSubgroup,
        tableHead: { name: "subgrupos_pessoas" },
    };

    static CLIENTS: AdModule = {
        appName: "admister",
        title: "Clientes",
        icon: QinAsset.FaceCostumer,
        tableHead: { name: "pessoas" },
    };

    static PRODUCTS: AdModule = {
        appName: "admister",
        title: "Produtos",
        icon: QinAsset.FaceProduct,
        tableHead: { name: "produtos" },
    };

    static PRODUCTS_GROUP: AdModule = {
        appName: "admister",
        title: "Grupos de Produtos",
        icon: QinAsset.FaceProductGroup,
        tableHead: { name: "grupos_produtos" },
    };

    static PRODUCTS_SUBGROUP: AdModule = {
        appName: "admister",
        title: "SubGrupos de Produtos",
        icon: QinAsset.FaceProductSubgroup,
        tableHead: { name: "subgrupos_produtos" },
    };

    static PRICES: AdModule = {
        appName: "admister",
        title: "Preços",
        icon: QinAsset.FacePrices,
        tableHead: { name: "precos" },
    };

    static PAYMENT_TERMS: AdModule = {
        appName: "admister",
        title: "Condições de Pagamento",
        icon: QinAsset.FaceCheckbook,
        tableHead: { name: "condicoes_pagamento" },
    };

    static SALES: AdModule = {
        appName: "admister",
        title: "Vendas",
        icon: QinAsset.FaceSales,
        tableHead: { name: "prepedidos" },
    };

    static SALES_ITEMS: AdModule = {
        appName: "admister",
        title: "Vendas Itens",
        icon: QinAsset.FaceSalesItems,
        tableHead: { name: "itens_prepedidos" },
    };
    
}
