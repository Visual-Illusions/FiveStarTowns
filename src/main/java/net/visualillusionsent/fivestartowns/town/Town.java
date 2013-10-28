
package net.visualillusionsent.fivestartowns.town;

import java.util.ArrayList;
import java.util.List;
import net.visualillusionsent.fivestartowns.Config;
import net.visualillusionsent.fivestartowns.database.TownAccess;
import net.visualillusionsent.fivestartowns.flag.FlagType;
import net.visualillusionsent.fivestartowns.flag.Flagable;
import net.visualillusionsent.fivestartowns.plot.PlotManager;
import net.visualillusionsent.fivestartowns.rank.RankManager;
import net.visualillusionsent.fivestartowns.rank.TownRank;

/**
 *
 * @author Somners
 */
public class Town extends Flagable {

    private final TownAccess data;
    /** ID for this Town, serves as Primary Key, Auto Incremented. */
    public int id;
    /** Name of this Town. */
    public String name;
    public String owner;
    public List<String> assistant;
    public List<String> members;
    public double balance;
    public int bonusPlots;
    public String welcome;
    public String farewell;

    public Town(TownAccess town) {
        this.data = town;
    }

    /**
     * Gets the name of the player who owns this town.
     * @return
     */
    public String getOwnerName() {
        return owner;
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
        return assistant;
    }

    /**
     * Gets a list of names of members.
     * @return
     */
    public List<String> getMembers() {
        return members;
    }

    /**
     * Gets how many members belong to this town. Owners and Assistants are
     * included in this number.
     * @return
     */
    public int getPopulation() {
        return members.size() + assistant.size() + 1;
    }

    /**
     * Get the TownPlayer instance of the town owner.
     * @return
     */
    public TownPlayer getOwner() {
        return TownManager.get().getTownPlayer(owner);
    }

    /**
     * Get a list of TownPlayer instances for the town assistants.
     * @return
     */
    public List<TownPlayer> getAssistant() {
        List<TownPlayer> list = new ArrayList<TownPlayer>();
        for (String name : assistant) {
            list.add(TownManager.get().getTownPlayer(name));
        }
        return list;
    }

    /**
     * Add an Assistant to this town.
     * @param tp Townplayer instance of the assistant to add.
     */
    public void addAssistant(TownPlayer tp) {
        this.addAssistant(tp.getName());
    }

    /**
     * Add an Assistant to this town.
     * @param name Name of the assistant to add.
     */
    public void addAssistant(String name) {
        if (!assistant.contains(name)) {
            assistant.add(name);
            this.setDirty(true);
        }
    }

    /**
     * Remove an assistant from this town.
     * @param tp TownPlayer instance of the assitant to remove
     */
    public void removeAssistant(TownPlayer tp) {
        this.removeAssistant(tp.getName());
    }

    /**
     * Remove an assistant from this town.
     * @param name Name of the assistant to remove
     */
    public void removeAssistant(String name) {
        for (String s : assistant) {
            if (s.equals(name)) {
                assistant.remove(s);
                this.setDirty(true);
                break;
            }
        }
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

    private final String OWNER_PLOT = "ownerPlot";
    private final String NO_PVP = "nopvp";
    private final String FRIENDLY_FIRE = "friendlyFire";
    private final String SANCTUARY = "sanctuary";
    private final String PROTECTION = "protection";
    private final String CREEPER_NERF = "creeperNerf";
    private final String NAME = "name";
    private final String OWNER = "owner";
    private final String ASSISTANT = "assistant";
    private final String MEMBERS = "members";
    private final String BONUS_PLOTS = "bonusPlots";
    private final String WELCOME = "welcome";
    private final String FAREWELL = "farewell";

    @Override
    public void load() {
        throw new UnsupportedOperationException("Method 'load' in class 'Town' is not supported yet.");
    }

    @Override
    public void save() {
        throw new UnsupportedOperationException("Method 'save' in class 'Town' is not supported yet.");
    }
}
