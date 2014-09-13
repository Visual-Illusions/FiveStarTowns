package net.visualillusionsent.fivestartowns.commands;

import net.canarymod.chat.Colors;
import net.visualillusionsent.fivestartowns.Config;
import net.visualillusionsent.fivestartowns.InviteManager;
import net.visualillusionsent.fivestartowns.player.IPlayer;
import net.visualillusionsent.fivestartowns.town.TownManager;
import net.visualillusionsent.fivestartowns.town.TownPlayer;

/**
 *
 * @author somners
 */
public class LeaveCommand {

    public static void execute(IPlayer player, String[] command) {
        TownPlayer tp = TownManager.get().getTownPlayer(player);
        if (tp.getTown() == null) {
            player.message(Config.get().getMessageHeader() + "You are not in a town to leave!");
            return;
        }
        player.message(Config.get().getMessageHeader() + "You have "
                + "now left " + Colors.GREEN + tp.getTownName() + "!");
        InviteManager.get().removeInvite(player.getName());
        tp.leaveTown();
    }

    public static boolean canUseCommand(IPlayer player) {
        return true;
    }

}
