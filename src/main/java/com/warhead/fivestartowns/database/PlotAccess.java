/*
 * This file is copyright of WarHead Gaming. It is open Source and
 * free to use. Use of our code without express consent from the
 * developers is in violation of our license which has been provided
 * for you in the source and compiled jar.
 */
package com.warhead.fivestartowns.database;

import net.canarymod.database.Column;
import net.canarymod.database.DataAccess;

/**
 * Data Storage object for a town.
 * @author Somners
 */
public class PlotAccess extends DataAccess {

    public PlotAccess() {
        super("plots");
    }

    /**
     * X coordinate for this plot.
     */
    @Column(columnName = "x", dataType = Column.DataType.INTEGER)
    public int x;

    /**
     * Z coordinate for this plot.
     */
    @Column(columnName = "z", dataType = Column.DataType.INTEGER)
    public int z;

    /**
     * World Name that contains this plot.
     */
    @Column(columnName = "world", dataType = Column.DataType.STRING)
    public String world;

    /**
     * Town Name that owns this plot.
     */
    @Column(columnName = "town", dataType = Column.DataType.STRING)
    public String town;

    /**
     * Player Name that owns this plot.
     */
    @Column(columnName = "owner", dataType = Column.DataType.STRING)
    public String owner;

    @Column(columnName = "nopvp", dataType = Column.DataType.BOOLEAN)
    public boolean nopvp;

    @Column(columnName = "friendlyFire", dataType = Column.DataType.BOOLEAN)
    public boolean friendlyFire;

    @Column(columnName = "sanctuary", dataType = Column.DataType.BOOLEAN)
    public boolean sanctuary;

    @Column(columnName = "protection", dataType = Column.DataType.BOOLEAN)
    public boolean protection;

    @Column(columnName = "creeperNerf", dataType = Column.DataType.BOOLEAN)
    public boolean creeperNerf;

}
