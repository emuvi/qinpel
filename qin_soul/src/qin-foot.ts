import { Nature } from "./qin-type";

export enum QinConstants {
    DEV_TOOLS = "DevTools",
    QIN_BASES = "QinBases",
    QIN_BASE_SELECTED = "QinBaseSelected",
    QIN_SETUP = "QinSetup",
    LANG_PT_BR = "pt-BR",
}

function getLocation() {
    return window.location.href;
}

function isLocalHost() {
    let location = getLocation();
    let start = location.indexOf("://");
    if (start == -1) {
        start = 0;
    } else {
        start += 3;
    }
    location = location.substring(start);
    return location.indexOf("localhost") === 0 || location.indexOf("127.0.0.1") === 0;
}

function getSeparator(ofPath: string): string {
    let result = "/";
    if (ofPath && ofPath.indexOf("\\") > -1) {
        result = "\\";
    }
    return result;
}

function getPathJoin(pathA: string, pathB: string): string {
    if (pathA == null || pathA == undefined) {
        pathA = "";
    }
    if (pathB == null || pathB == undefined) {
        pathB = "";
    }
    if (pathA.length == 0) {
        return pathB;
    } else if (pathB.length == 0) {
        return pathA;
    } else {
        let union = "/";
        if (pathA.indexOf("\\") > -1 || pathB.indexOf("\\") > -1) {
            union = "\\";
        }
        let pathAEnd = pathA.substring(pathA.length - 1, pathA.length);
        let pathBStart = pathB.substring(0, 1);
        if (pathAEnd == union || pathBStart == union) {
            union = "";
        }
        return pathA + union + pathB;
    }
}

function getParent(path: string): string {
    if (path) {
        let separator = getSeparator(path);
        let last = path.lastIndexOf(separator);
        if (last > -1) {
            return path.substring(0, last);
        }
    }
    return "";
}

function getStem(path: string): string {
    if (path) {
        let separator = getSeparator(path);
        let last = path.lastIndexOf(separator);
        if (last > -1) {
            return path.substring(last + 1);
        }
    }
    return "";
}

function getFileExtension(name: string): string {
    let position = name.lastIndexOf(".");
    if (position > -1) {
        return name.substring(position + 1);
    } else {
        return "";
    }
}

const appsExtensions = ["htm", "html", "css", "js", "jsx", "ts", "tsx", "phtml"];

function isFileApp(extension: string): boolean {
    return appsExtensions.indexOf(extension) > -1;
}

const cmdsExtensions = ["h", "c", "hpp", "cpp", "rs", "jl", "cs", "csproj", "fs", "ml", "fsi", "mli", "fsx", "fsscript", "java", "gy", "gvy", "groovy", "sc", "scala", "clj", "py", "ruby", "php", "phtml"];

function isFileCmd(extension: string): boolean {
    return cmdsExtensions.indexOf(extension) > -1;
}

const execExtensions = ["exe", "jar", "com", "bat", "sh"];

function isFileExec(extension: string): boolean {
    return execExtensions.indexOf(extension) > -1;
}

const imageExtensions = ["jpg", "jpeg", "png", "gif", "bmp"];

function isFileImage(extension: string): boolean {
    return imageExtensions.indexOf(extension) > -1;
}

const vectorExtensions = ["svg"];

function isFileVector(extension: string): boolean {
    return vectorExtensions.indexOf(extension) > -1;
}

const movieExtensions = ["avi", "mp4"];

function isFileMovie(extension: string): boolean {
    return movieExtensions.indexOf(extension) > -1;
}

const musicExtensions = ["wav", "mp3"];

function isFileMusic(extension: string): boolean {
    return musicExtensions.indexOf(extension) > -1;
}

const zippedExtensions = ["zip", "rar", "7z", "tar", "gz"];

function isFileZipped(extension: string): boolean {
    return zippedExtensions.indexOf(extension) > -1;
}

