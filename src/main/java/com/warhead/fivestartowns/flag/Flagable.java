/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.warhead.fivestartowns.flag;

import com.warhead.fivestartowns.town.TownPlayer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author somners
 */
public interface Flagable {

    /**
     * Are Creepers Nerfed?
     * @return true - creepers disabled<br>false - creepers enabled
     */
    boolean getCreeperNerf();

    FlagValue getFlagValue(FlagType type);

    /**
     * Is friendly Fire on?
     * @return true - FF enabled<br>false - FF disabled
     */
    boolean getFriendlyFire();

    /**
     * Is pvp Allowed?
     * @return true - pvp disabled<br>false - pvp enabled
     */
    boolean getNoPvp();

    /**
     * Get the TownPlayer instance of the owner of this plot within the town.
     * @return
     */
    TownPlayer getPlotOwner();

    /**
     * Get the name of the owner of this plot within the town.
     * @return
     */
    String getPlotOwnerName();

    /**
     * Are protections on?
     * @return true - enabled<br>false - disabled
     */
    boolean getProtected();

    /**
     * Can mobs Spawn?
     * @return true - mob spawning blocked<br>false - mob spawning allowed
     */
    boolean getSanctuary();

    /**
     * Sets whether or not creepers are disabled
     * @param value TRUE = enabled<br>FALSE = disabled<br>NULL = use global
     */
    void setCreeperNerf(FlagValue value);

    /**
     * Sets this flagtype and returns what the FlagType was set to.
     * @param type
     * @return
     */
    void setFlag(FlagType type, FlagValue value);

    /**
     * Sets whether or not friendly fire is enabled
     * @param value TRUE = enabled<br>FALSE = disabled<br>NULL = use global
     */
    void setFriendlyFire(FlagValue value);

    /**
     * Sets whether or not pvp is disabled.
     * @param value TRUE = enabled<br>FALSE = disabled<br>NULL = use global
     */
    void setNoPvp(FlagValue value);

    /**
     * Sets whether or not this is an owner plot.
     * @param value TRUE = enabled<br>FALSE = disabled<br>NULL = use global
     */
    void setOwnerPlot(FlagValue value);

    /**
     * Sets whether or not protection is enabled
     * @param value TRUE = enabled<br>FALSE = disabled<br>NULL = use global
     */
    void setProtected(FlagValue value);

    /**
     * Sets whether or not sanctuary is enabledfalse - sanctuary off
     * @param value TRUE = enabled<br>FALSE = disabled<br>NULL = use global
     */
    void setSanctuary(FlagValue value);

     /**
     *
     * @param flag
     * @return
     */
    boolean canUseFlag(String flag);
    
    /**
     *
     * @param flag
     * @return
     */
    public boolean canUseFlag(FlagType flag);
    
    /**
     * Gets a list of flags that can be set.
     * @return 
     */
    public List<FlagType> getFlags();
    
    /**
     * Gets a list of flags that can be set.
     * @return 
     */
    public List<String> getFlagNames();

   String[] getEnabledFlags();
    
}
