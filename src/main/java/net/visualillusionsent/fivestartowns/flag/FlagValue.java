package net.visualillusionsent.fivestartowns.flag;

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
        return value.equals("NULL") ? false : value.equals("TRUE");
    }

    @Override
    public String toString() {
        return this.value;
    }
}
