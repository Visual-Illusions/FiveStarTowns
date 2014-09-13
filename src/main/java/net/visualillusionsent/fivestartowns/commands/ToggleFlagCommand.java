package net.visualillusionsent.fivestartowns.commands;

import net.canarymod.chat.Colors;
import net.visualillusionsent.fivestartowns.Config;
import net.visualillusionsent.fivestartowns.flag.FlagType;
import net.visualillusionsent.fivestartowns.flag.FlagValue;
import net.visualillusionsent.fivestartowns.player.IPlayer;
import net.visualillusionsent.fivestartowns.plot.Plot;
import net.visualillusionsent.fivestartowns.plot.PlotManager;
import net.visualillusionsent.fivestartowns.town.Town;
import net.visualillusionsent.fivestartowns.town.TownManager;
import net.visualillusionsent.fivestartowns.town.TownPlayer;

/**
 *
 * @author somners
 */
public class ToggleFlagCommand {

    public static void execute(IPlayer player, String[] command) {
        Town town = TownManager.get().getTownFromPlayer(player);
        if (command.length != 4) {
            player.message(Config.get().getMessageHeader() + "Correct Usage: "
                    + Colors.GREEN + "/town flag [global|plot] [flagname] [NULL|TRUE|FALSE]" +
                    Colors.WHITE + "   " + "Toggle [flagname] for global or plot.");
            player.message(Colors.GREEN + "Available flags: " + town.getTownRank().getFlags().toString());
            return;
        }
        if (!command[1].equalsIgnoreCase("plot") && !command[1].equalsIgnoreCase("global")) {
            player.message(Config.get().getMessageHeader() + "Not a valid " +
                    "zone type: " + Colors.GREEN + command[1]);
            player.message(Colors.GREEN + "Available zones: global, plot");
            return;
        }
        FlagType type = FlagType.fromString(command[2]);
        if (type == null) {
            player.message(Config.get().getMessageHeader() + "Not a valid " +
                    "flag type: " + Colors.GREEN + command[1]);
            player.message(Colors.GREEN + "Available flags: " + town.getTownRank().getFlags().toString());
            return;
        }
        if (!town.canUseFlag(type.getName())) {
            player.message(Config.get().getMessageHeader() + "Your town cannot " +
                    "use this flag: " + Colors.GREEN + command[2]);
            player.message(Colors.GREEN + "Available flags: " + town.getTownRank().getFlags().toString());
            return;
        }
        FlagValue value = FlagValue.getType(command[3]);
        if (value == null) {
            player.message(Config.get().getMessageHeader() + "Not a valid flag " +
                    "value: " + Colors.GREEN + command[3]);
            player.message(Colors.GREEN + "Available flag values: NULL, TRUE, FALSE" + town.getTownRank().getFlags().toString());
            return;
        }
        TownPlayer tp = TownManager.get().getTownPlayer(player);
        if (type.equals(FlagType.OWNER_PLOT) || command[1].equalsIgnoreCase("plot")) {
            Plot plot = PlotManager.get().getFSTPlot(player);
            if (plot == null || !plot.getTownName().equals(tp.getTownName())) {
                player.message(Config.get().getMessageHeader() + "This plot " +
                        "does not belong to your town.");
            }
            plot.setFlagValue(type, value);
            player.message(Config.get().getMessageHeader() + "Flag " + Colors.GREEN +
                    type.getName() + Colors.WHITE + " for this plot has been toggled to " +
                    Colors.GREEN + value.toString());
            return;
        }
        else if (command[1].equalsIgnoreCase("global")) {
            town.setFlagValue(type, value);
            player.message(Config.get().getMessageHeader() + "Flag " + Colors.GREEN +
                    type.getName() + Colors.WHITE + "has been toggled to " + Colors.GREEN +
                    String.valueOf(town.getFlagValue(type)));
            return;
        }
    }

    public static boolean canUseCommand(IPlayer player) {
        TownPlayer tp = TownManager.get().getTownPlayer(player);
        if (tp != null && !tp.isAssistant() && !tp.isOwner()) {
            player.message(Config.get().getMessageHeader() + "You must be a "
                    + "town " + Colors.GREEN + "Owner " + Colors.WHITE + "or "
                    + Colors.GREEN + "Assistant " + Colors.WHITE + "to use this command!");
            return false;
        }
        return true;
    }

}
