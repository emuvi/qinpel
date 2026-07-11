import { AdExpect, AdModule, AdModules, AdRegBased, AdRegister, AdTools } from "admister";
import { Qine } from "qin_case";
import { Registry } from "qin_soul";

const base = Qine.qinpel.window.loadConfig(Qine.qinpel.ours.consts.QIN_BASE_SELECTED);

export const tableHead = AdModules.PEOPLE_SUBGROUP.tableHead;

export const registry: Registry = { base, tableHead };

export const regBased: AdRegBased = {
    registry,
    joinList: [
        {
            module: AdModules.PEOPLE_GROUP,
            alias: "people_group",
            filterList: [{ linked: { name: "grupo", upon: "codigo" } }],
        },
    ],
};

export class AdPeopleSubGroup extends AdRegister {
    public constructor(module: AdModule, expect: AdExpect) {
        super(module, expect, regBased);
        this.addField(AdTools.newAdFieldChars("grupo", "Grupo - Cód.", 4).putKey());
        this.addField(AdTools.newAdFieldChars("people_group.nome", "Grupo - Nome", 60));
        this.addField(AdTools.newAdFieldChars("codigo", "Código", 4).putKey());
        this.addField(AdTools.newAdFieldAtivo());
        this.addField(AdTools.newAdFieldChars("nome", "Nome", 60));
        this.prepare();
    }
}
