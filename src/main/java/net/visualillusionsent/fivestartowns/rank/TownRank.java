package net.visualillusionsent.fivestartowns.rank;

import java.util.List;
import net.visualillusionsent.fivestartowns.Saveable;
import net.visualillusionsent.fivestartowns.database.RankAccess;

/**
 *
 * @author Somners
 */
public class TownRank extends Saveable {

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
        throw new UnsupportedOperationException("Method 'load' in class 'TownRank' is not supported yet.");
    }

    @Override
    public void save() {
        throw new UnsupportedOperationException("Method 'save' in class 'TownRank' is not supported yet.");
    }
}
