package net.visualillusionsent.fivestartowns.commands;

import net.visualillusionsent.fivestartowns.Config;
import net.visualillusionsent.fivestartowns.InviteManager;
import net.visualillusionsent.fivestartowns.database.TownPlayerAccess;
import static net.visualillusionsent.fivestartowns.commands.FSTCommand.instance;
import net.visualillusionsent.fivestartowns.player.IPlayer;
import net.visualillusionsent.fivestartowns.town.TownManager;
import net.canarymod.chat.Colors;

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
        if (TownManager.get().getTownPlayer(player) != null) {
            player.message(Config.get().getMessageHeader() + "You are already in a town.");
            return;
        }
        if (!InviteManager.get().hasInvite(player.getName())) {
            player.message(Config.get().getMessageHeader() + "You have to town invites :(");
            return;
        }
        TownPlayerAccess townPlayer = new TownPlayerAccess();
        townPlayer.name = player.getName();
        townPlayer.townName = InviteManager.get().getInvite(player.getName());
        TownManager.get().addTownPlayer(townPlayer);
        player.message(Config.get().getMessageHeader() + "Congratulations! You are "
                + "now a part of " + Colors.GREEN + townPlayer.townName + "!");
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
