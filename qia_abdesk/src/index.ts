import { Qine, QinPanel } from "qin_case";
import { Modules } from "./consts";
import { Menu } from "./menu";
import { ModuleAPP } from "./module-app";
import { ModuleBAS } from "./module-bas";
import { ModuleCMD } from "./module-cmd";
import { ModuleDIR } from "./module-dir";
import { ModuleGIZ } from "./module-giz";
import { ModuleREG } from "./module-reg";
import { ModuleUTL } from "./module-utl";
import { ModuleWAY } from "./module-way";

const qinFrame = Qine.qinpel.frame;

class AbDesk extends QinPanel {
    public constructor() {
        super();
        if (!qinFrame.hasOption("Module")) {
            new Menu().install(this);
        } else {
            switch (qinFrame.getOption("Module")) {
                case Modules.APP: new ModuleAPP().install(this); break;
                case Modules.BAS: new ModuleBAS().install(this); break;
                case Modules.CMD: new ModuleCMD().install(this); break;
                case Modules.DIR: new ModuleDIR().install(this); break;
                case Modules.GIZ: new ModuleGIZ().install(this); break;
                case Modules.REG: new ModuleREG().install(this); break;
                case Modules.UTL: new ModuleUTL().install(this); break;
                case Modules.WAY: new ModuleWAY().install(this); break;
            }
        }
    }
}

new AbDesk().putAsBody();
