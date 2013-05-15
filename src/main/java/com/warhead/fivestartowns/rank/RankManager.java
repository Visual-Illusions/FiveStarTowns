/*
 * This file is copyright of WarHead Gaming. It is open Source and
 * free to use. Use of our code without express consent from the
 * developers is in violation of our license which has been provided
 * for you in the source and compiled jar.
 */
package com.warhead.fivestartowns.rank;

import com.warhead.fivestartowns.database.PlotAccess;
import com.warhead.fivestartowns.database.RankAccess;
import java.util.ArrayList;
import java.util.HashMap;
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
    
    private static final List<RankAccess> ranks = new ArrayList<RankAccess>();
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
                ranks.add((RankAccess)data);
            }
        }
    }

    public static RankManager get() {
        if (instance == null) {
            instance = new RankManager();
        }
        return instance;
    }
    
    
    
    
    
    
}
