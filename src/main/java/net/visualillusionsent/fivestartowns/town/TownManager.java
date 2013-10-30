package net.visualillusionsent.fivestartowns.town;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.canarymod.api.entity.living.humanoid.Player;
import net.visualillusionsent.fivestartowns.FiveStarTowns;
import net.visualillusionsent.fivestartowns.Saveable;
import net.visualillusionsent.fivestartowns.database.TownPlayerAccess;
import net.visualillusionsent.fivestartowns.player.IPlayer;

/**
 *
 * @author Somners
 */
public class TownManager extends Saveable {

    private static final HashMap<String, TownPlayer> players = new HashMap<String, TownPlayer>();
    private static final HashMap<String, Town> towns = new HashMap<String, Town>();
    private static TownManager instance = null;

    public static TownManager get() {
        if (instance == null) {
            instance = new TownManager();
        }
        return instance;
    }


    public TownPlayer getTownPlayer(IPlayer player) {
        return this.getTownPlayer(player.getName());
    }

    public TownPlayer getTownPlayer(Player player) {
        return this.getTownPlayer(player.getName());
    }

    public TownPlayer getTownPlayer(String playerName) {
        if (players.containsKey(playerName)) {
            return players.get(playerName);
        }
        return null;
    }

    public Town getTownFromPlayer(IPlayer player) {
        return this.getTownFromPlayer(player.getName());
    }

    public Town getTownFromPlayer(Player player) {
        return this.getTownFromPlayer(player.getName());
    }

    public Town getTownFromPlayer(String playerName) {
        if (players.containsKey(playerName)) {
            return this.getTown(players.get(playerName).getTownName());
        }
        return null;
    }

    public List<String> getMemberNames(String town) {
        List<String> toRet = new ArrayList<String>();
        for (TownPlayer tp : players.values()) {
            if (tp.getTownName().equalsIgnoreCase(town)) {
                toRet.add(tp.getName());
            }
        }
        return toRet;
    }

    public List<TownPlayer> getMembers(String town) {
        List<TownPlayer> toRet = new ArrayList<TownPlayer>();
        for (TownPlayer tp : players.values()) {
            if (tp.getTownName().equalsIgnoreCase(town)) {
                toRet.add(tp);
            }
        }
        return toRet;
    }

    public List<String> getMemberNames(Town town) {
        return this.getMemberNames(town.getName());
    }

    public List<TownPlayer> getMembers(Town town) {
        return this.getMembers(town.getName());
    }

    public Town getTown(String townName) {
        if (towns.containsKey(townName)) {
            return towns.get(townName);
        }
        return null;
    }

    public void addTown(Town data) {
        data.setDirty(true);
        towns.put(data.getName(), data);
    }

    public void deleteTown(Town data) {
        if (towns.containsKey(data.name)) {
            FiveStarTowns.database().deleteEntry(TOWN_TABLE,
                    FiveStarTowns.database().newQuery().add(NAME, data.getName()));
            towns.remove(data.name);
        }
    }

    public void addTownPlayer(TownPlayer data) {
        data.setDirty(true);
            players.put(data.getName(), data);
    }

    public void deleteTownPlayer(TownPlayerAccess data) {
        if (towns.containsKey(data.name)) {
            FiveStarTowns.database().deleteEntry(TOWN_TABLE,
                    FiveStarTowns.database().newQuery().add(NAME, data.getName()));
            towns.remove(data.name);
        }
    }

    private final String TOWN_PLAYER_TABLE = "town_Players";
    private final String TOWN_TABLE = "towns";
    private final String NAME = "name";

    @Override
    public void load() {
        ResultSet rs = null;

        try {
            /*
             * Load Towns
             */
            if (!towns.isEmpty()) {
                towns.clear();
            }
            rs = FiveStarTowns.database().getResultSet(TOWN_TABLE, null, 100);
            if (rs != null) {
                while (rs.next()) {
                    Town toLoad = new Town(rs.getString(NAME));
                    toLoad.load();
                    towns.put(toLoad.getName(), toLoad);
                }
            }
            FiveStarTowns.database().closeRSAndPS(rs);
            /*
             * Load Players
             */
            if (!players.isEmpty()) {
                players.clear();
            }
            rs = FiveStarTowns.database().getResultSet(TOWN_PLAYER_TABLE, null, 5000);
            if (rs != null) {
                while (rs.next()) {
                    Town toLoad = new Town(rs.getString(NAME));
                    toLoad.load();
                    towns.put(toLoad.getName(), toLoad);
                }
            }
            FiveStarTowns.database().closeRSAndPS(rs);
        } catch (SQLException ex) {
            FiveStarTowns.get().getPluginLogger().warning("Error Querying MySQL ResultSet in "
                    + TOWN_TABLE);
        }
    }

    @Override
    public void save() {
        for (Town t : towns.values()) {
            if (t.isDirty()) {
                t.save();
                t.setDirty(false);
            }
        }
        for (TownPlayer tp : players.values()) {
            if (tp.isDirty()) {
                tp.save();
                tp.setDirty(false);
            }
        }
    }
}
