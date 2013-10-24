package net.visualillusionsent.fivestartowns.plot;

import java.util.ArrayList;
import java.util.List;
import net.canarymod.Canary;
import net.canarymod.database.Database;
import net.canarymod.database.exceptions.DatabaseWriteException;
import net.visualillusionsent.fivestartowns.database.PlotAccess;
import net.visualillusionsent.fivestartowns.flag.FlagType;
import static net.visualillusionsent.fivestartowns.flag.FlagType.CREEPER_NERF;
import static net.visualillusionsent.fivestartowns.flag.FlagType.FRIENDLY_FIRE;
import static net.visualillusionsent.fivestartowns.flag.FlagType.NO_PVP;
import static net.visualillusionsent.fivestartowns.flag.FlagType.OWNER_PLOT;
import static net.visualillusionsent.fivestartowns.flag.FlagType.PROTECTION;
import static net.visualillusionsent.fivestartowns.flag.FlagType.SANCTUARY;
import net.visualillusionsent.fivestartowns.flag.FlagValue;
import net.visualillusionsent.fivestartowns.flag.Flagable;
import net.visualillusionsent.fivestartowns.town.Town;
import net.visualillusionsent.fivestartowns.town.TownManager;
import net.visualillusionsent.fivestartowns.town.TownPlayer;

/**
 *
 * @author Somners
 */
public class Plot implements Flagable {

    private final PlotAccess data;

    public Plot(PlotAccess access) {
        this.data = access;
    }

    /**
     * Get the X coordinate for this plot.
     * @return
     */
    public int getX() {
        return data.x;
    }

    /**
     * Get the Z coordinate for this plot.
     * @return
     */
    public int getZ() {
        return data.z;
    }

    /**
     * Get the world Name of this plot.
     * @return
     */
    public String getWorldName() {
        return data.world;
    }

    /**
     * Name of the town that owns this plot.
     * @return
     */
    public String getTownName() {
        return data.town;
    }

    /**
     * Instance of the town that owns this plot.
     * @return
     */
    public Town getTown() {
        return TownManager.get().getTown(data.town);
    }

    /**
     * Get the name of the owner of this plot within the town.
     * @return
     */
    @Override
    public String getPlotOwnerName() {
        return data.owner;
    }

    /**
     * Get the TownPlayer instance of the owner of this plot within the town.
     * @return
     */
    @Override
    public TownPlayer getPlotOwner() {
        return TownManager.get().getTownPlayer(data.owner);
    }

    /**
     * Sets this flagtype and returns what the FlagType was set to.
     * @param type
     * @return
     */
    @Override
    public void setFlag(FlagType type, FlagValue value) {
        switch(type) {
            case NO_PVP:
                this.setNoPvp(value);
            case FRIENDLY_FIRE:
                this.setFriendlyFire(value);
            case SANCTUARY:
                this.setSanctuary(value);
            case PROTECTION:
                this.setProtected(value);
            case CREEPER_NERF:
                this.setCreeperNerf(value);
            case OWNER_PLOT:
                this.setOwnerPlot(value);
        }
    }

    @Override
    public FlagValue getFlagValue(FlagType type) {
        switch(type) {
            case NO_PVP:
                return FlagValue.getType(data.nopvp);
            case FRIENDLY_FIRE:
                return FlagValue.getType(data.friendlyFire);
            case SANCTUARY:
                return FlagValue.getType(data.sanctuary);
            case PROTECTION:
                return FlagValue.getType(data.protection);
            case CREEPER_NERF:
                return FlagValue.getType(data.creeperNerf);
            case OWNER_PLOT:
                return FlagValue.getType(data.ownerPlot);
        }
        return null;
    }

    /**
     *
     * @param flag
     * @return
     */
    public boolean canUseFlag(FlagType flag) {
        return this.getTown().canUseFlag(flag);
    }

    /**
     *
     * @param flag
     * @return
     */
    public boolean canUseFlag(String flag) {
        return this.getTown().canUseFlag(flag);
    }

    /**
     * Gets a list of flags that can be set.
     * @return
     */
    @Override
    public List<FlagType> getFlags() {
        List<FlagType> list = new ArrayList<FlagType>();
        for (String name : this.getTown().getTownRank().getFlags()) {
            list.add(FlagType.fromString(name));
        }
        return list;
    }

    /**
     * Gets a list of flags that can be set.
     * @return
     */
    @Override
    public List<String> getFlagNames() {
        List<String> list = new ArrayList<String>();
        for (String name : this.getTown().getTownRank().getFlags()) {
            list.add(name);
        }
        return list;
    }

