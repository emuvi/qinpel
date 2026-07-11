import { QinGrandeur } from "qin_soul";
import { QinBase } from "./qin-base";
import { QinButton } from "./qin-button";
import { QinColumn } from "./qin-column";
import { QinIcon } from "./qin-icon";
import { QinLabel } from "./qin-label";
import { QinLine } from "./qin-line";
import { QinTitled } from "./qin-titled";
import { Qine, QinExpect, QinModule, QinSetup, QinTools } from "./qin-tools";

export type QinMenuAct<T extends QinBase> = new (module: QinModule, expect?: QinExpect) => T;

export type QinMenuItem<T extends QinBase> = {
    group?: string;
    module: QinModule;
    action: QinMenuAct<T>;
    expect?: QinExpect;
};

export class QinMenu extends QinColumn {
    private _lines = new Array<QinTitled>();

    constructor(items: QinMenuItem<any>[]) {
        super();
        for (const item of items) {
            const line = this.getLine(item.group);
            const button = new QinButton({
                icon: new QinIcon(item.module.icon, QinGrandeur.MEDIUM),
                label: new QinLabel(item.module.title),
            });
            button.styleAsMargin(3);
            button.styleAsPadding(9);
            button.styleAsMaxWidth(100);
            button.putAsColumn();
            button.addActionMain((_) => {
                this.qinpel.window.newFrame(
                    item.module.title,
                    item.module.appName,
                    QinTools.newQinSetupOption(item.module, item.expect)
                );
            });
            line.put(button);
        }
    }

    private getLine(title: string | undefined): QinLine {
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
        const newLine = new QinTitled({label: new QinLabel(title)});
        newLine.install(this);
        this._lines.push(newLine);
        return newLine;
    }
}

export function qinMenuStartUp(menus: QinMenuItem<any>[]): QinBase {
    const qinSetup = Qine.qinpel.frame.getOption(Qine.qinpel.ours.consts.QIN_SETUP) as QinSetup;
    if (qinSetup && qinSetup.module) {
        for (const menu of menus) {
            if (QinTools.isSameModule(menu.module, qinSetup.module)) {
                if (menu.action) {
                    return new menu.action(menu.module, menu.expect);
                } else {
                    throw new Error("No menu action defined");
                }
            }
        }
    }
    return new QinMenu(menus);
}
