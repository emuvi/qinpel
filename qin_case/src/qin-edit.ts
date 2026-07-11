import { Nature, QinArms, QinFoot, QinWaiter, QinWaiters } from "qin_soul";
import { QinBase } from "./qin-base";

export abstract class QinEdit<T> extends QinBase {
    
    private readonly _specs;
    
    public constructor(specs: any, isQindred: string, qined: HTMLElement | QinBase) {
        super((isQindred ? isQindred + "_" : "") + "edit", qined);
        this._specs = specs;
        this.styleAsEditable();
        this.qinedHTML.addEventListener("load", () => this.prepareEdit());
    }

    public abstract getNature(): Nature;
    public abstract mayChange(): HTMLElement[];
    public abstract turnReadOnly(): void;
    public abstract turnEditable(): void;
    public abstract isEditable(): boolean;

    protected abstract _getData(): T | null;
    protected abstract _setData(data: T | null): void;

    public get specs(): any {
        return this._specs;
    }

    public get value(): T | null {
        return this._getData();
    }

    public set value(data: T | null) {
        this._setData(data);
        this._changedWaiters.send(data);
    }

    public get valued(): any {
        return QinFoot.getValued(this.getNature(), this.value, this.specs);
    }

    private readonly _enteredWaiters = new QinWaiters<T>();
    private readonly _changedWaiters = new QinWaiters<T>();
    private readonly _exitedWaiters = new QinWaiters<T>();

    public addOnEntered(waiter: QinWaiter<T>) {
        this._enteredWaiters.put(waiter);
    }

    public addOnChanged(waiter: QinWaiter<T>) {
        this._changedWaiters.put(waiter);
    }

    public addOnExited(waiter: QinWaiter<T>) {
        this._exitedWaiters.put(waiter);
    }

    private prepareEdit() {
        for (let element of this.mayChange()) {
            element.addEventListener("change", () => {
                this._changedWaiters.send(this._getData());
            });
        }
        QinArms.putOnAllTabStop(this.qinedHTML,
            () => {
                this._enteredWaiters.send(this._getData());
            },
            () => {
                this._exitedWaiters.send(this._getData());
            }
        );
    }
}
