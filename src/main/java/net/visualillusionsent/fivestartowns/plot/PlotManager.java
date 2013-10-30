package net.visualillusionsent.fivestartowns.plot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.world.position.Location;
import net.visualillusionsent.fivestartowns.FiveStarTowns;
import net.visualillusionsent.fivestartowns.Saveable;
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

    public void removePlot(Plot data) {
        if (plots.containsKey(extractKey(data))) {
            plots.remove(extractKey(data));
            FiveStarTowns.database().deleteEntry(PLOT_TABLE,
                    FiveStarTowns.database().newQuery().add(X, data.getX()).add(Z, data.getZ()).add(WORLD, data.getWorldName()));
        }
    }

    /**
     * Database Stuff.
     */
    private final String PLOT_TABLE = "plots";
    private final String X = "x";
    private final String Z = "z";
    private final String WORLD = "world";

    @Override
    public void load() {
        ResultSet rs = null;

        try {
            rs = FiveStarTowns.database().getResultSet(PLOT_TABLE, null, 1000);
            if (rs != null) {
                while (rs.next()) {
                    String world = rs.getString(WORLD);
                    int z = rs.getInt(Z);
                    int x = rs.getInt(X);
                    Plot toLoad = new Plot(x, z, world);
                    toLoad.load();
                    plots.put(x+":"+z+":"+world, toLoad);
                }
            }
        } catch (SQLException ex) {
            FiveStarTowns.get().getPluginLogger().warning("Error Querying MySQL ResultSet in "
                    + PLOT_TABLE);
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
