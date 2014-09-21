package net.visualillusionsent.fivestartowns.plot;

import net.canarymod.Canary;
import net.canarymod.database.Database;
import net.canarymod.database.exceptions.DatabaseReadException;
import net.canarymod.database.exceptions.DatabaseWriteException;
import net.visualillusionsent.fivestartowns.database.PlotAccess;
import net.visualillusionsent.fivestartowns.flag.FlagType;
import net.visualillusionsent.fivestartowns.flag.FlagValue;
import net.visualillusionsent.fivestartowns.flag.Flagable;
import net.visualillusionsent.fivestartowns.town.Town;
import net.visualillusionsent.fivestartowns.town.TownManager;
import net.visualillusionsent.fivestartowns.town.TownPlayer;

import java.util.HashMap;

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
    private int townId = -1;
    /** Player Name that owns this plot. */
    private String ownerId = "";

    public Plot(int x, int z, String world) {
        this.x = x;
        this.z = z;
        this.world = world;
        this.load();
    }

    public Plot(int x, int z, String world, int townId, String owner, FlagValue ownerPlot, FlagValue nopvp,
            FlagValue friendlyFire, FlagValue sanctuary, FlagValue protection, FlagValue creeperNerf) {
        this.x = x;
        this.z = z;
        this.world = world;
        this.townId = townId;
        this.ownerId = owner;
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
        Town t = TownManager.get().getTown(townId);
        return t == null ? "Wilderness" : t.getName();
    }

    /**
     * Name of the town that owns this plot.
     * @return
     */
    public int getTownUUID() {
        return townId;
    }

    /**
     * Instance of the town that owns this plot.
     * @return
     */
    public Town getTown() {
        return TownManager.get().getTown(townId);
    }

    /**
     * Get the UUID of the owner of this plot within the town.
     * @return
     */
    public String getPlotOwnerID() {
        return ownerId;
    }

    /**
     * Get the name of the owner of this plot within the town.
     * @return
     */
    public String getPlotOwnerName() {
        TownPlayer tp = TownManager.get().getTownPlayer(ownerId);
        String ownerName = "Nobody";
        if (tp != null) {
            ownerName = tp.getName();
        }
        return ownerName;
    }
    
    public void setTownUUID(int id) {
        this.townId = id;
        this.setDirty(true);
    }

    /**
     * Get the TownPlayer instance of the owner of this plot within the town.
     * @return
     */
    public TownPlayer getPlotOwner() {
        return TownManager.get().getTownPlayer(ownerId);
    }

    /**
     * Sets the owner of this plot. For Player Plot OwnerShip.
     */
    public void setPlotOwner(String uuid) {
        ownerId = uuid;
        this.setDirty(true);
    }

    /**
     * Sets the owner of this plot. For Player Plot OwnerShip.
     */
    public void setPlotOwner(TownPlayer player) {
        ownerId = player.getUUID();
        this.setDirty(true);
    }

    /**
     * Resets the owner of this plot. For Player Plot OwnerShip. There will be no
     * owner after this is run.
     */
    public void clearPlotOwner() {
        ownerId = null;
        this.setDirty(true);
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

    @Override
    public FlagValue getFlagValue(FlagType type) {
        FlagValue flag = super.getFlagValue(type);
        if (flag.equals(FlagValue.NULL)) {
            flag = getTown().getFlagValue(type);
        }
        return flag;
    }

    @Override
    public boolean isFlagEnabled(FlagType type) {
        if (getFlagValue(type).equals(FlagValue.NULL)) {
            return getTown().isFlagEnabled(type);
        }
        return super.isFlagEnabled(type);
    }
    
    @Override
    public void load() {
        PlotAccess data = new PlotAccess();
        try {
            HashMap<String, Object> filter = new HashMap<String, Object>();
            filter.put("x", x);
            filter.put("z", z);
            filter.put("world", world);
            Database.get().load(data, filter);
        } catch (DatabaseReadException ex) {
            Canary.log.trace("Error Loading Town Data", ex);
        }
        this.townId = data.townId;
        this.ownerId = data.owner;
        this.x = data.x;
        this.z = data.z;
        this.world = data.world;
        this.creeperNerf = FlagValue.getType(data.creeperNerf);
        this.friendlyFire = FlagValue.getType(data.friendlyFire);
        this.nopvp = FlagValue.getType(data.nopvp);
        this.ownerPlot = FlagValue.getType(data.ownerPlot);
        this.protection = FlagValue.getType(data.protection);
        this.sanctuary = FlagValue.getType(data.sanctuary);
    }

    @Override
    public void save() {
        PlotAccess data = new PlotAccess();
        data.owner = this.ownerId;
        data.townId = this.townId;
        data.x = this.x;
        data.z = this.z;
        data.world = this.world;
        data.creeperNerf = this.creeperNerf.toString();
        data.friendlyFire = this.friendlyFire.toString();
        data.nopvp = this.nopvp.toString();
        data.ownerPlot = this.ownerPlot.toString();
        data.protection = this.protection.toString();
        data.sanctuary = this.sanctuary.toString();
        try {
            HashMap<String, Object> filter = new HashMap<String, Object>();
            filter.put("x", x);
            filter.put("z", z);
            filter.put("world", world);
            Database.get().update(data, filter);
        } catch (DatabaseWriteException ex) {
            Canary.log.trace("Error Loading Town Data", ex);
        }
    }
}
