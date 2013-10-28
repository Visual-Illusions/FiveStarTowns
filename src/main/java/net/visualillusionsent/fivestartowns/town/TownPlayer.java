
package net.visualillusionsent.fivestartowns.town;

import net.visualillusionsent.fivestartowns.Saveable;
import net.visualillusionsent.fivestartowns.database.TownPlayerAccess;

/**
 *
 * @author Somners
 */
public class TownPlayer extends Saveable {

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

    private final String NAME = "name";
    private final String TOWN_NAME = "townName";

    @Override
    public void load() {
        throw new UnsupportedOperationException("Method 'load' in class 'TownPlayer' is not supported yet.");
    }

    @Override
    public void save() {
        throw new UnsupportedOperationException("Method 'save' in class 'TownPlayer' is not supported yet.");
    }
}
