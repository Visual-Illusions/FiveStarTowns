package net.visualillusionsent.fivestartowns.plot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.canarymod.Canary;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.world.position.Location;
import net.canarymod.database.DataAccess;
import net.canarymod.database.Database;
import net.canarymod.database.exceptions.DatabaseReadException;
import net.visualillusionsent.fivestartowns.Saveable;
import net.visualillusionsent.fivestartowns.database.PlotAccess;
import net.visualillusionsent.fivestartowns.player.IPlayer;

/**
 *
 * @author Somners
 */
public class PlotManager extends Saveable {

    private static final HashMap<String, Plot> plots = new HashMap<String, Plot>();
    private static PlotManager instance = null;

    public static PlotManager get() {
        if (instance == null) {
            instance = new PlotManager();
        }
        return instance;
    }

    public Plot getFSTPlot(int x, int z, String world) {
        String plot = x+":"+z+":"+world;
        if (plots.containsKey(plot)) {
            return plots.get(plot);
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
        for (Plot plot : plots.values()) {
            if (plot.getTownName().equalsIgnoreCase(townName)) {
                list.add(plot);
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
        if (plots.containsKey((x+1)+":"+z+":"+world) && plots.get((x+1)+":"+z+":"+world).getTownName().equalsIgnoreCase(townName)) {
            return true;
        }
        if (plots.containsKey((x-1)+":"+z+":"+world) && plots.get((x-1)+":"+z+":"+world).getTownName().equalsIgnoreCase(townName)) {
            return true;
        }
        if (plots.containsKey(x+":"+(z+1)+":"+world) && plots.get(x+":"+(z+1)+":"+world).getTownName().equalsIgnoreCase(townName)) {
            return true;
        }
        if (plots.containsKey(x+":"+(z-1)+":"+world) && plots.get(x+":"+(z-1)+":"+world).getTownName().equalsIgnoreCase(townName)) {
            return true;
        }
        return false;
    }

    private String extractKey(Plot plot) {
        return plot.getX()+":"+plot.getZ()+":"+plot.getWorldName();
    }

    public void addNewPlot(Plot data) {
        plots.put(extractKey(data), data);
    }

    @Override
    public void load() {
        plots.clear();
        
        List<DataAccess> townData = new ArrayList<DataAccess>();
        try {
            HashMap<String, Object> filter = new HashMap<String, Object>();
            Database.get().loadAll(new PlotAccess(), townData, filter);
        } catch (DatabaseReadException ex) {
            Canary.log.trace("Error Loading plot Data.", ex);
        }
        
        for (DataAccess da : townData) {
            PlotAccess data = (PlotAccess) da;
            plots.put(data.x +":"+ data.z +":"+ data.world, new Plot(data.x, data.z, data.world));
        }
    }

    @Override
    public void save() {
        for (Plot p : plots.values()) {
            if (p.isDirty()) {
                p.save();
                p.setDirty(false);
            }
        }
    }
}
