import { AdExpect, AdModule, AdModules, AdRegBased, AdRegister, AdTools } from "admister";
import { Qine } from "qin_case";
import { Registry } from "qin_soul";

const base = Qine.qinpel.window.loadConfig(Qine.qinpel.ours.consts.QIN_BASE_SELECTED);

export const tableHead = AdModules.PRICES.tableHead;

export const registry: Registry = { base, tableHead };

export const regBased: AdRegBased = {
    registry,
    joinList: [
        {
            module: AdModules.PRODUCTS,
            alias: "products",
            filterList: [{ linked: { name: "produto", upon: "codigo" } }],
        },
    ],
};

export class AdPrices extends AdRegister {
    public constructor(module: AdModule, expect: AdExpect) {
        super(module, expect, regBased);
        this.addField(AdTools.newAdFieldChars("produto", "Produto - Cód.", 6).putKey());
        this.addField(AdTools.newAdFieldChars("products.nome", "Produto - Nome.", 60));
        this.addField(AdTools.newAdFieldChars("tabela", "Tabela", 6).putKey());
        this.addField(AdTools.newAdFieldNumeric("valor", "Valor"));
        this.prepare();
    }
}
