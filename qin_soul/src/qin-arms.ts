import { QinPoint, QinSkin } from "./qin-skin";

export type QinWaiter<T> = (result: T) => void;

export class QinWaiters<T> {
    private _waiters: QinWaiter<T>[];

    public constructor(initial?: QinWaiter<T>[]) {
        this._waiters = initial ? initial : [];
    }

    public put(waiter: QinWaiter<T>): QinWaiters<T> {
        this._waiters.push(waiter);
        return this;
    }

    public del(waiter: QinWaiter<T>): QinWaiters<T> {
        const index = this._waiters.indexOf(waiter);
        if (index !== -1) {
            this._waiters.splice(index, 1);
        }
        return this;
    }

    public has(waiter: QinWaiter<T>): boolean {
        return this._waiters.indexOf(waiter) > 0;
    }

    public clean(): QinWaiters<T> {
        this._waiters.length = 0;
        return this;
    }

    public send(result: any) {
        for (const waiter of this._waiters) {
            waiter(result);
        }
    }
}

export enum QinActionKind {
    MAIN = "MAIN",
    MIDI = "MIDI",
    MENU = "MENU",
}

export class QinEvent {
    private _origin: HTMLElement;
    private _start: boolean;
    private _eventKey: KeyboardEvent = null;
    private _eventMouse: MouseEvent = null;
    private _eventTouch: TouchEvent = null;
    private _point: QinPoint = null;
    private _stop: boolean = false;

    public constructor(origin: HTMLElement, isStart: boolean, kind: QinEventType) {
        this._origin = origin;
        this._start = isStart;
        this._eventKey = kind?.eventKey ?? null;
        this._eventMouse = kind?.eventMouse ?? null;
        this._eventTouch = kind?.eventTouch ?? null;
        if (this._eventMouse) {
            this._point = getEventMousePoint(isStart, this._eventMouse);
        } else if (this._eventTouch) {
            this._point = getEventTouchPoint(isStart, this._eventTouch);
        }
    }

    public get isStart(): boolean {
        return this._start;
    }

    public get fromOrigin(): any {
        return this._origin;
    }

    public get fromTarget(): any {
        if (this._eventKey) {
            return this._eventKey.target;
        } else if (this._eventMouse) {
            return this._eventMouse.target;
        } else if (this._eventTouch) {
            return this._eventTouch.target;
        }
        return null;
    }

    public get isTyping(): boolean {
        return !!this._eventKey;
    }

    public get isPointing(): boolean {
        return !!(this._eventMouse || this._eventTouch);
    }

    public get hasPointing(): boolean {
        return !!this._point;
    }

    public get hasAlt(): boolean {
        if (this._eventKey) {
            return this._eventKey.altKey;
        } else if (this._eventMouse) {
            return this._eventMouse.altKey;
        } else if (this._eventTouch) {
            return this._eventTouch.altKey;
        }
        return false;
    }

    public get hasCtrl(): boolean {
        if (this._eventKey) {
            return this._eventKey.ctrlKey;
        } else if (this._eventMouse) {
            return this._eventMouse.ctrlKey;
        } else if (this._eventTouch) {
            return this._eventTouch.ctrlKey;
        }
        return false;
    }

    public get hasShift(): boolean {
        if (this._eventKey) {
            return this._eventKey.shiftKey;
        } else if (this._eventMouse) {
            return this._eventMouse.shiftKey;
        } else if (this._eventTouch) {
            return this._eventTouch.shiftKey;
        }
        return false;
    }

    public get hasMeta(): boolean {
        if (this._eventKey) {
            return this._eventKey.metaKey;
        } else if (this._eventMouse) {
            return this._eventMouse.metaKey;
        } else if (this._eventTouch) {
            return this._eventTouch.metaKey;
        }
        return false;
    }

    public get keyPressed(): string {
        if (this._eventKey) {
            return this._eventKey.key.toUpperCase();
        }
        return null;
    }

    public get isEnter(): boolean {
        return isKeyEnter(this._eventKey);
    }

    public get isEscape(): boolean {
        return isKeyEscape(this._eventKey);
    }

    public get isSpace(): boolean {
        return isKeySpace(this._eventKey);
    }

    public get isDouble(): boolean {
        if (this._eventMouse) {
            return isEventMouseDouble(this._start, this._eventMouse);
        } else if (this._eventTouch) {
            return isEventTouchDouble(this._start, this._eventTouch);
        }
        return false;
    }