function getBit(value: any) {
    if (value === null || value === undefined || value === "") return null;
    return value ? 1 : 0;
}

function getBool(value: any) {
    if (value === null || value === undefined || value === "") return null;
    if (typeof value === "boolean") return value;
    if (typeof value === "string") {
        let lower = value.trim().toLowerCase();
        return ["true", "1", "yes", "y"].indexOf(lower) >= 0;
    }
    return Boolean(value);
}

function getByte(value: any) {
    if (value === null || value === undefined || value === "") return null;
    let num = Number(value);
    if (isNaN(num)) num = 0;
    num = Math.trunc(num);
    if (num < -128) return -128;
    if (num > 127) return 127;
    return num;
}

function getTiny(value: any) {
    if (value === null || value === undefined || value === "") return null;
    return getByte(value);
}

function getSmall(value: any) {
    if (value === null || value === undefined || value === "") return null;
    let num = Number(value);
    if (isNaN(num)) num = 0;
    num = Math.trunc(num);
    if (num < -32768) return -32768;
    if (num > 32767) return 32767;
    return num;
}

function getInt32(value: any) {
    if (value === null || value === undefined || value === "") return null;
    let num = Number(value);
    if (isNaN(num)) num = 0;
    num = Math.trunc(num);
    if (num < -2147483648) return -2147483648;
    if (num > 2147483647) return 2147483647;
    return num;
}

function getLong(value: any) {
    if (value === null || value === undefined || value === "") return null;
    let num = Number(value);
    if (isNaN(num)) num = 0;
    num = Math.trunc(num);
    if (num < Number.MIN_SAFE_INTEGER) return Number.MIN_SAFE_INTEGER;
    if (num > Number.MAX_SAFE_INTEGER) return Number.MAX_SAFE_INTEGER;
    return num;
}

function getBigInt(value: any) {
    if (value === null || value === undefined || value === "") return null;
    return getLong(value);
}

function getSerial(value: any) {
    if (value === null || value === undefined || value === "") return null;
    return getInt32(value);
}

function getBigSerial(value: any) {
    if (value === null || value === undefined || value === "") return null;
    return getLong(value);
}

function getFloat32(value: any) {
    if (value === null || value === undefined || value === "") return null;
    let num = Number(value);
    if (isNaN(num)) num = 0;
    if (num < -3.4028235e38) return -3.4028235e38;
    if (num > 3.4028235e38) return 3.4028235e38;
    return Math.fround(num);
}

function getReal(value: any) {
    if (value === null || value === undefined || value === "") return null;
    return getFloat32(value);
}

function getDouble(value: any) {
    if (value === null || value === undefined || value === "") return null;
    let num = Number(value);
    if (isNaN(num)) num = 0;
    if (num < -1.7976931348623157e308) return -1.7976931348623157e308;
    if (num > 1.7976931348623157e308) return 1.7976931348623157e308;
    return num;
}

function getNumeric(value: any, specs?: any) {
    if (value === null || value === undefined || value === "") return null;
    let numStr = String(value ?? "0").trim();
    if (specs?.precision) {
        let n = Number(numStr);
        if (!isNaN(n)) {
            numStr = n.toFixed(specs.precision);
        }
    }
    let num = Number(numStr);
    if (!isFinite(num) || numStr.length > 30) {
        return numStr;
    }
    return num;
}

function getBigNumeric(value: any, specs?: any) {
    if (value === null || value === undefined || value === "") return null;
    return getNumeric(value, specs);
}

function getChar(value: any) {
    if (value === null || value === undefined) return null;
    let str = String(value || "").trim();
    return str.charAt(0) || "\0";
}

function getChars(value: any) {
    if (value === null || value === undefined) return null;
    return String(value || "");
}

function getDate(value: any) {
    if (value === null || value === undefined || value === "") return null;
    let d = new Date(value);
    return isNaN(d.getTime()) ? new Date(0) : d;
}

