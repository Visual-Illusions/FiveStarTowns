package net.visualillusionsent.fivestartowns.flag;

/**
 *
 * @author somners
 */
public enum FlagType {

    NO_PVP("noPvp"),
    FRIENDLY_FIRE("friendlyFire"),
    SANCTUARY("sanctuary"),
    PROTECTION("protection"),
    CREEPER_NERF("creeperNerf"),
    OWNER_PLOT("ownerPlot");


    private String name;

    FlagType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static FlagType fromString(String name) {
        for (FlagType type : FlagType.values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
