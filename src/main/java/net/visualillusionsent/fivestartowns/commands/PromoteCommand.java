package net.visualillusionsent.fivestartowns.commands;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import net.canarymod.Canary;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.chat.Colors;
import net.visualillusionsent.fivestartowns.Config;
import net.visualillusionsent.fivestartowns.job.JobManager;
import net.visualillusionsent.fivestartowns.job.JobType;
import net.visualillusionsent.fivestartowns.player.IPlayer;
import net.visualillusionsent.fivestartowns.plot.Plot;
import net.visualillusionsent.fivestartowns.plot.PlotManager;
import net.visualillusionsent.fivestartowns.town.Town;
import net.visualillusionsent.fivestartowns.town.TownManager;
import net.visualillusionsent.fivestartowns.town.TownPlayer;

/**
 *
 * @author Aaron
 */
public class PromoteCommand {

    public static void execute(IPlayer player, String[] command) {
        Town town = TownManager.get().getTownFromPlayer(player);
        TownPlayer tp = TownManager.get().getTownPlayer(player);
        if (command.length == 1) {
            listJobs(player);
        }
        if (command.length != 3) {
            player.message(Config.get().getMessageHeader() + "Correct Usage: "
                    + Colors.GREEN + "/town promote <player name> <job>");
            return;
        }
        TownPlayer toPromote = TownManager.get().getTownPlayer(command[1]);
        if (toPromote == null) {
            player.message(Config.get().getMessageHeader() + "Player, "
                    + Colors.GREEN + toPromote.getName() + Colors.WHITE
                    + ", is not a valid player.");
            return;
        }
        if (!toPromote.getTown().equals(town)) {
            player.message(Config.get().getMessageHeader() + "Player, "
                    + Colors.GREEN + toPromote.getName() + Colors.WHITE
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
        if (toPromote.equals(tp)) {
            player.message(Config.get().getMessageHeader() + "You cannot promote"
                    + " yourself Silly!!!");
            return;
        }
        JobManager.get().removeAllJobs(toPromote.getUUID());
        JobManager.get().addJob(toPromote.getTownUUID(), toPromote.getUUID(), jobType);
        
        Player p = Canary.getServer().getPlayerFromUUID(toPromote.getUUID());
        if (p != null) {
            p.message(Config.get().getMessageHeader() + "You have been promoted to: " + Colors.GREEN + jobType.getName());
        }
        
        player.message(Config.get().getMessageHeader() + "You have promoted "
                + Colors.GREEN + toPromote.getName() + Colors.WHITE + "to " 
                + Colors.GREEN + jobType.getName());
    }
    
    public static void listJobs(IPlayer player) {
        
            player.message(Config.get().getMessageHeader() + "Available Jobs: "
                    + Colors.GREEN + "owner, assisstant, builder");
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
