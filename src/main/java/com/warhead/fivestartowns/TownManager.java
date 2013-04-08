/*
 * This file is copyright of WarHead Gaming. It is open Source and
 * free to use. Use of our code without express consent from the
 * developers is in violation of our license which has been provided
 * for you in the source and compiled jar.
 */
package com.warhead.fivestartowns;

import com.warhead.fivestartowns.database.TownAccess;
import com.warhead.fivestartowns.database.TownPlayerAccess;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.canarymod.Canary;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.database.DataAccess;
import net.canarymod.database.Database;
import net.canarymod.database.exceptions.DatabaseReadException;

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
                Canary.logStackTrace("Error loading Town Players Table in 'TownManager.class'. ", ex);
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
                Canary.logStackTrace("Error loading Town Table in 'TownManager.class'. ", ex);
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

    public TownPlayer getTownPlayer(Player player) {
        return this.getTownPlayer(player.getName());
    }

    public TownPlayer getTownPlayer(String playerName) {
        if (players.containsKey(playerName)) {
            return new TownPlayer(players.get(playerName));
        }
        return null;
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

}
