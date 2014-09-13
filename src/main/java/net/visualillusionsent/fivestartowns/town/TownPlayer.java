
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
     * Gets the name of this TownPlayer
     * @return this players name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the minecraft UUID of this player
     * @return this players UUID
     */
    public String getUUID() {
        return uuid;
    }

    
    public int getTownUUID() {
        return townUUID;
    }
    /**
     * Gets the name of the town this player belongs to.
     * @return the town name
     */
    public String getTownName() {
        return TownManager.get().getTown(townUUID).getName();
    }

    /**
     * gets the Town this player belongs to
     * @return this players Town
     */
    public Town getTown() {
        return TownManager.get().getTown(townUUID);
    }

    /**
     * Sets this players Town.
     * @param town The Town this player is to join.
     */
    public void setTown(Town town) {
        if (townUUID != -1) {
            leaveTown();
        }
        this.townUUID = town.getUUID();
        this.setDirty(true);
    }

    /**
     * Sets this players town
     * @param uuid The ID of the town this player is to join.
     */
    public void setTown(int uuid) {
        Town t = TownManager.get().getTown(uuid);
        if (t != null) this.setTown(t);
    }

    /**
     * Checks if this player is the owner of the town it belongs to.
     * @return returns true if this player is an owner, false otherwise.
     */
    public boolean isOwner() {
        return JobManager.get().hasJob(townUUID, uuid, JobType.OWNER);
    }

    /**
     * Checks if this player is the Assistant of the town it belongs to.
     * @return returns true if this player is an assistant, false otherwise.
     */
    public boolean isAssistant() {
        return JobManager.get().hasJob(townUUID, uuid, JobType.ASSISTANT);
    }

    /**
     * Leaves the players current town.
     */
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
