
package net.visualillusionsent.fivestartowns.town;

import net.visualillusionsent.fivestartowns.Config;
import net.visualillusionsent.fivestartowns.database.TownAccess;
import net.visualillusionsent.fivestartowns.flag.FlagType;
import static net.visualillusionsent.fivestartowns.flag.FlagType.CREEPER_NERF;
import static net.visualillusionsent.fivestartowns.flag.FlagType.FRIENDLY_FIRE;
import static net.visualillusionsent.fivestartowns.flag.FlagType.NO_PVP;
import static net.visualillusionsent.fivestartowns.flag.FlagType.PROTECTION;
import static net.visualillusionsent.fivestartowns.flag.FlagType.SANCTUARY;
import net.visualillusionsent.fivestartowns.flag.FlagValue;
import net.visualillusionsent.fivestartowns.flag.Flagable;
import net.visualillusionsent.fivestartowns.plot.PlotManager;
import net.visualillusionsent.fivestartowns.rank.RankManager;
import net.visualillusionsent.fivestartowns.rank.TownRank;
import java.util.ArrayList;
import java.util.List;
import net.canarymod.Canary;
import net.canarymod.database.Database;
import net.canarymod.database.exceptions.DatabaseWriteException;

/**
 *
 * @author Somners
 */
public class Town implements Flagable {

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
        return TownManager.get().getTownPlayer(data.owner);
    }

    /**
     * Get a list of TownPlayer instances for the town assistants.
     * @return
     */
    public List<TownPlayer> getAssistant() {
        List<TownPlayer> list = new ArrayList<TownPlayer>();
        for (String name : data.assistant) {
            list.add(TownManager.get().getTownPlayer(name));
        }
        return list;
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
                Canary.logStacktrace("Error updating assistant in '" + this.getName() + "'. ", ex);
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
                Canary.logStacktrace("Error updating assistant in '" + this.getName() + "'. ", ex);
            }
        }
    }

    /**
     * Remove an assistant from this town.
     * @param tp TownPlayer instance of the assitant to remove
     */
    public void removeAssistant(TownPlayer tp) {
        for (String s : data.assistant) {
            if (s.equals(tp.getName())) {
                data.assistant.remove(s);
                break;
            }
        }
        try {
            Database.get().update(data, new String[]{"assistant"}, new Object[]{data.assistant});
        } catch (DatabaseWriteException ex) {
            Canary.logStacktrace("Error updating assistant in '" + this.getName() + "'. ", ex);
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
            Canary.logStacktrace("Error updating assistant in '" + this.getName() + "'. ", ex);
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
            Canary.logStacktrace("Error updating Bonus Plots in '" + this.getName() + "'. ", ex);
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
            Canary.logStacktrace("Error updating Bonus Plots in '" + this.getName() + "'. ", ex);
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
            Canary.logStacktrace("Error updating Balance in '" + this.getName() + "'. ", ex);
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
            Canary.logStacktrace("Error updating Balance in '" + this.getName() + "'. ", ex);
        }
    }

    /**
     *
     * @return
     */
    public TownRank getTownRank() {
        return RankManager.get().getRank(this);
    }

    /**
     *
     * @param flag
     * @return
     */
    public boolean canUseFlag(FlagType flag) {
        return this.getTownRank().getFlags().contains(flag.getName());
    }

    /**
     *
     * @param flag
     * @return
     */
    public boolean canUseFlag(String flag) {
        return this.getTownRank().getFlags().contains(flag);
    }

    /**
     * Gets a list of flags that can be set.
     * @return
     */
    public List<FlagType> getFlags() {
        List<FlagType> list = new ArrayList<FlagType>();
        for (String name : this.getTownRank().getFlags()) {
            list.add(FlagType.fromString(name));
        }
        return list;
    }

    /**
     * Gets a list of flags that can be set.
     * @return
     */
    public List<String> getFlagNames() {
        List<String> list = new ArrayList<String>();
        for (String name : this.getTownRank().getFlags()) {
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
     * Get the name of the owner of this plot within the town.
     * @return
     */
    @Override
    public String getPlotOwnerName() {
        return "";
    }

    /**
     * Get the TownPlayer instance of the owner of this plot within the town.
     * @return
     */
    @Override
    public TownPlayer getPlotOwner() {
        return null;
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
        }
        return null;
    }

    /**
     * Sets whether or not pvp is disabled.
     * @param value TRUE = enabled<br>FALSE = disabled<br>NULL = use global
     */
    @Override
    public void setNoPvp(FlagValue value) {
        data.nopvp = value.toString();
        try {
            Database.get().update(data, new String[]{"nopvp"}, new Object[]{data.nopvp});
        } catch (DatabaseWriteException ex) {
            Canary.logStacktrace("Error updating 'ownerPlot' in Plot '" + this.getName(), ex);
        }
    }

    /**
     * Sets whether or not this is an owner plot.
     * @param value TRUE = enabled<br>FALSE = disabled<br>NULL = use global
     */
    @Override
    public void setOwnerPlot(FlagValue value) {
        // Not applicable
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
            Canary.logStacktrace("Error updating 'protection' in Town '" + this.getName(), ex);
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
            Canary.logStacktrace("Error updating 'sanctuary' in Town '" + this.getName(), ex);
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
            Canary.logStacktrace("Error updating 'creeperNerf' in Town '" + this.getName(), ex);
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
            Canary.logStacktrace("Error updating 'friendlyFire' in Town '" + this.getName(), ex);
        }
    }

    /**
     * Is pvp Allowed?
     * @return true - pvp disabled<br>false - pvp enabled
     */
    @Override
    public boolean getNoPvp() {
        return FlagValue.getType(data.nopvp).getBoolean();
    }

    /**
     * Are protections on?
     * @return true - enabled<br>false - disabled
     */
    @Override
    public boolean getProtected() {
        return FlagValue.getType(data.protection).getBoolean();
    }

    /**
     * Can mobs Spawn?
     * @return true - mob spawning blocked<br>false - mob spawning allowed
     */
    @Override
    public boolean getSanctuary() {
        return FlagValue.getType(data.sanctuary).getBoolean();
    }

    /**
     * Are Creepers Nerfed?
     * @return true - creepers disabled<br>false - creepers enabled
     */
    @Override
    public boolean getCreeperNerf() {
        return FlagValue.getType(data.creeperNerf).getBoolean();
    }

    /**
     * Is friendly Fire on?
     * @return true - FF enabled<br>false - FF disabled
     */
    @Override
    public boolean getFriendlyFire() {
        return FlagValue.getType(data.friendlyFire).getBoolean();
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
            Canary.logStacktrace("Error updating 'farewell' in '" + this.getName() + "'. ", ex);
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
            Canary.logStacktrace("Error updating 'welcome' in '" + this.getName() + "'. ", ex);
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
