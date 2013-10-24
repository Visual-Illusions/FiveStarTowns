package net.visualillusionsent.fivestartowns.commands;

import net.visualillusionsent.fivestartowns.Config;
import net.visualillusionsent.fivestartowns.town.Town;
import net.visualillusionsent.fivestartowns.town.TownManager;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.chat.Colors;

/**
 *
 * @author Somners
 */
public class CommandFilter {

    /**
     * Checks whether or not a Player belongs to a town.
     * @param player
     * @return True if player is in a town. false otherwise.
     */
    public static boolean isInATown(Player player) {
        Town t = TownManager.get().getTownFromPlayer(player);
        if (t != null) {
            player.message(Config.get().getMessageHeader() + "You are already a part of " + Colors.GREEN
                    + t.getName() + Colors.WHITE + "! Please leave this town before creating a new one!");
            return true;
        }
        return false;
    }
}
