import { QinBase, QinButton, QinColumn, Qine, QinIcon, QinLabel, QinLine, QinTitled } from "qin_case";
import { QinGrandeur } from "qin_soul";
import { AdExpect } from "./ad-expect";
import { AdNames } from "./ad-names";
import { AdRegister } from "./ad-register";
import { AdModule, AdScope, AdSetup, AdTools } from "./ad-tools";

export type AdMenuAct = new (module: AdModule, expect: AdExpect) => QinBase;

export type AdMenuItem = {
    group?: string;
    module: AdModule;
    action: AdMenuAct;
};

export class AdMenu extends QinColumn {
    private _lines = new Array<QinTitled>();

    constructor(menuItemList: AdMenuItem[]) {
        super();
        for (const menuItem of menuItemList) {
            const line = this.getLine(menuItem.group);
            const button = new QinButton({
                icon: new QinIcon(menuItem.module.icon, QinGrandeur.MEDIUM),
                label: new QinLabel(menuItem.module.title),
            });
            button.styleAsMargin(3);
            button.styleAsPadding(9);
            button.styleAsMaxWidth(100);
            button.putAsColumn();
            button.addActionMain((_) => {
                this.qinpel.window.newFrame(
                    menuItem.module.title,
                    menuItem.module.appName,
                    AdTools.newAdSetupOption(menuItem.module, [AdScope.ALL])
                );
            });
            line.put(button);
        }
    }

    private getLine(title?: string): QinLine {
        if (!title) {
            if (this._lines.length === 0) {
                const newLine = new QinTitled();
                newLine.install(this);
                this._lines.push(newLine);
            }
            return this._lines[this._lines.length - 1];
        }
        for (const line of this._lines) {
            if (line.title == title) {
                return line;
            }
        }
        const newLine = new QinTitled({ label: new QinLabel(title) });
        newLine.install(this);
        this._lines.push(newLine);
        return newLine;
    }
}

export function adMenuStartUp(menuItemList: AdMenuItem[]): QinBase {
    const adSetup = Qine.qinpel.frame.getOption(AdNames.AdSetup) as AdSetup;
    if (adSetup?.module) {
        for (const menuItem of menuItemList) {
            if (AdTools.isSameModule(menuItem.module, adSetup.module)) {
                let expect = new AdExpect({
                    scopeList: adSetup.scopeList,
                    filterList: adSetup.filterList,
                    fixedList: adSetup.fixedList,
                });
                if (menuItem.action) {
                    return new menuItem.action(menuItem.module, expect);
                } else {
                    throw new Error("No menu action defined");
                }
            }
        }
    }
    return new AdMenu(menuItemList);
}
