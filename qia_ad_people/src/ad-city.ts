import { AdExpect, AdModule, AdModules, AdRegBased, AdRegister, AdTools } from "admister";
import { Qine } from "qin_case";
import { Registry } from "qin_soul";

const base = Qine.qinpel.window.loadConfig(Qine.qinpel.ours.consts.QIN_BASE_SELECTED);

export const tableHead = AdModules.CITY.tableHead;

export const registry: Registry = { base, tableHead };

export const regBased: AdRegBased = {
    registry,
    joinList: [
        {
            module: AdModules.NATION,
            alias: "nation",
            filterList: [{ linked: { name: "pais", upon: "codigo" } }],
        },
        {
            module: AdModules.STATE,
            alias: "state",
            filterList: [
                { linked: { name: "pais", upon: "pais" } },
                { linked: { name: "estado", upon: "codigo" } },
            ],
        },
    ],
};

export class AdCity extends AdRegister {
    public constructor(module: AdModule, expect: AdExpect) {
        super(module, expect, regBased);
        this.addField(AdTools.newAdFieldChars("codigo", "Código", 6).putKey());
        this.addField(AdTools.newAdFieldAtivo());
        this.addField(AdTools.newAdFieldChars("pais", "País - Cód.", 4));
        this.addField(AdTools.newAdFieldChars("nation.nome", "País - Nome", 60));
        this.addField(AdTools.newAdFieldChars("estado", "Estado - Cód.", 4));
        this.addField(AdTools.newAdFieldChars("state.nome", "Estado - Nome", 60));
        this.addField(AdTools.newAdFieldChars("nome", "Nome", 60));
        this.prepare();
    }
}
