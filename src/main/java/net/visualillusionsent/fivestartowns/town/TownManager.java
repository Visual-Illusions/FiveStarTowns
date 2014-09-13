package net.visualillusionsent.fivestartowns.town;

import net.canarymod.Canary;
import net.canarymod.ToolBox;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.database.DataAccess;
import net.canarymod.database.Database;
import net.canarymod.database.exceptions.DatabaseReadException;
import net.canarymod.database.exceptions.DatabaseWriteException;
import net.visualillusionsent.fivestartowns.Saveable;
import net.visualillusionsent.fivestartowns.database.TownAccess;
import net.visualillusionsent.fivestartowns.database.TownPlayerAccess;
import net.visualillusionsent.fivestartowns.player.IPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Somners
 */
public class TownManager extends Saveable {

    private static final List<TownPlayer> players = new ArrayList<TownPlayer>();
    private static final List<Town> towns = new ArrayList<Town>();
    private static TownManager instance = null;

    public static TownManager get() {
        if (instance == null) {
            instance = new TownManager();
        }
        return instance;
    }

    @Deprecated
    public TownPlayer getTownPlayer(IPlayer player) {
        return this.getTownPlayer(player.getName());
    }

    public TownPlayer getTownPlayer(Player player) {
        return this.getTownPlayer(player.getUUIDString());
    }

    public TownPlayer getTownPlayer(String nameOrUUID) {
        String uuid = ToolBox.isUUID(nameOrUUID) ? nameOrUUID : ToolBox.usernameToUUID(nameOrUUID);
        for (TownPlayer p : players) {
            if (p.getUUID().equals(uuid)) {
                return p;
            }
        }
        return null;
    }

    
    public Town getTown(int uuid) {
        for (Town t : towns) {
            if (t.getUUID() == uuid) {
                return t;
            }
        }
        return null;
    }
    public Town getTownFromPlayer(IPlayer player) {
        return this.getTownFromPlayer(player.getName());
    }

    public Town getTownFromPlayer(Player player) {
        return this.getTownFromPlayer(player.getName());
    }

    public Town getTownFromPlayer(String nameOrUUID) {
        String uuid = ToolBox.isUUID(nameOrUUID) ? nameOrUUID : ToolBox.usernameToUUID(nameOrUUID);
        for (TownPlayer tp : players) {
            if (tp.getUUID().equals(uuid)) {
                return tp.getTown();
            }
        }
        return null;
    }

    public List<String> getMemberNames(String town) {
        List<String> toRet = new ArrayList<String>();
        for (TownPlayer tp : players) {
            if (tp.getTownName().equalsIgnoreCase(town)) {
                toRet.add(tp.getName());
            }
        }
        return toRet;
    }

    public List<String> getMemberNames(int townId) {
        List<String> toRet = new ArrayList<String>();
        for (TownPlayer tp : players) {
            if (tp.getTownUUID() == townId) {
                toRet.add(tp.getName());
            }
        }
        return toRet;
    }

    public List<TownPlayer> getMembers(String town) {
        List<TownPlayer> toRet = new ArrayList<TownPlayer>();
        for (TownPlayer tp : players) {
            if (tp.getTownName().equalsIgnoreCase(town)) {
                toRet.add(tp);
            }
        }
        return toRet;
    }

    public List<TownPlayer> getMembers(int townId) {
        List<TownPlayer> toRet = new ArrayList<TownPlayer>();
        for (TownPlayer tp : players) {
            if (tp.getTownUUID() == townId) {
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
        for (Town t : towns) {
            if (t.getName().equals(townName)) {
                return t;
            }
        }
        return null;
    }

    public void addTown(Town data) {
        data.setDirty(true);
        towns.add(data);
    }

    public void deleteTown(Town data) {
        if (towns.contains(data)) {
            HashMap<String, Object> filter = new HashMap<String, Object>();
            filter.put("name", data.getName());
            try {
                Database.get().remove(new TownAccess(), filter);
            } catch (DatabaseWriteException ex) {
                Canary.log.trace("Error deleting Town Data.", ex);
            }
            towns.remove(data);
        }
    }

    public void addTownPlayer(TownPlayer data) {
        data.setDirty(true);
        players.add( data);
    }

    public void deleteTownPlayer(TownPlayer data) {
        if (players.contains(data)) {
            HashMap<String, Object> filter = new HashMap<String, Object>();
            filter.put("uuid", data.getUUID());
            try {
                Database.get().remove(new TownPlayerAccess(), filter);
            } catch (DatabaseWriteException ex) {
                Canary.log.trace("Error deleting TownPlayer Data.", ex);
            }
            towns.remove(data);
        }
    }

    private final String TOWN_PLAYER_TABLE = "town_Players";
    private final String TOWN_TABLE = "towns";
    private final String NAME = "name";

    @Override
    public void load() {
        players.clear();
        towns.clear();
        
        List<DataAccess> townData = new ArrayList<DataAccess>();
        try {
            HashMap<String, Object> filter = new HashMap<String, Object>();
            Database.get().loadAll(new TownAccess(), townData, filter);
        } catch (DatabaseReadException ex) {
            Canary.log.trace("Error Loading Town Data.", ex);
        }
        
        for (DataAccess da : townData) {
            TownAccess data = (TownAccess) da;
            towns.add(new Town(data.uuid));
        }
        
        List<DataAccess> playerData = new ArrayList<DataAccess>();
        try {
            HashMap<String, Object> filter = new HashMap<String, Object>();
            Database.get().loadAll(new TownPlayerAccess(), playerData, filter);
        } catch (DatabaseReadException ex) {
            Canary.log.trace("Error Loading TownPlayer Data.", ex);
        }
        
        for (DataAccess da : playerData) {
            TownPlayerAccess data = (TownPlayerAccess) da;
            players.add(new TownPlayer(data.uuid));
        }
    }

    @Override
    public void save() {
        for (Town t : towns) {
            if (t.isDirty()) {
                t.save();
                t.setDirty(false);
            }
        }
        for (TownPlayer tp : players) {
            if (tp.isDirty()) {
                tp.save();
                tp.setDirty(false);
            }
        }
    }
}
