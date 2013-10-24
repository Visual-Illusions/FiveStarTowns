package net.visualillusionsent.fivestartowns.player;

/**
 *
 * @author Somners
 */
public interface IPlayer {

    /**
     * Get a Players name.
     * @return the name
     */
    public String getName();

    /**
     * Sends a player a message.
     * @param message message to send to player
     */
    public void message(String message);

    public int getChunkX();

    public int getChunkZ();

    public String getWorldName();
}
