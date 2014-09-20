package net.visualillusionsent.fivestartowns.canary.listeners;

import net.canarymod.Canary;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.entity.living.monster.Creeper;
import net.canarymod.chat.Colors;
import net.canarymod.database.Database;
import net.canarymod.database.exceptions.DatabaseWriteException;
import net.canarymod.hook.HookHandler;
import net.canarymod.hook.entity.DamageHook;
import net.canarymod.hook.player.BlockDestroyHook;
import net.canarymod.hook.player.BlockPlaceHook;
import net.canarymod.hook.player.ConnectionHook;
import net.canarymod.hook.player.PlayerMoveHook;
import net.canarymod.hook.world.ExplosionHook;
import net.canarymod.plugin.PluginListener;
import net.visualillusionsent.fivestartowns.Config;
import net.visualillusionsent.fivestartowns.database.TownPlayerAccess;
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


    /*
     * Login Hook, handles initializing player data.
     */
    @HookHandler
    public void onLogin(ConnectionHook hook) {
        TownPlayer tp = TownManager.get().getTownPlayer(hook.getPlayer().getUUIDString());
        if (tp == null) {
            TownPlayerAccess tpa = new TownPlayerAccess();
            tpa.uuid = hook.getPlayer().getUUIDString();
            tpa.name = hook.getPlayer().getName();
            tpa.townUUID = -1;
            
            try {
                Database.get().insert(tpa);
            } catch (DatabaseWriteException ex) {
                Canary.log.trace("Error inserting new TownPlayer data.", ex);
            }
            tp = new TownPlayer(tpa.uuid);
            TownManager.get().addTownPlayer(tp);
        }
        Canary.log.info("Town id: " + tp.getTownUUID());
        Canary.log.info("Player id: " + tp.getUUID());
    }
    
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
            if ((a != null && a.isFlagEnabled(FlagType.NO_PVP)) ||
                    (d != null && d.isFlagEnabled(FlagType.NO_PVP))) {
                hook.setCanceled();
                return;
            }
            TownPlayer tpa = TownManager.get().getTownPlayer(attacker);
            TownPlayer tpd = TownManager.get().getTownPlayer(defender);
            if ((tpa != null && tpd != null) && tpa.getTownName().equals(tpd.getTownName())) {
                if ((a != null && !a.isFlagEnabled(FlagType.FRIENDLY_FIRE)) ||
                        (d != null && !d.isFlagEnabled(FlagType.FRIENDLY_FIRE))) {
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
            if (p != null && p.isFlagEnabled(FlagType.CREEPER_NERF)) {
                hook.setCanceled();
            }
        }
    }

    /*
     * Protection Check
     */
    @HookHandler
    public void onBlockBreak(BlockDestroyHook hook) {
            Plot p = PlotManager.get().getFSTPlot(hook.getBlock().getLocation());
            if (p != null){
                TownPlayer tp = TownManager.get().getTownPlayer(hook.getPlayer());
                /* Check protection flag */
                if (p.isFlagEnabled(FlagType.PROTECTION)) {
                    /* player isn't in town CANCEL! */
                    if (tp.getTown() == null) {
                        hook.setCanceled();
                        return;
                    }
                    /* player is in a different town CANCEL! */
                    if (!tp.getTown().equals(p.getTown())) {
                        hook.setCanceled();
                        return;
                    }
                }
                /* check plot owner stuff */
                if (p.getPlotOwner() != null) {
                    /* player is not the plots owner */
                    if (!p.getPlotOwner().equals(tp)) {
                        hook.setCanceled();
                        return;
                    }
                }
            }
    }

    /*
     * Protection Check
     */
    @HookHandler
    public void onBlockPlace(BlockPlaceHook hook) {
        Plot p = PlotManager.get().getFSTPlot(hook.getBlockPlaced().getLocation());
        if (p != null){
            TownPlayer tp = TownManager.get().getTownPlayer(hook.getPlayer());
                /* Check protection flag */
            if (p.isFlagEnabled(FlagType.PROTECTION)) {
                    /* player isn't in town CANCEL! */
                if (tp.getTown() == null) {
                    hook.setCanceled();
                    return;
                }
                    /* player is in a different town CANCEL! */
                if (!tp.getTown().equals(p.getTown())) {
                    hook.setCanceled();
                    return;
                }
            }
                /* check plot owner stuff */
            if (p.getPlotOwner() != null) {
                if (!p.getPlotOwner().equals(tp)) {
                    hook.setCanceled();
                    return;
                }
            }
        }
    }
}
