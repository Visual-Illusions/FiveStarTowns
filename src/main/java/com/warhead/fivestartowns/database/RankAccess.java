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
 *
 * @author Somners
 */
public class RankAccess extends DataAccess {

    public RankAccess() {
        super("ranks");
    }

    /**
     * ID for this rank.
     */
    @Column(columnName = "id", dataType = Column.DataType.INTEGER, autoIncrement = true)
    public int id;

    /**
     * Population needed for this Rank
     */
    @Column(columnName = "population", dataType = Column.DataType.INTEGER)
    public int population;

    /**
     * Type of this town. Currently unused.
     */
    @Column(columnName = "townType", dataType = Column.DataType.STRING)
    public String townType;

    @Column(columnName = "mayorPrefix", dataType = Column.DataType.STRING)
    public String mayorPrefix;

    @Column(columnName = "assistantPrefix", dataType = Column.DataType.STRING)
    public String assistantPrefix;
    
    @Column(columnName = "townNickname", dataType = Column.DataType.STRING)
    public String townNickname;

    @Column(columnName = "flags", dataType = Column.DataType.STRING, isList = true)
    public String flags;
}
