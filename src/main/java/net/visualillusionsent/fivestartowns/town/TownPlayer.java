
package net.visualillusionsent.fivestartowns.town;

import java.sql.ResultSet;
import java.sql.SQLException;
import net.visualillusionsent.fivestartowns.FiveStarTowns;
import net.visualillusionsent.fivestartowns.Saveable;
import net.visualillusionsent.fivestartowns.database.FSTDatabase;

/**
 *
 * @author Somners
 */
public class TownPlayer extends Saveable {

    private final String name;
    private String townName;

    public TownPlayer(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public String getTownName() {
        return townName;
    }

    /**
     *
     * @return
     */
    public Town getTown() {
        return TownManager.get().getTown(townName);
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

    private final String TOWN_PLAYER_TABLE = "town_Players";
    private final String NAME = "name";
    private final String TOWN_NAME = "townName";

    @Override
    public void load() {
        ResultSet rs = null;

        try {
            rs = FiveStarTowns.database().getResultSet(TOWN_PLAYER_TABLE,
                    FiveStarTowns.database().newQuery().add(NAME, name), 1);

            if (rs != null && rs.next()) {
                this.townName = rs.getString(TOWN_NAME);
            }
        } catch (SQLException ex) {
            FiveStarTowns.get().getPluginLogger().warning("Error Querying MySQL ResultSet in "
                    + TOWN_PLAYER_TABLE);
        }
    }

    @Override
    public void save() {
        FSTDatabase.Query where = FiveStarTowns.database().newQuery();
        where.add(NAME, name);
        FSTDatabase.Query update = FiveStarTowns.database().newQuery();
        update.add(TOWN_NAME, townName);
        FiveStarTowns.database().updateEntry(TOWN_PLAYER_TABLE, where, update);
    }
}
