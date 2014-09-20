package net.visualillusionsent.fivestartowns.commands;

import net.canarymod.Canary;
import net.canarymod.api.entity.living.humanoid.Player;
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
public class InviteCommand {

    public static void execute(IPlayer player, String[] command) {
        if (command.length < 2) {
            player.message(Config.get().getMessageHeader() + "You must specify "
                    + "a player to invite.");
            return;
        }
        Player p = Canary.getServer().getPlayer(command[1]);
        if (p == null) {
            player.message(Config.get().getMessageHeader() + "You must specify "
                    + "an online player to invite.");
            return;
        }
        TownPlayer tp = TownManager.get().getTownPlayer(p);
        if (tp.getTown() != null) {
            player.message(Config.get().getMessageHeader() + Colors.GREEN +
                    command[1] + Colors.WHITE + " is already in a town.");
            return;
        }
        String town = TownManager.get().getTownPlayer(player).getTownName();
        int townId = TownManager.get().getTownPlayer(player).getTownUUID();
        InviteManager.get().addInvite(p.getName(), townId);
        player.message(Config.get().getMessageHeader() + Colors.GREEN +
                    command[1] + Colors.WHITE + " has been invited to your town.");
        p.message(Config.get().getMessageHeader() + Colors.GREEN +
                player.getName() + Colors.WHITE + " has invited you totown " +
                Colors.GREEN + town + Colors.WHITE + ".  To accept use command: " +
                Colors.GREEN +"/town accept");
    }

    public static boolean canUseCommand(IPlayer player) {
        TownPlayer tp = TownManager.get().getTownPlayer(player);
        if (tp != null && !tp.isAssistant() && !tp.isOwner()) {
            player.message(Config.get().getMessageHeader() + "You must be a "
                    + "town " + Colors.GREEN + "Owner " + Colors.WHITE + "or "
                    + Colors.GREEN + "Assistant " + Colors.WHITE + "to use this command!");
            return false;
        }
        return true;
    }
}
