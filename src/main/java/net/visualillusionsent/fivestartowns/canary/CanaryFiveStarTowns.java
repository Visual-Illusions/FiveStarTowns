package net.visualillusionsent.fivestartowns.canary;

import java.util.logging.Logger;
import net.canarymod.Canary;
import net.canarymod.commandsys.CommandDependencyException;
import net.canarymod.database.Database;
import net.canarymod.database.exceptions.DatabaseWriteException;
import net.visualillusionsent.fivestartowns.FiveStarTowns;
import net.visualillusionsent.fivestartowns.canary.listeners.FSTCommandListener;
import net.visualillusionsent.fivestartowns.canary.listeners.FiveStarTownsListener;
import net.visualillusionsent.fivestartowns.database.PlotAccess;
import net.visualillusionsent.fivestartowns.database.TownAccess;
import net.visualillusionsent.fivestartowns.database.TownPlayerAccess;
import net.visualillusionsent.minecraft.plugin.canary.VisualIllusionsCanaryPlugin;

/**
 *
 * @author Somners
 */
public class CanaryFiveStarTowns extends VisualIllusionsCanaryPlugin implements FiveStarTowns {

    private FSTCommandListener commands;
    private FiveStarTownsListener listener;
    private static FiveStarTowns instance;

    public boolean enable() {
        instance = this;
        if (!this.createTables()) {
            this.getLogman().logWarning("Error Creating/Updating tables. Five Star Towns"
                    + "will not load.");
            return false;
        }
        commands = new FSTCommandListener();
        listener = new FiveStarTownsListener();
        try {
            Canary.commands().registerCommands(commands, this, true);
        } catch (CommandDependencyException ex) {
            this.getLogman().logWarning("Commands failed to register. Five Star Towns"
                    + "will not load.");
            return false;
        }
        Canary.hooks().registerListener(listener, this);
        return true;
    }

    @Override
    public void disable() {
    }

    public static FiveStarTowns get() {
        return instance;
    }

    public boolean createTables() {
        try {
            Database.get().updateSchema(new PlotAccess());
            Database.get().updateSchema(new TownAccess());
            Database.get().updateSchema(new TownPlayerAccess());
        } catch (DatabaseWriteException ex) {
            return false;
        }
        return true;
    }

    @Override
    public Logger getPluginLogger() {
        return this.getLogman().getParent();
    }
}
