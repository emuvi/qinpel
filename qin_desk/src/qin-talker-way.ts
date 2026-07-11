import { Bases, Groups, Setup, Users } from "qin_soul";
import { QinTalker } from "./qin-talker";

export class QinTalkerWay {
    private readonly _talker: QinTalker;

    public constructor(talker: QinTalker) {
        this._talker = talker;
    }

    public getSetup(): Promise<Setup> {
        return new Promise<Setup>((resolve, reject) => {
            this._talker
                ._get<Setup>("/way/setup")
                .then((res) => resolve(res))
                .catch((err) => reject(err));
        });
    }

    public setSetup(setup: Setup): Promise<string> {
        return new Promise<string>((resolve, reject) => {
            this._talker
                ._post<string>("/way/setup", setup)
                .then((res) => resolve(res))
                .catch((err) => reject(err));
        });
    }

    public getBases(): Promise<Bases> {
        return new Promise<Bases>((resolve, reject) => {
            this._talker
                ._get<Bases>("/way/bases")
                .then((res) => resolve(res))
                .catch((err) => reject(err));
        });
    }

    public setBases(bases: Bases): Promise<string> {
        return new Promise<string>((resolve, reject) => {
            this._talker
                ._post<string>("/way/bases", bases)
                .then((res) => resolve(res))
                .catch((err) => reject(err));
        });
    }

    public getUsers(): Promise<Users> {
        return new Promise<Users>((resolve, reject) => {
            this._talker
                ._get<Users>("/way/users")
                .then((res) => resolve(res))
                .catch((err) => reject(err));
        });
    }

    public setUsers(users: Users): Promise<string> {
        return new Promise<string>((resolve, reject) => {
            this._talker
                ._post<string>("/way/users", users)
                .then((res) => resolve(res))
                .catch((err) => reject(err));
        });
    }

    public getGroups(): Promise<Groups> {
        return new Promise<Groups>((resolve, reject) => {
            this._talker
                ._get<Groups>("/way/groups")
                .then((res) => resolve(res))
                .catch((err) => reject(err));
        });
    }

    public setGroups(groups: Groups): Promise<string> {
        return new Promise<string>((resolve, reject) => {
            this._talker
                ._post<string>("/way/groups", groups)
                .then((res) => resolve(res))
                .catch((err) => reject(err));
        });
    }
}

