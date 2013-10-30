package net.visualillusionsent.fivestartowns;

import net.visualillusionsent.fivestartowns.database.FSTDatabase;
import net.visualillusionsent.minecraft.plugin.canary.VisualIllusionsCanaryPlugin;

/**
 * The main class for FiveStarTowns.
 * @author Somners
 */
public abstract class FiveStarTowns extends VisualIllusionsCanaryPlugin {

    protected static FiveStarTowns instance;
    protected static FSTDatabase data;

    public static FiveStarTowns get() {
        return instance;
    }

    public static FSTDatabase database() {
        return data;
    }
}