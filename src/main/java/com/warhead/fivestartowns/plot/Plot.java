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
     * Toggles this flagtype and returns what the FlagType was set to.
     * @param type
     * @return 
     */
    public boolean toggleFlag(FlagType type) {
        switch(type) {
            case NO_PVP:
                this.setNoPvp(!data.nopvp);
                return data.nopvp;
            case FRIENDLY_FIRE:
                this.setFriendlyFire(!data.friendlyFire);
                return data.friendlyFire;
            case SANCTUARY:
                this.setSanctuary(!data.sanctuary);
                return data.sanctuary;
            case PROTECTION:
                this.setProtected(!data.protection);
                return data.protection;
            case CREEPER_NERF:
                this.setCreeperNerf(!data.creeperNerf);
                return data.creeperNerf;
            case OWNER_PLOT:
                this.setOwnerPlot(!data.ownerPlot);
                return data.ownerPlot;
        }
        return false;
    }
    
    public void toggleFlag(FlagType type, boolean flag) {
        switch(type) {
            case NO_PVP:
                this.setNoPvp(flag);
            case FRIENDLY_FIRE:
                this.setFriendlyFire(flag);
            case SANCTUARY:
                this.setSanctuary(flag);
            case PROTECTION:
                this.setProtected(flag);
            case CREEPER_NERF:
                this.setCreeperNerf(flag);
            case OWNER_PLOT:
                this.setOwnerPlot(flag);
        }
    }
    
    public boolean getFlagValue(FlagType type) {
        switch(type) {
            case NO_PVP:
                return data.nopvp;
            case FRIENDLY_FIRE:
                return data.friendlyFire;
            case SANCTUARY:
                return data.sanctuary;
            case PROTECTION:
                return data.protection;
            case CREEPER_NERF:
                return data.creeperNerf;
            case OWNER_PLOT:
                return data.ownerPlot;
        }
        return false;
    }

    /**
     * Sets whether or not pvp is disabled. 
     * @param toSet true - no pvp allowed<br>false - pvp allowed here
     */
    public void setNoPvp(boolean value) {
        data.nopvp = value;
        try {
            Database.get().update(data, new String[]{"nopvp"}, new Object[]{data.ownerPlot});
        } catch (DatabaseWriteException ex) {
            Canary.logStackTrace("Error updating 'ownerPlot' in Plot '"
                    + data.x + ":" + data.z + ":" + data.world + "'. ", ex);
        }
    }
    
    /**
     * Sets whether or not this is an owner plot. 
     * @param toSet true - it is an owner plot<br>false - not an owner plot
     */
    public void setOwnerPlot(boolean value) {
        data.nopvp = value;
        try {
            Database.get().update(data, new String[]{"nopvp"}, new Object[]{data.ownerPlot});
        } catch (DatabaseWriteException ex) {
            Canary.logStackTrace("Error updating 'nopvp' in Plot '"
                    + data.x + ":" + data.z + ":" + data.world + "'. ", ex);
        }
    }

    /**
     * Sets whether or not protection is enabled 
     * @param toSet true - protections on<br>false - protections off.
     */
    public void setProtected(boolean value) {
        data.protection = value;
        try {
            Database.get().update(data, new String[]{"protection"}, new Object[]{data.protection});
        } catch (DatabaseWriteException ex) {
            Canary.logStackTrace("Error updating 'protection' in Plot '"
                    + data.x + ":" + data.z + ":" + data.world + "'. ", ex);
        }
    }

    /**
     * Sets whether or not sanctuary is enabledfalse - sanctuary off
     */
    public void setSanctuary(boolean value) {
        data.sanctuary = value;
        try {
            Database.get().update(data, new String[]{"sanctuary"}, new Object[]{data.sanctuary});
        } catch (DatabaseWriteException ex) {
            Canary.logStackTrace("Error updating 'sanctuary' in Plot '"
                    + data.x + ":" + data.z + ":" + data.world + "'. ", ex);
        }
    }

    /**
     * Sets whether or not creepers are disabled 
     * @param toSet true - no creepin' allowed<br>false - creepin' allowed here
     */
    public void setCreeperNerf(boolean value) {
        data.creeperNerf = value;
        try {
            Database.get().update(data, new String[]{"creeperNerf"}, new Object[]{data.creeperNerf});
        } catch (DatabaseWriteException ex) {
            Canary.logStackTrace("Error updating 'creeperNerf' in Plot '"
                    + data.x + ":" + data.z + ":" + data.world + "'. ", ex);
        }
    }

    /**
     * Sets whether or not friendly fire is enabled 
     * @param toSet true - frienldy fire allowed<br>false - friendly fire not allowed here
     */
    public void setFriendlyFire(boolean value) {
        data.friendlyFire = value;
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
        return data.nopvp;
    }

    /**
     * Are protections on?
     * @return true - enabled<br>false - disabled
     */
    public boolean getProtected() {
        return data.protection;
    }

    /**
     * Can mobs Spawn?
     * @return true - mob spawning blocked<br>false - mob spawning allowed
     */
    public boolean getSanctuary() {
        return data.sanctuary;
    }

    /**
     * Are Creepers Nerfed?
     * @return true - creepers disabled<br>false - creepers enabled
     */
    public boolean getCreeperNerf() {
        return data.creeperNerf;
    }

    /**
     * Is friendly Fire on?
     * @return true - FF enabled<br>false - FF disabled
     */
    public boolean getFriendlyFire() {
        return data.friendlyFire;
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
