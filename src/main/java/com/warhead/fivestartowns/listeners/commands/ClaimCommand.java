/*
 * This file is copyright of WarHead Gaming. It is open Source and
 * free to use. Use of our code without express consent from the
 * developers is in violation of our license which has been provided
 * for you in the source and compiled jar.
 */
package com.warhead.fivestartowns.listeners.commands;

import com.warhead.fivestartowns.ChunkManager;
import com.warhead.fivestartowns.Config;
import com.warhead.fivestartowns.FSTChunk;
import com.warhead.fivestartowns.Town;
import com.warhead.fivestartowns.TownManager;
import com.warhead.fivestartowns.database.ChunkAccess;
import net.canarymod.Canary;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.world.position.Location;
import net.canarymod.chat.Colors;

/**
 *
 * @author Somners
 */
public class ClaimCommand implements FSTCommand {

    public void execute(Player player, String[] command) {
        Canary.logInfo(player.getWorld().getName());
        FSTChunk chunk = ChunkManager.get().getFSTChunk(player);
        if (chunk != null) {
            player.sendMessage(Config.get().getMessageHeader() + "This chunks is already owned by: " + chunk.getTownName());
            return;
        }
        Town town = TownManager.get().getTownFromPlayer(player);
        if (town == null) {
            player.sendMessage(Config.get().getMessageHeader() + "town is null :p");
            return;
        }
        if (town.getCurrentClaimCount() >= town.getMaxClaimCount()) {
            player.sendMessage(Config.get().getMessageHeader() + "Your town has already claimed too many chunks! "
                    + "Consider unclaiming some.");
            return;
        }
        if (!ChunkManager.get().isChunkNextToTown(player.getLocation(), town.getName()) && ChunkManager.get().getTownChunks(town.getName()).length != 0) {
            player.sendMessage(Config.get().getMessageHeader() + "Your town does not sit adjacent to this chunk! "
                    + "Try claiming a chunk next to your town.");
            return;
        }
        Location loc = player.getLocation();
        ChunkAccess data = new ChunkAccess();
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
        ChunkManager.get().addChunk(data);

        player.sendMessage(Config.get().getMessageHeader() + "Plot Claimed for " + Colors.GREEN + town.getName() + "!");

    }

    public String getBase() {
        return "/claim";
    }

    public String getUsage() {
        return "/claim";
    }

    public String getDescription() {
        return "Claims the chunk you are standing in.";
    }

}
