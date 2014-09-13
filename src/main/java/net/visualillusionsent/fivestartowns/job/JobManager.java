/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.visualillusionsent.fivestartowns.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.canarymod.Canary;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.database.DataAccess;
import net.canarymod.database.Database;
import net.canarymod.database.exceptions.DatabaseReadException;
import net.canarymod.database.exceptions.DatabaseWriteException;
import net.visualillusionsent.fivestartowns.Saveable;
import net.visualillusionsent.fivestartowns.database.JobAccess;
import net.visualillusionsent.fivestartowns.town.TownManager;
import net.visualillusionsent.fivestartowns.town.TownPlayer;

/**
 *
 * @author Aaron
 */
public class JobManager extends Saveable {

    private static final List<JobAccess> jobs = new ArrayList<JobAccess>();
    private static JobManager instance = null;

    public static JobManager get() {
        if (instance == null) {
            instance = new JobManager();
        }
        return instance;
    }
    
    public boolean hasJob(int townId, String playerId, JobType type) {
        for (JobAccess j : jobs) {
            if (j.townId == townId && j.jobId == type.getID() && j.playerId.equals(playerId)) return true;
        }
        return false;
    }
    
    public void addJob(int townId, String playerId, JobType type) {
        JobAccess data = new JobAccess();
        data.jobId = type.getID();
        data.playerId = playerId;
        data.townId = townId;
        
        try {
            Database.get().insert(data);
        } catch (DatabaseWriteException ex) {
            Canary.log.trace("Error Inserting Job Data", ex);
        }
    }
    
    public void removeJob(int townId, String playerId, JobType type) {
        for (JobAccess j : jobs) {
            if (j.townId == townId && j.jobId == type.getID() && j.playerId.equals(playerId)) {
                jobs.remove(j);
                try {
                    HashMap<String, Object> filter = new HashMap<String, Object>();
                    filter.put("jobId", j.jobId);
                    filter.put("playerId", j.playerId);
                    filter.put("townId", j.townId);
                    Database.get().remove(j, filter);
                } catch (DatabaseWriteException ex) {
                    Canary.log.trace("Error removing Job Data", ex);
                }
            }
        }
    }
    
    public void removeAllJobs(int townId) {
        for (JobAccess j : jobs) {
            if (j.townId == townId) {
                try {
                    HashMap<String, Object> filter = new HashMap<String, Object>();
                    filter.put("jobId", j.jobId);
                    filter.put("playerId", j.playerId);
                    filter.put("townId", j.townId);
                    Database.get().remove(j, filter);
                    jobs.remove(j);
                } catch (DatabaseWriteException ex) {
                    Canary.log.trace("Error removing Job Data", ex);
                }
            }
        }
    }
    
    public void removeAllJobs(String playerId) {
        for (JobAccess j : jobs) {
            if (j.playerId.equals(playerId)) {
                try {
                    HashMap<String, Object> filter = new HashMap<String, Object>();
                    filter.put("jobId", j.jobId);
                    filter.put("playerId", j.playerId);
                    filter.put("townId", j.townId);
                    Database.get().remove(j, filter);
                    jobs.remove(j);
                } catch (DatabaseWriteException ex) {
                    Canary.log.trace("Error removing Job Data", ex);
                }
            }
        }
    }
    
    public List<TownPlayer> getJobHolders(int townId, int jobId) {
        List<TownPlayer> players = new ArrayList<TownPlayer>();
        
        for (JobAccess j : jobs) {
            if (j.townId == townId && j.jobId == jobId) {
                TownPlayer tp = TownManager.get().getTownPlayer(j.playerId);
                if (tp != null) players.add(tp);
            }
        }
        return players;
    }


    @Override
    public void load() {
        jobs.clear();
        
        List<DataAccess> jobData = new ArrayList<DataAccess>();
        try {
            HashMap<String, Object> filter = new HashMap<String, Object>();
            Database.get().loadAll(new JobAccess(), jobData, filter);
        } catch (DatabaseReadException ex) {
            Canary.log.trace("Error Loading Job Data.", ex);
        }
        
        for (DataAccess da : jobData) {
            JobAccess data = (JobAccess) da;
            jobs.add(data);
        }
    }

    @Override
    public void save() {
        // Currently no need to save
    }
    
}
