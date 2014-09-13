/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.visualillusionsent.fivestartowns.job;

/**
 *
 * @author Aaron
 */
public enum JobType {
    
    OWNER(0),
    ASSISTANT(1);
    
    private int id;
    JobType(int i) {
        id = i;
    }
    
    public int getID() {
        return id;
    }
    
    public static JobType fromID(int id) {
        for (JobType jt : JobType.values()) {
            if (jt.getID() == id) return jt;
        }
        return null;
    }
    
    
}
