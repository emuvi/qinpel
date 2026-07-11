import { AdExpect, AdModule, AdModules, AdRegBased, AdRegister, AdTools } from "admister";
import { Qine } from "qin_case";
import { Registry } from "qin_soul";

const base = Qine.qinpel.window.loadConfig(Qine.qinpel.ours.consts.QIN_BASE_SELECTED);

export const tableHead = AdModules.CLIENTS.tableHead;

export const registry: Registry = { base, tableHead };

export const regBased: AdRegBased = {
    registry,
    joinList: [
        {
            module: AdModules.PEOPLE_GROUP,
            alias: "people_group",
            filterList: [{ linked: { name: "grupo", upon: "codigo" } }],
        },
        {
            module: AdModules.PEOPLE_SUBGROUP,
            alias: "people_subgroup",
            filterList: [
                { linked: { name: "grupo", upon: "grupo" } },
                { linked: { name: "subgrupo", upon: "codigo" } },
            ],
        },
        {
            module: AdModules.CITY,
            alias: "city",
            filterList: [{ linked: { name: "cidade", upon: "codigo" } }],
        },
        {
            module: AdModules.DISTRICT,
            alias: "district",
            filterList: [
                { linked: { name: "cidade", upon: "cidade" } },
                { linked: { name: "bairro", upon: "codigo" } },
            ],
        },
        {
            module: AdModules.REGION,
            alias: "region",
            filterList: [{ linked: { name: "regiao", upon: "codigo" } }],
        },
        {
            module: AdModules.PAYMENT_TERMS,
            alias: "payment_terms",
            filterList: [{ linked: { name: "cond_pagamento", upon: "codigo" } }],
        },
    ],
};

