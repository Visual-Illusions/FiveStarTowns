package net.visualillusionsent.fivestartowns.commands;

import net.canarymod.chat.Colors;
import net.visualillusionsent.fivestartowns.Config;
import net.visualillusionsent.fivestartowns.player.IPlayer;
import net.visualillusionsent.fivestartowns.town.Town;
import net.visualillusionsent.fivestartowns.town.TownManager;
import net.visualillusionsent.fivestartowns.town.TownPlayer;

/**
 * Created by Aaron on 9/14/2014.
 */
public class KickCommand {

    public static void execute(IPlayer player, String[] command) {
        if (command.length != 2) {
            player.message(Config.get().getMessageHeader() + "Correct Usage: "
                    + Colors.GREEN + "/town kick <player name>");
            return;
        }
        TownPlayer tp = TownManager.get().getTownPlayer(player);
        Town town = tp.getTown();
        TownPlayer toKick = TownManager.get().getTownPlayer(command[1]);
        if (!toKick.getTown().equals(town)) {
            player.message(Config.get().getMessageHeader() + "Player, "
                    + Colors.GREEN + toKick.getName() + Colors.WHITE
                    + ", is not in your town.");
            return;
        }
        toKick.leaveTown();

        player.message(Config.get().getMessageHeader() + "Player, "
                + Colors.GREEN + toKick.getName() + Colors.WHITE
                + ", has been kicked from, " + Colors.GREEN + town.getName()
                + Colors.WHITE + "by " + Colors.GREEN + tp.getName()
                + Colors.WHITE + ".");

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
