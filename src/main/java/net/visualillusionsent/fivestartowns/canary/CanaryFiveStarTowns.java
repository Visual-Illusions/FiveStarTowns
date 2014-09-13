package net.visualillusionsent.fivestartowns.canary;

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
import net.visualillusionsent.fivestartowns.job.JobManager;
import net.visualillusionsent.fivestartowns.plot.PlotManager;
import net.visualillusionsent.fivestartowns.town.TownManager;

/**
 *
 * @author Somners
 */
public class CanaryFiveStarTowns extends FiveStarTowns {

    private FSTCommandListener commands;
    private FiveStarTownsListener listener;

    public boolean enable() {
        instance = this;
        /* Create our Database Tables */
        if (!this.createTables()) {
            this.getLogman().warn("Error Creating/Updating tables. Five Star Towns"
                    + "will not load.");
            return false;
        }
        /* Load Database Data */
        TownManager.get().load();
        JobManager.get().load();
        PlotManager.get().load();
        /* Register Listeners */
        commands = new FSTCommandListener();
        listener = new FiveStarTownsListener();
        try {
            Canary.commands().registerCommands(commands, this, true);
        } catch (CommandDependencyException ex) {
            this.getLogman().warn("Commands failed to register. Five Star Towns"
                    + "will not load.", ex);
            return false;
        }
        Canary.hooks().registerListener(listener, this);
        /* All went well, lets load the plugin */
        return true;
    }

    @Override
    public void disable() {
        /* Save all our Data */
        TownManager.get().save();
        JobManager.get().save();
        PlotManager.get().save();
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
}
