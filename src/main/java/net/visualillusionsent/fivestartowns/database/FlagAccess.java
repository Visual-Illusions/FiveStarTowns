package net.visualillusionsent.fivestartowns.database;

import net.canarymod.database.Column;
import net.canarymod.database.DataAccess;

/**
 * This is a common { @link DataAccess } for other { @link DataAccess } to
 * extend.  This contains fields relevant to objects that can have flags.
 * @author Somners
 */
public abstract class FlagAccess extends DataAccess {

    public FlagAccess(String tableName) {
        super(tableName);
    }

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

}
