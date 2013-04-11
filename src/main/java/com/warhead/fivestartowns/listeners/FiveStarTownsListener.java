/*
 * This file is copyright of WarHead Gaming. It is open Source and
 * free to use. Use of our code without express consent from the
 * developers is in violation of our license which has been provided
 * for you in the source and compiled jar.
 */
package com.warhead.fivestartowns.listeners;

import com.warhead.fivestartowns.ChunkManager;
import com.warhead.fivestartowns.Config;
import com.warhead.fivestartowns.FSTChunk;
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
        FSTChunk toChunk = ChunkManager.get().getFSTChunk(hook.getTo());
        FSTChunk fromChunk = ChunkManager.get().getFSTChunk(hook.getFrom());
        if (toChunk != null) {
            to = toChunk.getTownName();
        }
        if (fromChunk != null) {
            from = fromChunk.getTownName();
        }
        if (!from.equalsIgnoreCase(to)) {
            hook.getPlayer().sendMessage(Config.get().getMessageHeader() + "You are leaving "
                    + Colors.GREEN + from + Colors.WHITE + " and entering "
                    + Colors.GREEN + to);
            if (fromChunk != null) {
                hook.getPlayer().sendMessage(Colors.BLACK + " | " + Colors.WHITE + fromChunk.getTown().getFarewell());
            }
            if (toChunk != null) {
                hook.getPlayer().sendMessage(Colors.BLACK + " | " + Colors.WHITE + toChunk.getTown().getWelcome());
            }
        }
    }
}
