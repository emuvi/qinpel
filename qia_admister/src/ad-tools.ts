import { QinAsset, QinComboItem, QinComboSet, QinMutants, QinCharsSet, QinSuggestionSet } from "qin_case";
import { Filter, TableHead, Valued } from "qin_soul";
import { AdField } from "./ad-field";
import { AdNames } from "./ad-names";

export type AdSetup = {
    module: AdModule;
    scopeList: AdScope[];
    filterList?: Filter[];
    fixedList?: Valued[];
};

export enum AdScope {
    ALL = "ALL",
    INSERT = "INSERT",
    SEARCH = "SEARCH",
    NOTICE = "NOTICE",
    RELATE = "RELATE",
    MUTATE = "MUTATE",
    DELETE = "DELETE",
}

export type AdModule = {
    appName: string;
    title: string;
    icon: QinAsset;
    tableHead?: TableHead;
};

function isSameModule(moduleA: AdModule, moduleB: AdModule): boolean {
    return moduleA?.appName == moduleB?.appName && moduleA?.title == moduleB?.title;
}

function newAdSetupOption(module: AdModule, scopeList: AdScope[], filterList?: Filter[], fixedList?: Valued[]) {
    let result = {};
    result[AdNames.AdSetup] = newAdSetup(module, scopeList, filterList, fixedList);
    return result;
}

function newAdSetup(module: AdModule, scopeList: AdScope[], filterList?: Filter[], fixedList?: Valued[]): AdSetup {
    return {
        module,
        scopeList,
        filterList,
        fixedList,
    };
}

function newAdFieldInt(name: string, title: string): AdField {
    return new AdField({
        key: false,
        name,
        title,
        kind: QinMutants.INT,
    });
}

function newAdFieldNumeric(name: string, title: string): AdField {
    return new AdField({
        key: false,
        name,
        title,
        kind: QinMutants.NUMERIC,
    });
}

function newAdFieldChars(name: string, title: string, maxLength: number): AdField {
    return new AdField({
        key: false,
        name,
        title,
        kind: QinMutants.CHARS,
        options: {
            maxLength,
        } as QinCharsSet,
    });
}

function newAdFieldSuggestion(name: string, title: string, maxLength: number, items: string[]): AdField {
    return new AdField({
        key: false,
        name: name,
        title: title,
        kind: QinMutants.SUGGESTION,
        options: {
            maxLength: maxLength,
            items: items,
        } as QinSuggestionSet,
    });
}

function newAdFieldDate(name: string, title: string): AdField {
    return new AdField({
        key: false,
        name,
        title,
        kind: QinMutants.DATE,
    });
}

function newAdFieldCombo(name: string, title: string, items: QinComboItem[]): AdField {
    return new AdField({
        key: false,
        name,
        title,
        kind: QinMutants.COMBO,
        options: {
            items,
        } as QinComboSet,
    });
}

function newAdFieldBoolean(name: string, title: string): AdField {
    return newAdFieldCombo(name, title, [
        {
            title: "",
            value: "",
        },
        {
            title: "Sim",
            value: "S",
        },
        {
            title: "Não",
            value: "N",
        },
    ]);
}

function newAdFieldAtivo(): AdField {
    return newAdFieldBoolean("ativo", "Ativo");
}

export const AdTools = {
    isSameModule,
    newAdSetup,
    newAdSetupOption,
    newAdFieldInt,
    newAdFieldNumeric,
    newAdFieldChars,
    newAdFieldSuggestion,
    newAdFieldDate,
    newAdFieldCombo,
    newAdFieldBoolean,
    newAdFieldAtivo,
};
