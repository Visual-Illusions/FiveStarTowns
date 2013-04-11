/*
 * This file is copyright of WarHead Gaming. It is open Source and
 * free to use. Use of our code without express consent from the
 * developers is in violation of our license which has been provided
 * for you in the source and compiled jar.
 */
package com.warhead.fivestartowns;

import com.warhead.fivestartowns.database.ChunkAccess;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.canarymod.Canary;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.world.position.Location;
import net.canarymod.database.DataAccess;
import net.canarymod.database.Database;
import net.canarymod.database.exceptions.DatabaseReadException;
import net.canarymod.database.exceptions.DatabaseWriteException;

/**
 *
 * @author Somners
 */
public class ChunkManager {

    private static final HashMap<String, ChunkAccess> chunks = new HashMap<String, ChunkAccess>();
    private static ChunkManager instance = null;

    public ChunkManager() {
        if (chunks.isEmpty()) {
            List<DataAccess> dataList = new ArrayList<DataAccess>();
            try {
                Database.get().loadAll(new ChunkAccess(), dataList, new String[]{}, new Object[]{});
            } catch (DatabaseReadException ex) {
                Canary.logStackTrace("Error loading Chunk Table in 'ChunkManager.class'. ", ex);
            }
            for (DataAccess data : dataList) {
                ChunkAccess chunk = (ChunkAccess)data;
                chunks.put(chunk.x+":"+chunk.z+":"+chunk.world, (ChunkAccess)data);
            }
        }
    }

    public static ChunkManager get() {
        if (instance == null) {
            instance = new ChunkManager();
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

    public FSTChunk getFSTChunk(Location location) {
        return this.getFSTChunk(((int)location.getX()) >> 4, ((int)location.getZ()) >> 4, location.getWorld().getName());
    }

    /**
     * Get an array of all the specified town's claimed chunks.
     * @param townName
     * @return
     */
    public FSTChunk[] getTownChunks(String townName) {
        List list = new ArrayList();
        for (ChunkAccess chunk : chunks.values()) {
            if (chunk.town.equalsIgnoreCase(townName)) {
                list.add(new FSTChunk(chunk));
            }
        }
        return (FSTChunk[])list.toArray(new FSTChunk[list.size()]);
    }

    /**
     * Check if the specified chunk is next to one owned by the specified town.
     * This is called when making Chunk Claims.
     *
     * @return
     */
    public boolean isChunkNextToTown(Location location, String townName) {
        return isChunkNextToTown(((int)location.getX()) >>4, ((int)location.getZ()) >>4, location.getWorld().getName(), townName);
    }

    /**
     * Check if the specified chunk is next to one owned by the specified town.
     * This is called when making Chunk Claims.
     *
     * @return
     */
    public boolean isChunkNextToTown(int x, int z, String world, String townName) {
        if (chunks.containsKey((x+1)+":"+z+":"+world) && chunks.get((x+1)+":"+z+":"+world).town.equalsIgnoreCase(townName)) {
            return true;
        }
        if (chunks.containsKey((x-1)+":"+z+":"+world) && chunks.get((x-1)+":"+z+":"+world).town.equalsIgnoreCase(townName)) {
            return true;
        }
        if (chunks.containsKey(x+":"+(z+1)+":"+world) && chunks.get(x+":"+(z+1)+":"+world).town.equalsIgnoreCase(townName)) {
            return true;
        }
        if (chunks.containsKey(x+":"+(z-1)+":"+world) && chunks.get(x+":"+(z-1)+":"+world).town.equalsIgnoreCase(townName)) {
            return true;
        }
        return false;
    }

    private String extractKey(ChunkAccess chunk) {
        return chunk.x+":"+chunk.z+":"+chunk.world;
    }

    public void addChunk(ChunkAccess data) {
        try {
            chunks.put(extractKey(data), data);
            Database.get().insert(data);
        } catch (DatabaseWriteException ex) {
            Canary.logStackTrace("Error Adding Land Claim at: " + data.toString(), ex);
        }
    }

    public void removeChunk(ChunkAccess data) {
        try {
            if (chunks.containsKey(extractKey(data))) {
                Database.get().remove(data.getName(), new String[] {"x", "z", "world"}, new Object[] {data.x, data.z, data.world});
                chunks.remove(extractKey(data));
            }
        } catch (DatabaseWriteException ex) {
            Canary.logStackTrace("Error Deleting Land Claim at: " + data.toString(), ex);
        }
    }
}