    public get isLong(): boolean {
        if (this._eventMouse) {
            return isEventMouseLong(this._start, this._eventMouse);
        } else if (this._eventTouch) {
            return isEventTouchLong(this._start, this._eventTouch);
        }
        return false;
    }

    public get point(): QinPoint {
        return this._point;
    }

    public get pointX(): number {
        return this._point?.posX;
    }

    public get pointY(): number {
        return this._point?.posY;
    }

    public get isFirstButton(): boolean {
        return isFirstButton(this._eventMouse);
    }

    public get isMiddleButton(): boolean {
        return isMiddleButton(this._eventMouse);
    }

    public get isSecondButton(): boolean {
        return isSecondButton(this._eventMouse);
    }

    public get isOneFinger(): boolean {
        return isOneFinger(this._eventTouch);
    }

    public get isTwoFingers(): boolean {
        return isTwoFingers(this._eventTouch);
    }

    public get isThreeFingers(): boolean {
        return isThreeFingers(this._eventTouch);
    }

    public get isFourFingers(): boolean {
        return isFourFingers(this._eventTouch);
    }

    public get isMaster(): boolean {
        if (this.isTyping) {
            return this.hasCtrl && this.isEnter;
        } else if (this.isPointing) {
            return this.isFirstButton && this.isDouble;
        } else {
            return false;
        }
    }

    public get isMain(): boolean {
        if (this._eventKey) {
            return isMainKey(this._eventKey);
        } else if (this._eventMouse) {
            return isMainMouse(this._eventMouse);
        } else if (this._eventTouch) {
            return isMainTouch(this._eventTouch);
        }
        return false;
    }

    public get isMainKey(): boolean {
        return isMainKey(this._eventKey);
    }

    public get isMainMouse(): boolean {
        return isMainMouse(this._eventMouse);
    }

    public get isMainTouch(): boolean {
        return isMainTouch(this._eventTouch);
    }

    public get isMainPoint(): boolean {
        return isMainMouse(this._eventMouse) || isMainTouch(this._eventTouch);
    }

    public get isMidi(): boolean {
        if (this._eventKey) {
            return isMidiKey(this._eventKey);
        } else if (this._eventMouse) {
            return isMidiMouse(this._eventMouse);
        } else if (this._eventTouch) {
            return isMidiTouch(this._eventTouch);
        }
        return false;
    }

    public get isMidiKey(): boolean {
        return isMidiKey(this._eventKey);
    }

    public get isMidiMouse(): boolean {
        return isMidiMouse(this._eventMouse);
    }

    public get isMidiTouch(): boolean {
        return isMidiTouch(this._eventTouch);
    }

    public get isMidiPoint(): boolean {
        if (this._eventMouse) {
            return isMidiMouse(this._eventMouse);
        } else if (this._eventTouch) {
            return isMidiTouch(this._eventTouch);
        }
        return false;
    }

    public get isMenu(): boolean {
        if (this._eventKey) {
            return isMenuKey(this._eventKey);
        } else if (this._eventMouse) {
            return isMenuMouse(this._eventMouse);
        } else if (this._eventTouch) {
            return isMenuTouch(this._eventTouch);
        }
        return false;
    }

    public get isMenuKey(): boolean {
        return isMenuKey(this._eventKey);
    }

    public get isMenuMouse(): boolean {
        return isMenuMouse(this._eventMouse);
    }

    public get isMenuTouch(): boolean {
        return isMenuTouch(this._eventTouch);
    }

    public get isMenuPoint(): boolean {
        if (this._eventMouse) {
            return isMenuMouse(this._eventMouse);
        } else if (this._eventTouch) {
            return isMenuTouch(this._eventTouch);
        }
        return false;
    }

    public get stop(): boolean {
        return this._stop;
    }

    public done() {
        this._stop = true;
    }

    public isKeyIn(list: string[]): boolean {
        return isKeyInList(this._eventKey, list);
    }
}

export type QinEventType = {
    eventKey?: KeyboardEvent;
    eventMouse?: MouseEvent;
    eventTouch?: TouchEvent;
};

export type QinAction = (event: QinEvent) => void;

type QinPointerCaller = (event?: MouseEvent | TouchEvent) => void | boolean;

export class QinPointerCalls {
    onDouble?: QinPointerCaller;
    onLong?: QinPointerCaller;
    onStart?: QinPointerCaller;
    onMove?: QinPointerCaller;
    onEnd?: QinPointerCaller;
}

function stopEvent(event: any) {
    if (event.preventDefault) {
        event.preventDefault();
    }
    return stopPropagation(event);
}

function stopPropagation(event: any) {
    if (event.stopPropagation) {
        event.stopPropagation();
    }
    event.cancelBubble = true;
    return false;
}

