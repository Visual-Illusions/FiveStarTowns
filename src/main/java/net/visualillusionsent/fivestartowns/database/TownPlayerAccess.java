package net.visualillusionsent.fivestartowns.database;

import net.canarymod.database.Column;
import net.canarymod.database.DataAccess;

/**
 *
 * @author Somners
 */
public class TownPlayerAccess extends DataAccess {

    public TownPlayerAccess() {
        super("fst_town_players");
    }

    /**
     * ID for this TownPlayer, serves as Primary Key, Auto Incremented.
     */
    @Column(columnName = "uuid", dataType = Column.DataType.STRING, autoIncrement = false, columnType = Column.ColumnType.PRIMARY)
    public String uuid;

    /**
     * Name of this TownPlayer.
     */
    @Column(columnName = "name", dataType = Column.DataType.STRING)
    public String name;

    @Column(columnName = "townUUID", dataType = Column.DataType.INTEGER)
    public int townUUID;

    @Override
    public DataAccess getInstance() {
        return new TownPlayerAccess();
    }
}
