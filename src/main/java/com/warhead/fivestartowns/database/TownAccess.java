/*
 * This file is copyright of WarHead Gaming. It is open Source and
 * free to use. Use of our code without express consent from the
 * developers is in violation of our license which has been provided
 * for you in the source and compiled jar.
 */
package com.warhead.fivestartowns.database;

import java.util.List;
import net.canarymod.database.Column;
import net.canarymod.database.DataAccess;

/**
 * Data Storage object for a town.
 * @author Somners
 */
public class TownAccess extends DataAccess {

    public TownAccess() {
        super("towns");
    }

    /**
     * ID for this Town, serves as Primary Key, Auto Incremented.
     */
    @Column(columnName = "id", dataType = Column.DataType.INTEGER, autoIncrement = true, columnType = Column.ColumnType.PRIMARY)
    public int id;

    /**
     * Name of this Town.
     */
    @Column(columnName = "name", dataType = Column.DataType.STRING)
    public String name;

    @Column(columnName = "owner", dataType = Column.DataType.STRING)
    public String owner;

    @Column(columnName = "assistant", dataType = Column.DataType.STRING, isList = true)
    public List<String> assistant;

    @Column(columnName = "members", dataType = Column.DataType.STRING, isList = true)
    public List<String> members;

    @Column(columnName = "balance", dataType = Column.DataType.DOUBLE)
    public double balance;

    @Column(columnName = "bonusChunks", dataType = Column.DataType.INTEGER)
    public int bonusChunks;

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

    @Column(columnName = "welcome", dataType = Column.DataType.STRING)
    public String welcome;

    @Column(columnName = "farewell", dataType = Column.DataType.STRING)
    public String farewell;
}
