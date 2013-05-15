/*
 * This file is copyright of WarHead Gaming. It is open Source and
 * free to use. Use of our code without express consent from the
 * developers is in violation of our license which has been provided
 * for you in the source and compiled jar.
 */
package com.warhead.fivestartowns.listeners.commands;

import com.warhead.fivestartowns.Config;
import com.warhead.fivestartowns.FlagType;
import com.warhead.fivestartowns.plot.Plot;
import com.warhead.fivestartowns.plot.PlotManager;
import com.warhead.fivestartowns.town.Town;
import com.warhead.fivestartowns.town.TownManager;
import com.warhead.fivestartowns.town.TownPlayer;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.chat.Colors;

/**
 *
 * @author somners
 */
public class ToggleFlagCommand implements FSTCommand {

    public void execute(Player player, String[] command) {
        Town town = TownManager.get().getTownFromPlayer(player);
        if (command.length != 2) {
            player.sendMessage(Config.get().getMessageHeader() + "Correct Usage: "
                    + Colors.GREEN + this.getUsage() + Colors.WHITE + "   " +
                    this.getDescription());
            player.sendMessage(Colors.GREEN + "Available flags: " + town.getFlagNames().toString());
            return;
        }
        FlagType type = FlagType.fromString(command[1]);
        if (type == null) {
            player.sendMessage(Config.get().getMessageHeader() + "Not a valid " + 
                    "flag type: " + Colors.GREEN + command[1]);
            player.sendMessage(Colors.GREEN + "Available flags: " + town.getFlagNames().toString());
            return;
        }
        if (!town.canUseFlag(command[1])) {
            player.sendMessage(Config.get().getMessageHeader() + "Your town cannot " + 
                    "use this flag: " + Colors.GREEN + command[1]);
            player.sendMessage(Colors.GREEN + "Available flags: " + town.getFlagNames().toString());
            return;
        }
        TownPlayer tp = TownManager.get().getTownPlayer(player);
        if (type.equals(FlagType.OWNER_PLOT) || command[0].equalsIgnoreCase("plot")) {
            Plot plot = PlotManager.get().getFSTPlot(player);
            if (plot == null || !plot.getTownName().equals(tp.getTownName())) {
                player.sendMessage(Config.get().getMessageHeader() + "This plot " +
                        "does not belong to your town.");
            }
            plot.toggleFlag(type);
            player.sendMessage(Config.get().getMessageHeader() + "Flag " + Colors.GREEN +
                    type.getName() + Colors.WHITE + " for this plot has been toggled to " +
                    Colors.GREEN + String.valueOf(plot.getFlagValue(type)));
            return;
        }
        else if (command[0].equalsIgnoreCase("global")) {
            town.toggleFlag(type, !town.getFlagValue(type));
            for (Plot p : PlotManager.get().getTownPlots(town.getName())) {
                p.toggleFlag(type, town.getFlagValue(type));
            }
            player.sendMessage(Config.get().getMessageHeader() + "Flag " + Colors.GREEN +
                    type.getName() + Colors.WHITE + "has been toggled to " + Colors.GREEN +
                    String.valueOf(town.getFlagValue(type)));
            return;
        }
    }

    public String getBase() {
        return "toggle";
    }

    public String getUsage() {
        return "/toggle [global|plot] [flag]";
    }

    public String getDescription() {
        return "Toggle [flag] for global or plot.";
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
