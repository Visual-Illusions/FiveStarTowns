/*
 * This file is copyright of WarHead Gaming. It is open Source and
 * free to use. Use of our code without express consent from the
 * developers is in violation of our license which has been provided
 * for you in the source and compiled jar.
 */
package com.warhead.fivestartowns.rank;

import com.warhead.fivestartowns.database.PlotAccess;
import com.warhead.fivestartowns.database.RankAccess;
import com.warhead.fivestartowns.town.Town;
import java.util.ArrayList;
import java.util.List;
import net.canarymod.Canary;
import net.canarymod.database.DataAccess;
import net.canarymod.database.Database;
import net.canarymod.database.exceptions.DatabaseReadException;

/**
 *
 * @author somners
 */
public class RankManager {
    
    private static final List<TownRank> ranks = new ArrayList<TownRank>();
    private static RankManager instance = null;

    public RankManager() {
        if (ranks.isEmpty()) {
            List<DataAccess> dataList = new ArrayList<DataAccess>();
            try {
                Database.get().loadAll(new PlotAccess(), dataList, new String[]{}, new Object[]{});
            } catch (DatabaseReadException ex) {
                Canary.logStackTrace("Error loading Plot Table in 'PlotManager.class'. ", ex);
            }
            for (DataAccess data : dataList) {
                ranks.add(new TownRank((RankAccess)data));
            }
        }
    }

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
        int pop = town.getMemberSize();
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
    
}
