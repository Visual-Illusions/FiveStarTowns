package net.visualillusionsent.fivestartowns.database;

import net.canarymod.database.Column;
import net.canarymod.database.DataAccess;

/**
 *
 * @author Somners
 */
public class TownPlayerAccess extends DataAccess {

    public TownPlayerAccess() {
        super("town_players");
    }

    /**
     * ID for this TownPlayer, serves as Primary Key, Auto Incremented.
     */
    @Column(columnName = "id", dataType = Column.DataType.INTEGER, autoIncrement = true, columnType = Column.ColumnType.PRIMARY)
    public int id;

    /**
     * Name of this TownPlayer.
     */
    @Column(columnName = "name", dataType = Column.DataType.STRING)
    public String name;

    @Column(columnName = "town_name", dataType = Column.DataType.STRING)
    public String townName;

    @Override
    public DataAccess getInstance() {
        return new TownPlayerAccess();
    }
}
