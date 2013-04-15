/*
 * This file is copyright of WarHead Gaming. It is open Source and
 * free to use. Use of our code without express consent from the
 * developers is in violation of our license which has been provided
 * for you in the source and compiled jar.
 */
package com.warhead.fivestartowns.town;

import com.warhead.fivestartowns.database.TownPlayerAccess;

/**
 *
 * @author Somners
 */
public class TownPlayer {

    private final TownPlayerAccess data;

    public TownPlayer(TownPlayerAccess data) {
        this.data = data;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return data.name;
    }

    /**
     *
     * @return
     */
    public String getTownName() {
        return data.townName;
    }

    /**
     *
     * @return
     */
    public Town getTown() {
        return TownManager.get().getTown(data.townName);
    }

    /**
     *
     * @return
     */
    public boolean isOwner() {
        return this.getTown().getOwnerName().equals(this.getName());
    }

    /**
     *
     * @return
     */
    public boolean isAssistant() {
        return this.getTown().getAssistantName().contains(this.getName());
    }
}
