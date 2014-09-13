package net.visualillusionsent.fivestartowns.canary.listeners;

import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.chat.MessageReceiver;
import net.canarymod.commandsys.Command;
import net.canarymod.commandsys.CommandListener;
import net.visualillusionsent.fivestartowns.commands.*;
import net.visualillusionsent.fivestartowns.player.CanaryPlayer;

import java.util.HashMap;

/**
 *
 * @author Somners
 */
public class FSTCommandListener implements CommandListener {

    private HashMap<String, FSTCommand> commands;

    public FSTCommandListener() {
    }

    @Command(aliases = {"town", "t"},
            toolTip = "/town ?",
            description = "Five Start Towns Base Command",
            permissions = {"fivestartowns"})
    public void onBaseCommand(MessageReceiver caller, String[] args) {
        if (!(caller instanceof Player)) {
            return;
        }
    }

    @Command(aliases = {"accept"},
            toolTip = "/town accept",
            description = "Accepts an invite to the last town to send you an invite.",
            permissions = {},
            parent = "town")
    public void onAcceptInviteCommand(MessageReceiver caller, String[] args) {
        if (caller instanceof Player) {
            AcceptInviteCommand.get().execute(new CanaryPlayer((Player)caller), args);
        }
    }

    @Command(aliases = {"claim"},
            toolTip = "/town claim",
            description = "Claims the plot you are standing in.",
            permissions = {},
            parent = "town")
    public void onClaimCommand(MessageReceiver caller, String[] args) {
        if (caller instanceof Player) {
            if (!ClaimCommand.get().canUseCommand(new CanaryPlayer((Player) caller))) return;
            ClaimCommand.get().execute(new CanaryPlayer((Player)caller), args);
        }
    }

    @Command(aliases = {"create"},
            toolTip = "/town create [name]",
            description = "Creates a Town with he given name.",
            permissions = {},
            parent = "town")
    public void onCreateCommand(MessageReceiver caller, String[] args) {
        if (caller instanceof Player) {
            CreateCommand.get().execute(new CanaryPlayer((Player)caller), args);
        }
    }

    @Command(aliases = {"leave"},
            toolTip = "/town leave",
            description = "Leaves the player's current town.",
            permissions = {},
            parent = "town")
    public void onLeaveCommand(MessageReceiver caller, String[] args) {
        if (caller instanceof Player) {
            LeaveCommand.get().execute(new CanaryPlayer((Player)caller), args);
        }
    }

    @Command(aliases = {"info"},
            toolTip = "/town info",
            description = "Get the plot you are standing in, or the given towns info.",
            permissions = {},
            parent = "town")
    public void onInfoCommand(MessageReceiver caller, String[] args) {
        if (caller instanceof Player) {
            InfoCommand.get().execute(new CanaryPlayer((Player)caller), args);
        }
    }

    @Command(aliases = {"invite"},
            toolTip = "/town invite [playername]",
            description = "Invites this player to your town.",
            permissions = {},
            parent = "town")
    public void onInviteCommand(MessageReceiver caller, String[] args) {
        if (caller instanceof Player) {
            InviteCommand.get().execute(new CanaryPlayer((Player)caller), args);
        }
    }

    @Command(aliases = {"flag"},
            toolTip = "/town flag [global|plot] [flagname] [NULL|TRUE|FALSE]",
            description = "Toggle [flagname] for global or plot.",
            permissions = {},
            parent = "town")
    public void onToggleFlagCommand(MessageReceiver caller, String[] args) {
        if (caller instanceof Player) {
            ToggleFlagCommand.get().execute(new CanaryPlayer((Player)caller), args);
        }
    }

    @Command(aliases = {"unclaim"},
            toolTip = "/unclaim",
            description = "Unclaims the plot you are standing in.",
            permissions = {},
            parent = "town")
    public void onUnclaimCommand(MessageReceiver caller, String[] args) {
        if (caller instanceof Player) {
            UnclaimCommand.get().execute(new CanaryPlayer((Player)caller), args);
        }
    }
}