let lastEventMouse: MouseEvent = null;
let lastEventTouch: TouchEvent = null;
let actualEventMouse: MouseEvent = null;
let actualEventTouch: TouchEvent = null;

function getEventPointerPoint(isStart: boolean, ev: MouseEvent | TouchEvent): QinPoint {
    if (ev instanceof MouseEvent) {
        return getEventMousePoint(isStart, ev);
    } else if (ev instanceof TouchEvent) {
        return getEventTouchPoint(isStart, ev);
    } else {
        return null;
    }
}

function getEventMousePoint(isStart: boolean, ev: MouseEvent): QinPoint {
    if (!ev) {
        return null;
    }
    const result = {
        posX: 0,
        posY: 0,
    };
    if (ev.clientX || ev.clientY) {
        result.posX = ev.clientX;
        result.posY = ev.clientY;
    }
    if (!isStart) {
        lastEventMouse = actualEventMouse;
        actualEventMouse = ev;
    }
    return result;
}

function getEventTouchPoint(isStart: boolean, ev: TouchEvent): QinPoint {
    if (!ev) {
        return null;
    }
    const result = {
        posX: 0,
        posY: 0,
    };
    if (ev.changedTouches && ev.changedTouches.length >= 1) {
        let index = Math.floor(ev.changedTouches.length / 2);
        result.posX = ev.changedTouches[index].clientX;
        result.posY = ev.changedTouches[index].clientY;
    }
    if (!isStart) {
        lastEventTouch = actualEventTouch;
        actualEventTouch = ev;
    }
    return result;
}

function isEventPointerDouble(isStart: boolean, ev: MouseEvent | TouchEvent): boolean {
    if (ev instanceof MouseEvent) {
        return isEventMouseDouble(isStart, ev);
    } else if (ev instanceof TouchEvent) {
        return isEventTouchDouble(isStart, ev);
    } else {
        return false;
    }
}

function isEventMouseDouble(isStart: boolean, ev: MouseEvent): boolean {
    if (isStart || lastEventMouse == null || ev == null) {
        return false;
    }
    const timeDif = ev.timeStamp - lastEventMouse.timeStamp;
    return timeDif < 450;
}

function isEventTouchDouble(isStart: boolean, ev: TouchEvent): boolean {
    if (isStart || lastEventTouch == null || ev == null) {
        return false;
    }
    const timeDif = ev.timeStamp - lastEventTouch.timeStamp;
    return timeDif < 450;
}

function isEventPointerLong(isStart: boolean, ev: MouseEvent | TouchEvent): boolean {
    if (ev instanceof MouseEvent) {
        return isEventMouseLong(isStart, ev);
    } else if (ev instanceof TouchEvent) {
        return isEventTouchLong(isStart, ev);
    } else {
        return false;
    }    
}

function isEventMouseLong(isStart: boolean, ev: MouseEvent): boolean {
    if (isStart || lastEventMouse == null || ev == null) {
        return false;
    }
    const timeDif = ev.timeStamp - lastEventMouse.timeStamp;
    return timeDif > 840;
}

function isEventTouchLong(isStart: boolean, ev: TouchEvent): boolean {
    if (isStart || lastEventTouch == null || ev == null) {
        return false;
    }
    const timeDif = ev.timeStamp - lastEventTouch.timeStamp;
    return timeDif > 840;
}

function isKeyInList(ev: KeyboardEvent, list: string[]): boolean {
    if (!ev) {
        return false;
    }
    let keyUpper = ev.key.toUpperCase();
    return list.indexOf(keyUpper) > -1;
}

function isKeyEnter(ev: KeyboardEvent): boolean {
    if (!ev) {
        return false;
    }
    return isKeyInList(ev, ["ENTER", "RETURN"]) || ev.keyCode === 13;
}

function isKeyEscape(ev: KeyboardEvent): boolean {
    if (!ev) {
        return false;
    }
    return isKeyInList(ev, ["ESC", "ESCAPE"]) || ev.keyCode === 27;
}

function isKeySpace(ev: KeyboardEvent): boolean {
    if (!ev) {
        return false;
    }
    return isKeyInList(ev, [" ", "SPACE", "SPACEBAR"]) || ev.keyCode === 32;
}

function isFirstButton(ev: MouseEvent): boolean {
    if (!ev) {
        return false;
    }
    return ev?.button == 0;
}

function isMiddleButton(ev: MouseEvent): boolean {
    if (!ev) {
        return false;
    }
    return ev?.button == 1;
}

