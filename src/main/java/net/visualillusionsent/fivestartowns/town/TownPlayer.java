
package net.visualillusionsent.fivestartowns.town;

import net.visualillusionsent.fivestartowns.database.TownPlayerAccess;

/**
 *
 * @author Somners
 */
public class TownPlayer {

    private final TownPlayerAccess data;

    public TownPlayer(TownPlayerAccess data) {
        this.data = data;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return data.name;
    }

    /**
     *
     * @return
     */
    public String getTownName() {
        return data.townName;
    }

    /**
     *
     * @return
     */
    public Town getTown() {
        return TownManager.get().getTown(data.townName);
    }

    /**
     *
     * @return
     */
    public boolean isOwner() {
        return this.getTown().getOwnerName().equals(this.getName());
    }

    /**
     *
     * @return
     */
    public boolean isAssistant() {
        return this.getTown().getAssistantName().contains(this.getName());
    }
}
