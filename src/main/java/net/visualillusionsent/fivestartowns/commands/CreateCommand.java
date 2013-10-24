package net.visualillusionsent.fivestartowns.commands;

import net.visualillusionsent.fivestartowns.Config;
import net.visualillusionsent.fivestartowns.database.TownAccess;
import net.visualillusionsent.fivestartowns.database.TownPlayerAccess;
import net.visualillusionsent.fivestartowns.player.IPlayer;
import net.visualillusionsent.fivestartowns.town.Town;
import net.visualillusionsent.fivestartowns.town.TownManager;
import java.util.ArrayList;
import net.canarymod.chat.Colors;

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
        TownAccess town = new TownAccess();
        town.name = newName;
        town.owner = player.getName();
        town.assistant = new ArrayList<String>();
        town.members = new ArrayList<String>();;
        town.balance = 0;
        town.bonusPlots = 0;
        town.creeperNerf = "FALSE";
        town.friendlyFire = "FALSE";
        town.nopvp = "FALSE";
        town.protection = "FALSE";
        town.sanctuary = "FALSE";
        town.welcome = "Welcome to " + newName + "!";
        town.farewell = "You are now leaving " + newName + "!";
        TownManager.get().addTown(town);
        TownPlayerAccess townPlayer = new TownPlayerAccess();
        townPlayer.name = player.getName();
        townPlayer.townName = town.name;
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
