/*
 * This file is copyright of WarHead Gaming. It is open Source and
 * free to use. Use of our code without express consent from the
 * developers is in violation of our license which has been provided
 * for you in the source and compiled jar.
 */
package com.warhead.fivestartowns.listeners.commands;

import com.warhead.fivestartowns.plot.PlotManager;
import com.warhead.fivestartowns.Config;
import com.warhead.fivestartowns.plot.Plot;
import com.warhead.fivestartowns.town.Town;
import com.warhead.fivestartowns.town.TownManager;
import com.warhead.fivestartowns.town.TownPlayer;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.chat.Colors;

/**
 *
 * @author Somners
 */
public class UnclaimCommand implements FSTCommand {

    public void execute(Player player, String[] command) {
        Plot plot = PlotManager.get().getFSTPlot(player);
        Town town = TownManager.get().getTownFromPlayer(player);
        if (plot == null || !plot.getTown().equals(town)) {
            player.sendMessage(Config.get().getMessageHeader() + "This plots is "
                    + "not owned by your town. You cannot unclaim it.");
            return;
        }
        PlotManager.get().removePlot(plot.getAccess());

        player.sendMessage(Config.get().getMessageHeader() + "Plot UnClaimed for " + Colors.GREEN + town.getName() + "!");

    }

    public String getBase() {
        return "/unclaim";
    }

    public String getUsage() {
        return "/unclaim";
    }

    public String getDescription() {
        return "Unclaims the plot you are standing in.";
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
