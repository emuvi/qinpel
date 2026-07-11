const _dictionary: Map<string, string> = new Map();

function tr(of: string): string {
    return _dictionary.get(of) ?? of;
}

function loadDictionary(dictionary: Record<string, string>) {
    for (const keyOf in dictionary) {
        _dictionary.set(keyOf, dictionary[keyOf]);
    }
}

function dictionaryForget() {
    _dictionary.clear();
}

function getCookie(name: string, orDefault?: string): string {
    let cookies = document.cookie.split(";");
    for (const cookie of cookies) {
        let cookiePair = cookie.split("=");
        if (name == decodeURIComponent(cookiePair[0]).trim()) {
            return decodeURIComponent(cookiePair[1]);
        }
    }
    return orDefault;
}

function setCookie(name: string, value: any, options: any = {}) {
    options = {
        path: "/",
        ...options,
    };
    if (!options.expires) {
        let date = new Date();
        date.setTime(date.getTime() + 1 * 24 * 60 * 60 * 1000);
        options.expires = date;
    }
    if (options.expires instanceof Date) {
        options.expires = options.expires.toUTCString();
    }
    options["SameSite"] = "Strict";
    let updatedCookie = encodeURIComponent(name) + "=" + encodeURIComponent(value);
    for (let optionKey in options) {
        updatedCookie += "; " + optionKey;
        let optionValue = options[optionKey];
        if (optionValue !== true) {
            updatedCookie += "=" + optionValue;
        }
    }
    updatedCookie += "; Secure";
    document.cookie = updatedCookie;
}

function delCookie(name: string, options: any = {}) {
    let updatedCookie = encodeURIComponent(name) + "=;  expires=Thu, 01 Jan 1970 00:00:00 GMT";
    if (options.expires) {
        delete options.expires;
    }
    for (let optionKey in options) {
        updatedCookie += "; " + optionKey;
        let optionValue = options[optionKey];
        if (optionValue !== true) {
            updatedCookie += "=" + optionValue;
        }
    }
    document.cookie = updatedCookie;
}

function log(message: string) {
    console.log(message);
}

function logInfo(info: any, origin: string) {
    log(getInfoMessage(info, origin));
}

function getInfoMessage(info: any, origin: string) {
    return getTreatMessage(tr("Notice"), info, origin);
}

function logError(error: any, origin: string) {
    log(getErrorMessage(error, origin));
}

function getErrorMessage(error: any, origin: string) {
    return getTreatMessage(tr("Problem"), error, origin);
}

function logWarning(warn: any, origin: string) {
    log(getWarningMessage(warn, origin));
}

function getWarningMessage(warn: any, origin: string) {
    return getTreatMessage(tr("Attention"), warn, origin);
}

function getTreatMessage(prefix: string, value: any, origin: string) {
    var result = prefix + ": ";
    if (typeof value == "string" || value instanceof String) {
        result += value.toString();
    } else {
        if (value && value.why) {
            result += getMessageOrData(value.why);
        }
        if (value && value.message) {
            result += getMessageOrData(value.message);
        }
        if (value && value.response && value.response.data) {
            if (result) {
                result += "\n" + tr("And");
            }
            result += tr(" was returned") + getMessageOrData(value.response.data);
        }
    }
    if (origin) {
        result += "\n" + tr("By origin: ") + origin;
    }
    return result;
}

function getMessageOrData(of: any): string {
    if (typeof of == "string" || of instanceof String) {
        return of.toString();
    } else {
        return tr(" with data:") + "\n" + JSON.stringify(of);
    }
}

function stopBrowserShortcuts(window: Window) {
    window.document.body.onkeydown = (event) => {
        if (event.ctrlKey) {
            event.stopPropagation();
            event.preventDefault();
            return false;
        } else if (
            ["F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12"].indexOf(
                event.key
            ) >= 0
        ) {
            event.stopPropagation();
            event.preventDefault();
            return false;
        }
        return true;
    };
}

export const QinHead = {
    tr,
    loadDictionary,
    dictionaryForget,
    getCookie,
    setCookie,
    delCookie,
    log,
    logInfo,
    getInfoMessage,
    logError,
    getErrorMessage,
    logWarning,
    getWarningMessage,
    getTreatMessage,
    stopBrowserShortcuts,
};
