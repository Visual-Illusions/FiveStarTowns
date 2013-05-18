/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.warhead.fivestartowns.listeners.commands;

import com.warhead.fivestartowns.Config;
import com.warhead.fivestartowns.plot.Plot;
import com.warhead.fivestartowns.plot.PlotManager;
import com.warhead.fivestartowns.town.Town;
import com.warhead.fivestartowns.town.TownManager;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.chat.Colors;

/**
 *
 * @author somners
 */
public class InfoCommand implements FSTCommand {

    @Override
    public void execute(Player player, String[] command) {
        if (command.length > 1) {
            Town town = TownManager.get().getTown(command[0]);
            if (town == null) {
                player.sendMessage(Config.get().getMessageHeader() + "The town "
                    + Colors.GREEN + command[0] + Colors.WHITE + " does not exist.");
                return;
            }
            player.sendMessage(Config.get().getMessageHeader() + "The town "
                    + Colors.GREEN + town.getTownRank().getTownPrefix() + town.getName() +
                    town.getTownRank().getTownSuffix());
            player.sendMessage(Colors.BLACK + "-- " + Colors.GREEN + "Owner: " + 
                    Colors.WHITE + town.getTownRank().getMayorPrefix() + town.getOwnerName() + 
                    town.getTownRank().getMayorSuffix());
            player.sendMessage(Colors.BLACK + "-- " + Colors.GREEN + "Assistants: " + 
                    Colors.WHITE + town.getAssistant().toString());
            player.sendMessage(Colors.BLACK + "-- " + Colors.GREEN + "Plots: "
                    + Colors.WHITE + town.getCurrentClaimCount() + "/" + town.getMaxClaimCount()); 
            player.sendMessage(Colors.BLACK + "-- " + Colors.GREEN + "Flags enabled: "
                    + Colors.WHITE + town.getEnabledFlags());                   
            return;
        }
        else {
            Plot plot = PlotManager.get().getFSTPlot(player);
            if (plot == null) {
                player.sendMessage(Config.get().getMessageHeader() + "This plot is "
                        + "not owned by a town.");
                return;
            }
            Town town = plot.getTown();
            player.sendMessage(Config.get().getMessageHeader() + "The town "
                    + Colors.GREEN + town.getTownRank().getTownPrefix() + town.getName() +
                    town.getTownRank().getTownSuffix());
            player.sendMessage(Colors.BLACK + "-- " + Colors.GREEN + "Owner: " + 
                    Colors.WHITE + town.getTownRank().getMayorPrefix() + town.getOwnerName() + 
                    town.getTownRank().getMayorSuffix());
            player.sendMessage(Colors.BLACK + "-- " + Colors.GREEN + "Assistants: " + 
                    Colors.WHITE + town.getAssistant().toString());
            player.sendMessage(Colors.BLACK + "-- " + Colors.GREEN + "Plots: "
                    + Colors.WHITE + town.getCurrentClaimCount() + "/" + town.getMaxClaimCount()); 
            player.sendMessage(Colors.BLACK + "-- " + Colors.GREEN + "Flags enabled: "
                    + Colors.WHITE + town.getEnabledFlags());
            player.sendMessage(Colors.BLACK + "-- " + Colors.GREEN + "Plot Flags enabled: "
                    + Colors.WHITE + plot.getEnabledFlags());  
            return;
        }
    }

    @Override
    public String getBase() {
        return "info";
    }

    @Override
    public String getUsage() {
        return "/town info <town name>";
    }

    @Override
    public String getDescription() {
        return "Get the plot you are standing in, or the given towns info.";
    }

    @Override
    public boolean canUseCommand(Player player) {
        return true;
    }
    
}
