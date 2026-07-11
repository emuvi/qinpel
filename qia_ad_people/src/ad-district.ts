import { AdExpect, AdModule, AdModules, AdRegBased, AdRegister, AdTools } from "admister";
import { Qine } from "qin_case";
import { Registry } from "qin_soul";

const base = Qine.qinpel.window.loadConfig(Qine.qinpel.ours.consts.QIN_BASE_SELECTED);

export const tableHead = AdModules.DISTRICT.tableHead;

export const registry: Registry = { base, tableHead };

const regBased: AdRegBased = {
    registry,
    joinList: [
        {
            module: AdModules.CITY,
            alias: "city",
            filterList: [{ linked: { name: "cidade", upon: "codigo" } }],
        },
    ],
};

export class AdDistrict extends AdRegister {
    public constructor(module: AdModule, expect: AdExpect) {
        super(module, expect, regBased);
        this.addField(AdTools.newAdFieldChars("cidade", "Cidade - Cód.", 6).putKey());
        this.addField(AdTools.newAdFieldChars("city.nome", "Cidade - Nome", 60));
        this.addField(AdTools.newAdFieldChars("codigo", "Código", 4).putKey());
        this.addField(AdTools.newAdFieldAtivo());
        this.addField(AdTools.newAdFieldChars("nome", "Nome", 60));
        this.prepare();
    }
}
