/*
 * This file is copyright of WarHead Gaming. It is open Source and
 * free to use. Use of our code without express consent from the
 * developers is in violation of our license which has been provided
 * for you in the source and compiled jar.
 */
package com.warhead.fivestartowns.listeners.commands;

import net.canarymod.api.entity.living.humanoid.Player;

/**
 *
 * @author Somners
 */
public class UnclaimCommand implements FSTCommand {

    public void execute(Player player, String[] command) {
        throw new UnsupportedOperationException("Method 'execute' in class 'ClaimCommand' is not supported yet.");
    }

    public String getBase() {
        return "/unclaim";
    }

    public String getUsage() {
        return "/unclaim";
    }

    public String getDescription() {
        return "Unclaims the chunk you are standing in.";
    }
}
