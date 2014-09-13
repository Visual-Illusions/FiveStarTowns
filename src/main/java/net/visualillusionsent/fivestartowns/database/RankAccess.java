package net.visualillusionsent.fivestartowns.database;

import net.canarymod.database.Column;
import net.canarymod.database.DataAccess;

import java.util.List;

/**
 *
 * @author Somners
 */
public class RankAccess extends DataAccess {

    public RankAccess() {
        super("fst_ranks");
    }

    /**
     * ID for this rank.
     */
    //@Column(columnName = "id", dataType = Column.DataType.INTEGER, autoIncrement = true)
    //public int id;

    /**
     * Population needed for this Rank
     */
    @Column(columnName = "populationReq", dataType = Column.DataType.INTEGER)
    public int populationReq;

    /**
     * Type of this town. Currently unused.
     */
    @Column(columnName = "townType", dataType = Column.DataType.STRING)
    public String townType;

    @Column(columnName = "mayorPrefix", dataType = Column.DataType.STRING)
    public String mayorPrefix;

    @Column(columnName = "mayorSuffix", dataType = Column.DataType.STRING)
    public String mayorSuffix;

    @Column(columnName = "assistantPrefix", dataType = Column.DataType.STRING)
    public String assistantPrefix;

    @Column(columnName = "assistantSuffix", dataType = Column.DataType.STRING)
    public String assistantSuffix;

    @Column(columnName = "townPrefix", dataType = Column.DataType.STRING)
    public String townPrefix;

    @Column(columnName = "townSuffix", dataType = Column.DataType.STRING)
    public String townSuffix;

    @Column(columnName = "flags", dataType = Column.DataType.STRING, isList = true)
    public List<String> flags;

    @Override
    public DataAccess getInstance() {
        return new RankAccess();
    }
}