function isSecondButton(ev: MouseEvent): boolean {
    if (!ev) {
        return false;
    }
    return ev?.button == 2;
}

function isOneFinger(ev: TouchEvent): boolean {
    if (!ev) {
        return false;
    }
    return ev?.changedTouches.length == 1;
}

function isTwoFingers(ev: TouchEvent): boolean {
    if (!ev) {
        return false;
    }
    return ev?.changedTouches.length == 2;
}

function isThreeFingers(ev: TouchEvent): boolean {
    if (!ev) {
        return false;
    }
    return ev?.changedTouches.length == 3;
}

function isFourFingers(ev: TouchEvent): boolean {
    if (!ev) {
        return false;
    }
    return ev?.changedTouches.length == 4;
}

function isMainKey(ev: KeyboardEvent): boolean {
    if (!ev) {
        return false;
    }
    return isKeyEnter(ev);
}

function isMidiKey(ev: KeyboardEvent): boolean {
    if (!ev) {
        return false;
    }
    return ev.ctrlKey && ev.altKey && isKeySpace(ev);
}

function isMenuKey(ev: KeyboardEvent): boolean {
    if (!ev) {
        return false;
    }
    return ev.ctrlKey && !ev.altKey && isKeySpace(ev);
}

function isMainMouse(ev: MouseEvent): boolean {
    if (!ev) {
        return false;
    }
    return isFirstButton(ev);
}

function isMainTouch(ev: TouchEvent): boolean {
    if (!ev) {
        return false;
    }
    return isOneFinger(ev);
}

function isMidiMouse(ev: MouseEvent): boolean {
    if (!ev) {
        return false;
    }
    return isMiddleButton(ev);
}

function isMidiTouch(ev: TouchEvent): boolean {
    if (!ev) {
        return false;
    }
    return isThreeFingers(ev);
}

function isMenuMouse(ev: MouseEvent): boolean {
    if (!ev) {
        return false;
    }
    return isSecondButton(ev);
}

function isMenuTouch(ev: TouchEvent): boolean {
    if (!ev) {
        return false;
    }
    return isTwoFingers(ev);
}

function addAction(origin: HTMLElement, action: QinAction) {
    origin.addEventListener("keydown", actKeyDown);
    origin.addEventListener("keyup", actKeyUp);
    origin.addEventListener("mousedown", actMouseDown);
    origin.addEventListener("mouseup", actMouseUp);
    origin.addEventListener("touchstart", actTouchStart);
    origin.addEventListener("touchend", actTouchEnd);

    function actKeyDown(ev: KeyboardEvent) {
        let qinEvent = new QinEvent(origin, true, { eventKey: ev });
        action(qinEvent);
        if (qinEvent.stop) {
            return stopEvent(ev);
        }
    }

    function actKeyUp(ev: KeyboardEvent) {
        let qinEvent = new QinEvent(origin, false, { eventKey: ev });
        action(qinEvent);
        if (qinEvent.stop) {
            return stopEvent(ev);
        }
    }

    function actMouseDown(ev: MouseEvent) {
        let qinEvent = new QinEvent(origin, true, { eventMouse: ev });
        action(qinEvent);
        if (qinEvent.stop) {
            return stopEvent(ev);
        }
    }

    function actMouseUp(ev: MouseEvent) {
        let qinEvent = new QinEvent(origin, false, { eventMouse: ev });
        action(qinEvent);
        if (qinEvent.stop) {
            return stopEvent(ev);
        }
    }

    function actTouchStart(ev: TouchEvent) {
        let qinEvent = new QinEvent(origin, true, { eventTouch: ev });
        action(qinEvent);
        if (qinEvent.stop) {
            return stopEvent(ev);
        }
    }

    function actTouchEnd(ev: TouchEvent) {
        let qinEvent = new QinEvent(origin, false, { eventTouch: ev });
        action(qinEvent);
        if (qinEvent.stop) {
            return stopEvent(ev);
        }
    }
}

function addActionMain(origin: HTMLElement, action: QinAction) {
    addAction(origin, (qinEvent: QinEvent) => {
        if (qinEvent.isStart && qinEvent.isMain) {
            qinEvent.done();
        }
        if (!qinEvent.isStart && qinEvent.isMain) {
            action(qinEvent);
            qinEvent.done();
        }
    });
}

function addActionMainKey(origin: HTMLElement, action: QinAction) {
    addAction(origin, (qinEvent: QinEvent) => {
        if (qinEvent.isStart && qinEvent.isMainKey) {
            qinEvent.done();
        }
        if (!qinEvent.isStart && qinEvent.isMainKey) {
            action(qinEvent);
            qinEvent.done();
        }
    });
}

