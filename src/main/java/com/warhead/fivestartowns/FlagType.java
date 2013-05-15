/*
 * This file is copyright of WarHead Gaming. It is open Source and
 * free to use. Use of our code without express consent from the
 * developers is in violation of our license which has been provided
 * for you in the source and compiled jar.
 */
package com.warhead.fivestartowns;

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
