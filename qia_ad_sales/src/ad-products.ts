import { AdExpect, AdModule, AdModules, AdRegBased, AdRegister, AdScope, AdTools } from "admister";
import { Qine } from "qin_case";
import { Registry } from "qin_soul";

const base = Qine.qinpel.window.loadConfig(Qine.qinpel.ours.consts.QIN_BASE_SELECTED);

export const tableHead = AdModules.PRODUCTS.tableHead;

export const registry: Registry = { base, tableHead };

export const regBased: AdRegBased = {
    registry,
    joinList: [
        {
            module: AdModules.PRODUCTS_GROUP,
            alias: "products_group",
            filterList: [{ linked: { name: "grupo", upon: "codigo" } }],
        },
        {
            module: AdModules.PRODUCTS_SUBGROUP,
            alias: "products_subgroup",
            filterList: [
                { linked: { name: "grupo", upon: "grupo" } },
                { linked: { name: "subgrupo", upon: "codigo" } },
            ],
        },
    ],
};

export class AdProducts extends AdRegister {
    public constructor(module: AdModule, expect: AdExpect) {
        super(module, expect, regBased);
        this.addField(AdTools.newAdFieldChars("codigo", "Código", 6).putKey());
        this.addField(AdTools.newAdFieldAtivo());
        this.addField(AdTools.newAdFieldChars("nome", "Nome", 60));
        this.addField(AdTools.newAdFieldChars("grupo", "Grupo - Cód.", 4).putKey());
        this.addField(AdTools.newAdFieldChars("products_group.nome", "Grupo - Nome", 60));
        this.addField(AdTools.newAdFieldChars("subgrupo", "SubGrupo - Cód.", 4).putKey());
        this.addField(AdTools.newAdFieldChars("products_subgroup.nome", "SubGrupo - Nome", 60));
        this.addField(AdTools.newAdFieldNumeric("ordem", "Ordem"));
        this.addDetail({
            setup: {
                module: AdModules.PRICES,
                scopeList: [AdScope.ALL],
                filterList: [{ linked: { name: "produto", upon: "codigo" } }],
            },
        });
        this.prepare();
    }
}