function addActionMainMouse(origin: HTMLElement, action: QinAction) {
    addAction(origin, (qinEvent: QinEvent) => {
        if (qinEvent.isStart && qinEvent.isMainMouse) {
            qinEvent.done();
        }
        if (!qinEvent.isStart && qinEvent.isMainMouse) {
            action(qinEvent);
            qinEvent.done();
        }
    });
}

function addActionMainTouch(origin: HTMLElement, action: QinAction) {
    addAction(origin, (qinEvent: QinEvent) => {
        if (qinEvent.isStart && qinEvent.isMainTouch) {
            qinEvent.done();
        }
        if (!qinEvent.isStart && qinEvent.isMainTouch) {
            action(qinEvent);
            qinEvent.done();
        }
    });
}

function addActionMainPoint(origin: HTMLElement, action: QinAction) {
    addAction(origin, (qinEvent: QinEvent) => {
        if (qinEvent.isStart && qinEvent.isMainPoint) {
            qinEvent.done();
        }
        if (!qinEvent.isStart && qinEvent.isMainPoint) {
            action(qinEvent);
            qinEvent.done();
        }
    });
}

function addActionMidi(origin: HTMLElement, action: QinAction) {
    addAction(origin, (qinEvent: QinEvent) => {
        if (qinEvent.isStart && qinEvent.isMidi) {
            qinEvent.done();
        }
        if (!qinEvent.isStart && qinEvent.isMidi) {
            action(qinEvent);
            qinEvent.done();
        }
    });
}

function addActionMidiKey(origin: HTMLElement, action: QinAction) {
    addAction(origin, (qinEvent: QinEvent) => {
        if (qinEvent.isStart && qinEvent.isMidiKey) {
            qinEvent.done();
        }
        if (!qinEvent.isStart && qinEvent.isMidiKey) {
            action(qinEvent);
            qinEvent.done();
        }
    });
}

function addActionMidiMouse(origin: HTMLElement, action: QinAction) {
    addAction(origin, (qinEvent: QinEvent) => {
        if (qinEvent.isStart && qinEvent.isMidiMouse) {
            qinEvent.done();
        }
        if (!qinEvent.isStart && qinEvent.isMidiMouse) {
            action(qinEvent);
            qinEvent.done();
        }
    });
}

function addActionMidiTouch(origin: HTMLElement, action: QinAction) {
    addAction(origin, (qinEvent: QinEvent) => {
        if (qinEvent.isStart && qinEvent.isMidiTouch) {
            qinEvent.done();
        }
        if (!qinEvent.isStart && qinEvent.isMidiTouch) {
            action(qinEvent);
            qinEvent.done();
        }
    });
}

function addActionMidiPoint(origin: HTMLElement, action: QinAction) {
    addAction(origin, (qinEvent: QinEvent) => {
        if (qinEvent.isStart && qinEvent.isMidiPoint) {
            qinEvent.done();
        }
        if (!qinEvent.isStart && qinEvent.isMidiPoint) {
            action(qinEvent);
            qinEvent.done();
        }
    });
}

function addActionMenu(origin: HTMLElement, action: QinAction) {
    addAction(origin, (qinEvent: QinEvent) => {
        if (qinEvent.isStart && qinEvent.isMenu) {
            qinEvent.done();
        }
        if (!qinEvent.isStart && qinEvent.isMenu) {
            action(qinEvent);
            qinEvent.done();
        }
    });
}

function addActionMenuKey(origin: HTMLElement, action: QinAction) {
    addAction(origin, (qinEvent: QinEvent) => {
        if (qinEvent.isStart && qinEvent.isMenuKey) {
            qinEvent.done();
        }
        if (!qinEvent.isStart && qinEvent.isMenuKey) {
            action(qinEvent);
            qinEvent.done();
        }
    });
}

function addActionMenuMouse(origin: HTMLElement, action: QinAction) {
    addAction(origin, (qinEvent: QinEvent) => {
        if (qinEvent.isStart && qinEvent.isMenuMouse) {
            qinEvent.done();
        }
        if (!qinEvent.isStart && qinEvent.isMenuMouse) {
            action(qinEvent);
            qinEvent.done();
        }
    });
}

function addActionMenuTouch(origin: HTMLElement, action: QinAction) {
    addAction(origin, (qinEvent: QinEvent) => {
        if (qinEvent.isStart && qinEvent.isMenuTouch) {
            qinEvent.done();
        }
        if (!qinEvent.isStart && qinEvent.isMenuTouch) {
            action(qinEvent);
            qinEvent.done();
        }
    });
}

