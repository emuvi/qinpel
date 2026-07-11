import { QinStyles, QinWaiter, QinWaiters } from "qin_soul";
import { QinBase } from "./qin-base";

export class QinSplitter extends QinBase {    
    
    private readonly _elSideA = document.createElement("div");
    private readonly _elMover = document.createElement("div");
    private readonly _elSideB = document.createElement("div");

    private _isHorizontal = true;

    private _qinSideA: QinBase | null = null;
    private _qinSideB: QinBase | null = null;

    private readonly _changedWaiters = new QinWaiters<QinSplitterBalance>();

    private _dragStartPos: number = 0;
    private _dragStartSizeA: number = 0;
    private readonly _dragHandler = this.onDrag.bind(this);
    private readonly _dragEndHandler = this.stopDrag.bind(this);

    public constructor(options?: QinSplitterSet, isQindred?: string) {
        super((isQindred ? isQindred + "_" : "") + "splitter", document.createElement("div"));
        this.qinedHTML.appendChild(this._elSideA);
        this.qinedHTML.appendChild(this._elMover);
        this.qinedHTML.appendChild(this._elSideB);
        
        this.qinedHTML.style.display = "flex";
        this.qinedHTML.style.width = "100%";
        this.qinedHTML.style.height = "100%";
        this.qinedHTML.style.overflow = "hidden";

        this._elSideA.style.flex = "1 1 50%";
        this._elSideA.style.overflow = "auto";
        this._elSideA.style.position = "relative";
        this._elSideA.style.minWidth = "0";
        this._elSideA.style.minHeight = "0";

        this._elSideB.style.flex = "1 1 50%";
        this._elSideB.style.overflow = "auto";
        this._elSideB.style.position = "relative";
        this._elSideB.style.minWidth = "0";
        this._elSideB.style.minHeight = "0";

        this._elMover.style.flex = "0 0 6px";
        this._elMover.style.background = QinStyles.ColorResize;
        this._elMover.style.userSelect = "none";
        this._elMover.style.zIndex = "10";

        this._elMover.addEventListener("mousedown", (e) => this.startDrag(e));
        this._elMover.addEventListener("touchstart", (e) => this.startDrag(e));

        if (options) {
            if (options.sideA) {
                this.setSideA(options.sideA);
            }
            if (options.sideB) {
                this.setSideB(options.sideB);
            }
        }
        if (options?.horizontal) {
            this.setHorizontal();
        } else {
            this.setVertical();
        }
    }

    public override castedQine(): HTMLDivElement {
        return this.qinedHTML as HTMLDivElement;
    }

    public setHorizontal() {
        this._isHorizontal = true;
        this.qinedHTML.style.flexDirection = "row";
        
        this._elMover.style.cursor = "col-resize";
        this._elMover.style.width = "6px";
        this._elMover.style.height = "100%";
        
        this._elSideA.style.width = "50%";
        this._elSideA.style.height = "100%";
        this._elSideB.style.width = "50%";
        this._elSideB.style.height = "100%";
    }

    public setVertical() {
        this._isHorizontal = false;
        this.qinedHTML.style.flexDirection = "column";
        
        this._elMover.style.cursor = "row-resize";
        this._elMover.style.width = "100%";
        this._elMover.style.height = "6px";

        this._elSideA.style.width = "100%";
        this._elSideA.style.height = "50%";
        this._elSideB.style.width = "100%";
        this._elSideB.style.height = "50%";
    }

    public setSideA(side: QinBase) {
        if (this._qinSideA !== null) {
            this._qinSideA.unInstall();
            this._qinSideA = null;
        }
        this._qinSideA = side;
        this._elSideA.appendChild(side.qinedHTML);
    }

    public setSideB(side: QinBase) {
        if (this._qinSideB !== null) {
            this._qinSideB.unInstall();
            this._qinSideB = null;
        }
        this._qinSideB = side;
        this._elSideB.appendChild(side.qinedHTML);
    }

    public addOnChanged(waiter: QinWaiter<QinSplitterBalance>) {
        this._changedWaiters.put(waiter);
    }

