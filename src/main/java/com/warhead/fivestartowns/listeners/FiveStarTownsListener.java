/*
 * This file is copyright of WarHead Gaming. It is open Source and
 * free to use. Use of our code without express consent from the
 * developers is in violation of our license which has been provided
 * for you in the source and compiled jar.
 */
package com.warhead.fivestartowns.listeners;

import com.warhead.fivestartowns.plot.PlotManager;
import com.warhead.fivestartowns.Config;
import com.warhead.fivestartowns.plot.Plot;
import net.canarymod.chat.Colors;
import net.canarymod.hook.HookHandler;
import net.canarymod.hook.player.PlayerMoveHook;
import net.canarymod.plugin.PluginListener;

/**
 *
 * @author Somners
 */
public class FiveStarTownsListener implements PluginListener {

    @HookHandler
    public void onPlayerMove(PlayerMoveHook hook) {
        String to = "The Wilderness";
        String from = "The Wilderness";
        Plot toPlot = PlotManager.get().getFSTPlot(hook.getTo());
        Plot fromPlot = PlotManager.get().getFSTPlot(hook.getFrom());
        if (toPlot != null) {
            to = toPlot.getTownName();
        }
        if (fromPlot != null) {
            from = fromPlot.getTownName();
        }
        if (!from.equalsIgnoreCase(to)) {
            hook.getPlayer().sendMessage(Config.get().getMessageHeader() + "You are leaving "
                    + Colors.GREEN + from + Colors.WHITE + " and entering "
                    + Colors.GREEN + to);
            if (fromPlot != null) {
                hook.getPlayer().sendMessage(Colors.BLACK + " | " + Colors.WHITE + fromPlot.getTown().getFarewell());
            }
            if (toPlot != null) {
                hook.getPlayer().sendMessage(Colors.BLACK + " | " + Colors.WHITE + toPlot.getTown().getWelcome());
            }
        }
    }
}
