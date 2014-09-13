
package net.visualillusionsent.fivestartowns.town;

import net.canarymod.Canary;
import net.canarymod.ToolBox;
import net.canarymod.database.Database;
import net.canarymod.database.exceptions.DatabaseReadException;
import net.canarymod.database.exceptions.DatabaseWriteException;
import net.visualillusionsent.fivestartowns.Config;
import net.visualillusionsent.fivestartowns.database.TownAccess;
import net.visualillusionsent.fivestartowns.flag.FlagType;
import net.visualillusionsent.fivestartowns.flag.FlagValue;
import net.visualillusionsent.fivestartowns.flag.Flagable;
import net.visualillusionsent.fivestartowns.job.JobManager;
import net.visualillusionsent.fivestartowns.job.JobType;
import net.visualillusionsent.fivestartowns.plot.PlotManager;
import net.visualillusionsent.fivestartowns.rank.RankManager;
import net.visualillusionsent.fivestartowns.rank.TownRank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Somners
 */
public class Town extends Flagable {

    public final int townId;
    /** Name of this Town. */
    public String name;
    public double balance;
    public int bonusPlots;
    public String welcome;
    public String farewell;

    public Town(int uuid) {
        this.townId = uuid;
        this.load();
    }
    
    /**
     * Gets the UUID for this Town object.
     * 
     * @return unique id for this town 
     */
    public int getUUID() {
        return this.townId;
    }

    /**
     * Gets the names of the player who owns this town.
     * @return
     */
    public List<String> getOwnerName() {
        List<String> names = new ArrayList<String>();
        for (TownPlayer tp : JobManager.get().getJobHolders(townId, JobType.OWNER.getID())) {
            names.add(tp.getName());
        };
        return names;
    }

    /**
     * Gets the name of this Town.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Gets a list of names of Town Assistants.
     * @return
     */
    public List<String> getAssistantName() {
        List<String> names = new ArrayList<String>();
        for (TownPlayer tp : JobManager.get().getJobHolders(townId, JobType.ASSISTANT.getID())) {
            names.add(tp.getName());
        };
        return names;
    }

    /**
     * Gets a list of names of members.
     * @return
     */
    public List<String> getMembers() {
        return TownManager.get().getMemberNames(townId);
    }

    /**
     * Gets how many members belong to this town. Owners and Assistants are
     * included in this number.
     * @return
     */
    public int getPopulation() {
        return getMembers().size();
    }

    /**
     * Get the TownPlayer instance of the town owner.
     * @return
     */
    public List<TownPlayer> getOwner() {
        return JobManager.get().getJobHolders(townId, JobType.OWNER.getID());
    }

    /**
     * Get a list of TownPlayer instances for the town assistants.
     * @return
     */
    public List<TownPlayer> getAssistant() {
        return JobManager.get().getJobHolders(townId, JobType.ASSISTANT.getID());
    }

    /**
     * Add an Assistant to this town.
     * @param tp Townplayer instance of the assistant to add.
     */
    public void addAssistant(TownPlayer tp) {
        JobManager.get().addJob(townId, tp.getUUID(), JobType.ASSISTANT);
    }

    /**
     * Add an Assistant to this town.
     * @param nameOrUUID Name of the assistant to add.
     */
    public void addAssistant(String nameOrUUID) {
        String uuid = ToolBox.isUUID(nameOrUUID) ? nameOrUUID : ToolBox.usernameToUUID(nameOrUUID);
        JobManager.get().addJob(this.townId, uuid, JobType.ASSISTANT);
    }

    /**
     * Remove an assistant from this town.
     * @param tp TownPlayer instance of the assitant to remove
     */
    public void removeAssistant(TownPlayer tp) {
        JobManager.get().removeJob(this.townId, tp.getUUID(), JobType.ASSISTANT);
    }

    /**
     * Remove an assistant from this town.
     * @param name Name of the assistant to remove
     */
    public void removeAssistant(String nameOrUUID) {
        String uuid = ToolBox.isUUID(nameOrUUID) ? nameOrUUID : ToolBox.usernameToUUID(nameOrUUID);
        JobManager.get().removeJob(this.townId, uuid, JobType.ASSISTANT);
    }

    /**
     * Get the amount of Bonus Plots Awarded to this town.
     * @return
     */
    public int getBonus() {
        return bonusPlots;
    }

    /**
     * Set the amount of Bonus Plots awarded to this town.
     * @param num
     */
    public void setBonus(int num) {
        bonusPlots = num;
        this.setDirty(true);
    }

    /**
     * Add some bonus Plots to this town.
     * @param toAdd
     */
    public void addBonus(int toAdd) {
        bonusPlots += toAdd;
        this.setDirty(true);
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
        return this.canUseFlag(flag.getName());
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
     * Gets the message displayed upon entering this town.
     * @return
     */
    public String getWelcome() {
        return welcome;
    }

    /**
     * Gets the message displayed upon leaving this town.
     * @return
     */
    public String getFarewell() {
        return farewell;
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
        farewell = msg;
        this.setDirty(true);
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
        welcome = msg;
        this.setDirty(true);
        return true;
    }

    /**
     * The most plots that this town can claim
     *
     * @return
     */
    public int getMaxClaimCount() {
        return this.getPopulation() * Config.get().getPlotsPerPlayer();
    }

    /**
     * The current amount of plots that this town has claimed.
     *
     * @return
     */
    public int getCurrentClaimCount() {
        return PlotManager.get().getTownPlots(this.getName()).length;
    }

    @Override
    public void load() {
        TownAccess data = new TownAccess();
        try {
            HashMap<String, Object> filter = new HashMap<String, Object>();
            filter.put("uuid", this.townId);
            Database.get().load(data, filter);
        } catch (DatabaseReadException ex) {
            Canary.log.trace("Error Loading Town Data", ex);
        }
        this.name = data.name;
        this.balance = data.balance;
        this.bonusPlots = data.bonusPlots;
        this.farewell = data.farewell;
        this.welcome = data.welcome;
        this.creeperNerf = FlagValue.valueOf(data.creeperNerf);
        this.friendlyFire = FlagValue.valueOf(data.friendlyFire);
        this.nopvp = FlagValue.valueOf(data.nopvp);
        this.ownerPlot = FlagValue.valueOf(data.ownerPlot);
        this.protection = FlagValue.valueOf(data.protection);
        this.sanctuary = FlagValue.valueOf(data.sanctuary);
        
    }

    @Override
    public void save() {
        TownAccess data = new TownAccess();
        data.name = this.name;
        data.balance = this.balance;
        data.bonusPlots = this.bonusPlots;
        data.farewell = this.farewell;
        data.welcome = this.welcome;
        data.creeperNerf = this.creeperNerf.toString();
        data.friendlyFire = this.friendlyFire.toString();
        data.nopvp = this.nopvp.toString();
        data.ownerPlot = this.ownerPlot.toString();
        data.protection = this.protection.toString();
        data.sanctuary = this.sanctuary.toString();
        try {
            HashMap<String, Object> filter = new HashMap<String, Object>();
            filter.put("uuid", this.townId);
            Database.get().update(data, filter);
        } catch (DatabaseWriteException ex) {
            Canary.log.trace("Error Loading Town Data", ex);
        }
    }
}
