import { QinBool } from "./qin-bool";
import { QinCombo } from "./qin-combo";
import { QinDate } from "./qin-date";
import { QinEdit } from "./qin-edit";
import { QinFilePath } from "./qin-file-path";
import { QinFilePick } from "./qin-file-pick";
import { QinFileView } from "./qin-file-view";
import { QinIconPick } from "./qin-icon-pick";
import { QinInt } from "./qin-int";
import { QinNumeric } from "./qin-numeric";
import { QinChars } from "./qin-chars";
import { QinSuggestion } from "./qin-suggestion";
import { Qine } from "./qin-tools";
import { QinPassword } from "./qin-password";
import { QinText } from "./qin-text";
import { QinValued } from "./qin-valued";

export enum QinMutants {
    BOOL = "BOOLEAN",
    INT = "INTEGER",
    NUMERIC = "NUMERIC",
    CHARS = "STRING",
    PASSWORD = "PASSWORD",
    SUGGESTION = "SUGGESTION",
    DATE = "DATE",
    COMBO = "COMBO",
    TEXT = "TEXT",
    ICON_PICK = "ICON_PICK",
    FILE_PATH = "FILE_PATH",
    FILE_PICK = "FILE_PICK",
    FILE_VIEW = "FILE_VIEW",
    VALUED = "VALUED",
}

function newEdit(kind: QinMutants, options: any): QinEdit<any> {
    switch (kind) {
        case QinMutants.BOOL:
            return new QinBool(options);
        case QinMutants.INT:
            return new QinInt(options);
        case QinMutants.NUMERIC:
            return new QinNumeric(options);
        case QinMutants.CHARS:
            return new QinChars(options);
        case QinMutants.SUGGESTION:
            return new QinSuggestion(options);
        case QinMutants.PASSWORD:
            return new QinPassword(options);
        case QinMutants.DATE:
            return new QinDate(options);
        case QinMutants.COMBO:
            return new QinCombo(options);
        case QinMutants.TEXT:
            return new QinText(options);
        case QinMutants.ICON_PICK:
            return new QinIconPick(options);
        case QinMutants.FILE_PATH:
            return new QinFilePath(options);
        case QinMutants.FILE_PICK:
            return new QinFilePick(options);
        case QinMutants.FILE_VIEW:
            return new QinFileView(options);
        case QinMutants.VALUED:
            return new QinValued(options);
        default:
            throw new Error(Qine.qinpel.tr("Unknown kind of mutant to create: ") + kind);
    }
}

export const QinMutantsArm = {
    newEdit,
};