export class AdClients extends AdRegister {
    public constructor(module: AdModule, expect: AdExpect) {
        super(module, expect, regBased);
        this.addTab("Principal");
        this.addField(AdTools.newAdFieldChars("codigo", "Código", 8).putKey());
        this.addField(AdTools.newAdFieldAtivo());
        this.addField(AdTools.newAdFieldBoolean("potencial", "Potencial"));
        this.addField(AdTools.newAdFieldBoolean("cliente", "Cliente"));
        this.addField(AdTools.newAdFieldChars("nome", "Nome", 80));
        this.addField(AdTools.newAdFieldChars("fantasia", "Fantasia", 60));
        this.addField(AdTools.newAdFieldCombo("natureza", "Natureza", naturezaFieldItems));
        this.addField(AdTools.newAdFieldChars("cnpjcpf", "CNPJ/CPF", 20));
        this.addField(AdTools.newAdFieldChars("insestadual", "Ins. Estadual", 20));
        this.addField(AdTools.newAdFieldDate("aniversario", "Aniversário"));
        this.addField(AdTools.newAdFieldChars("grupo", "Grupo - Cód.", 2));
        this.addField(AdTools.newAdFieldChars("people_group.nome", "Grupo - Nome", 60));
        this.addField(AdTools.newAdFieldChars("subgrupo", "SubGrupo - Cód.", 3));
        this.addField(AdTools.newAdFieldChars("people_subgroup.nome", "SubGrupo - Nome", 60));
        this.addField(AdTools.newAdFieldChars("obs", "Obs", 120));
        this.addTab("Contato");
        this.addField(AdTools.newAdFieldSuggestion("tratamento", "Tratamento", 18, tratamentoSuggestions));
        this.addField(AdTools.newAdFieldChars("contato", "Contato", 45));
        this.addField(AdTools.newAdFieldChars("cargo", "Cargo", 40));
        this.addField(AdTools.newAdFieldDate("contato_aniversario", "Cont. Aniversário"));
        this.addField(AdTools.newAdFieldSuggestion("tipo_fone1", "Tipo Tel 1", 18, typeContactSuggestions));
        this.addField(AdTools.newAdFieldChars("fone1", "Telefone 1", 25));
        this.addField(AdTools.newAdFieldSuggestion("tipo_fone2", "Tipo Tel 2", 18, typeContactSuggestions));
        this.addField(AdTools.newAdFieldChars("fone2", "Telefone 2", 25));
        this.addField(AdTools.newAdFieldSuggestion("tipo_fone3", "Tipo Tel 3", 18, typeContactSuggestions));
        this.addField(AdTools.newAdFieldChars("fone3", "Telefone 3", 25));
        this.addField(AdTools.newAdFieldSuggestion("tipo_email1", "Tipo EMail 1", 18, typeContactSuggestions));
        this.addField(AdTools.newAdFieldChars("email1", "EMail 1", 25));
        this.addField(AdTools.newAdFieldSuggestion("tipo_email2", "Tipo EMail 2", 18, typeContactSuggestions));
        this.addField(AdTools.newAdFieldChars("email2", "EMail 2", 25));
        this.addField(AdTools.newAdFieldSuggestion("tipo_email3", "Tipo EMail 3", 18, typeContactSuggestions));
        this.addField(AdTools.newAdFieldChars("email3", "EMail 3", 25));
        this.addField(AdTools.newAdFieldSuggestion("tipo_website1", "Tipo WebSite 1", 18, typeContactSuggestions));
        this.addField(AdTools.newAdFieldChars("website1", "WebSite 1", 25));
        this.addField(AdTools.newAdFieldSuggestion("tipo_website2", "Tipo WebSite 2", 18, typeContactSuggestions));
        this.addField(AdTools.newAdFieldChars("website2", "WebSite 2", 25));
        this.addField(AdTools.newAdFieldSuggestion("tipo_website3", "Tipo WebSite 3", 18, typeContactSuggestions));
        this.addField(AdTools.newAdFieldChars("website3", "WebSite 3", 25));
        this.addTab("Endereço");
        this.addField(AdTools.newAdFieldSuggestion("tipo_endereco", "Tipo Endereço", 18, typeContactSuggestions));
        this.addField(AdTools.newAdFieldChars("cep", "CEP", 10));
        this.addField(AdTools.newAdFieldChars("cidade", "Cidade - Cód.", 6));
        this.addField(AdTools.newAdFieldChars("city.nome", "Cidade - Nome", 60));
        this.addField(AdTools.newAdFieldChars("city.pais", "Cidade - Pais", 4));
        this.addField(AdTools.newAdFieldChars("city.estado", "Cidade - Estado", 4));
        this.addField(AdTools.newAdFieldChars("bairro", "Bairro - Cód.", 4));
        this.addField(AdTools.newAdFieldChars("district.nome", "Bairro - Nome", 60));
        this.addField(AdTools.newAdFieldChars("regiao", "Região - Cód.", 4));
        this.addField(AdTools.newAdFieldChars("region.nome", "Região - Nome", 60));
        this.addField(AdTools.newAdFieldSuggestion("logradouro", "Logradouro", 10, typeStreetSuggestions));
        this.addField(AdTools.newAdFieldChars("endereco", "Endereço", 80));
        this.addField(AdTools.newAdFieldChars("numero", "Número", 10));
        this.addField(AdTools.newAdFieldChars("complemento", "Complemento", 50));
        this.addTab("Vendas");
        this.addField(AdTools.newAdFieldChars("tabela_preco", "Tab. Preço", 6));
        this.addField(AdTools.newAdFieldChars("tabela_secundaria", "Tab. Secundária", 6));
        this.addField(AdTools.newAdFieldChars("cond_pagamento", "Cond. Pgto - Cód.", 4));
        this.addField(AdTools.newAdFieldChars("payment_terms.nome", "Cond. Pgto - Nome", 45));
        this.addField(AdTools.newAdFieldChars("credito", "Crédito", 6));
        this.addField(AdTools.newAdFieldChars("credito_obs", "Crédito - Obs", 250));
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

const tratamentoSuggestions = ["Você", "Senhor", "Senhora"];
const typeContactSuggestions = ["Pessoal", "Profissional"];
const typeStreetSuggestions = ["R.", "AV.", "AL.", "LOT.", "TV.", "SER.", "ROD.", "PCA."];
