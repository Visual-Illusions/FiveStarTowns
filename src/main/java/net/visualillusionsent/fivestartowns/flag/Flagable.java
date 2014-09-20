package net.visualillusionsent.fivestartowns.flag;

import net.visualillusionsent.fivestartowns.Saveable;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author somners
 */
public abstract class Flagable extends Saveable {

    protected FlagValue ownerPlot;
    protected FlagValue nopvp;
    protected FlagValue friendlyFire;
    protected FlagValue sanctuary;
    protected FlagValue protection;
    protected FlagValue creeperNerf;

    public Flagable(){}

    public Flagable(String ownerPlot, String nopvp, String friendlyFire,
            String sanctuary, String protection, String creeperNerf) {
        this.ownerPlot = FlagValue.valueOf(ownerPlot);
        this.nopvp = FlagValue.valueOf(nopvp);
        this.friendlyFire = FlagValue.valueOf(friendlyFire);
        this.sanctuary = FlagValue.valueOf(sanctuary);
        this.protection = FlagValue.valueOf(protection);
        this.creeperNerf = FlagValue.valueOf(creeperNerf);
    }
    /**
     * Sets the { @link FlagType } to the given { @link FlagValue}.
     *
     * @param type
     * @return
     */
    public void setFlagValue(FlagType type, FlagValue value) {
        switch(type) {
            case NO_PVP:
                this.nopvp = value;
                this.setDirty(true);
                break;
            case FRIENDLY_FIRE:
                this.friendlyFire = value;
                this.setDirty(true);
                break;
            case SANCTUARY:
                this.sanctuary = value;
                this.setDirty(true);
                break;
            case PROTECTION:
                this.protection = value;
                this.setDirty(true);
                break;
            case CREEPER_NERF:
                this.creeperNerf = value;
                this.setDirty(true);
                break;
            case OWNER_PLOT:
                this.ownerPlot = value;
                this.setDirty(true);
                break;
        }
    }

    /**
     * Gets the { @link FlagValue } for the { @link FlagType } for this Flagable type.
     *
     * @param type
     * @return
     */
    public FlagValue getFlagValue(FlagType type) {
        switch(type) {
            case NO_PVP:
                return this.nopvp;
            case FRIENDLY_FIRE:
                return this.friendlyFire;
            case SANCTUARY:
                return this.sanctuary;
            case PROTECTION:
                return this.protection;
            case CREEPER_NERF:
                return this.creeperNerf;
            case OWNER_PLOT:
                return this.ownerPlot;
        }
        return null;
    }

    /**
     * Gets a list of flags enabled for the Flagable type.
     * @return
     */
    public String[] getEnabledFlags() {
        List<String> flags = new ArrayList<String>();
        if (this.creeperNerf != null && this.creeperNerf.getBoolean()) {
            flags.add("creeperNerf");
        }
        if (this.creeperNerf != null && this.friendlyFire.getBoolean()) {
            flags.add("friendlyFire");
        }
        if (this.nopvp != null && this.nopvp.getBoolean()) {
            flags.add("nopvp");
        }
        if (this.ownerPlot != null && this.ownerPlot.getBoolean()) {
            flags.add("ownerPlot");
        }
        if (this.protection != null && this.protection.getBoolean()) {
            flags.add("protection");
        }
        if (this.sanctuary != null && this.sanctuary.getBoolean()) {
            flags.add("sanctuary");
        }
        return flags.toArray(new String[flags.size()]);
    }

    /**
     * Checks whether or not the flag is enabled for this Flagable type.
     *
     * @return
     */
    public boolean isFlagEnabled(FlagType type) {
        return getFlagValue(type).getBoolean();
    }
}
