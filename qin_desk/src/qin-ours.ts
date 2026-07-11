import { SHA1 } from "crypto-js";
import { QinConstants, QinSoul } from "qin_soul";

const sha1 = (text: string) => SHA1(text).toString();

const crypto = {
    sha1,
};

export const QinOurs = {
    soul: { ...QinSoul },
    consts: QinConstants,
    crypto,
    tr: QinSoul.head.tr,
};
