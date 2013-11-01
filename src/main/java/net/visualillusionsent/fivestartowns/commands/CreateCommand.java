package net.visualillusionsent.fivestartowns.commands;

import java.util.ArrayList;
import net.canarymod.chat.Colors;
import net.visualillusionsent.fivestartowns.Config;
import net.visualillusionsent.fivestartowns.FiveStarTowns;
import net.visualillusionsent.fivestartowns.database.FSTDatabase;
import net.visualillusionsent.fivestartowns.player.IPlayer;
import net.visualillusionsent.fivestartowns.town.Town;
import net.visualillusionsent.fivestartowns.town.TownManager;
import net.visualillusionsent.fivestartowns.town.TownPlayer;

/**
 *
 * @author Somners
 */
public class CreateCommand extends FSTCommand {

    public CreateCommand() {
        base = new String[] {"create"};
        description = "Creates a Town with he given name.";
        usage = "/town create [name]";
    }

    public void execute(IPlayer player, String[] command) {
        if (!(command.length >= 1)) {
            player.message(Config.get().getMessageHeader() + "Improper syntax. Please use:\n  "
                    + Colors.GREEN + this.getUsage());
            return;
        }
        StringBuilder name = new StringBuilder();
        for (String s : command) {
            name.append(s).append(" ");
        }
        String newName = name.toString().trim();
        if (TownManager.get().getTown(newName) != null) {
            player.message(Config.get().getMessageHeader() + "A town named " + Colors.GREEN
                    + command[0] + Colors.WHITE + " already exists! Please pick a new name!");
            return;
        }
        /* insert to database */
        FSTDatabase.Query query = FiveStarTowns.database().newQuery();
        query.add(Town.NAME, newName).add(Town.ASSISTANT, new ArrayList<String>(), true);
        query.add(Town.BONUS_PLOTS, 0).add(Town.CREEPER_NERF, "FALSE").add(Town.FAREWELL, "You are now leaving " + newName + "!");
        query.add(Town.FRIENDLY_FIRE, "FALSE").add(Town.NO_PVP, "FALSE").add(Town.OWNER, player.getName());
        query.add(Town.OWNER_PLOT, "FALSE").add(Town.PROTECTION, "FALSE").add(Town.SANCTUARY, "FALSE");
        query.add(Town.WELCOME, "Welcome to " + newName + "!");
        FiveStarTowns.database().insertEntry(FSTDatabase.TOWN_PLAYER_TABLE, query);
        /* Create and load a town instance */
        Town town = new Town(newName);
        town.load();
        /* Register with FST */
        TownManager.get().addTown(town);

        /* insert to database */
        query = FiveStarTowns.database().newQuery();
        query.add(TownPlayer.NAME, player.getName()).add(TownPlayer.TOWN_NAME, town.getName());
        FiveStarTowns.database().insertEntry(FSTDatabase.TOWN_PLAYER_TABLE, query);
        /* Create and load a townplayer instance */
        TownPlayer townPlayer = new TownPlayer(player.getName());
        townPlayer.load();
        /* Register with FST */
        TownManager.get().addTownPlayer(townPlayer);

        player.message(Config.get().getMessageHeader() + "Congratulations! You are "
                + "now the owner of " + Colors.GREEN + newName + "!");
    }

    public static FSTCommand get() {
        if (instance == null) {
            instance = new CreateCommand();
        }
        return instance;
    }

    public boolean canUseCommand(IPlayer player) {
        Town t = TownManager.get().getTownFromPlayer(player);
        if (t != null) {
            player.message(Config.get().getMessageHeader() + "You are already a part of " + Colors.GREEN
                    + t.getName() + Colors.WHITE + "! Please leave this town before creating a new one!");
            return false;
        }
        return true;
    }

}
