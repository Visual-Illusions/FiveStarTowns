/*
 * This file is copyright of WarHead Gaming. It is open Source and
 * free to use. Use of our code without express consent from the
 * developers is in violation of our license which has been provided
 * for you in the source and compiled jar.
 */
package com.warhead.fivestartowns.rank;

import com.warhead.fivestartowns.database.RankAccess;
import java.util.List;

/**
 *
 * @author Somners
 */
public class TownRank {
    
    private RankAccess data;

    public TownRank(RankAccess data) {
        this.data = data;
    }
    
    public int getPopulationRequirement() {
        return data.populationReq;
    }
    
    public String getMayorPrefix() {
        return data.mayorPrefix;
    }
    
    public String getMayorSuffix() {
        return data.mayorSuffix;
    }
    
    public String getAssistantPrefix() {
        return data.assistantPrefix;
    }
    
    public String getAssistantSuffix() {
        return data.assistantSuffix;
    }
    
    public String getTownPrefix() {
        return data.townPrefix;
    }
    
    public String getTownSuffix() {
        return data.townSuffix;
    }
    
    public List<String> getFlags() {
        return data.flags;
    }
}
