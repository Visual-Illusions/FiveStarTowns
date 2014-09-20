package net.visualillusionsent.fivestartowns.commands;

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
public class MeCommand {
    
    public static void execute(IPlayer player, String[] command) {
        if (command.length > 1) {
            
        }

        TownPlayer tp = TownManager.get().getTownPlayer(player);
        if (tp == null) {
            player.message(Config.get().getMessageHeader() + "Player, "
                    + Colors.GREEN + "TODO" + Colors.WHITE
                    + ", is not a valid player.");
            return;
        }
        Town town = tp.getTown();

        if (town == null) {
            player.message(Config.get().getMessageHeader() + "The player "
                + Colors.GREEN + tp.getName() + Colors.WHITE + " is not in a town.");
            return;
        }
        
        player.message(Config.get().getMessageHeader() + "Info about: "
                + Colors.GREEN +  tp.getName());
        /* Town name with prefix/suffix */
        player.message(Colors.BLACK + "-- " + Colors.GREEN + "Town: " +
                Colors.WHITE + town.getTownRank().getTownPrefix() + town.getName() +
                town.getTownRank().getTownSuffix());
        /* Prepare Jobs */
        StringBuilder jobSB = new StringBuilder();
        for (JobType jt : JobManager.get().getJobs(tp.getTownUUID(), tp.getUUID())) {
            jobSB.append(" ").append(jt.getName()).append(",");
        }
        jobSB.deleteCharAt(jobSB.length() - 1);
        player.message(Colors.BLACK + "-- " + Colors.GREEN + "Jobs: " +
                Colors.WHITE + jobSB.toString());

        return;
        
        
        
        
        
    }

    public static boolean canUseCommand(IPlayer player) {
        return true;
    }

}
