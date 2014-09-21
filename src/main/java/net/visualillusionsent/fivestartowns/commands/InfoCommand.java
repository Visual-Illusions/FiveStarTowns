package net.visualillusionsent.fivestartowns.commands;

import net.visualillusionsent.fivestartowns.Config;
import net.visualillusionsent.fivestartowns.player.IPlayer;
import net.visualillusionsent.fivestartowns.plot.Plot;
import net.visualillusionsent.fivestartowns.plot.PlotManager;
import net.visualillusionsent.fivestartowns.town.Town;
import net.visualillusionsent.fivestartowns.town.TownManager;
import net.canarymod.chat.Colors;
import net.visualillusionsent.fivestartowns.job.JobManager;
import net.visualillusionsent.fivestartowns.job.JobType;

/**
 *
 * @author somners
 */
public class InfoCommand {

    public static void execute(IPlayer player, String[] command) {
        if (command.length > 1) {
            Town town = TownManager.get().getTown(command[1]);
            if (town == null) {
                player.message(Config.get().getMessageHeader() + "The town "
                    + Colors.GREEN + command[1] + Colors.WHITE + " does not exist.");
                return;
            }
            player.message(Config.get().getMessageHeader() + "The town "
                    + Colors.GREEN + town.getTownRank().getTownPrefix() + town.getName() +
                    town.getTownRank().getTownSuffix());
            player.message(Colors.BLACK + "-- " + Colors.GREEN + "Owner: " +
                    Colors.WHITE + town.getTownRank().getMayorPrefix() + town.getOwnerName() +
                    town.getTownRank().getMayorSuffix());
            player.message(Colors.BLACK + "-- " + Colors.GREEN + "Assistants: " +
                    Colors.WHITE + town.getAssistant().toString());
            player.message(Colors.BLACK + "-- " + Colors.GREEN + "Plots: "
                    + Colors.WHITE + town.getCurrentClaimCount() + "/" + town.getMaxClaimCount());
            player.message(Colors.BLACK + "-- " + Colors.GREEN + "Flags enabled: "
                    + Colors.WHITE + town.getEnabledFlags());
            return;
        }
        else {
            Plot plot = PlotManager.get().getFSTPlot(player);
            if (plot == null) {
                player.message(Config.get().getMessageHeader() + "This plot is "
                        + "not owned by a town.");
                return;
            }
            Town town = plot.getTown();
            player.message(Config.get().getMessageHeader() + "The town "
                    + Colors.GREEN + town.getTownRank().getTownPrefix() + town.getName() +
                    town.getTownRank().getTownSuffix());
            player.message(Colors.BLACK + "-- " + Colors.GREEN + "Owner: " +
                    Colors.WHITE + town.getTownRank().getMayorPrefix() + town.getOwnerName() +
                    town.getTownRank().getMayorSuffix());
            player.message(Colors.BLACK + "-- " + Colors.GREEN + "Assistants: " +
                    Colors.WHITE + town.getAssistant().toString());
            player.message(Colors.BLACK + "-- " + Colors.GREEN + "Plots: "
                    + Colors.WHITE + town.getCurrentClaimCount() + "/" + town.getMaxClaimCount());
            /* prepare town flags */
            StringBuilder townSB = new StringBuilder();
            for (String s : town.getEnabledFlags()) {
                townSB.append(" ").append(s).append(",");
            }
            if (townSB.length() > 0) townSB.deleteCharAt(townSB.length() - 1);
            player.message(Colors.BLACK + "-- " + Colors.GREEN + "Flags enabled: "
                    + Colors.WHITE + townSB.toString());
            if (plot != null) {
            /* prepare plot flags */
                StringBuilder plotSB = new StringBuilder();
                for (String s : plot.getEnabledFlags()) {
                    plotSB.append(" ").append(s).append(",");
                }
                if (townSB.length() > 0) plotSB.deleteCharAt(plotSB.length() - 1);
                player.message(Colors.BLACK + "-- " + Colors.GREEN + "Plot Flags enabled: "
                        + Colors.WHITE + plotSB.toString());
            } else {
                return;
            }
            String owner = "Nobody";
            if (plot.getPlotOwner() != null) {
                owner = plot.getPlotOwner().getName();
            }
                player.message(Colors.BLACK + "-- " + Colors.GREEN + "Plot Owner: "
                        + Colors.WHITE + owner);
            return;
        }
    }

    public static boolean canUseCommand(IPlayer player) {
        return true;
    }

}