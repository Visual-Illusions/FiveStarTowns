/*
 * This file is copyright of WarHead Gaming. It is open Source and
 * free to use. Use of our code without express consent from the
 * developers is in violation of our license which has been provided
 * for you in the source and compiled jar.
 */
package com.warhead.fivestartowns.listeners.commands;

import com.warhead.fivestartowns.Config;
import com.warhead.fivestartowns.InviteManager;
import com.warhead.fivestartowns.database.TownPlayerAccess;
import com.warhead.fivestartowns.town.TownManager;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.chat.Colors;

/**
 *
 * @author somners
 */
public class AcceptInviteCommand implements FSTCommand {

    public void execute(Player player, String[] command) {
        if (TownManager.get().getTownPlayer(player) != null) {
            player.sendMessage(Config.get().getMessageHeader() + "You are already in a town.");
            return;
        }
        if (!InviteManager.get().hasInvite(player.getName())) {
            player.sendMessage(Config.get().getMessageHeader() + "You have to town invites :(");
            return;
        }
        TownPlayerAccess townPlayer = new TownPlayerAccess();
        townPlayer.name = player.getName();
        townPlayer.townName = InviteManager.get().getInvite(player.getName());
        TownManager.get().addTownPlayer(townPlayer);
        player.sendMessage(Config.get().getMessageHeader() + "Congratulations! You are "
                + "now a part of " + Colors.GREEN + townPlayer.townName + "!");
        InviteManager.get().removeInvite(player.getName());
    }

    public String getBase() {
        return "accept";
    }

    public String getUsage() {
        return "/town accept";
    }

    public String getDescription() {
        return "Accepts an invite to the last town to send you an invite.";
    }

    public boolean canUseCommand(Player player) {
        return true;
    }
    
}
