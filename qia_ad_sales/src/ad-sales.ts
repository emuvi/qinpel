import { AdExpect, AdModule, AdModules, AdRegBased, AdRegister, AdScope, AdTools } from "admister";
import { QinButton, QinLabel, Qine } from "qin_case";
import { QinAction, Registry } from "qin_soul";

const base = Qine.qinpel.window.loadConfig(Qine.qinpel.ours.consts.QIN_BASE_SELECTED);

export const tableHead = AdModules.SALES.tableHead;

export const registry: Registry = { base, tableHead };

export const regBased: AdRegBased = {
    registry,
    joinList: [
        {
            module: AdModules.CLIENTS,
            alias: "clients",
            filterList: [{ linked: { name: "cliente", upon: "codigo" } }],
        },
        {
            module: AdModules.PAYMENT_TERMS,
            alias: "payment_terms",
            filterList: [{ linked: { name: "cond_pagamento", upon: "codigo" } }],
        },
    ],
};

export class AdSales extends AdRegister {
    private _qinEnviar = new QinButton({ label: new QinLabel("Enviar") });
    private _actEnviar: QinAction = (_) => {
        if (!this.hasSelectedNoticed()) {
            this.qinpel.frame.showError("You must have a selected sales to send.", "{qia_ad_sales}(ErrCode-000003)");
            return;
        }
        const codigo = this.model.getFieldByName("codigo").value;
        this.qinpel.talk.giz
            .run({
                name: "adsales/send-sale.giz",
                argList: [base, codigo],
            })
            .then((token) => {
                this.qinpel.talk.utl.aux
                    .askWhenDone({
                        token,
                        askHasOut: true,
                        askOutLines: true,
                        askHasErr: true,
                        askErrLines: true,
                    })
                    .then((results) => {
                        this.tryRefresh();
                        if (results.hasOut) {
                            this.qinpel.frame.showInfo(results.outLines, "{qia_ad_sales}(ErrCode-000005)");
                        }
                        if (results.hasErr) {
                            this.qinpel.frame.showError(results.errLines, "{qia_ad_sales}(ErrCode-000004)");
                        }
                    })
                    .catch((err) => this.qinpel.frame.showError(err, "{qia_ad_sales}(ErrCode-000002)"));
            })
            .catch((err) => this.qinpel.frame.showError(err, "{qia_ad_sales}(ErrCode-000001)"));
    };

    public constructor(module: AdModule, expect: AdExpect) {
        super(module, expect, regBased);
        this.addField(AdTools.newAdFieldChars("codigo", "Código", 10).putKey());
        this.addField(AdTools.newAdFieldDate("emitido_data", "Emitido Em").putReadOnly());
        this.addField(AdTools.newAdFieldBoolean("enviado", "Enviado").putReadOnly());
        this.addField(AdTools.newAdFieldDate("enviado_data", "Enviado Em").putReadOnly());
        this.addField(AdTools.newAdFieldChars("cliente", "Cliente - Cod.", 8));
        this.addField(AdTools.newAdFieldChars("clients.nome", "Cliente - Nome.", 60));
        this.addField(AdTools.newAdFieldChars("clients.fantasia", "Cliente - Fantasia.", 60));
        this.addField(AdTools.newAdFieldNumeric("out_desc", "Out. Descontos"));
        this.addField(AdTools.newAdFieldNumeric("out_acresc", "Out. Acréscimos"));
        this.addField(AdTools.newAdFieldNumeric("subtotal", "SubTotal").putReadOnly());
        this.addField(AdTools.newAdFieldNumeric("desc_itens", "Desc. Itens").putReadOnly());
        this.addField(AdTools.newAdFieldNumeric("acresc_itens", "Acresc. Itens").putReadOnly());
        this.addField(AdTools.newAdFieldNumeric("total", "Total").putReadOnly());
        this.addField(AdTools.newAdFieldChars("cond_pagamento", "Cond. Pgto - Cod.", 4));
        this.addField(AdTools.newAdFieldChars("payment_terms.nome", "Cond. Pgto - Nome.", 45));
        this.addField(AdTools.newAdFieldChars("obs", "Obs", 400));
        this.addDetail({
            setup: {
                module: AdModules.SALES_ITEMS,
                scopeList: [AdScope.ALL],
                filterList: [{ linked: { name: "prepedido", upon: "codigo" } }],
            },
            title: "Itens",
        });
        this._qinEnviar.addActionMain(this._actEnviar);
        this.addAct(this._qinEnviar);
        this.prepare();
    }
}
