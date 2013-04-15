/*
 * This file is copyright of WarHead Gaming. It is open Source and
 * free to use. Use of our code without express consent from the
 * developers is in violation of our license which has been provided
 * for you in the source and compiled jar.
 */
package com.warhead.fivestartowns.town;

import com.warhead.fivestartowns.Config;
import com.warhead.fivestartowns.plot.PlotManager;
import com.warhead.fivestartowns.database.TownAccess;
import java.util.List;
import net.canarymod.Canary;
import net.canarymod.database.Database;
import net.canarymod.database.exceptions.DatabaseWriteException;

/**
 *
 * @author Somners
 */
public class Town {

    private final TownAccess data;

    public Town(TownAccess town) {
        this.data = town;
    }

    /**
     * Gets the name of the player who owns this town.
     * @return
     */
    public String getOwnerName() {
        return data.owner;
    }

    /**
     * Gets the name of this Town.
     * @return
     */
    public String getName() {
        return data.name;
    }

    /**
     * Gets a list of names of Town Assistants.
     * @return
     */
    public List<String> getAssistantName() {
        return data.assistant;
    }

    /**
     * Gets a list of names of members.
     * @return
     */
    public List<String> getMembers() {
        return data.members;
    }

    /**
     * Gets how many members belong to this town. Owners and Assistants are
     * included in this number.
     * @return
     */
    public int getMemberSize() {
        return data.members.size() + data.assistant.size() + 1;
    }

    /**
     * Get the TownPlayer instance of the town owner.
     * @return
     */
    public TownPlayer getOwner() {
        return null;// TODO
    }

    /**
     * Get a list of TownPlayer instances for the town assistants.
     * @return
     */
    public List<TownPlayer> getAssistant() {
        return null;// TODO
    }

    /**
     * Add an Assistant to this town.
     * @param tp Townplayer instance of the assistant to add.
     */
    public void addAssistant(TownPlayer tp) {
        if (!data.assistant.contains("")) {
            data.assistant.add("");// TODO
            try {
                Database.get().update(data, new String[]{"assistant"}, new Object[]{data.assistant});
            } catch (DatabaseWriteException ex) {
                Canary.logStackTrace("Error updating assistant in '" + this.getName() + "'. ", ex);
            }
        }
    }

    /**
     * Add an Assistant to this town.
     * @param name Name of the assistant to add.
     */
    public void addAssistant(String name) {
        if (!data.assistant.contains(name)) {
            data.assistant.add(name);
            try {
                Database.get().update(data, new String[]{"assistant"}, new Object[]{data.assistant});
            } catch (DatabaseWriteException ex) {
                Canary.logStackTrace("Error updating assistant in '" + this.getName() + "'. ", ex);
            }
        }
    }

    /**
     * Remove an assistant from this town.
     * @param tp TownPlayer instance of the assitant to remove
     */
    public void removeAssistant(TownPlayer tp) {
        for (String s : data.assistant) {
            if (s.equals("")) {// TODO
                data.assistant.remove(s);
                break;
            }
        }
        try {
            Database.get().update(data, new String[]{"assistant"}, new Object[]{data.assistant});
        } catch (DatabaseWriteException ex) {
            Canary.logStackTrace("Error updating assistant in '" + this.getName() + "'. ", ex);
        }
    }

    /**
     * Remove an assistant from this town.
     * @param name Name of the assistant to remove
     */
    public void removeAssistant(String name) {
        for (String s : data.assistant) {
            if (s.equals("")) {// TODO
                data.assistant.remove(s);
                break;
            }
        }
        try {
            Database.get().update(data, new String[]{"assistant"}, new Object[]{data.assistant});
        } catch (DatabaseWriteException ex) {
            Canary.logStackTrace("Error updating assistant in '" + this.getName() + "'. ", ex);
        }
    }

    /**
     * Get the amount of Bonus Plots Awarded to this town.
     * @return
     */
    public int getBonus() {
        return data.bonusPlots;
    }

    /**
     * Set the amount of Bonus Plots awarded to this town.
     * @param num
     */
    public void setBonus(int num) {
        data.bonusPlots = num;
        try {
            Database.get().update(data, new String[]{"bonusPlots"}, new Object[]{data.bonusPlots});
        } catch (DatabaseWriteException ex) {
            Canary.logStackTrace("Error updating Bonus Plots in '" + this.getName() + "'. ", ex);
        }

    }

    /**
     * Add some bonus Plots to this town.
     * @param toAdd
     */
    public void addBonus(int toAdd) {
        data.bonusPlots += toAdd;
        try {
            Database.get().update(data, new String[]{"bonusPlots"}, new Object[]{data.bonusPlots});
        } catch (DatabaseWriteException ex) {
            Canary.logStackTrace("Error updating Bonus Plots in '" + this.getName() + "'. ", ex);
        }
    }

    /**
     * Gets how many members are a part of this town.
     * @return
     */
    public int getMemberCount() {
        return getMembers().size();
    }

    /**
     * Gets this towns bank balance.
     * @return
     */
    public double getBalance() {
        return data.balance;
    }

    /**
     * Add to this towns bank balance.
     * @param toAdd
     */
    public void addBalance(double toAdd) {
        data.balance += toAdd;
        try {
            Database.get().update(data, new String[]{"balance"}, new Object[]{data.balance});
        } catch (DatabaseWriteException ex) {
            Canary.logStackTrace("Error updating Balance in '" + this.getName() + "'. ", ex);
        }
    }

