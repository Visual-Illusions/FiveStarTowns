/*
 * This file is copyright of WarHead Gaming. It is open Source and
 * free to use. Use of our code without express consent from the
 * developers is in violation of our license which has been provided
 * for you in the source and compiled jar.
 */
package com.warhead.fivestartowns;

import com.warhead.fivestartowns.database.PlotAccess;
import com.warhead.fivestartowns.database.TownAccess;
import com.warhead.fivestartowns.database.TownPlayerAccess;
import com.warhead.fivestartowns.listeners.CommandListener;
import com.warhead.fivestartowns.listeners.FiveStarTownsListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.canarymod.Canary;
import net.canarymod.database.Database;
import net.canarymod.database.exceptions.DatabaseWriteException;
import net.canarymod.plugin.Plugin;

/**
 * The main class for FiveStarTowns.
 * @author Somners
 */
public class FiveStarTowns extends Plugin{

    private CommandListener commands;
    private FiveStarTownsListener listener;
    private static FiveStarTowns instance;


    public boolean enable() {
        instance = this;
        this.createTables();
        commands = new CommandListener();
        listener = new FiveStarTownsListener();
        Canary.hooks().registerListener(commands, this);
        Canary.hooks().registerListener(listener, this);
        return true;
    }

    @Override
    public void disable() {
    }

    public static FiveStarTowns get() {
        return instance;
    }

    public void createTables() {
        try {
            Database.get().updateSchema(new PlotAccess());
            Database.get().updateSchema(new TownAccess());
            Database.get().updateSchema(new TownPlayerAccess());
        } catch (DatabaseWriteException ex) {
            Logger.getLogger(FiveStarTowns.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
