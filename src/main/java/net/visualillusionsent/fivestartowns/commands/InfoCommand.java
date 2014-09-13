package net.visualillusionsent.fivestartowns.commands;

import net.visualillusionsent.fivestartowns.Config;
import net.visualillusionsent.fivestartowns.player.IPlayer;
import net.visualillusionsent.fivestartowns.plot.Plot;
import net.visualillusionsent.fivestartowns.plot.PlotManager;
import net.visualillusionsent.fivestartowns.town.Town;
import net.visualillusionsent.fivestartowns.town.TownManager;
import net.canarymod.chat.Colors;

/**
 *
 * @author somners
 */
public class InfoCommand {

    public static void execute(IPlayer player, String[] command) {
        if (command.length > 1) {
            Town town = TownManager.get().getTown(command[0]);
            if (town == null) {
                player.message(Config.get().getMessageHeader() + "The town "
                    + Colors.GREEN + command[0] + Colors.WHITE + " does not exist.");
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
            player.message(Colors.BLACK + "-- " + Colors.GREEN + "Flags enabled: "
                    + Colors.WHITE + town.getEnabledFlags());
            player.message(Colors.BLACK + "-- " + Colors.GREEN + "Plot Flags enabled: "
                    + Colors.WHITE + plot.getEnabledFlags());
            return;
        }
    }

    public static boolean canUseCommand(IPlayer player) {
        return true;
    }

}
