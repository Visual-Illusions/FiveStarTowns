/*
 * This file is copyright of WarHead Gaming. It is open Source and
 * free to use. Use of our code without express consent from the
 * developers is in violation of our license which has been provided
 * for you in the source and compiled jar.
 */
package com.warhead.fivestartowns.listeners;

import com.warhead.fivestartowns.listeners.commands.*;
import java.util.Arrays;
import java.util.HashMap;
import net.canarymod.Canary;
import net.canarymod.hook.HookHandler;
import net.canarymod.hook.command.PlayerCommandHook;
import net.canarymod.plugin.PluginListener;

/**
 *
 * @author Somners
 */
public class CommandListener implements PluginListener {

    private HashMap<String, FSTCommand> commands;

    public CommandListener() {
//        commands;
        commands = new HashMap<String, FSTCommand>();
        init();
    }

    @HookHandler
    public void onPlayerCommand(PlayerCommandHook hook) {
        String[] command = hook.getCommand();
        /*
         * Claim Command
         */
        Canary.logInfo(command[0]);
        if (command[0].equalsIgnoreCase("/town") || command[0].equalsIgnoreCase("/t")) {
            if (command.length > 1 && commands.containsKey(command[1])) {
                commands.get(command[1]).execute(hook.getPlayer(), this.remove(command, 2));
                hook.setCanceled();
            }
        }
        else if(commands.containsKey(command[0])) {
            Canary.logInfo(command[0]);
            Canary.logInfo(String.valueOf(remove(command, 1).length));
//            commands.get(command[0]).execute(hook.getPlayer(), this.remove(command, 1));
            commands.get(command[0]).execute(hook.getPlayer(), new String[0]);
            hook.setCanceled();
        }

    }

    public String[] remove(String[] command, int num) {
        if (num == command.length) {
            return new String[0];
        }
        return Arrays.copyOfRange(command, num, command.length);
//        String[] toRet = new String[(command.length - num)];
//        for (int i = num; i < command.length; i++) {
//            toRet[(i - num)] = command[i];
//        }
//        return toRet;
    }

    public void init() {
        ClaimCommand claim = new ClaimCommand();
        commands.put(claim.getBase(), claim);
//        Canary.help().registerCommand(FiveStarTowns.get(), claim.getUsage(), claim.getDescription());
        UnclaimCommand unclaim = new UnclaimCommand();
        commands.put(unclaim.getBase(), unclaim);
        CreateCommand create = new CreateCommand();
        commands.put(create.getBase(), create);
    }
}
