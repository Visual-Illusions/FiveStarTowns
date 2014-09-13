package net.visualillusionsent.fivestartowns.rank;

import net.canarymod.Canary;
import net.canarymod.database.DataAccess;
import net.canarymod.database.Database;
import net.canarymod.database.exceptions.DatabaseReadException;
import net.canarymod.database.exceptions.DatabaseWriteException;
import net.visualillusionsent.fivestartowns.Saveable;
import net.visualillusionsent.fivestartowns.database.RankAccess;
import net.visualillusionsent.fivestartowns.town.Town;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
            Canary.log.warn("No TownRank in FiveStarTowns with a population " +
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
        ranks.clear();

        List<DataAccess> rankData = new ArrayList<DataAccess>();
        RankAccess rankAccess = new RankAccess();
        try {
            HashMap<String, Object> filter = new HashMap<String, Object>();
            Database.get().loadAll(rankAccess, rankData, filter);
        } catch (DatabaseReadException ex) {
            Canary.log.trace("Error Loading Town Data", ex);
        }

        if(rankData.size() == 0) {
            createDefaults();
            load();
            return;
        }

        for (DataAccess da : rankData) {
            RankAccess data = (RankAccess) da;

            TownRank toLoad = new TownRank(data.populationReq, data.townType,
                    data.mayorPrefix, data.mayorSuffix, data.assistantPrefix,
                    data.assistantSuffix, data.townPrefix, data.townSuffix,
                    data.flags);
            ranks.add(toLoad);
        }


    }

    @Override
    public void save() {
        /** these don't get saved quite yet. */
    }

    public void createDefaults() {
        ArrayList<String> list = new ArrayList<String>();
        RankAccess data = new RankAccess();

        data.populationReq = 0;
        data.townType = "Tribe";
        data.mayorPrefix = "Tribe Leader";
        data.mayorSuffix = "";
        data.assistantPrefix = "Tribe Elder";
        data.assistantSuffix = "";
        data.townPrefix = "";
        data.townSuffix = "Tribe";
        list.add("protection");
        list.add("friendlyFire");
        data.flags = list;

        try {
            Database.get().insert(data);
        } catch (DatabaseWriteException ex) {
            Canary.log.trace("Error Inserting Rank Data", ex);
        }

        data = new RankAccess();
        data.populationReq = 10;
        data.townType = "Village";
        data.mayorPrefix = "Chief";
        data.mayorSuffix = "";
        data.assistantPrefix = "Village Elder";
        data.assistantSuffix = "";
        data.townPrefix = "";
        data.townSuffix = "Village";
        list.add("noPvp");
        list.add("creeperNerf");
        data.flags = list;

        try {
            Database.get().insert(data);
        } catch (DatabaseWriteException ex) {
            Canary.log.trace("Error Inserting Rank Data", ex);
        }

        data = new RankAccess();
        data.populationReq = 30;
        data.townType = "Town";
        data.mayorPrefix = "Mayor";
        data.mayorSuffix = "";
        data.assistantPrefix = "Town Assistant";
        data.assistantSuffix = "";
        data.townPrefix = "";
        data.townSuffix = "Town";
        list.add("ownerPlot");
        list.add("sanctuary");
        data.flags = list;

        try {
            Database.get().insert(data);
        } catch (DatabaseWriteException ex) {
            Canary.log.trace("Error Inserting Rank Data", ex);
        }
    }

}
