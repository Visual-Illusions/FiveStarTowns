/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.visualillusionsent.fivestartowns.commands;

import net.canarymod.Canary;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.chat.Colors;
import net.visualillusionsent.fivestartowns.Config;
import net.visualillusionsent.fivestartowns.job.JobManager;
import net.visualillusionsent.fivestartowns.job.JobType;
import net.visualillusionsent.fivestartowns.player.IPlayer;
import net.visualillusionsent.fivestartowns.town.Town;
import net.visualillusionsent.fivestartowns.town.TownManager;
import net.visualillusionsent.fivestartowns.town.TownPlayer;

/**
 *
 * @author Aaron
 */
public class DemoteCommand {

    public static void execute(IPlayer player, String[] command) {
        Town town = TownManager.get().getTownFromPlayer(player);
        TownPlayer tp = TownManager.get().getTownPlayer(player);
        if (command.length != 2) {
            player.message(Config.get().getMessageHeader() + "Correct Usage: "
                    + Colors.GREEN + "/town demote <player name>");
            return;
        }
        TownPlayer toRemove = TownManager.get().getTownPlayer(command[1]);
        if (toRemove == null) {
            player.message(Config.get().getMessageHeader() + "Player, "
                    + Colors.GREEN + toRemove.getName() + Colors.WHITE
                    + ", is not a valid player.");
            return;
        }
        if (!toRemove.getTown().equals(town)) {
            player.message(Config.get().getMessageHeader() + "Player, "
                    + Colors.GREEN + toRemove.getName() + Colors.WHITE
                    + ", is not in your town.");
            return;
        }
        JobType jobType = JobType.fromName(command[2]);
        if (jobType == null) {
            player.message(Config.get().getMessageHeader() + "Job,  "
                    + Colors.GREEN + command[2] + Colors.WHITE
                    + ", is not a valid Job.");
            return;
        }
        if (toRemove.equals(tp)) {
            player.message(Config.get().getMessageHeader() + "You cannot demote"
                    + " yourself Silly!!!");
            return;
        }
        JobManager.get().removeAllJobs(toRemove.getUUID());
        
        Player p = Canary.getServer().getPlayerFromUUID(toRemove.getUUID());
        if (p != null) {
            p.message(Config.get().getMessageHeader() + "You have been Demoted from your Town Job.");
        }
        
        player.message(Config.get().getMessageHeader() + "You have demoted "
                + Colors.GREEN + toRemove.getName() + Colors.WHITE + " from their Town Job.");
    }

    public static boolean canUseCommand(IPlayer player) {
        TownPlayer tp = TownManager.get().getTownPlayer(player);
        if (tp != null && !tp.isOwner()) {
            player.message(Config.get().getMessageHeader() + "You must be a "
                    + "town " + Colors.GREEN + "Owner " + Colors.WHITE + "to use this command!");
            return false;
        }
        return true;
    }
    
}
