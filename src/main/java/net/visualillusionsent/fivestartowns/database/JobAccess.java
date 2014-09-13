/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.visualillusionsent.fivestartowns.database;

import net.canarymod.database.Column;
import net.canarymod.database.DataAccess;

/**
 *
 * @author Aaron
 */
public class JobAccess extends DataAccess {

    public JobAccess() {
        super("fst_jobs");
    }

    /**
     * UUID for the player this row refers to 
     */
    @Column(columnName = "playerId", dataType = Column.DataType.STRING)
    public String playerId;

    /**
     * ID for the job
     */
    @Column(columnName = "townId", dataType = Column.DataType.INTEGER)
    public int townId;

    /**
     * ID for the job
     */
    @Column(columnName = "jobId", dataType = Column.DataType.INTEGER)
    public int jobId;

    @Override
    public DataAccess getInstance() {
        return new JobAccess();
    }
    
}
