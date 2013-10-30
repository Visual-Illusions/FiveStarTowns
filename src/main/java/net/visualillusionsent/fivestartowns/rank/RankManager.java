package net.visualillusionsent.fivestartowns.rank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.canarymod.Canary;
import net.visualillusionsent.fivestartowns.FiveStarTowns;
import net.visualillusionsent.fivestartowns.Saveable;
import net.visualillusionsent.fivestartowns.database.JDBCHelper;
import net.visualillusionsent.fivestartowns.town.Town;

/**
 *
 * @author somners
 */
public class RankManager extends Saveable {

    private static final List<TownRank> ranks = new ArrayList<TownRank>();
    private static RankManager instance = null;

    public static RankManager get() {
        if (instance == null) {
            instance = new RankManager();
        }
        return instance;
    }

    /**
     * Gets the current rank for Town.
     * @param town the town to get the rank of.
     * @return the towns rank
     */
    public TownRank getRank(Town town) {
        int pop = town.getPopulation();
        TownRank rank = null;
        for (TownRank r : ranks) {
            if (rank == null) {
                rank = r;
            }
            else if (pop - r.getPopulationRequirement() > 0) {
                if (rank == null) {
                    rank = r;
                }
                else if (pop - r.getPopulationRequirement() < pop - rank.getPopulationRequirement()) {
                    rank = r;
                }
            }
        }
        if (rank == null) {
            Canary.logSevere("No TownRank in FiveStarTowns with a population " +
                    "requirement of 0 found, errors will follow. Fix this now!!!");
        }
        return rank;
    }
    private final String RANK_TABLE = "ranks";
    private final String POPULATION_REQ = "populationReq";
    private final String TOWN_TYPE = "townType";
    private final String MAYOR_PREFIX = "mayorPrefix";
    private final String MAYOR_SUFFIX = "mayorSuffix";
    private final String ASSISTANT_PREFIX = "assistantPrefix";
    private final String ASSISTANT_SUFFIX = "assistantSuffix";
    private final String TOWN_PREFIX = "townPrefix";
    private final String TOWN_SUFFIX = "townSuffix";
    private final String FLAGS = "flags";

    @Override
    public void load() {

        ResultSet rs = null;

        try {
            rs = FiveStarTowns.database().getResultSet(RANK_TABLE, null, 1000);
            if (rs != null) {
                while (rs.next()) {
                    TownRank toLoad = new TownRank(rs.getInt(POPULATION_REQ), rs.getString(TOWN_TYPE),
                            rs.getString(MAYOR_PREFIX), rs.getString(MAYOR_SUFFIX), rs.getString(ASSISTANT_PREFIX),
                            rs.getString(ASSISTANT_SUFFIX), rs.getString(TOWN_PREFIX), rs.getString(TOWN_SUFFIX),
                            ((List<String>)JDBCHelper.getList(JDBCHelper.DataType.STRING, rs.getString(FLAGS))));
                    ranks.add(toLoad);
                }
            }
        } catch (SQLException ex) {
            FiveStarTowns.get().getPluginLogger().warning("Error Querying MySQL ResultSet in "
                    + RANK_TABLE);
        }
    }

    @Override
    public void save() {
        /** these don't get saved quite yet. */
    }

}
