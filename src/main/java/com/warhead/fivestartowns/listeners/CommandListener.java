/*
 * This file is copyright of WarHead Gaming. It is open Source and
 * free to use. Use of our code without express consent from the
 * developers is in violation of our license which has been provided
 * for you in the source and compiled jar.
 */
package com.warhead.fivestartowns.listeners;

import com.warhead.fivestartowns.listeners.commands.*;
import java.util.HashMap;
import net.canarymod.hook.HookHandler;
import net.canarymod.hook.command.PlayerCommandHook;
import net.canarymod.plugin.PluginListener;

/**
 *
 * @author Somners
 */
public class CommandListener implements PluginListener {

    private static HashMap<String, FSTCommand> commands = new HashMap<String, FSTCommand>();

    @HookHandler()
    public void onPlayerCommand(PlayerCommandHook hook) {
        String[] command = hook.getCommand();
        /*
         * Claim Command
         */
        if (command[0].equalsIgnoreCase("/town") || command[0].equalsIgnoreCase("/t")) {
            if (commands.containsKey(command[1])) {
                commands.get(command[1]).execute(hook.getPlayer(), this.remove(command, 2));
            } else if(commands.containsKey(command[0])) {
                commands.get(command[0]).execute(hook.getPlayer(), this.remove(command, 1));
            }
        }


    }

    public String[] remove(String[] command, int num) {
        String[] toRet = new String[(command.length - 2)];
        for (int i = num; i < toRet.length; i++) {
            toRet[(i - num)] = command[i];
        }
        return toRet;
    }

    static {
        ClaimCommand claim = new ClaimCommand();
        commands.put(claim.getBase(), claim);
        UnclaimCommand unclaim = new UnclaimCommand();
        commands.put(unclaim.getBase(), unclaim);
    }
}