function addActionMenuPoint(origin: HTMLElement, action: QinAction) {
    addAction(origin, (qinEvent: QinEvent) => {
        if (qinEvent.isStart && qinEvent.isMenuPoint) {
            qinEvent.done();
        }
        if (!qinEvent.isStart && qinEvent.isMenuPoint) {
            action(qinEvent);
            qinEvent.done();
        }
    });
}

function addActionKey(keyList: string[], origin: HTMLElement, action: QinAction) {
    addAction(origin, (qinEvent: QinEvent) => {
        if (qinEvent.isStart && qinEvent.isKeyIn(keyList)) {
            qinEvent.done();
        }
        if (!qinEvent.isStart && qinEvent.isKeyIn(keyList)) {
            action(qinEvent);
            qinEvent.done();
        }
    });
}

function addActions(origins: HTMLElement[], action: QinAction) {
    for (const element of origins) {
        addAction(element, action);
    }
}

function addActionsMain(origins: HTMLElement[], action: QinAction) {
    for (const element of origins) {
        addActionMain(element, action);
    }
}

function addActionsMainKey(origins: HTMLElement[], action: QinAction) {
    for (const element of origins) {
        addActionMainKey(element, action);
    }
}

function addActionsMainMouse(origins: HTMLElement[], action: QinAction) {
    for (const element of origins) {
        addActionMainMouse(element, action);
    }
}

function addActionsMainTouch(origins: HTMLElement[], action: QinAction) {
    for (const element of origins) {
        addActionMainTouch(element, action);
    }
}

function addActionsMainPoint(origins: HTMLElement[], action: QinAction) {
    for (const element of origins) {
        addActionMainPoint(element, action);
    }
}

function addActionsMidi(origins: HTMLElement[], action: QinAction) {
    for (const element of origins) {
        addActionMidi(element, action);
    }
}

function addActionsMidiKey(origins: HTMLElement[], action: QinAction) {
    for (const element of origins) {
        addActionMidiKey(element, action);
    }
}

function addActionsMidiMouse(origins: HTMLElement[], action: QinAction) {
    for (const element of origins) {
        addActionMidiMouse(element, action);
    }
}

function addActionsMidiTouch(origins: HTMLElement[], action: QinAction) {
    for (const element of origins) {
        addActionMidiTouch(element, action);
    }
}

function addActionsMidiPoint(origins: HTMLElement[], action: QinAction) {
    for (const element of origins) {
        addActionMidiPoint(element, action);
    }
}

function addActionsMenu(origins: HTMLElement[], action: QinAction) {
    for (const element of origins) {
        addActionMenu(element, action);
    }
}

function addActionsMenuKey(origins: HTMLElement[], action: QinAction) {
    for (const element of origins) {
        addActionMenuKey(element, action);
    }
}

function addActionsMenuMouse(origins: HTMLElement[], action: QinAction) {
    for (const element of origins) {
        addActionMenuMouse(element, action);
    }
}

function addActionsMenuTouch(origins: HTMLElement[], action: QinAction) {
    for (const element of origins) {
        addActionMenuTouch(element, action);
    }
}

function addActionsMenuPoint(origins: HTMLElement[], action: QinAction) {
    for (const element of origins) {
        addActionMenuPoint(element, action);
    }
}

function addActionsKey(keyList: string[], origins: HTMLElement[], action: QinAction) {
    for (const element of origins) {
        addActionKey(keyList, element, action);
    }
}

