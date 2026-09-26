package br.com.pointel.wiz_jarch.data;

/**
 * Describes the supported database actions and whether they mutate data.
 */
public enum Deeds {

    Select(false), 
    Insert(true), 
    Update(true), 
    Delete(true);

    /**
     * Indicates whether the action changes persistent state.
     */
    public final boolean mutates;

    /**
     * Creates an action descriptor with its mutation flag.
     *
     * @param mutates whether the action modifies data
     */
    private Deeds(boolean mutates) {
        this.mutates = mutates;
    }
    
}
