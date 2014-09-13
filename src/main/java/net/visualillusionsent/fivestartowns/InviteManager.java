package net.visualillusionsent.fivestartowns;

import java.util.HashMap;

/**
 *
 * @author somners
 */
public class InviteManager {

    private static HashMap<String, Integer> invites = new HashMap<String, Integer>();
    private static InviteManager instance;

    public static InviteManager get() {
        if (instance == null) {
            instance = new InviteManager();
        }
        return instance;
    }

    public void addInvite(String player, int town) {
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

    public int getInvite(String player) {
        if (invites.containsKey(player)) {
            return invites.get(player);
        }
        return -1;
    }

}
