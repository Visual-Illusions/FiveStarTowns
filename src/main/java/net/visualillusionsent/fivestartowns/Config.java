package net.visualillusionsent.fivestartowns;

import java.io.File;
import java.io.IOException;
import net.canarymod.Canary;
import net.canarymod.chat.Colors;
import net.visualillusionsent.utils.PropertiesFile;

/**
 *
 * @author Somners
 */
public class Config {

    private static Config instance = null;
    private PropertiesFile config;
    private String server;
    private int plotMultiplier;

    public Config() {
        File file = new File("config/FiveStarTowns/FiveStarTowns.properties");
        if (!file.exists()) {
            new File("config/FiveStarTowns/").mkdirs();
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Canary.log.trace("Unable to Create: " + file.getName(), ex);
            }
        }
        config = new PropertiesFile("config/FiveStarTowns/FiveStarTowns.properties");
        this.load();
    }

    public static Config get() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public void load() {
        server = config.getString("server-name", "FiveStarTowns");
        plotMultiplier = config.getInt("plot-multiplier-per-player", 4);
        config.save();

    }

    public void comment() {
        config.addComment("server-name", "This will be in the header for messages sent to players");
        config.addComment("server-name", "By default it will be: '[FiveStarTowns]'");
        config.addComment("plot-multiplier-per-player", "How many plots does a town get for every player?");
    }

    public String getServerName() {
        return this.server;
    }

    public String getMessageHeader() {
        return Colors.BLACK + "[" + Colors.ORANGE + this.server + Colors.BLACK + "] " + Colors.WHITE;
    }

    public int getPlotsPerPlayer() {
        return this.plotMultiplier;
    }

}
