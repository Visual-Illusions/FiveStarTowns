package net.visualillusionsent.fivestartowns.plot;

import net.visualillusionsent.fivestartowns.database.PlotAccess;
import net.visualillusionsent.fivestartowns.player.IPlayer;
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
public class PlotManager {

    private static final HashMap<String, PlotAccess> plots = new HashMap<String, PlotAccess>();
    private static PlotManager instance = null;

    public PlotManager() {
        if (plots.isEmpty()) {
            List<DataAccess> dataList = new ArrayList<DataAccess>();
            try {
                Database.get().loadAll(new PlotAccess(), dataList, new String[]{}, new Object[]{});
            } catch (DatabaseReadException ex) {
                Canary.logStacktrace("Error loading Plot Table in 'PlotManager.class'. ", ex);
            }
            for (DataAccess data : dataList) {
                PlotAccess plot = (PlotAccess)data;
                plots.put(plot.x+":"+plot.z+":"+plot.world, (PlotAccess)data);
            }
        }
    }

    public static PlotManager get() {
        if (instance == null) {
            instance = new PlotManager();
        }
        return instance;
    }

    public Plot getFSTPlot(int x, int z, String world) {
        String plot = x+":"+z+":"+world;
        if (plots.containsKey(plot)) {
            return new Plot(plots.get(plot));
        }
        return null;
    }

    public Plot getFSTPlot(IPlayer player) {
        return this.getFSTPlot(player.getChunkX(), player.getChunkZ(), player.getWorldName());
    }

    public Plot getFSTPlot(Player player) {
        return this.getFSTPlot(((int)player.getX()) >> 4, ((int)player.getZ()) >> 4, player.getWorld().getName());
    }

    public Plot getFSTPlot(Location location) {
        return this.getFSTPlot(((int)location.getX()) >> 4, ((int)location.getZ()) >> 4, location.getWorld().getName());
    }

    /**
     * Get an array of all the specified town's claimed plots.
     * @param townName
     * @return
     */
    public Plot[] getTownPlots(String townName) {
        List list = new ArrayList();
        for (PlotAccess plot : plots.values()) {
            if (plot.town.equalsIgnoreCase(townName)) {
                list.add(new Plot(plot));
            }
        }
        return (Plot[])list.toArray(new Plot[list.size()]);
    }

    /**
     * Check if the specified plot is next to one owned by the specified town.
     * This is called when making Plot Claims.
     *
     * @return
     */
    public boolean isPlotNextToTown(Location location, String townName) {
        return isPlotNextToTown(((int)location.getX()) >>4, ((int)location.getZ()) >>4, location.getWorld().getName(), townName);
    }

    /**
     * Check if the specified plot is next to one owned by the specified town.
     * This is called when making Plot Claims.
     *
     * @return
     */
    public boolean isPlotNextToTown(int x, int z, String world, String townName) {
        if (plots.containsKey((x+1)+":"+z+":"+world) && plots.get((x+1)+":"+z+":"+world).town.equalsIgnoreCase(townName)) {
            return true;
        }
        if (plots.containsKey((x-1)+":"+z+":"+world) && plots.get((x-1)+":"+z+":"+world).town.equalsIgnoreCase(townName)) {
            return true;
        }
        if (plots.containsKey(x+":"+(z+1)+":"+world) && plots.get(x+":"+(z+1)+":"+world).town.equalsIgnoreCase(townName)) {
            return true;
        }
        if (plots.containsKey(x+":"+(z-1)+":"+world) && plots.get(x+":"+(z-1)+":"+world).town.equalsIgnoreCase(townName)) {
            return true;
        }
        return false;
    }

    private String extractKey(PlotAccess plot) {
        return plot.x+":"+plot.z+":"+plot.world;
    }

    public void addPlot(PlotAccess data) {
        try {
            plots.put(extractKey(data), data);
            Database.get().insert(data);
        } catch (DatabaseWriteException ex) {
            Canary.logStacktrace("Error Adding Land Claim at: " + data.toString(), ex);
        }
    }

    public void removePlot(PlotAccess data) {
        try {
            if (plots.containsKey(extractKey(data))) {
                Database.get().remove(data.getName(), new String[] {"x", "z", "world"}, new Object[] {data.x, data.z, data.world});
                plots.remove(extractKey(data));
            }
        } catch (DatabaseWriteException ex) {
            Canary.logStacktrace("Error Deleting Land Claim at: " + data.toString(), ex);
        }
    }
}
