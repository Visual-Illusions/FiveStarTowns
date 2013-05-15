/*
 * This file is copyright of WarHead Gaming. It is open Source and
 * free to use. Use of our code without express consent from the
 * developers is in violation of our license which has been provided
 * for you in the source and compiled jar.
 */
package com.warhead.fivestartowns;

import com.warhead.fivestartowns.plot.PlotManager;
import java.util.HashMap;

/**
 *
 * @author somners
 */
public class InviteManager {
    
    private static HashMap<String, String> invites = new HashMap<String, String>();
    private static InviteManager instance;
    
    public static InviteManager get() {
        if (instance == null) {
            instance = new InviteManager();
        }
        return instance;
    }
    
    public void addInvite(String player, String town) {
        if (invites.containsKey(player)) {
            invites.remove(player);
            invites.put(player, town);
        } else {
            invites.put(player, town);
        }
    }
    
    public boolean hasInvite(String player) {
        return invites.containsKey(player);
    }
    
    public void removeInvite(String player) {
        if (invites.containsKey(player)) {
            invites.remove(player);
        }
    }
    
    public String getInvite(String player) {
        if (invites.containsKey(player)) {
            return invites.get(player);
        }
        return null;
    }
    
}
