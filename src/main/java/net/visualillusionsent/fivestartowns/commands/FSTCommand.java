package net.visualillusionsent.fivestartowns.commands;

import net.visualillusionsent.fivestartowns.player.IPlayer;

/**
 *
 * @author Somners
 */
public abstract class FSTCommand {

    protected static FSTCommand instance = null;
    protected String[] base = null;
    protected String usage = null;
    protected String description = null;

    public abstract void execute(IPlayer player, String[] command);

    public String[] getBase() {
        return base;
    }

    public String getUsage() {
        return usage;
    }

    public String getDescription() {
        return description;
    }
    
    public abstract boolean canUseCommand(IPlayer player);
}
