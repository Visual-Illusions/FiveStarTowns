/*
 * Copyright (c) 2013 WarHead Gaming.
 * All rights reserved.
 *
 * This file is copyright of WarHead Gaming. It is open Source and
 * free to use. It is licensed under the two-clause BSD License.
 */
package net.visualillusionsent.fivestartowns;

/**
 *
 * @author Somners
 */
public abstract class Saveable {

    private boolean isDirty = false;

    /**
     * Gets whether or not this needs to be saved to the database.
     *
     * @return True if needs to be saved, false if it does not.
     */
    public boolean isDirty() {
        return isDirty;
    }

    /**
     * Sets whether or not this needs to be saved to the database.
     *
     * @param isDirty True if needs to be saved, false if it does not.
     */
    public void setDirty(boolean isDirty) {
        this.isDirty = isDirty;
    }

    /**
     * Loads data into this class.
     */
    public abstract void load();

    /**
     * Saves data from this class.
     */
    public abstract void save();
}