    public setBalance(balance: QinSplitterBalance) {
        if (this._isHorizontal) {
            this._elSideA.style.flex = `0 0 ${balance.sideA}%`;
            this._elSideA.style.width = `${balance.sideA}%`;
            this._elSideB.style.flex = `1 1 auto`;
        } else {
            this._elSideA.style.flex = `0 0 ${balance.sideA}%`;
            this._elSideA.style.height = `${balance.sideA}%`;
            this._elSideB.style.flex = `1 1 auto`;
        }
    }

    private startDrag(e: MouseEvent | TouchEvent) {
        e.preventDefault();
        const clientPos = this.getClientPos(e);
        this._dragStartPos = this._isHorizontal ? clientPos.x : clientPos.y;
        
        const rectA = this._elSideA.getBoundingClientRect();
        this._dragStartSizeA = this._isHorizontal ? rectA.width : rectA.height;

        document.addEventListener("mousemove", this._dragHandler);
        document.addEventListener("mouseup", this._dragEndHandler);
        document.addEventListener("touchmove", this._dragHandler);
        document.addEventListener("touchend", this._dragEndHandler);
    }

    private onDrag(e: MouseEvent | TouchEvent) {
        const clientPos = this.getClientPos(e);
        const currentPos = this._isHorizontal ? clientPos.x : clientPos.y;
        const delta = currentPos - this._dragStartPos;
        
        const containerRect = this.qinedHTML.getBoundingClientRect();
        const totalSize = this._isHorizontal ? containerRect.width : containerRect.height;
        
        let newSizeA = this._dragStartSizeA + delta;
        let percentA = (newSizeA / totalSize) * 100;
        
        if (percentA < 5) percentA = 5;
        if (percentA > 95) percentA = 95;

        if (this._isHorizontal) {
            this._elSideA.style.flex = `0 0 ${percentA}%`;
            this._elSideA.style.width = `${percentA}%`;
        } else {
            this._elSideA.style.flex = `0 0 ${percentA}%`;
            this._elSideA.style.height = `${percentA}%`;
        }
    }

    private stopDrag() {
        document.removeEventListener("mousemove", this._dragHandler);
        document.removeEventListener("mouseup", this._dragEndHandler);
        document.removeEventListener("touchmove", this._dragHandler);
        document.removeEventListener("touchend", this._dragEndHandler);

        const rectA = this._elSideA.getBoundingClientRect();
        const rectContainer = this.qinedHTML.getBoundingClientRect();
        const total = this._isHorizontal ? rectContainer.width : rectContainer.height;
        const sizeA = this._isHorizontal ? rectA.width : rectA.height;
        const percentA = (sizeA / total) * 100;
        
        this._changedWaiters.send({ sideA: percentA, sideB: 100 - percentA });
    }

    private getClientPos(e: MouseEvent | TouchEvent) {
        if ((e as TouchEvent).touches && (e as TouchEvent).touches.length > 0) {
            return { x: (e as TouchEvent).touches[0].clientX, y: (e as TouchEvent).touches[0].clientY };
        }
        return { x: (e as MouseEvent).clientX, y: (e as MouseEvent).clientY };
    }

    public override addChild(child: QinBase): this {
        if (this._qinSideA === null) {
            this._qinSideA = child;
            this._elSideA.appendChild(child.qinedHTML);
        } else {
            if (this._qinSideB !== null) {
                this._qinSideB.unInstall();
                this._qinSideB = null;
            }
            this._qinSideB = child;
            this._elSideB.appendChild(child.qinedHTML);
        }
        this._baseChildren.push(child);
        return this;
    }

    public override delChild(child: QinBase): this {
        let index = this._baseChildren.indexOf(child);
        if (index > -1) {
            this._baseChildren.splice(index, 1);
        }
        child.qinedHTML.remove();
        if (this._qinSideA === child) {
            this._qinSideA = null;
        } else if (this._qinSideB === child) {
            this._qinSideB = null;
        }
        return this;
    }

    public override styled(styles: Partial<CSSStyleDeclaration>): this {
        super.styled(styles);
        return this;
    }

}

export type QinSplitterSet = {
    sideA?: QinBase;
    sideB?: QinBase;
    horizontal?: boolean;
};

export type QinSplitterBalance = {
    sideA: number;
    sideB: number;
};