function addMover(sources: HTMLElement[], target: HTMLElement, pointerCalls?: QinPointerCalls) {
    let dragInitEventX = 0;
    let dragInitEventY = 0;
    let dragInitPosX = 0;
    let dragInitPosY = 0;

    for (let source of sources) {
        source.onmousedown = onMoverPointerInit;
        source.ontouchstart = onMoverPointerInit;
        source.ondragstart = stopEvent;
    }

    function onMoverPointerInit(ev: MouseEvent | TouchEvent) {
        if (document.onmousemove || document.ontouchmove) {
            return;
        }
        if (pointerCalls && pointerCalls.onDouble && isEventPointerDouble(true, ev)) {
            pointerCalls.onDouble(ev);
        }
        if (pointerCalls && pointerCalls.onLong && isEventPointerLong(true, ev)) {
            pointerCalls.onLong(ev);
        }
        const pointer = getEventPointerPoint(true, ev);
        dragInitEventX = pointer.posX;
        dragInitEventY = pointer.posY;
        dragInitPosX = parseInt(target.style.left, 10);
        dragInitPosY = parseInt(target.style.top, 10);
        document.onmousemove = onMoverPointerMove;
        document.ontouchmove = onMoverPointerMove;
        document.onmouseup = onMoverClose;
        document.ontouchend = onMoverClose;
        QinSkin.hideAllIFrames();
        if (pointerCalls && pointerCalls.onStart) {
            pointerCalls.onStart(ev);
        }
        return stopEvent(ev);
    }

    function onMoverPointerMove(ev: MouseEvent | TouchEvent) {
        const pointer = getEventPointerPoint(false, ev);
        let dragDifX = pointer.posX - dragInitEventX;
        let dragDifY = pointer.posY - dragInitEventY;
        let dragFinalX = dragInitPosX + dragDifX;
        let dragFinalY = dragInitPosY + dragDifY;
        if (dragFinalX < 0) {
            dragFinalX = 0;
        }
        if (dragFinalY < 0) {
            dragFinalY = 0;
        }
        target.style.left = dragFinalX + "px";
        target.style.top = dragFinalY + "px";
        if (pointerCalls && pointerCalls.onMove) {
            pointerCalls.onMove(ev);
        }
        return stopEvent(ev);
    }

    function onMoverClose(ev: MouseEvent | TouchEvent) {
        return onTransformCloser(ev, pointerCalls);
    }
}

function addResizer(sources: HTMLElement[], target: HTMLElement, pointerCalls?: QinPointerCalls) {
    let dragInitEventX = 0;
    let dragInitEventY = 0;
    let dragInitWidth = 0;
    let dragInitHeight = 0;
    
    for (let source of sources) {
        source.onmousedown = onResizerPointerInit;
        source.ontouchstart = onResizerPointerInit;
        source.ondragstart = stopEvent;
    }

    function onResizerPointerInit(ev: MouseEvent | TouchEvent) {
        if (document.onmousemove || document.ontouchmove) {
            return;
        }
        if (pointerCalls && pointerCalls.onDouble && isEventPointerDouble(true, ev)) {
            pointerCalls.onDouble(ev);
        }
        if (pointerCalls && pointerCalls.onLong && isEventPointerLong(true, ev)) {
            pointerCalls.onLong(ev);
        }
        const pointer = getEventPointerPoint(true, ev);
        dragInitEventX = pointer.posX;
        dragInitEventY = pointer.posY;
        dragInitWidth = parseInt(target.style.width, 10);
        dragInitHeight = parseInt(target.style.height, 10);
        document.onmousemove = onResizerPointerMove;
        document.ontouchmove = onResizerPointerMove;
        document.onmouseup = onResizerClose;
        document.ontouchend = onResizerClose;
        QinSkin.hideAllIFrames();
        if (pointerCalls && pointerCalls.onStart) {
            pointerCalls.onStart(ev);
        }
        return stopEvent(ev);
    }

    function onResizerPointerMove(ev: MouseEvent | TouchEvent) {
        const pointer = getEventPointerPoint(false, ev);
        let frameDragDifX = pointer.posX - dragInitEventX;
        let frameDragDifY = pointer.posY - dragInitEventY;
        let frameDragFinalWidth = dragInitWidth + frameDragDifX;
        let frameDragFinalHeight = dragInitHeight + frameDragDifY;
        if (frameDragFinalWidth < 10) {
            frameDragFinalWidth = 10;
        }
        if (frameDragFinalHeight < 10) {
            frameDragFinalHeight = 10;
        }
        QinSkin.styleAsSize(target, {width: frameDragFinalWidth, height: frameDragFinalHeight});
        if (pointerCalls && pointerCalls.onMove) {
            pointerCalls.onMove(ev);
        }
        return stopEvent(ev);
    }

    function onResizerClose(ev: MouseEvent | TouchEvent) {
        return onTransformCloser(ev, pointerCalls);
    }
}

