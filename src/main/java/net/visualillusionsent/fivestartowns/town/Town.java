
package net.visualillusionsent.fivestartowns.town;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.visualillusionsent.fivestartowns.Config;
import net.visualillusionsent.fivestartowns.FiveStarTowns;
import net.visualillusionsent.fivestartowns.database.FSTDatabase;
import net.visualillusionsent.fivestartowns.database.JDBCHelper;
import net.visualillusionsent.fivestartowns.flag.FlagType;
import net.visualillusionsent.fivestartowns.flag.FlagValue;
import net.visualillusionsent.fivestartowns.flag.Flagable;
import net.visualillusionsent.fivestartowns.plot.PlotManager;
import net.visualillusionsent.fivestartowns.rank.RankManager;
import net.visualillusionsent.fivestartowns.rank.TownRank;

/**
 *
 * @author Somners
 */
public class Town extends Flagable {

    /** Name of this Town. */
    public String name;
    public String owner;
    public List<String> assistant;
    public double balance;
    public int bonusPlots;
    public String welcome;
    public String farewell;

    public Town(String name) {
        this.name = name;
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
        return FiveStarTowns.database().getTownPlayerNames(name);
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

    private final String TOWN_TABLE = "towns";
    private final String OWNER_PLOT = "ownerPlot";
    private final String NO_PVP = "nopvp";
    private final String FRIENDLY_FIRE = "friendlyFire";
    private final String SANCTUARY = "sanctuary";
    private final String PROTECTION = "protection";
    private final String CREEPER_NERF = "creeperNerf";
    private final String NAME = "name";
    private final String OWNER = "owner";
    private final String ASSISTANT = "assistant";
    private final String BONUS_PLOTS = "bonusPlots";
    private final String WELCOME = "welcome";
    private final String FAREWELL = "farewell";

    @Override
    public void load() {
        ResultSet rs = null;

        try {
            rs = FiveStarTowns.database().getResultSet(TOWN_TABLE,
                    FiveStarTowns.database().newQuery().add(NAME, name), 1);

            if (rs != null && rs.next()) {
                this.creeperNerf = FlagValue.getType(rs.getString(CREEPER_NERF));
                this.friendlyFire = FlagValue.getType(rs.getString(FRIENDLY_FIRE));
                this.nopvp = FlagValue.getType(rs.getString(NO_PVP));
                this.ownerPlot = FlagValue.getType(rs.getString(OWNER_PLOT));
                this.protection = FlagValue.getType(rs.getString(PROTECTION));
                this.sanctuary = FlagValue.getType(rs.getString(SANCTUARY));
                this.owner = rs.getString(OWNER);
                this.bonusPlots = rs.getInt(BONUS_PLOTS);
                this.welcome = rs.getString(WELCOME);
                this.farewell = rs.getString(FAREWELL);
            }
        } catch (SQLException ex) {
            FiveStarTowns.get().getPluginLogger().warning("Error Querying MySQL ResultSet in "
                    + TOWN_TABLE);
        }
    }

    @Override
    public void save() {
        FSTDatabase.Query where = FiveStarTowns.database().newQuery();
        where.add(NAME, name);
        FSTDatabase.Query update = FiveStarTowns.database().newQuery();
        update.add(OWNER, owner).add(ASSISTANT, JDBCHelper.getListString(assistant)).add(OWNER_PLOT, ownerPlot);
        update.add(NO_PVP, nopvp).add(FRIENDLY_FIRE, friendlyFire).add(SANCTUARY, sanctuary);
        update.add(PROTECTION, protection).add(CREEPER_NERF, creeperNerf).add(BONUS_PLOTS, bonusPlots);
        update.add(WELCOME, welcome).add(FAREWELL, farewell);
        FiveStarTowns.database().updateEntry(TOWN_TABLE, where, update);
    }
}
