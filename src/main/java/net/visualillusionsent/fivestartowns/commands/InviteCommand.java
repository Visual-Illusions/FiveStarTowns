package net.visualillusionsent.fivestartowns.commands;

import net.visualillusionsent.fivestartowns.Config;
import net.visualillusionsent.fivestartowns.InviteManager;
import static net.visualillusionsent.fivestartowns.commands.FSTCommand.instance;
import net.visualillusionsent.fivestartowns.player.IPlayer;
import net.visualillusionsent.fivestartowns.town.TownManager;
import net.visualillusionsent.fivestartowns.town.TownPlayer;
import net.canarymod.Canary;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.chat.Colors;

/**
 *
 * @author somners
 */
public class InviteCommand extends FSTCommand {

    public InviteCommand() {
        base = new String[] {"invite"};
        usage = "/town invite [playername]";
        description = "Invites this player to your town.";
    }

    public void execute(IPlayer player, String[] command) {
        if (command.length < 1) {
            player.message(Config.get().getMessageHeader() + "You must specify "
                    + "a player to invite.");
            return;
        }
        Player p = Canary.getServer().getPlayer(command[0]);
        if (p == null) {
            player.message(Config.get().getMessageHeader() + "You must specify "
                    + "an online player to invite.");
            return;
        }
        if (TownManager.get().getTownPlayer(p) != null) {
            player.message(Config.get().getMessageHeader() + Colors.GREEN +
                    command[0] + Colors.WHITE + " is already in a town.");
            return;
        }
        String town = TownManager.get().getTownPlayer(player).getTownName();
        InviteManager.get().addInvite(p.getName(), town);
        player.message(Config.get().getMessageHeader() + Colors.GREEN +
                    command[0] + Colors.WHITE + " has been invited to your town.");
        p.message(Config.get().getMessageHeader() + Colors.GREEN +
                player.getName() + Colors.WHITE + " has invited you totown " +
                Colors.GREEN + town + Colors.WHITE + ".  To accept use command: " +
                Colors.GREEN + "/town accept");
    }

    public static FSTCommand get() {
        if (instance == null) {
            instance = new InviteCommand();
        }
        return instance;
    }

    public boolean canUseCommand(IPlayer player) {
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
