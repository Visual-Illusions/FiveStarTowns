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
public interface FSTCommand {

    public void execute(Player player, String[] command);

    public String getBase();

    public String getUsage();

    public String getDescription();
}
