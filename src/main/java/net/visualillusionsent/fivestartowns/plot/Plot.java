package net.visualillusionsent.fivestartowns.plot;

import net.visualillusionsent.fivestartowns.database.PlotAccess;
import net.visualillusionsent.fivestartowns.flag.Flagable;
import net.visualillusionsent.fivestartowns.town.Town;
import net.visualillusionsent.fivestartowns.town.TownManager;
import net.visualillusionsent.fivestartowns.town.TownPlayer;

/**
 *
 * @author Somners
 */
public class Plot extends Flagable {

    private PlotAccess data = null;
    /** X coordinate for this plot. */
    private int x;
    /** Z coordinate for this plot. */
    private int z;
    /** World Name that contains this plot. */
    private String world;
    /** Town Name that owns this plot. */
    private String town;
    /** Player Name that owns this plot. */
    private String owner;

    public Plot() {}

    public Plot(PlotAccess access) {
        this.data = access;
    }

    /**
     * Get the X coordinate for this plot.
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * Get the Z coordinate for this plot.
     * @return
     */
    public int getZ() {
        return z;
    }

    /**
     * Get the world Name of this plot.
     * @return
     */
    public String getWorldName() {
        return world;
    }

    /**
     * Name of the town that owns this plot.
     * @return
     */
    public String getTownName() {
        return town;
    }

    /**
     * Instance of the town that owns this plot.
     * @return
     */
    public Town getTown() {
        return TownManager.get().getTown(town);
    }

    /**
     * Get the name of the owner of this plot within the town.
     * @return
     */
    public String getPlotOwnerName() {
        return owner;
    }

    /**
     * Get the TownPlayer instance of the owner of this plot within the town.
     * @return
     */
    public TownPlayer getPlotOwner() {
        return TownManager.get().getTownPlayer(owner);
    }

    public boolean isPlotEqual(int x, int z, String world) {
        if (x != x) {
            return false;
        } else if (z != z) {
            return false;
        } else if (!world.equals(world)) {
            return false;
        }
        return true;
    }

    public boolean equals(Plot plot) {
        if(plot == null) {
            return false;
        }
        return plot.getTownName().equals(this.getTownName());
    }

    public PlotAccess getAccess() {
        return data;
    }

    private final String OWNER_PLOT = "ownerPlot";
    private final String NO_PVP = "nopvp";
    private final String FRIENDLY_FIRE = "friendlyFire";
    private final String SANCTUARY = "sanctuary";
    private final String PROTECTION = "protection";
    private final String CREEPER_NERF = "creeperNerf";
    private final String X = "x";
    private final String Z = "z";
    private final String WORLD = "world";
    private final String TOWN = "town";
    private final String OWNER = "owner";

    @Override
    public void load() {
        throw new UnsupportedOperationException("Method 'load' in class 'Plot' is not supported yet.");
    }

    @Override
    public void save() {
        throw new UnsupportedOperationException("Method 'save' in class 'Plot' is not supported yet.");
    }
}
