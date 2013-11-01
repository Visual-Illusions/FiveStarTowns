package net.visualillusionsent.fivestartowns.commands;

import net.canarymod.chat.Colors;
import net.visualillusionsent.fivestartowns.Config;
import net.visualillusionsent.fivestartowns.FiveStarTowns;
import net.visualillusionsent.fivestartowns.InviteManager;
import static net.visualillusionsent.fivestartowns.commands.FSTCommand.instance;
import net.visualillusionsent.fivestartowns.database.FSTDatabase;
import net.visualillusionsent.fivestartowns.database.FSTDatabase.Query;
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
        if (TownManager.get().getTownPlayer(player) != null) {
            player.message(Config.get().getMessageHeader() + "You are already in a town.");
            return;
        }
        if (!InviteManager.get().hasInvite(player.getName())) {
            player.message(Config.get().getMessageHeader() + "You have to town invites :(");
            return;
        }
        /* insert to database */
        Query query = FiveStarTowns.database().newQuery();
        query.add(TownPlayer.NAME, player.getName()).add(TownPlayer.TOWN_NAME, InviteManager.get().getInvite(player.getName()));
        FiveStarTowns.database().insertEntry(FSTDatabase.TOWN_PLAYER_TABLE, query);
        /* Create and load a townplayer instance */
        TownPlayer townPlayer = new TownPlayer(player.getName());
        townPlayer.load();
        /* Register with FST */
        TownManager.get().addTownPlayer(townPlayer);
        player.message(Config.get().getMessageHeader() + "Congratulations! You are "
                + "now a part of " + Colors.GREEN + townPlayer.getTownName() + "!");
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
