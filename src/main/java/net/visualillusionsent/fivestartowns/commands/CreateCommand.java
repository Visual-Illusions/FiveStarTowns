package net.visualillusionsent.fivestartowns.commands;

import net.canarymod.Canary;
import net.canarymod.chat.Colors;
import net.canarymod.database.Database;
import net.canarymod.database.exceptions.DatabaseReadException;
import net.canarymod.database.exceptions.DatabaseWriteException;
import net.visualillusionsent.fivestartowns.Config;
import net.visualillusionsent.fivestartowns.database.TownAccess;
import net.visualillusionsent.fivestartowns.job.JobManager;
import net.visualillusionsent.fivestartowns.job.JobType;
import net.visualillusionsent.fivestartowns.player.IPlayer;
import net.visualillusionsent.fivestartowns.town.Town;
import net.visualillusionsent.fivestartowns.town.TownManager;
import net.visualillusionsent.fivestartowns.town.TownPlayer;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Somners
 */
public class CreateCommand {

    public static void execute(IPlayer player, String[] command) {
        if (!(command.length >= 2)) {
            player.message(Config.get().getMessageHeader() + "Improper syntax. Please use:\n  "
                    + Colors.GREEN + "/town create [name]");
            return;
        }

        TownPlayer tp = TownManager.get().getTownPlayer(player.getUUID());
        if (tp.getTown() != null) {
            player.message(Config.get().getMessageHeader() + "You are already in a town: " + Colors.GREEN +
                    tp.getTownName());
            return;
        }
        /*StringBuilder name = new StringBuilder();
        for (String s : command) {
            name.append(s).append(" ");
        }
        String newName = name.toString().trim();*/
        String newName = command[1];
        if (TownManager.get().getTown(newName) != null) {
            player.message(Config.get().getMessageHeader() + "A town named " + Colors.GREEN
                    + command[1] + Colors.WHITE + " already exists! Please pick a new name!");
            return;
        }
        /* insert to database */
        TownAccess townA = new TownAccess();
        townA.name = newName;
        townA.balance = 0;
        townA.bonusPlots = 0;
        townA.farewell = String.format("You are now leaving %s!", newName);
        townA.welcome = String.format("Welcome to %s!", newName);
        townA.creeperNerf = "FALSE";
        townA.friendlyFire = "FALSE";
        townA.protection = "FALSE";
        townA.sanctuary = "FALSE";
        townA.ownerPlot = "FALSE";
        townA.nopvp = "FALSE";
        
        try {
            Database.get().insert(townA);
        } catch (DatabaseWriteException ex) {
            Canary.log.trace("Error Inserting Town Data.", ex);
        }
        
        /* Create and load a town instance */
        HashMap<String, Object> filter = new HashMap<String, Object>();
        filter.put("name", newName);
        townA = new TownAccess();
        try {
            Database.get().load(townA, filter);
        } catch (DatabaseReadException ex) {
            Logger.getLogger(CreateCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Town town = new Town(townA.uuid);
        
        /* Register with FST */
        TownManager.get().addTown(town);
        
        /* Update a new TownPlayer */
        tp = TownManager.get().getTownPlayer(player.getUUID());
        tp.setTown(town);
        tp.save();
        
        
        JobManager.get().addJob(town.getUUID(), tp.getUUID(), JobType.OWNER);

        player.message(Config.get().getMessageHeader() + "Congratulations! You are "
                + "now the owner of " + Colors.GREEN + newName + "!");
    }

    public static boolean canUseCommand(IPlayer player) {
        Town t = TownManager.get().getTownFromPlayer(player);
        if (t != null) {
            player.message(Config.get().getMessageHeader() + "You are already a part of " + Colors.GREEN
                    + t.getName() + Colors.WHITE + "! Please leave this town before creating a new one!");
            return false;
        }
        return true;
    }

}
