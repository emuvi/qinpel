import { AdExpect, AdModule, AdModules, AdRegBased, AdRegister, AdTools } from "admister";
import { Qine } from "qin_case";
import { Registry } from "qin_soul";

const base = Qine.qinpel.window.loadConfig(Qine.qinpel.ours.consts.QIN_BASE_SELECTED);

export const tableHead = AdModules.PEOPLE.tableHead;

export const registry: Registry = { base, tableHead };

export const regBased: AdRegBased = { registry };

export class AdPeople extends AdRegister {
    public constructor(module: AdModule, expect: AdExpect) {
        super(module, expect, regBased);
        this.addTab("Principal");
        this.addField(AdTools.newAdFieldChars("codigo", "Código", 8).putKey());
        this.addField(AdTools.newAdFieldAtivo());
        this.addField(AdTools.newAdFieldBoolean("potencial", "Potencial"));
        this.addField(AdTools.newAdFieldBoolean("cliente", "Cliente"));
        this.addField(AdTools.newAdFieldBoolean("fornecedor", "Fornecedor"));
        this.addField(AdTools.newAdFieldBoolean("transportadora", "Transportadora"));
        this.addField(AdTools.newAdFieldBoolean("colaborador", "Colaborador"));
        this.addField(AdTools.newAdFieldBoolean("consultor", "Consultor"));
        this.addField(AdTools.newAdFieldChars("nome", "Nome", 80));
        this.addField(AdTools.newAdFieldChars("fantasia", "Fantasia", 60));
        this.addField(AdTools.newAdFieldCombo("natureza", "Natureza", naturezaFieldItems));
        this.addField(AdTools.newAdFieldChars("cnpjcpf", "CNPJ/CPF", 20));
        this.addField(AdTools.newAdFieldChars("insestadual", "Ins. Estadual", 20));
        this.addField(AdTools.newAdFieldDate("aniversario", "Aniversário"));
        this.addTab("Contato");
        this.addField(
            AdTools.newAdFieldSuggestion("tratamento", "Tratamento", 18, tratamentoFieldItems)
        );
        this.addField(AdTools.newAdFieldChars("contato", "Contato", 45));
        this.prepare();
    }
}

const naturezaFieldItems = [
    {
        title: "",
        value: "",
    },
    {
        title: "Física",
        value: "F",
    },
    {
        title: "Jurídica",
        value: "J",
    },
];

const tratamentoFieldItems = ["Você", "Senhor", "Senhora"];
