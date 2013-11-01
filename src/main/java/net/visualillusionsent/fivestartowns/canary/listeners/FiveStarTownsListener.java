package net.visualillusionsent.fivestartowns.canary.listeners;

import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.entity.living.monster.Creeper;
import net.canarymod.chat.Colors;
import net.canarymod.hook.HookHandler;
import net.canarymod.hook.entity.DamageHook;
import net.canarymod.hook.player.PlayerMoveHook;
import net.canarymod.hook.world.ExplosionHook;
import net.canarymod.plugin.PluginListener;
import net.visualillusionsent.fivestartowns.Config;
import net.visualillusionsent.fivestartowns.flag.FlagType;
import net.visualillusionsent.fivestartowns.plot.Plot;
import net.visualillusionsent.fivestartowns.plot.PlotManager;
import net.visualillusionsent.fivestartowns.town.TownManager;
import net.visualillusionsent.fivestartowns.town.TownPlayer;

/**
 *
 * @author Somners
 */
public class FiveStarTownsListener implements PluginListener {

    @HookHandler
    public void onPlayerMove(PlayerMoveHook hook) {
        String to = "The Wilderness";
        String from = "The Wilderness";
        Plot toPlot = PlotManager.get().getFSTPlot(hook.getTo());
        Plot fromPlot = PlotManager.get().getFSTPlot(hook.getFrom());
        if (toPlot != null) {
            to = toPlot.getTownName();
        }
        if (fromPlot != null) {
            from = fromPlot.getTownName();
        }
        if (!from.equalsIgnoreCase(to)) {
            hook.getPlayer().message(Config.get().getMessageHeader() + "You are leaving "
                    + Colors.GREEN + from + Colors.WHITE + " and entering "
                    + Colors.GREEN + to);
            if (fromPlot != null) {
                hook.getPlayer().message(Colors.BLACK + " | " + Colors.WHITE + fromPlot.getTown().getFarewell());
            }
            if (toPlot != null) {
                hook.getPlayer().message(Colors.BLACK + " | " + Colors.WHITE + toPlot.getTown().getWelcome());
            }
        }
    }

    /*
     * PvP Check & Friendly Fire Check
     */
    @HookHandler
    public void onPlayerAttacked(DamageHook hook) {
        if (hook.getAttacker() instanceof Player && hook.getDefender() instanceof Player) {
            Player attacker = (Player)hook.getAttacker();
            Player defender = (Player)hook.getDefender();
            Plot a = PlotManager.get().getFSTPlot(attacker);
            Plot d = PlotManager.get().getFSTPlot(defender);
            if ((a != null && a.getFlagValue(FlagType.NO_PVP).getBoolean()) ||
                    (d != null && d.getFlagValue(FlagType.NO_PVP).getBoolean())) {
                hook.setCanceled();
                return;
            }
            TownPlayer tpa = TownManager.get().getTownPlayer(attacker);
            TownPlayer tpd = TownManager.get().getTownPlayer(defender);
            if ((tpa != null && tpd != null) && tpa.getTownName().equals(tpd.getTownName())) {
                if ((a != null && !a.getFlagValue(FlagType.FRIENDLY_FIRE).getBoolean()) ||
                        (d != null && !d.getFlagValue(FlagType.FRIENDLY_FIRE).getBoolean())) {
                    hook.setCanceled();
                    return;
                }
            }
        }
    }

    /*
     * Creeper Explosion Check
     */
    @HookHandler
    public void onCreeperCreep(ExplosionHook hook) {
        if (hook.getEntity() instanceof Creeper) {
            Plot p = PlotManager.get().getFSTPlot(hook.getEntity().getLocation());
            if (p != null && p.getFlagValue(FlagType.CREEPER_NERF).getBoolean()) {
                hook.setCanceled();
            }
        }
    }
}
