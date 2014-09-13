package net.visualillusionsent.fivestartowns.player;

/**
 *
 * @author Somners
 */
public class CanaryPlayer implements IPlayer {

    private net.canarymod.api.entity.living.humanoid.Player player = null;

    public CanaryPlayer(net.canarymod.api.entity.living.humanoid.Player player) {
        this.player = player;
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public void message(String message) {
        player.message(message);
    }

    @Override
    public int getChunkX() {
        return (int) player.getX() >> 4;
    }

    @Override
    public int getChunkZ() {
        return (int) player.getZ() >> 4;
    }

    @Override
    public String getWorldName() {
        return player.getWorld().getName();
    }

    @Override
    public String getUUID() {
        return player.getUUIDString();
    }

}
