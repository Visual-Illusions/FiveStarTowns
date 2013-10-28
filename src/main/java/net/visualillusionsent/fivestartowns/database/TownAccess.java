package net.visualillusionsent.fivestartowns.database;

import java.util.List;
import net.canarymod.database.Column;
import net.canarymod.database.DataAccess;

/**
 * Data Storage object for a town.
 * @author Somners
 */
public class TownAccess extends FlagAccess {

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
