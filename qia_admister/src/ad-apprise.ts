export class AdApprised {
    public message: string;
    public popup: boolean;

    constructor(message: string, popup?: boolean) {
        this.message = message;
        this.popup = popup ?? true;
    }
}

export class AdApprise {
    public static readonly CANCELED_BY_MUTATIONS = 
            new AdApprised("The user canceled this action to not loose his mutations.", false);

    public static readonly NO_RESULTS_FOUND = 
            new AdApprised("No results found.", true);

    public static readonly INSERTED_REGISTER = 
            new AdApprised("Inserted one register.", false);

    public static readonly UPDATED_REGISTER = 
            new AdApprised("Updated one register.", false);

    public static readonly DELETED_REGISTER = 
            new AdApprised("Row deleted with success.", true);
}
