package net.visualillusionsent.fivestartowns.rank;

import java.util.List;

/**
 *
 * @author Somners
 */
public class TownRank {

    /** Population needed for this Rank */
    public int populationReq;
    /** Type of this town. Currently unused. */
    public String townType;
    public String mayorPrefix;
    public String mayorSuffix;
    public String assistantPrefix;
    public String assistantSuffix;
    public String townPrefix;
    public String townSuffix;
    public List<String> flags;

    public TownRank(int popReq, String townType, String mayorPrefix, String mayorSuffix,
            String assistantPrefix, String assistantSuffix, String townPrefix, String townSuffix, List<String> flags) {
        this.populationReq = popReq;
        this.townType = townType;
        this.mayorPrefix = mayorPrefix;
        this.mayorSuffix = mayorSuffix;
        this.assistantPrefix = assistantPrefix;
        this.assistantSuffix = assistantSuffix;
        this.townPrefix = townPrefix;
        this.townSuffix = townSuffix;
        this.flags = flags;
    }

    public int getPopulationRequirement() {
        return populationReq;
    }

    public String getMayorPrefix() {
        return mayorPrefix;
    }

    public String getMayorSuffix() {
        return mayorSuffix;
    }

    public String getAssistantPrefix() {
        return assistantPrefix;
    }

    public String getAssistantSuffix() {
        return assistantSuffix;
    }

    public String getTownPrefix() {
        return townPrefix;
    }

    public String getTownSuffix() {
        return townSuffix;
    }

    public List<String> getFlags() {
        return flags;
    }
}