function getTime(value: any) {
    if (value === null || value === undefined || value === "") return null;
    if (typeof value === "string") return value;
    let d = new Date(value);
    return isNaN(d.getTime()) ? "00:00:00" : d.toTimeString().split(" ")[0];
}

function getDateTime(value: any) {
    if (value === null || value === undefined || value === "") return null;
    let d = new Date(value);
    return isNaN(d.getTime()) ? new Date(0) : d;
}

function getZoneTime(value: any) {
    if (value === null || value === undefined || value === "") return null;
    let d = new Date(value);
    return isNaN(d.getTime()) ? new Date(0) : d;
}

function getTimestamp(value: any) {
    if (value === null || value === undefined || value === "") return null;
    let d = new Date(value);
    return isNaN(d.getTime()) ? new Date(0) : d;
}

function getBytes(value: any) {
    if (value === null || value === undefined || value === "") return null;
    if (value instanceof Uint8Array) return value;
    if (Array.isArray(value)) return new Uint8Array(value.map(function(v){return Number(v)||0;}));
    if (typeof value === "string") return new Uint8Array([].map.call(value,function(c){return c.charCodeAt(0);}));
    return new Uint8Array(0);
}

function getBlob(value: any) {
    if (value === null || value === undefined || value === "") return null;
    if (value instanceof Uint8Array || value instanceof ArrayBuffer) return value;
    return getBytes(value);
}

function getText(value: any) {
    if (value === null || value === undefined) return null;
    return String(value || "");
}

function getObject(value: any) {
    if (value === null || value === undefined || value === "") return null;
    if (value instanceof Map) {
        return Array.from(value.entries())
            .reduce((obj, [k, v]) => {
                obj[k] = v;
                return obj;
            }, {} as Object);
    }
    return JSON.parse(String(value || "{}"));
}

function getValued(type: Nature, data: any, specs?: any): any {
    switch (type) {
        case Nature.Bit: return getBit(data);
        case Nature.Bool: return getBool(data);
        case Nature.Byte: return getByte(data);
        case Nature.Tiny: return getTiny(data);
        case Nature.Small: return getSmall(data);
        case Nature.Int: return getInt32(data);
        case Nature.Long: return getLong(data);
        case Nature.BigInt: return getBigInt(data);
        case Nature.Serial: return getSerial(data);
        case Nature.BigSerial: return getBigSerial(data);
        case Nature.Float: return getFloat32(data);
        case Nature.Real: return getReal(data);
        case Nature.Double: return getDouble(data);
        case Nature.Numeric: return getNumeric(data, specs);
        case Nature.BigNumeric: return getBigNumeric(data, specs);
        case Nature.Char: return getChar(data);
        case Nature.Chars: return getChars(data);
        case Nature.Date: return getDate(data);
        case Nature.Time: return getTime(data);
        case Nature.DateTime: return getDateTime(data);
        case Nature.ZoneTime: return getZoneTime(data);
        case Nature.Timestamp: return getTimestamp(data);
        case Nature.Bytes: return getBytes(data);
        case Nature.Blob: return getBlob(data);
        case Nature.Text: return getText(data);
        case Nature.Object: return getObject(data);
        default: return data;
    }
}

export const QinFoot = {
    getLocation,
    isLocalHost,
    getSeparator,
    getPathJoin,
    getParent,
    getStem,
    getFileExtension,
    isFileApp,
    isFileCmd,
    isFileExec,
    isFileImage,
    isFileVector,
    isFileMovie,
    isFileMusic,
    isFileZipped,
    getBit,
    getBool,
    getByte,
    getTiny,
    getSmall,
    getInt32,
    getLong,
    getSerial,
    getBigSerial,
    getFloat32,
    getReal,
    getDouble,
    getNumeric,
    getBigNumeric,
    getChar,
    getChars,
    getDate,
    getTime,
    getDateTime,
    getTimestamp,
    getBytes,
    getBlob,
    getText,
    getValued
};
