package net.visualillusionsent.fivestartowns.canary.listeners;

import net.canarymod.Canary;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.chat.Colors;
import net.canarymod.chat.MessageReceiver;
import net.canarymod.commandsys.Command;
import net.canarymod.commandsys.CommandListener;
import net.canarymod.help.HelpNode;
import net.visualillusionsent.fivestartowns.Config;
import net.visualillusionsent.fivestartowns.commands.*;
import net.visualillusionsent.fivestartowns.player.CanaryPlayer;

/**
 *
 * @author Somners
 */
public class FSTCommandListener implements CommandListener {

    private String[] helpMenu = new String[] {
        "town",
        "town info",
        "town invite",
        "town accept",
        "town leave",
        "town claim",
        "town unclaim",
        "town flag",
        "town create",
        "town kick",
        "town promote",
        "town demote"

    };
    
    public FSTCommandListener() {
    }

    @Command(aliases = {"town", "t"},
            toolTip = "/town [help page number]",
            description = "Five Start Towns Base/Help Command",
            permissions = {"fivestartowns"},
            helpLookup = "town")
    public void onBaseCommand(MessageReceiver caller, String[] args) {
        if (!(caller instanceof Player)) {
            return;
        }
        short page = 1;
        if (args.length > 1) {
            try {
                page = Short.valueOf(args[1]);
            } catch (NumberFormatException ex) {
                // Do nothing, just don't error out here.
            }
        }
        caller.message(Config.get().getMessageHeader() + "FiveStarTowns Help Page #"
                + Colors.GREEN + String.valueOf(page) + "/" + String.valueOf(helpMenu.length / 5));
        for (int i = (5 * (page - 1)); i <( 5 * page) ; i++) {
            if (helpMenu.length > i) {
                HelpNode help = Canary.help().getRawHelp(this.helpMenu[i]);
                caller.message(Colors.GREEN + help.getTooltip() + "  " + Colors.WHITE + help.getDescription());
            }
        }
    }

    @Command(aliases = {"accept"},
            toolTip = "/town accept",
            description = "Accepts an invite to the last town to send you an invite.",
            permissions = {},
            parent = "town",
            helpLookup = "town accept")
    public void onAcceptInviteCommand(MessageReceiver caller, String[] args) {
        if (caller instanceof Player) {
            AcceptInviteCommand.execute(new CanaryPlayer((Player)caller), args);
        }
    }

    @Command(aliases = {"claim"},
            toolTip = "/town claim",
            description = "Claims the plot you are standing in.",
            permissions = {},
            parent = "town",
            helpLookup = "town claim")
    public void onClaimCommand(MessageReceiver caller, String[] args) {
        if (caller instanceof Player) {
            if (!ClaimCommand.canUseCommand(new CanaryPlayer((Player) caller))) return;
            ClaimCommand.execute(new CanaryPlayer((Player)caller), args);
        }
    }

    @Command(aliases = {"create"},
            toolTip = "/town create [name]",
            description = "Creates a Town with he given name.",
            permissions = {},
            parent = "town",
            helpLookup = "town create")
    public void onCreateCommand(MessageReceiver caller, String[] args) {
        if (caller instanceof Player) {
            CreateCommand.execute(new CanaryPlayer((Player)caller), args);
        }
    }

    @Command(aliases = {"leave"},
            toolTip = "/town leave",
            description = "Leaves the player's current town.",
            permissions = {},
            parent = "town",
            helpLookup = "town leave")
    public void onLeaveCommand(MessageReceiver caller, String[] args) {
        if (caller instanceof Player) {
            LeaveCommand.execute(new CanaryPlayer((Player)caller), args);
        }
    }

    @Command(aliases = {"info"},
            toolTip = "/town info",
            description = "Get the plot you are standing in, or the given towns info.",
            permissions = {},
            parent = "town",
            helpLookup = "town info")
    public void onInfoCommand(MessageReceiver caller, String[] args) {
        if (caller instanceof Player) {
            InfoCommand.execute(new CanaryPlayer((Player)caller), args);
        }
    }

    @Command(aliases = {"invite"},
            toolTip = "/town invite [playername]",
            description = "Invites this player to your town.",
            permissions = {},
            parent = "town",
            helpLookup = "town invite")
    public void onInviteCommand(MessageReceiver caller, String[] args) {
        if (caller instanceof Player) {
            if (!InviteCommand.canUseCommand(new CanaryPlayer((Player) caller))) return;
            InviteCommand.execute(new CanaryPlayer((Player)caller), args);
        }
    }

    @Command(aliases = {"flag"},
            toolTip = "/town flag [global|plot] [flagname] [NULL|TRUE|FALSE]",
            description = "Toggle [flagname] for global or plot.",
            permissions = {},
            parent = "town",
            helpLookup = "town flag")
    public void onToggleFlagCommand(MessageReceiver caller, String[] args) {
        if (caller instanceof Player) {
            if (!ToggleFlagCommand.canUseCommand(new CanaryPlayer((Player) caller))) return;
            ToggleFlagCommand.execute(new CanaryPlayer((Player)caller), args);
        }
    }

    @Command(aliases = {"unclaim"},
            toolTip = "/unclaim",
            description = "Unclaims the plot you are standing in.",
            permissions = {},
            parent = "town",
            helpLookup = "town unclaim")
    public void onUnclaimCommand(MessageReceiver caller, String[] args) {
        if (caller instanceof Player) {
            if (!UnclaimCommand.canUseCommand(new CanaryPlayer((Player) caller))) return;
            UnclaimCommand.execute(new CanaryPlayer((Player)caller), args);
        }
    }

    @Command(aliases = {"kick"},
            toolTip = "/town kick [playername]",
            description = "Kicks this player from your town.",
            permissions = {},
            parent = "town",
            helpLookup = "town kick")
    public void onKickCommand(MessageReceiver caller, String[] args) {
        if (caller instanceof Player) {
            if (!KickCommand.canUseCommand(new CanaryPlayer((Player) caller))) return;
            KickCommand.execute(new CanaryPlayer((Player)caller), args);
        }
    }

    @Command(aliases = {"promote"},
            toolTip = "/town promote [playername] [job]",
            description = "promotes the player to the given job.",
            permissions = {},
            parent = "town",
            helpLookup = "town promote")
    public void onPromoteCommand(MessageReceiver caller, String[] args) {
        if (caller instanceof Player) {
            if (!PromoteCommand.canUseCommand(new CanaryPlayer((Player) caller))) return;
            PromoteCommand.execute(new CanaryPlayer((Player)caller), args);
        }
    }

    @Command(aliases = {"demote"},
            toolTip = "/town demote <player name>",
            description = "demotes the player from their job.",
            permissions = {},
            parent = "town",
            helpLookup = "town demote")
    public void onDemoteCommand(MessageReceiver caller, String[] args) {
        if (caller instanceof Player) {
            if (!DemoteCommand.canUseCommand(new CanaryPlayer((Player) caller))) return;
            DemoteCommand.execute(new CanaryPlayer((Player)caller), args);
        }
    }

    @Command(aliases = {"me"},
            toolTip = "/town me <player name>",
            description = "Get info about yourself, or the player if specified",
            permissions = {},
            parent = "town",
            helpLookup = "town me")
    public void onMeCommand(MessageReceiver caller, String[] args) {
        if (caller instanceof Player) {
            if (!MeCommand.canUseCommand(new CanaryPlayer((Player) caller))) return;
            MeCommand.execute(new CanaryPlayer((Player)caller), args);
        }
    }
}
