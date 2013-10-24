package net.visualillusionsent.fivestartowns.town;

import net.visualillusionsent.fivestartowns.database.TownAccess;
import net.visualillusionsent.fivestartowns.database.TownPlayerAccess;
import net.visualillusionsent.fivestartowns.player.IPlayer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.canarymod.Canary;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.database.DataAccess;
import net.canarymod.database.Database;
import net.canarymod.database.exceptions.DatabaseReadException;
import net.canarymod.database.exceptions.DatabaseWriteException;

/**
 *
 * @author Somners
 */
public class TownManager {

    private static final HashMap<String, TownPlayerAccess> players = new HashMap<String, TownPlayerAccess>();
    private static final HashMap<String, TownAccess> towns = new HashMap<String, TownAccess>();
    private static TownManager instance = null;

    public TownManager() {
        if (players.isEmpty()) {
            List<DataAccess> dataList = new ArrayList<DataAccess>();
            try {
                Database.get().loadAll(new TownPlayerAccess(), dataList, new String[]{}, new Object[]{});
            } catch (DatabaseReadException ex) {
                Canary.logStacktrace("Error loading Town Players Table in 'TownManager.class'. ", ex);
            }
            for (DataAccess data : dataList) {
                players.put(((TownPlayerAccess)data).name, (TownPlayerAccess)data);
            }
        }
        if (towns.isEmpty()) {
            List<DataAccess> dataList = new ArrayList<DataAccess>();
            try {
                Database.get().loadAll(new TownAccess(), dataList, new String[]{}, new Object[]{});
            } catch (DatabaseReadException ex) {
                Canary.logStacktrace("Error loading Town Table in 'TownManager.class'. ", ex);
            }
            for (DataAccess data : dataList) {
                towns.put(((TownAccess)data).name, (TownAccess)data);
            }
        }
    }

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
            return new TownPlayer(players.get(playerName));
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
            return this.getTown(players.get(playerName).townName);
        }
        return null;
    }

    public Town getTown(String townName) {
        if (towns.containsKey(townName)) {
            return new Town(towns.get(townName));
        }
        return null;
    }

    public void addTown(TownAccess data) {
        try {
            towns.put(data.name, data);
            Database.get().insert(data);
        } catch (DatabaseWriteException ex) {
            Canary.logStacktrace("Error Adding Town at: " + data.toString(), ex);
        }
    }

    public void removeTown(TownAccess data) {
        try {
            if (towns.containsKey(data.name)) {
                Database.get().remove(data.getName(), new String[] {"name"}, new Object[] {data.name});
                towns.remove(data.name);
            }
        } catch (DatabaseWriteException ex) {
            Canary.logStacktrace("Error Deleting Town at: " + data.toString(), ex);
        }
    }

    public void addTownPlayer(TownPlayerAccess data) {
        try {
            players.put(data.name, data);
            Database.get().insert(data);
        } catch (DatabaseWriteException ex) {
            Canary.logStacktrace("Error Adding Town Player at: " + data.toString(), ex);
        }
    }

    public void removeTownPlayer(TownPlayerAccess data) {
        try {
            if (players.containsKey(data.name)) {
                Database.get().remove(data.getName(), new String[] {"name"}, new Object[] {data.name});
                players.remove(data.name);
            }
        } catch (DatabaseWriteException ex) {
            Canary.logStacktrace("Error Deleting Town Player at: " + data.toString(), ex);
        }
    }
}
