
package net.visualillusionsent.fivestartowns.town;

import net.canarymod.Canary;
import net.canarymod.database.Database;
import net.canarymod.database.exceptions.DatabaseReadException;
import net.canarymod.database.exceptions.DatabaseWriteException;
import net.visualillusionsent.fivestartowns.Saveable;
import net.visualillusionsent.fivestartowns.database.TownPlayerAccess;
import net.visualillusionsent.fivestartowns.job.JobManager;
import net.visualillusionsent.fivestartowns.job.JobType;

import java.util.HashMap;

/**
 *
 * @author Somners
 */
public class TownPlayer extends Saveable {

    private final String uuid;
    private String name;
    private int townUUID;

    public TownPlayer(String uuid) {
        this.uuid = uuid;
        this.load();
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }
    
    public String getUUID() {
        return uuid;
    }

    
    public int getTownUUID() {
        return townUUID;
    }
    /**
     *
     * @return
     */
    public String getTownName() {
        return TownManager.get().getTown(townUUID).getName();
    }

    /**
     *
     * @return
     */
    public Town getTown() {
        return TownManager.get().getTown(townUUID);
    }

    public void setTown(Town town) {
        this.townUUID = town.getUUID();
        this.setDirty(true);
    }

    public void setTown(int uuid) {
        Town t = TownManager.get().getTown(uuid);
        if (t != null) this.setTown(t);
    }

    /**
     *
     * @return
     */
    public boolean isOwner() {
        return JobManager.get().hasJob(townUUID, uuid, JobType.OWNER);
    }

    /**
     *
     * @return
     */
    public boolean isAssistant() {
        return JobManager.get().hasJob(townUUID, uuid, JobType.ASSISTANT);
    }

    public void leaveTown() {
        if (townUUID != -1) {
            JobManager.get().removeAllJobs(uuid);
            townUUID = -1;
            this.setDirty(true);
        }
    }

    @Override
    public void load() {
        TownPlayerAccess data = new TownPlayerAccess();
        try {
            HashMap<String, Object> filter = new HashMap<String, Object>();
            filter.put("uuid", uuid);
            Database.get().load(data, filter);
        } catch (DatabaseReadException ex) {
            Canary.log.trace("Error Loading TownPlayer Data", ex);
        }
        this.name = data.name;
        this.townUUID = data.townUUID;
    }

    @Override
    public void save() {
        TownPlayerAccess data = new TownPlayerAccess();
        data.uuid = this.uuid;
        data.name = this.name;
        data.townUUID = this.townUUID;
        try {
            HashMap<String, Object> filter = new HashMap<String, Object>();
            filter.put("uuid", this.uuid);
            Database.get().update(data, filter);
        } catch (DatabaseWriteException ex) {
            Canary.log.trace("Error Saving TownPlayer Data", ex);
        }
    }
}
