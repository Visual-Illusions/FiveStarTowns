/*
 * This file is copyright of WarHead Gaming. It is open Source and
 * free to use. Use of our code without express consent from the
 * developers is in violation of our license which has been provided
 * for you in the source and compiled jar.
 */
package com.warhead.fivestartowns;

import com.warhead.fivestartowns.database.TownAccess;
import com.warhead.fivestartowns.database.TownPlayerAccess;
import java.util.HashMap;

/**
 *
 * @author Somners
 */
public class TownManager {

    private static final HashMap<String, TownPlayerAccess> players = new HashMap<String, TownPlayerAccess>();
    private static final HashMap<String, TownAccess> towns = new HashMap<String, TownAccess>();
    
}
