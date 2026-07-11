import { AdExpect, AdModule, AdModules, AdRegBased, AdRegCalls, AdRegister, AdSelect, AdTools } from "admister";
import { Qine } from "qin_case";
import { Nature, Registry } from "qin_soul";
import { registry as regPrices } from "./ad-prices";
import { registry as regSales } from "./ad-sales";

const base = Qine.qinpel.window.loadConfig(Qine.qinpel.ours.consts.QIN_BASE_SELECTED);

export const tableHead = AdModules.SALES_ITEMS.tableHead;

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

export class AdSalesItems extends AdRegister {
    public constructor(module: AdModule, expect: AdExpect) {
        super(module, expect, regBased);
        this.addFields([
            AdTools.newAdFieldChars("prepedido", "PréPedido", 10).putKey().putReadOnly(),
            AdTools.newAdFieldChars("codigo", "Código", 4).putKey(),
            AdTools.newAdFieldChars("produto", "Produto - Cód.", 6)
                .putOnEntered(this._productPriorValueSaver)
                .putOnChanged(this._updatePrice)
                .putOnExited(this._updatePrice),
            AdTools.newAdFieldChars("products.nome", "Produto - Nome.", 60),
            AdTools.newAdFieldNumeric("quantidade", "Quantidade")
                .putOnExited(this._updateValues),
            AdTools.newAdFieldChars("tabela", "Tabela", 6)
                .putOnEntered(this._tablePriorValueSaver)
                .putOnChanged(this._updatePrice)
                .putOnExited(this._updatePrice),
            AdTools.newAdFieldNumeric("preco", "Preço")
                .putOnExited(this._updateValues),
            AdTools.newAdFieldNumeric("subtotal", "SubTotal").putReadOnly(),
            AdTools.newAdFieldNumeric("desconto_per", "% Desconto")
                .putOnExited(this._updateValues),
            AdTools.newAdFieldNumeric("desconto", "Desconto").putReadOnly(),
            AdTools.newAdFieldNumeric("acrescimo_per", "% Acréscimo")
                .putOnExited(this._updateValues),
            AdTools.newAdFieldNumeric("acrescimo", "Acréscimo").putReadOnly(),
            AdTools.newAdFieldNumeric("total", "Total").putReadOnly(),
            AdTools.newAdFieldChars("obs", "Obs", 100),
        ]);
        let fixedPrepedido = null;
        if (expect) {
            if (expect.fixedList) {
                for (const fixed of expect.fixedList) {
                    if (fixed.name === "prepedido") {
                        fixedPrepedido = fixed.data;
                    }
                }
            }
        }
        if (fixedPrepedido) {
            AdRegCalls.selectOne(this.makeSelectClientTableQuery(fixedPrepedido))
                .then((res) => {
                    this.model.getFieldByName("tabela").defaultValue = res;
                    this.prepare();
                })
                .catch((err) => this.qinpel.frame.showError(err, "{qia_ad_sales}(ErrCode-000007)"));
        } else {
            this.prepare();
        }
    }

    private _updateValues = (_: any) => {
        if (!this.isRegModeInsert() && !this.isRegModeMutate()) {
            return;
        }
        let quantidade = this.model.getFieldByName("quantidade").value;
        if (!quantidade) quantidade = 0.0;
        let preco = this.model.getFieldByName("preco").value;
        if (!preco) preco = 0.0;
        let subtotal = quantidade * preco;
        this.model.getFieldByName("subtotal").value = subtotal.toFixed(2);
        let desconto = 0.0;
        let desconto_per = this.model.getFieldByName("desconto_per").value;
        if (!desconto_per) desconto_per = 0.0;
        if (desconto_per > 0.0) {
            desconto = (subtotal * desconto_per) / 100.0;
        }
        this.model.getFieldByName("desconto").value = desconto.toFixed(2);
        let acrescimo = 0.0;
        let acrescimo_per = this.model.getFieldByName("acrescimo_per").value;
        if (!acrescimo_per) acrescimo_per = 0.0;
        if (acrescimo_per > 0.0) {
            acrescimo = (subtotal * acrescimo_per) / 100.0;
        }
        this.model.getFieldByName("acrescimo").value = acrescimo.toFixed(2);
        let total = subtotal + acrescimo - desconto;
        this.model.getFieldByName("total").value = total.toFixed(2);
    };

    private _tablePriorValue = null;
    private _productPriorValue = null;

    private _tablePriorValueSaver = (_: any) => {
        this._tablePriorValue = this.model.getFieldByName("tabela").value;
    };

    private _productPriorValueSaver = (_: any) => {
        this._productPriorValue = this.model.getFieldByName("produto").value;
    };

    private _updatePrice = (_: any) => {
        if (!this.isRegModeInsert() && !this.isRegModeMutate()) {
            return;
        }
        let produto = this.model.getFieldByName("produto").value;
        let tabela = this.model.getFieldByName("tabela").value;
        if (
            produto &&
            tabela &&
            (produto !== this._productPriorValue || tabela !== this._tablePriorValue)
        ) {
            AdRegCalls.selectOne(this.makeSelectPriceQuery(produto, tabela))
                .then((res) => {
                    this.model.getFieldByName("preco").value = res;
                    this._updateValues(null);
                })
                .catch((err) => this.qinpel.frame.showError(err, "{qia_ad_sales}(ErrCode-000006)"));
        }
        this._productPriorValue = produto;
        this._tablePriorValue = tabela;
    };

    private makeSelectClientTableQuery(prepedido: any): AdSelect {
        return {
            registry: regSales,
            fieldList: [{ name: "clients.tabela_preco", type: Nature.Chars }],
            joinList: [
                {
                    module: AdModules.CLIENTS,
                    alias: "clients",
                    filterList: [{ linked: { name: "cliente", upon: "codigo" } }],
                },
            ],
            filterList: [
                { 
                    valued: { 
                        name: "codigo", 
                        type: Nature.Chars, 
                        data: prepedido, 
                    } 
                },
            ],
        };
    }

    private makeSelectPriceQuery(produto: any, tabela: any): AdSelect {
        return {
            registry: regPrices,
            fieldList: [{ name: "valor", type: Nature.Numeric }],
            filterList: [
                {
                    valued: {
                        name: "produto",
                        type: Nature.Chars,
                        data: produto,
                    },
                },
                {
                    valued: {
                        name: "tabela",
                        type: Nature.Chars,
                        data: tabela,
                    },
                },
            ],
        };
    }
}
