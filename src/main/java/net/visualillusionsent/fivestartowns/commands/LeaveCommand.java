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
public class LeaveCommand extends FSTCommand {

    public LeaveCommand() {
        base = new String[] {"leave"};
        usage = "/town leave";
        description = "Leaves the town you are currently in.";
    }

    public void execute(IPlayer player, String[] command) {
        TownPlayer tp = TownManager.get().getTownPlayer(player);
        if (tp.getTown() == null) {
            player.message(Config.get().getMessageHeader() + "You are not in a town to leave!");
            return;
        }
        tp.leaveTown();
        player.message(Config.get().getMessageHeader() + "You have "
                + "now left " + Colors.GREEN + tp.getTownName() + "!");
        InviteManager.get().removeInvite(player.getName());
    }

    public static FSTCommand get() {
        if (instance == null) {
            instance = new LeaveCommand();
        }
        return instance;
    }

    public boolean canUseCommand(IPlayer player) {
        return true;
    }

}
