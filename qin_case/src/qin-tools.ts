import { Qinpel } from "qin_desk/types/qinpel";
import { QinWaiters } from "qin_soul";
import { QinAsset } from "./qin-assets";

const _mainQinpel = (window.frameElement as any).qinpel as Qinpel;

export const Qine = {
    qinpel: _mainQinpel,
};

export type QinModule = {
    appName: string;
    title: string;
    icon: QinAsset;
};

export class QinExpect {
    private _waiters: QinWaiters<any>;

    public constructor(options: QinExpectSet) {
        this._waiters = options.waiters;
    }

    public get waiters(): QinWaiters<any> {
        return this._waiters;
    }
}

export type QinExpectSet = {
    waiters?: QinWaiters<any>;
};

export type QinSetup = {
    module: QinModule;
    expect?: QinExpect;
};

function isSameModule(one: QinModule, two: QinModule): boolean {
    return one?.appName == two?.appName && one?.title == two?.title;
}

function newQinSetupOption(module: QinModule, expect?: QinExpect) {
    let result = {};
    result[_mainQinpel.ours.consts.QIN_SETUP] = newQinSetup(module, expect);
    return result;
}

function newQinSetup(module: QinModule, expect?: QinExpect): QinSetup {
    return {
        module,
        expect,
    };
}

export const QinTools = {
    isSameModule,
    newQinSetup,
    newQinSetupOption,
};
