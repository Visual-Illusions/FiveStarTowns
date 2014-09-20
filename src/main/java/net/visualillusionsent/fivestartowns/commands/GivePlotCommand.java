package net.visualillusionsent.fivestartowns.commands;

import net.canarymod.Canary;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.chat.Colors;
import net.visualillusionsent.fivestartowns.Config;
import net.visualillusionsent.fivestartowns.flag.FlagType;
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
public class GivePlotCommand {

    public static void execute(IPlayer player, String[] command) {
        Town town = TownManager.get().getTownFromPlayer(player);
        TownPlayer tp = TownManager.get().getTownPlayer(player);
        if (command.length != 2) {
            player.message(Config.get().getMessageHeader() + "Correct Usage: "
                    + Colors.GREEN + "/town giveplot <player name>");
            return;
        }
        TownPlayer receiver = TownManager.get().getTownPlayer(command[1]);
        if (receiver == null) {
            player.message(Config.get().getMessageHeader() + "Player, "
                    + Colors.GREEN + receiver.getName() + Colors.WHITE
                    + ", is not a valid player.");
            return;
        }
        if (!town.equals(receiver.getTown())) {
            player.message(Config.get().getMessageHeader() + "Player, "
                    + Colors.GREEN + receiver.getName() + Colors.WHITE
                    + ", is not in your town.");
            return;
        }
        Plot plot = PlotManager.get().getFSTPlot(player);
        if (plot == null || !plot.getTown().equals(town)) {
            player.message(Config.get().getMessageHeader() + "Your Town does not "
                    + "own this plot, you cannot give it away.");
            return;
        }
        if (plot.isFlagEnabled(FlagType.OWNER_PLOT)) {
            player.message(Config.get().getMessageHeader() + "This plot is an "
                    + "owner plot, you cannot give it away.");
            return;
        }
        if (plot.getPlotOwner() != null) {
            player.message(Config.get().getMessageHeader() + "This plot has an "
                    + "owner already, you cannot give it away.");
            return;
        }
        
        plot.setPlotOwner(receiver);
        
        Player p = Canary.getServer().getPlayerFromUUID(receiver.getUUID());
        if (p != null) {
            p.message(Config.get().getMessageHeader() + "You have been given a town plot at: " + Colors.GREEN 
                    + String.format("x: %s, z: %s", plot.getX() << 4, plot.getZ() << 4));
        }
        
        player.message(Config.get().getMessageHeader() + "You given "
                + Colors.GREEN + receiver.getName() + Colors.WHITE + " this plot!");
    }

    public static boolean canUseCommand(IPlayer player) {
        TownPlayer tp = TownManager.get().getTownPlayer(player);
        if (tp != null && !tp.isOwner() && !tp.isAssistant()) {
            player.message(Config.get().getMessageHeader() + "You must be a "
                    + "town " + Colors.GREEN + "Owner " + Colors.WHITE + "to use this command!");
            return false;
        }
        return true;
    }
    
}
