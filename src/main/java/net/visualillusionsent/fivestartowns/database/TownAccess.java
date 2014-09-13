package net.visualillusionsent.fivestartowns.database;

import net.canarymod.database.Column;
import net.canarymod.database.DataAccess;

/**
 * Data Storage object for a town.
 * @author Somners
 */
public class TownAccess extends FlagAccess {

    public TownAccess() {
        super("fst_towns");
    }

    /**
     * ID for this Town, serves as Primary Key, Auto Incremented.
     */
    @Column(columnName = "uuid", dataType = Column.DataType.INTEGER, autoIncrement = true, columnType = Column.ColumnType.PRIMARY)
    public int uuid;

    /**
     * Name of this Town.
     */
    @Column(columnName = "name", dataType = Column.DataType.STRING)
    public String name;

    @Column(columnName = "balance", dataType = Column.DataType.DOUBLE)
    public double balance;

    @Column(columnName = "bonusPlots", dataType = Column.DataType.INTEGER)
    public int bonusPlots;

    @Column(columnName = "welcome", dataType = Column.DataType.STRING)
    public String welcome;

    @Column(columnName = "farewell", dataType = Column.DataType.STRING)
    public String farewell;

    @Override
    public DataAccess getInstance() {
        return new TownAccess();
    }
}
