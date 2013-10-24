package net.visualillusionsent.fivestartowns.database;

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

    @Column(columnName = "ownerPlot", dataType = Column.DataType.STRING)
    public String ownerPlot;

    @Column(columnName = "nopvp", dataType = Column.DataType.STRING)
    public String nopvp;

    @Column(columnName = "friendlyFire", dataType = Column.DataType.STRING)
    public String friendlyFire;

    @Column(columnName = "sanctuary", dataType = Column.DataType.STRING)
    public String sanctuary;

    @Column(columnName = "protection", dataType = Column.DataType.STRING)
    public String protection;

    @Column(columnName = "creeperNerf", dataType = Column.DataType.STRING)
    public String creeperNerf;

    @Override
    public DataAccess getInstance() {
        return new PlotAccess();
    }
}