    @Override
    public String[] getEnabledFlags() {
        List<String> list = new ArrayList<String>();
        for (FlagType type : this.getFlags()) {
            if (this.getFlagValue(type).getBoolean()) {
                list.add(type.getName());
            }
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * Sets whether or not pvp is disabled.
     * @param value TRUE = enabled<br>FALSE = disabled<br>NULL = use global
     */
    @Override
    public void setNoPvp(FlagValue value) {
        data.nopvp = value.toString();
        try {
            Database.get().update(data, new String[]{"nopvp"}, new Object[]{data.ownerPlot});
        } catch (DatabaseWriteException ex) {
            Canary.logStacktrace("Error updating 'ownerPlot' in Plot '"
                    + data.x + ":" + data.z + ":" + data.world + "'. ", ex);
        }
    }

    /**
     * Sets whether or not this is an owner plot.
     * @param value TRUE = enabled<br>FALSE = disabled<br>NULL = use global
     */
    @Override
    public void setOwnerPlot(FlagValue value) {
        data.ownerPlot = value.toString();
        try {
            Database.get().update(data, new String[]{"ownerPlot"}, new Object[]{data.ownerPlot});
        } catch (DatabaseWriteException ex) {
            Canary.logStacktrace("Error updating 'ownerPlot' in Plot '"
                    + data.x + ":" + data.z + ":" + data.world + "'. ", ex);
        }
    }

    /**
     * Sets whether or not protection is enabled
     * @param value TRUE = enabled<br>FALSE = disabled<br>NULL = use global
     */
    @Override
    public void setProtected(FlagValue value) {
        data.protection = value.toString();
        try {
            Database.get().update(data, new String[]{"protection"}, new Object[]{data.protection});
        } catch (DatabaseWriteException ex) {
            Canary.logStacktrace("Error updating 'protection' in Plot '"
                    + data.x + ":" + data.z + ":" + data.world + "'. ", ex);
        }
    }

    /**
     * Sets whether or not sanctuary is enabledfalse - sanctuary off
     * @param value TRUE = enabled<br>FALSE = disabled<br>NULL = use global
     */
    @Override
    public void setSanctuary(FlagValue value) {
        data.sanctuary = value.toString();
        try {
            Database.get().update(data, new String[]{"sanctuary"}, new Object[]{data.sanctuary});
        } catch (DatabaseWriteException ex) {
            Canary.logStacktrace("Error updating 'sanctuary' in Plot '"
                    + data.x + ":" + data.z + ":" + data.world + "'. ", ex);
        }
    }

    /**
     * Sets whether or not creepers are disabled
     * @param value TRUE = enabled<br>FALSE = disabled<br>NULL = use global
     */
    @Override
    public void setCreeperNerf(FlagValue value) {
        data.creeperNerf = value.toString();
        try {
            Database.get().update(data, new String[]{"creeperNerf"}, new Object[]{data.creeperNerf});
        } catch (DatabaseWriteException ex) {
            Canary.logStacktrace("Error updating 'creeperNerf' in Plot '"
                    + data.x + ":" + data.z + ":" + data.world + "'. ", ex);
        }
    }

    /**
     * Sets whether or not friendly fire is enabled
     * @param value TRUE = enabled<br>FALSE = disabled<br>NULL = use global
     */
    @Override
    public void setFriendlyFire(FlagValue value) {
        data.friendlyFire = value.toString();
        try {
            Database.get().update(data, new String[]{"friendlyFire"}, new Object[]{data.friendlyFire});
        } catch (DatabaseWriteException ex) {
            Canary.logStacktrace("Error updating 'friendlyFire' in Plot '"
                    + data.x + ":" + data.z + ":" + data.world + "'. ", ex);
        }
    }

    /**
     * Is pvp Allowed?
     * @return true - pvp disabled<br>false - pvp enabled
     */
    @Override
    public boolean getNoPvp() {
        if (data.nopvp.equals(FlagValue.NULL.toString())) {
            return this.getTown().getNoPvp();
        }
        return FlagValue.getType(data.nopvp).getBoolean();
    }

    /**
     * Are protections on?
     * @return true - enabled<br>false - disabled
     */
    @Override
    public boolean getProtected() {
        if (data.protection.equals(FlagValue.NULL.toString())) {
            return this.getTown().getProtected();
        }
        return FlagValue.getType(data.protection).getBoolean();
    }

    /**
     * Can mobs Spawn?
     * @return true - mob spawning blocked<br>false - mob spawning allowed
     */
    @Override
    public boolean getSanctuary() {
        if (data.sanctuary.equals(FlagValue.NULL.toString())) {
            return this.getTown().getSanctuary();
        }
        return FlagValue.getType(data.sanctuary).getBoolean();
    }

    /**
     * Are Creepers Nerfed?
     * @return true - creepers disabled<br>false - creepers enabled
     */
    @Override
    public boolean getCreeperNerf() {
        if (data.creeperNerf.equals(FlagValue.NULL.toString())) {
            return this.getTown().getCreeperNerf();
        }
        return FlagValue.getType(data.creeperNerf).getBoolean();
    }

    /**
     * Is friendly Fire on?
     * @return true - FF enabled<br>false - FF disabled
     */
    @Override
    public boolean getFriendlyFire() {
        if (data.friendlyFire.equals(FlagValue.NULL.toString())) {
            return this.getTown().getFriendlyFire();
        }
        return FlagValue.getType(data.friendlyFire).getBoolean();
    }

    public boolean isPlotEqual(int x, int z, String world) {
        if (data.x != x) {
            return false;
        } else if (data.z != z) {
            return false;
        } else if (!data.world.equals(world)) {
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
}
