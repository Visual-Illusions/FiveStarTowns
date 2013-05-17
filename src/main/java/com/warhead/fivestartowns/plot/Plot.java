/*
 * This file is copyright of WarHead Gaming. It is open Source and
 * free to use. Use of our code without express consent from the
 * developers is in violation of our license which has been provided
 * for you in the source and compiled jar.
 */
package com.warhead.fivestartowns.plot;

import com.warhead.fivestartowns.FlagType;
import static com.warhead.fivestartowns.FlagType.CREEPER_NERF;
import static com.warhead.fivestartowns.FlagType.FRIENDLY_FIRE;
import static com.warhead.fivestartowns.FlagType.NO_PVP;
import static com.warhead.fivestartowns.FlagType.OWNER_PLOT;
import static com.warhead.fivestartowns.FlagType.PROTECTION;
import static com.warhead.fivestartowns.FlagType.SANCTUARY;
import com.warhead.fivestartowns.FlagValue;
import com.warhead.fivestartowns.town.TownPlayer;
import com.warhead.fivestartowns.town.TownManager;
import com.warhead.fivestartowns.town.Town;
import com.warhead.fivestartowns.database.PlotAccess;
import java.util.ArrayList;
import java.util.List;
import net.canarymod.Canary;
import net.canarymod.database.Database;
import net.canarymod.database.exceptions.DatabaseWriteException;

/**
 *
 * @author Somners
 */
public class Plot {

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
    public String getPlotOwnerName() {
        return data.owner;
    }

    /**
     * Get the TownPlayer instance of the owner of this plot within the town.
     * @return
     */
    public TownPlayer getPlotOwner() {
        return TownManager.get().getTownPlayer(data.owner);
    }
        
    /**
     * Sets this flagtype and returns what the FlagType was set to.
     * @param type
     * @return 
     */
    public FlagValue setFlag(FlagType type, FlagValue value) {
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
        return null;
    }

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
     * Sets whether or not pvp is disabled. 
     * @param value TRUE = enabled<br>FALSE = disabled<br>NULL = use global
     */
    public void setNoPvp(FlagValue value) {
        data.nopvp = value.toString();
        try {
            Database.get().update(data, new String[]{"nopvp"}, new Object[]{data.ownerPlot});
        } catch (DatabaseWriteException ex) {
            Canary.logStackTrace("Error updating 'ownerPlot' in Plot '"
                    + data.x + ":" + data.z + ":" + data.world + "'. ", ex);
        }
    }
    
    /**
     * Sets whether or not this is an owner plot. 
     * @param value TRUE = enabled<br>FALSE = disabled<br>NULL = use global
     */
    public void setOwnerPlot(FlagValue value) {
        data.ownerPlot = value.toString();
        try {
            Database.get().update(data, new String[]{"ownerPlot"}, new Object[]{data.ownerPlot});
        } catch (DatabaseWriteException ex) {
            Canary.logStackTrace("Error updating 'ownerPlot' in Plot '"
                    + data.x + ":" + data.z + ":" + data.world + "'. ", ex);
        }
    }

    /**
     * Sets whether or not protection is enabled 
     * @param value TRUE = enabled<br>FALSE = disabled<br>NULL = use global
     */
    public void setProtected(FlagValue value) {
        data.protection = value.toString();
        try {
            Database.get().update(data, new String[]{"protection"}, new Object[]{data.protection});
        } catch (DatabaseWriteException ex) {
            Canary.logStackTrace("Error updating 'protection' in Plot '"
                    + data.x + ":" + data.z + ":" + data.world + "'. ", ex);
        }
    }

    /**
     * Sets whether or not sanctuary is enabledfalse - sanctuary off
     * @param value TRUE = enabled<br>FALSE = disabled<br>NULL = use global
     */
    public void setSanctuary(FlagValue value) {
        data.sanctuary = value.toString();
        try {
            Database.get().update(data, new String[]{"sanctuary"}, new Object[]{data.sanctuary});
        } catch (DatabaseWriteException ex) {
            Canary.logStackTrace("Error updating 'sanctuary' in Plot '"
                    + data.x + ":" + data.z + ":" + data.world + "'. ", ex);
        }
    }

    /**
     * Sets whether or not creepers are disabled 
     * @param value TRUE = enabled<br>FALSE = disabled<br>NULL = use global
     */
    public void setCreeperNerf(FlagValue value) {
        data.creeperNerf = value.toString();
        try {
            Database.get().update(data, new String[]{"creeperNerf"}, new Object[]{data.creeperNerf});
        } catch (DatabaseWriteException ex) {
            Canary.logStackTrace("Error updating 'creeperNerf' in Plot '"
                    + data.x + ":" + data.z + ":" + data.world + "'. ", ex);
        }
    }

    /**
     * Sets whether or not friendly fire is enabled 
     * @param value TRUE = enabled<br>FALSE = disabled<br>NULL = use global
     */
    public void setFriendlyFire(FlagValue value) {
        data.friendlyFire = value.toString();
        try {
            Database.get().update(data, new String[]{"friendlyFire"}, new Object[]{data.friendlyFire});
        } catch (DatabaseWriteException ex) {
            Canary.logStackTrace("Error updating 'friendlyFire' in Plot '"
                    + data.x + ":" + data.z + ":" + data.world + "'. ", ex);
        }
    }

    /**
     * Is pvp Allowed?
     * @return true - pvp disabled<br>false - pvp enabled
     */
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
