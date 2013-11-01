package net.visualillusionsent.fivestartowns.flag;

import java.util.ArrayList;
import java.util.List;
import net.visualillusionsent.fivestartowns.Saveable;
import static net.visualillusionsent.fivestartowns.flag.FlagType.CREEPER_NERF;
import static net.visualillusionsent.fivestartowns.flag.FlagType.FRIENDLY_FIRE;
import static net.visualillusionsent.fivestartowns.flag.FlagType.NO_PVP;
import static net.visualillusionsent.fivestartowns.flag.FlagType.OWNER_PLOT;
import static net.visualillusionsent.fivestartowns.flag.FlagType.PROTECTION;
import static net.visualillusionsent.fivestartowns.flag.FlagType.SANCTUARY;

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
        if (this.creeperNerf.getBoolean()) {
            flags.add(creeperNerf.toString());
        }
        if (this.friendlyFire.getBoolean()) {
            flags.add(this.friendlyFire.toString());
        }
        if (this.nopvp.getBoolean()) {
            flags.add(this.nopvp.toString());
        }
        if (this.ownerPlot.getBoolean()) {
            flags.add(this.ownerPlot.toString());
        }
        if (this.protection.getBoolean()) {
            flags.add(this.protection.toString());
        }
        if (this.sanctuary.getBoolean()) {
            flags.add(this.sanctuary.toString());
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
