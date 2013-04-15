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
import com.warhead.fivestartowns.database.PlotAccess;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.world.position.Location;
import net.canarymod.chat.Colors;

/**
 *
 * @author Somners
 */
public class ClaimCommand implements FSTCommand {

    public void execute(Player player, String[] command) {
        Plot plot = PlotManager.get().getFSTPlot(player);
        if (plot != null) {
            player.sendMessage(Config.get().getMessageHeader() + "This plots is already owned by: " + plot.getTownName());
            return;
        }
        Town town = TownManager.get().getTownFromPlayer(player);
        if (town == null) {
            player.sendMessage(Config.get().getMessageHeader() + "town is null :p");
            return;
        }
        if (town.getCurrentClaimCount() >= town.getMaxClaimCount()) {
            player.sendMessage(Config.get().getMessageHeader() + "Your town has already claimed too many plots! "
                    + "Consider unclaiming some.");
            return;
        }
        if (!PlotManager.get().isPlotNextToTown(player.getLocation(), town.getName()) && PlotManager.get().getTownPlots(town.getName()).length != 0) {
            player.sendMessage(Config.get().getMessageHeader() + "Your town does not sit adjacent to this plot! "
                    + "Try claiming a plot next to your town.");
            return;
        }
        Location loc = player.getLocation();
        PlotAccess data = new PlotAccess();
        data.x = ((int)loc.getX()) >> 4;
        data.z = ((int)loc.getZ()) >> 4;
        data.world = loc.getWorldName();
        data.creeperNerf = town.getCreeperNerf();
        data.friendlyFire = town.getFriendlyFire();
        data.nopvp = town.getNoPvp();
        data.protection = town.getProtected();
        data.sanctuary = town.getSanctuary();
        data.town = town.getName();
        data.owner = "";
        PlotManager.get().addPlot(data);

        player.sendMessage(Config.get().getMessageHeader() + "Plot Claimed for " + Colors.GREEN + town.getName() + "!");

    }

    public String getBase() {
        return "/claim";
    }

    public String getUsage() {
        return "/claim";
    }

    public String getDescription() {
        return "Claims the plot you are standing in.";
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
