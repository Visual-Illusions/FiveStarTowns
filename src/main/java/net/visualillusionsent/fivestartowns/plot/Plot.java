package net.visualillusionsent.fivestartowns.plot;

import java.sql.ResultSet;
import java.sql.SQLException;
import net.visualillusionsent.fivestartowns.FiveStarTowns;
import net.visualillusionsent.fivestartowns.database.FSTDatabase.Query;
import net.visualillusionsent.fivestartowns.flag.FlagValue;
import net.visualillusionsent.fivestartowns.flag.Flagable;
import net.visualillusionsent.fivestartowns.town.Town;
import net.visualillusionsent.fivestartowns.town.TownManager;
import net.visualillusionsent.fivestartowns.town.TownPlayer;

/**
 *
 * @author Somners
 */
public class Plot extends Flagable {

    /** X coordinate for this plot. */
    private int x;
    /** Z coordinate for this plot. */
    private int z;
    /** World Name that contains this plot. */
    private String world;
    /** Town Name that owns this plot. */
    private String town = "";
    /** Player Name that owns this plot. */
    private String owner = "";

    public Plot(int x, int z, String world) {
        this.x = x;
        this.z = z;
        this.world = world;
    }

    public Plot(int x, int z, String world, String town, String owner, FlagValue ownerPlot, FlagValue nopvp,
            FlagValue friendlyFire, FlagValue sanctuary, FlagValue protection, FlagValue creeperNerf) {
        this.x = x;
        this.z = z;
        this.world = world;
        this.town = town;
        this.owner = owner;
        this.ownerPlot = ownerPlot;
        this.nopvp = nopvp;
        this.friendlyFire = friendlyFire;
        this.sanctuary = sanctuary;
        this.protection = protection;
        this.creeperNerf = creeperNerf;
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

    /*
     * Database Stuff.
     */
    private final String PLOT_TABLE = "plots";
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
        ResultSet rs = null;

        try {
            rs = FiveStarTowns.database().getResultSet(PLOT_TABLE,
                    FiveStarTowns.database().newQuery().add(X, x).add(Z, z).add(WORLD, world), 1);

            if (rs != null && rs.next()) {
                this.creeperNerf = FlagValue.getType(rs.getString(CREEPER_NERF));
                this.friendlyFire = FlagValue.getType(rs.getString(FRIENDLY_FIRE));
                this.nopvp = FlagValue.getType(rs.getString(NO_PVP));
                this.ownerPlot = FlagValue.getType(rs.getString(OWNER_PLOT));
                this.protection = FlagValue.getType(rs.getString(PROTECTION));
                this.sanctuary = FlagValue.getType(rs.getString(SANCTUARY));
                this.owner = rs.getString(OWNER);
                this.town = rs.getString(TOWN);
                this.world = rs.getString(WORLD);
                this.z = rs.getInt(Z);
                this.x = rs.getInt(X);
            }
        } catch (SQLException ex) {
            FiveStarTowns.get().getPluginLogger().warning("Error Querying MySQL ResultSet in "
                    + PLOT_TABLE);
        }
    }

    @Override
    public void save() {
        Query where = FiveStarTowns.database().newQuery();
        where.add(X, x).add(Z, z).add(WORLD, world);
        Query update = FiveStarTowns.database().newQuery();
        update.add(TOWN, town).add(OWNER, owner).add(OWNER_PLOT, ownerPlot);
        update.add(NO_PVP, nopvp).add(FRIENDLY_FIRE, friendlyFire).add(SANCTUARY, sanctuary);
        update.add(PROTECTION, protection).add(CREEPER_NERF, creeperNerf);
        FiveStarTowns.database().updateEntry(PLOT_TABLE, where, update);
    }
}
