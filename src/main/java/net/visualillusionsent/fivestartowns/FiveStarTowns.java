package net.visualillusionsent.fivestartowns;

import net.visualillusionsent.minecraft.plugin.canary.VisualIllusionsCanaryPlugin;

/**
 * The main class for FiveStarTowns.
 * @author Somners
 */
public abstract class FiveStarTowns extends VisualIllusionsCanaryPlugin {

    protected static FiveStarTowns instance;

    public static FiveStarTowns get() {
        return instance;
    }
}