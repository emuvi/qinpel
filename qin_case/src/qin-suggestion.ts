import { QinBody } from "qin_soul";
import { QinChars } from "./qin-chars";

export class QinSuggestion extends QinChars {
    private _dataList = document.createElement("datalist");

    public constructor(options?: QinSuggestionSet, isQindred?: string) {
        super(options, (isQindred ? isQindred + "_" : "") + "suggestion");
        this.castedQine().appendChild(this._dataList);
        this._dataList.id = QinBody.makeQinUID();
        this.castedQine().setAttribute("list", this._dataList.id);
        if (options?.items) {
            for (const item of options.items) {
                const option = document.createElement("option");
                option.value = item;
                this._dataList.appendChild(option);
            }
        }
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): QinSuggestion {
        super.styled(styles);
        return this;
    }
}

export type QinSuggestionSet = {
    initial?: string;
    maxLength?: number;
    readOnly?: boolean;
    items?: string[];
};
