/*
 * This file is copyright of WarHead Gaming. It is open Source and
 * free to use. Use of our code without express consent from the
 * developers is in violation of our license which has been provided
 * for you in the source and compiled jar.
 */
package com.warhead.fivestartowns;

import com.warhead.fivestartowns.database.ChunkAccess;
import com.warhead.fivestartowns.database.TownAccess;
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
public class ChunkManager {

    private static final HashMap<String, ChunkAccess> chunks = new HashMap<String, ChunkAccess>();
    private static TownManager instance = null;

    public ChunkManager() {
        if (chunks.isEmpty()) {
            List<DataAccess> dataList = new ArrayList<DataAccess>();
            try {
                Database.get().loadAll(new TownAccess(), dataList, new String[]{}, new Object[]{});
            } catch (DatabaseReadException ex) {
                Canary.logStackTrace("Error loading Town Table in 'TownManager.class'. ", ex);
            }
            for (DataAccess data : dataList) {
                ChunkAccess chunk = (ChunkAccess)data;
                chunks.put(chunk.x+":"+chunk.z+":"+chunk.world, (ChunkAccess)data);
            }
        }
    }

    public static TownManager get() {
        if (instance == null) {
            instance = new TownManager();
        }
        return instance;
    }

    public FSTChunk getFSTChunk(int x, int z, String world) {
        String chunk = x+":"+z+":"+world;
        if (chunks.containsKey(chunk)) {
            return new FSTChunk(chunks.get(chunk));
        }
        return null;
    }

    public FSTChunk getFSTChunk(Player player) {
        return this.getFSTChunk(((int)player.getX()) >> 4, ((int)player.getZ()) >> 4, player.getWorld().getName());
    }
}
