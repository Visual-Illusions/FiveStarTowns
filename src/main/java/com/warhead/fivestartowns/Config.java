/*
 * This file is copyright of WarHead Gaming. It is open Source and
 * free to use. Use of our code without express consent from the
 * developers is in violation of our license which has been provided
 * for you in the source and compiled jar.
 */
package com.warhead.fivestartowns;

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
    private int chunkMultiplier;

    public Config() {
        File file = new File("plugins/config/FiveStarTowns/FiveStarTowns.properties");
        if (!file.exists()) {
            new File("plugins/config/FiveStarTowns/").mkdirs();
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Canary.logStackTrace("Unable to Create: " + file.getName(), ex);
            }
        }
        config = new PropertiesFile("plugins/config/FiveStarTowns/FiveStarTowns.properties");
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
        chunkMultiplier = config.getInt("chunk-multiplier-per-player", 4);
        config.save();

    }

    public void comment() {
        config.addComment("server-name", "This will be in the header for messages sent to players");
        config.addComment("server-name", "By default it will be: '[FiveStarTowns]'");
        config.addComment("chunk-multiplier-per-player", "How many chunks does a town get for every player?");
    }

    public String getServerName() {
        return this.server;
    }

    public String getMessageHeader() {
        return Colors.BLACK + "[" + Colors.ORANGE + this.server + Colors.BLACK + "] " + Colors.WHITE;
    }

    public int getChunksPerPlayer() {
        return this.chunkMultiplier;
    }

}
