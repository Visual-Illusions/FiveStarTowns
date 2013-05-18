/*
 * This file is copyright of WarHead Gaming. It is open Source and
 * free to use. Use of our code without express consent from the
 * developers is in violation of our license which has been provided
 * for you in the source and compiled jar.
 */
package com.warhead.fivestartowns.flag;

/**
 *
 * @author somners
 */
public enum FlagValue {
    
    TRUE("TRUE"), //
    FALSE("FALSE"), //
    NULL("NULL"), //
    ;
    
    private String value;
    
    
    FlagValue(String value) {
        this.value = value;
    }
    
    public static FlagValue getType(String s) {
        for (FlagValue type : FlagValue.values()) {
            if (s.equalsIgnoreCase(type.toString())) {
                return type;
            }
        }
        return null;
    }
    
    public boolean getBoolean() {
        return value.equals("NULL") ? null : value.equals("TRUE");
    }
    
    @Override
    public String toString() {
        return this.value;
    }
}
