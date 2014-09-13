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
public class AcceptInviteCommand extends FSTCommand {

    public AcceptInviteCommand() {
        base = new String[] {"accept"};
        usage = "/town accept";
        description = "Accepts an invite to the last town to send you an invite.";
    }

    public void execute(IPlayer player, String[] command) {
        TownPlayer tp = TownManager.get().getTownPlayer(player);
        if (tp.getTown() != null) {
            player.message(Config.get().getMessageHeader() + "You are already in a town.");
            return;
        }
        if (!InviteManager.get().hasInvite(player.getName())) {
            player.message(Config.get().getMessageHeader() + "You have no town invites.");
            return;
        }
        /*if (command.length < 2) {
            player.message(Config.get().getMessageHeader() + "You must specify "
                    + "an invite to accept.");
            return;
        }*/
        int townId = InviteManager.get().getInvite(player.getName());
        tp.setTown(townId);
        player.message(Config.get().getMessageHeader() + "Congratulations! You are "
                + "now a part of " + Colors.GREEN + tp.getTownName() + "!");
        InviteManager.get().removeInvite(player.getName());
    }

    public static FSTCommand get() {
        if (instance == null) {
            instance = new CreateCommand();
        }
        return instance;
    }

    public boolean canUseCommand(IPlayer player) {
        return true;
    }

}