function addScroller(target: HTMLElement, pointerCalls?: QinPointerCalls) {
    let dragInitX = 0;
    let dragInitY = 0;
    let dragScrollX = 0;
    let dragScrollY = 0;

    target.ondragstart = stopEvent;
    target.onmousedown = onScrollerPointerInit;
    target.ontouchstart = onScrollerPointerInit;

    function onScrollerPointerInit(ev: MouseEvent | TouchEvent) {
        if (document.onmousemove || document.ontouchmove) {
            return;
        }
        if (pointerCalls && pointerCalls.onDouble && isEventPointerDouble(true, ev)) {
            pointerCalls.onDouble(ev);
            return;
        }
        if (pointerCalls && pointerCalls.onLong && isEventPointerLong(true, ev)) {
            pointerCalls.onLong(ev);
            return;
        }
        const pointer = getEventPointerPoint(true, ev);
        dragInitX = pointer.posX;
        dragInitY = pointer.posY;
        dragScrollX = target.scrollLeft;
        dragScrollY = target.scrollTop;
        document.onmousemove = onScrollerPointerMove;
        document.ontouchmove = onScrollerPointerMove;
        document.ontouchend = onScrollerClose;
        document.onmouseup = onScrollerClose;
        QinSkin.hideAllIFrames();
        if (pointerCalls && pointerCalls.onStart) {
            pointerCalls.onStart();
        }
        return stopEvent(ev);
    }

    function onScrollerPointerMove(ev: MouseEvent | TouchEvent) {
        const pointer = getEventPointerPoint(false, ev);
        let dragDifX = pointer.posX - dragInitX;
        let dragDifY = pointer.posY - dragInitY;
        let dragNewX = dragScrollX - dragDifX;
        let dragNewY = dragScrollY - dragDifY;
        target.scrollTo(dragNewX, dragNewY);
        if (pointerCalls && pointerCalls.onMove) {
            pointerCalls.onMove(ev);
        }
        return stopEvent(ev);
    }

    function onScrollerClose(ev: MouseEvent | TouchEvent) {
        return onTransformCloser(ev, pointerCalls);
    }
}

function onTransformCloser(ev: MouseEvent | TouchEvent, pointerCalls?: QinPointerCalls) {
    document.ontouchmove = null;
    document.onmousemove = null;
    document.ontouchend = null;
    document.onmouseup = null;
    QinSkin.showAllIFrames();
    QinSkin.clearSelection();
    if (pointerCalls && pointerCalls.onDouble && isEventPointerDouble(false, ev)) {
        pointerCalls.onDouble(ev);
    }
    if (pointerCalls && pointerCalls.onLong && isEventPointerLong(false, ev)) {
        pointerCalls.onLong(ev);
    }
    if (pointerCalls && pointerCalls.onEnd) {
        pointerCalls.onEnd(ev);
    }
    return stopEvent(ev);
}

function putOnAllTabStop(el: HTMLElement, actIn: (this: HTMLElement, ev: FocusEvent) => any, actOut: (this: HTMLElement, ev: FocusEvent) => any) {
    if (el.tabIndex != null && el.tabIndex != undefined) {
        if (actIn) {
            el.addEventListener("focusin", actIn);
        }
        if (actOut) {
            el.addEventListener("focusout", actOut);
        }
    }
    for (const child of el.children) {
        if (child instanceof HTMLElement) {
            putOnAllTabStop(child, actIn, actOut);
        }
    }
}

export const QinArms = {
    stopEvent,
    getEventPointerPoint,
    getEventMousePoint,
    getEventTouchPoint,
    isEventPointerDouble,
    isEventMouseDouble,
    isEventTouchDouble,
    isEventPointerLong,
    isEventMouseLong,
    isEventTouchLong,
    isKeyInList,
    isKeyEnter,
    isKeyEscape,
    isKeySpace,
    isFirstButton,
    isMiddleButton,
    isSecondButton,
    isOneFinger,
    isTwoFingers,
    isThreeFingers,
    isFourFingers,
    isMainMouse,
    isMainTouch,
    isMidiMouse,
    isMidiTouch,
    isMenuMouse,
    isMenuTouch,
    addAction,
    addActionMain,
    addActionMainKey,
    addActionMainMouse,
    addActionMainTouch,
    addActionMainPoint,
    addActionMidi,
    addActionMidiKey,
    addActionMidiMouse,
    addActionMidiTouch,
    addActionMidiPoint,
    addActionMenu,
    addActionMenuKey,
    addActionMenuMouse,
    addActionMenuTouch,
    addActionMenuPoint,
    addActionKey,
    addActions,
    addActionsMain,
    addActionsMainKey,
    addActionsMainMouse,
    addActionsMainTouch,
    addActionsMainPoint,
    addActionsMidi,
    addActionsMidiKey,
    addActionsMidiMouse,
    addActionsMidiTouch,
    addActionsMidiPoint,
    addActionsMenu,
    addActionsMenuKey,
    addActionsMenuMouse,
    addActionsMenuTouch,
    addActionsMenuPoint,
    addActionsKey,
    addMover,
    addResizer,
    addScroller,
    putOnAllTabStop,
};
