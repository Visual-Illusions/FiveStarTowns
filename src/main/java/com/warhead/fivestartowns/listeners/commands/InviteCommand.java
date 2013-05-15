/*
 * This file is copyright of WarHead Gaming. It is open Source and
 * free to use. Use of our code without express consent from the
 * developers is in violation of our license which has been provided
 * for you in the source and compiled jar.
 */
package com.warhead.fivestartowns.listeners.commands;

import com.warhead.fivestartowns.Config;
import com.warhead.fivestartowns.InviteManager;
import com.warhead.fivestartowns.town.TownManager;
import com.warhead.fivestartowns.town.TownPlayer;
import net.canarymod.Canary;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.chat.Colors;

/**
 *
 * @author somners
 */
public class InviteCommand implements FSTCommand {

    public void execute(Player player, String[] command) {
        if (command.length < 1) {
            player.sendMessage(Config.get().getMessageHeader() + "You must specify "
                    + "a player to invite.");
            return;
        }
        Player p = Canary.getServer().getPlayer(command[0]);
        if (p == null) {
            player.sendMessage(Config.get().getMessageHeader() + "You must specify "
                    + "an online player to invite.");
            return;
        }
        if (TownManager.get().getTownPlayer(p) != null) {
            player.sendMessage(Config.get().getMessageHeader() + Colors.GREEN + 
                    command[0] + Colors.WHITE + " is already in a town.");
            return;
        }
        String town = TownManager.get().getTownPlayer(player).getTownName();
        InviteManager.get().addInvite(p.getName(), town);
        player.sendMessage(Config.get().getMessageHeader() + Colors.GREEN + 
                    command[0] + Colors.WHITE + " has been invited to your town.");
        p.sendMessage(Config.get().getMessageHeader() + Colors.GREEN + 
                player.getName() + Colors.WHITE + " has invited you totown " +
                Colors.GREEN + town + Colors.WHITE + ".  To accept use command: " +
                Colors.GREEN + "/town accept");
    }

    public String getBase() {
        return "invite";
    }

    public String getUsage() {
        return "/town invite [playername]";
    }

    public String getDescription() {
        return "Invites this player to your town.";
    }

    public boolean canUseCommand(Player player) {
        TownPlayer tp = TownManager.get().getTownPlayer(player);
        if (tp != null && (tp.isAssistant() || tp.isOwner())) {
            player.sendMessage(Config.get().getMessageHeader() + "You must be a "
                    + "town " + Colors.GREEN + "Owner " + Colors.WHITE + "or "
                    + Colors.GREEN + "Assistant " + Colors.WHITE + "to use this command!");
            return false;
        }
        return true;
    }
}
