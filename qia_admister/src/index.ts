import { QinPanel } from "qin_case";
import { AdNames } from "./ad-names";

class AdMister extends QinPanel {
    public constructor() {
        super();
        const qinDesk = this.qinpel.window.newDesk(this.qinpel, {
            shouldAddApp: (manifest) => manifest.group == AdNames.AdMister,
            shouldAddCfg: (manifest) => manifest.title == this.qinpel.ours.consts.QIN_BASES,
        });
        this.castedQine().appendChild(qinDesk.getMain());
    }
}

new AdMister().putAsBody();