    /**
     * remove from this towns bank balance.
     * @param toRemove
     */
    public void removeBalance(double toRemove) {
        data.balance -= toRemove;
        try {
            Database.get().update(data, new String[]{"balance"}, new Object[]{data.balance});
        } catch (DatabaseWriteException ex) {
            Canary.logStackTrace("Error updating Balance in '" + this.getName() + "'. ", ex);
        }
    }

    /**
     *
     * @return
     */
    public int getTownRank() {
        return 0;// TODO
    }

    /**
     *
     * @return
     */
    public String getRankName() {
        return "";// TODO
    }

    /**
     *
     * @return
     */
    public String getMayorPrefix() {
        return "";// TODO
    }

    /**
     *
     * @return
     */
    public String getAssistantPrefix() {
        return "";// TODO
    }

    /**
     *
     * @param flag
     * @return
     */
    public boolean canUseFlag(String flag) {
        return false;// TODO
    }

    /**
     * Sets whether or not pvp is disabled globally. Per plot settings
     * override this.
     * @param toSet true - no pvp allowed<br>false - pvp allowed here
     */
    public void setNoPvp(boolean value) {
        data.nopvp = value;
        try {
            Database.get().update(data, new String[]{"nopvp"}, new Object[]{data.nopvp});
        } catch (DatabaseWriteException ex) {
            Canary.logStackTrace("Error updating 'nopvp' in '" + this.getName() + "'. ", ex);
        }
    }

    /**
     * Sets whether or not protection is enabled globally. Per plot settings
     * override this.
     * @param toSet true - protections on<br>false - protections off.
     */
    public void setProtected(boolean value) {
        data.protection = value;
        try {
            Database.get().update(data, new String[]{"protection"}, new Object[]{data.protection});
        } catch (DatabaseWriteException ex) {
            Canary.logStackTrace("Error updating 'protection' in '" + this.getName() + "'. ", ex);
        }
    }

    /**
     * Sets whether or not sanctuary is enabled globally. Per plot settings
     * override this.
     * @param toSet true - sanctuary on<br>false - sanctuary off
     */
    public void setSanctuary(boolean value) {
        data.sanctuary = value;
        try {
            Database.get().update(data, new String[]{"sanctuary"}, new Object[]{data.sanctuary});
        } catch (DatabaseWriteException ex) {
            Canary.logStackTrace("Error updating 'sanctuary' in '" + this.getName() + "'. ", ex);
        }
    }

    /**
     * Sets whether or not creepers are disabled globally. Per plot settings
     * override this.
     * @param toSet true - no creepin' allowed<br>false - creepin' allowed here
     */
    public void setCreeperNerf(boolean value) {
        data.creeperNerf = value;
        try {
            Database.get().update(data, new String[]{"creeperNerf"}, new Object[]{data.creeperNerf});
        } catch (DatabaseWriteException ex) {
            Canary.logStackTrace("Error updating 'creeperNerf' in '" + this.getName() + "'. ", ex);
        }
    }

    /**
     * Sets whether or not friendly fire is enabled globally. Per plot settings
     * override this.
     * @param toSet true - frienldy fire allowed<br>false - friendly fire not allowed here
     */
    public void setFriendlyFire(boolean value) {
        data.friendlyFire = value;
        try {
            Database.get().update(data, new String[]{"friendlyFire"}, new Object[]{data.friendlyFire});
        } catch (DatabaseWriteException ex) {
            Canary.logStackTrace("Error updating 'friendlyFire' in '" + this.getName() + "'. ", ex);
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

    /**
     * Gets the message displayed upon entering this town.
     * @return
     */
    public String getWelcome() {
        return data.welcome;
    }

    /**
     * Gets the message displayed upon leaving this town.
     * @return
     */
    public String getFarewell() {
        return data.farewell;
    }

    /**
     * Sets the message displayed upon leaving this town.
     *
     * @param msg
     */
    public boolean setFarewell(String msg) {
        if (msg.length() > 90) {
            return false;
        }
        data.farewell = msg;
        try {
            Database.get().update(data, new String[]{"farewell"}, new Object[]{data.farewell});
        } catch (DatabaseWriteException ex) {
            Canary.logStackTrace("Error updating 'farewell' in '" + this.getName() + "'. ", ex);
        }
        return true;
    }

    /**
     * Sets the message displayed upon entering this town.
     *
     * @param msg
     */
    public boolean setWelcome(String msg) {
        if (msg.length() > 90) {
            return false;
        }
        data.welcome = msg;
        try {
            Database.get().update(data, new String[]{"welcome"}, new Object[]{data.welcome});
        } catch (DatabaseWriteException ex) {
            Canary.logStackTrace("Error updating 'welcome' in '" + this.getName() + "'. ", ex);
        }
        return true;
    }

    /**
     * The most plots that this town can claim
     *
     * @return
     */
    public int getMaxClaimCount() {
        return this.getMemberSize() * Config.get().getPlotsPerPlayer();
    }

    /**
     * The current amount of plots that this town has claimed.
     *
     * @return
     */
    public int getCurrentClaimCount() {
        return PlotManager.get().getTownPlots(this.getName()).length;
    }
}
