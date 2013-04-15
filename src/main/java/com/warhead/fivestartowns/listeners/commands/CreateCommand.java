/*
 * This file is copyright of WarHead Gaming. It is open Source and
 * free to use. Use of our code without express consent from the
 * developers is in violation of our license which has been provided
 * for you in the source and compiled jar.
 */
package com.warhead.fivestartowns.listeners.commands;

import com.warhead.fivestartowns.Config;
import com.warhead.fivestartowns.town.Town;
import com.warhead.fivestartowns.town.TownManager;
import com.warhead.fivestartowns.database.TownAccess;
import com.warhead.fivestartowns.database.TownPlayerAccess;
import java.util.ArrayList;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.chat.Colors;

/**
 *
 * @author Somners
 */
public class CreateCommand implements FSTCommand {

    public void execute(Player player, String[] command) {
        if (!(command.length >= 1)) {
            player.sendMessage(Config.get().getMessageHeader() + "Improper syntax. Please use:\n  "
                    + Colors.GREEN + this.getUsage());
            return;
        }
        StringBuilder name = new StringBuilder();
        for (String s : command) {
            name.append(s).append(" ");
        }
        String newName = name.toString().trim();
        if (TownManager.get().getTown(newName) != null) {
            player.sendMessage(Config.get().getMessageHeader() + "A town named " + Colors.GREEN
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
        town.creeperNerf = false;
        town.friendlyFire = true;
        town.nopvp = false;
        town.protection = false;
        town.sanctuary = false;
        town.welcome = "Welcome to " + newName + "!";
        town.farewell = "You are now leaving " + newName + "!";
        TownManager.get().addTown(town);
        TownPlayerAccess townPlayer = new TownPlayerAccess();
        townPlayer.name = player.getName();
        townPlayer.townName = town.name;
        TownManager.get().addTownPlayer(townPlayer);
        player.sendMessage(Config.get().getMessageHeader() + "Congratulations! You are "
                + "now the owner of " + Colors.GREEN + newName + "!");
    }

    public String getBase() {
        return "create";
    }

    public String getUsage() {
        return "/town create [name]";
    }

    public String getDescription() {
        return "Creates a Town witht he given name.";
    }

    public boolean canUseCommand(Player player) {
        Town t = TownManager.get().getTownFromPlayer(player);
        if (t != null) {
            player.sendMessage(Config.get().getMessageHeader() + "You are already a part of " + Colors.GREEN
                    + t.getName() + Colors.WHITE + "! Please leave this town before creating a new one!");
            return false;
        }
        return true;
    }

}
