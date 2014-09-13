package net.visualillusionsent.fivestartowns.commands;

import net.canarymod.chat.Colors;
import net.visualillusionsent.fivestartowns.Config;
import net.visualillusionsent.fivestartowns.player.IPlayer;
import net.visualillusionsent.fivestartowns.plot.Plot;
import net.visualillusionsent.fivestartowns.plot.PlotManager;
import net.visualillusionsent.fivestartowns.town.Town;
import net.visualillusionsent.fivestartowns.town.TownManager;
import net.visualillusionsent.fivestartowns.town.TownPlayer;

/**
 *
 * @author Somners
 */
public class UnclaimCommand {

    public static void execute(IPlayer player, String[] command) {
        Plot plot = PlotManager.get().getFSTPlot(player);
        Town town = TownManager.get().getTownFromPlayer(player);
        if (plot == null || !plot.getTown().equals(town)) {
            player.message(Config.get().getMessageHeader() + "This plots is "
                    + "not owned by your town. You cannot unclaim it.");
            return;
        }
        plot.setTownUUID(-1);

        player.message(Config.get().getMessageHeader() + "Plot UnClaimed for " + Colors.GREEN + town.getName() + "!");

    }

    public static boolean canUseCommand(IPlayer player) {
        TownPlayer tp = TownManager.get().getTownPlayer(player);
        if (tp != null && (tp.isAssistant() || tp.isOwner())) {
            player.message(Config.get().getMessageHeader() + "You must be a "
                    + "town " + Colors.GREEN + "Owner " + Colors.WHITE + "or "
                    + Colors.GREEN + "Assistant " + Colors.WHITE + "to use this command!");
            return false;
        }
        return true;
    }
}
