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
    
    OWNER(0, "Owner"),
    ASSISTANT(1, "Assistant"),
    Builder(2, "Builder");
    
    private int id;
    private String name;
    JobType(int i, String n) {
        id = i;
        name = n;
    }
    
    public int getID() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public static JobType fromID(int id) {
        for (JobType jt : JobType.values()) {
            if (jt.getID() == id) return jt;
        }
        return null;
    }
    
    public static JobType fromName(String name) {
        for (JobType jt : JobType.values()) {
            if (jt.getName().equalsIgnoreCase(name)) return jt;
        }
        return null;
    }
    
    
}
