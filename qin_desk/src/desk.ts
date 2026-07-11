import { QinConstants } from "qin_soul";
import { QinAuthorize, QinDesk } from "./qin-desk";
import { Qinpel } from "./qinpel";
const qinpel = (window.frameElement as any).qinpel as Qinpel;

qinpel.window.needToEnter().then((need) => {
    if (need) {
        (window.frameElement as HTMLIFrameElement).src = "./login.html";
    } else {
        new QinDesk(qinpel, {
            shouldAddApp: appsAuthorize,
            shouldAddCfg: cfgsAuthorize,
        }).putInDocBody();
    }
});

const appsAuthorize: QinAuthorize = function (manifest) {
    return !manifest.group;
}

const cfgsAuthorize: QinAuthorize = function (manifest) {
    return cfgsTitles.indexOf(manifest.title) > -1;
}

const cfgsTitles: Array<string> = [
    QinConstants.DEV_TOOLS,
    QinConstants.QIN_BASES
];