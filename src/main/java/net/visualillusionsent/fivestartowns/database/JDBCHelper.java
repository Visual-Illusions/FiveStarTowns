package net.visualillusionsent.fivestartowns.database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Somners
 */
public class JDBCHelper {

    public enum DataType {
        INTEGER(Integer.class), FLOAT(Float.class), DOUBLE(Double.class), LONG(Long.class), SHORT(Short.class), BYTE(Byte.class), STRING(String.class), BOOLEAN(Boolean.class);

        private Class<?> cls;

        DataType(Class<?> cls) {
            this.cls = cls;
        }

        public boolean isAssignable(Class<?> cls) {
            return this.cls.isAssignableFrom(cls);
        }

        public static DataType fromString(String in) {
            for (DataType t : DataType.values()) {
                if (in.equalsIgnoreCase(t.name())) {
                    return t;
                }
            }
            return STRING;
        }

        public Class<?> getTypeClass() {
            return cls;
        }
    }

    private static final String LIST_REGEX = "\u00B6";

    /**
* Get the database entry for a Java List.
*
* @param list
*
* @return a string representation of the passed list.
*/
    public static String getListString(List<?> list) {
        StringBuilder sb = new StringBuilder();
        Iterator<?> it = list.iterator();
        while (it.hasNext()) {
            Object o = it.next();
            sb.append(String.valueOf(o));
            if (it.hasNext()) {
                sb.append(LIST_REGEX);
            }
        }
        return sb.toString();
    }

    /**
* Gets a Java List representation from the mysql String.
*
* @param type
* @param field
*
* @return
*/
    public static List<Comparable<?>> getList(DataType type, String field) {
        List<Comparable<?>> list = new ArrayList<Comparable<?>>();
        if (field == null) {
            return list;
        }
        switch (type) {
            case BYTE:
                for (String s : field.split(LIST_REGEX)) {
                    list.add(Byte.valueOf(s));
                }
                break;
            case INTEGER:
                for (String s : field.split(LIST_REGEX)) {
                    list.add(Integer.valueOf(s));
                }
                break;
            case FLOAT:
                for (String s : field.split(LIST_REGEX)) {
                    list.add(Float.valueOf(s));
                }
                break;
            case DOUBLE:
                for (String s : field.split(LIST_REGEX)) {
                    list.add(Double.valueOf(s));
                }
                break;
            case LONG:
                for (String s : field.split(LIST_REGEX)) {
                    list.add(Long.valueOf(s));
                }
                break;
            case SHORT:
                for (String s : field.split(LIST_REGEX)) {
                    list.add(Short.valueOf(s));
                }
                break;
            case STRING:
                for (String s : field.split(LIST_REGEX)) {
                    list.add(s);
                }
                break;
            case BOOLEAN:
                for (String s : field.split(LIST_REGEX)) {
                    list.add(Boolean.valueOf(s));
                }
                break;
        }
        return list;
    }

    /**
* Replaces '*' character with '\\*' if the Object is a String.
*
* @param o
*
* @return
*/
    public static Object convert(Object o) {
        if (o instanceof String && ((String) o).contains("*")) {
            ((String) o).replace("*", "\\*");
        }
        return o;
    }

    public static String getDataTypeSyntax(DataType type) {
        switch (type) {
            case BYTE:
                return "INT";
            case INTEGER:
                return "INT";
            case FLOAT:
                return "DOUBLE";
            case DOUBLE:
                return "DOUBLE";
            case LONG:
                return "BIGINT";
            case SHORT:
                return "INT";
            case STRING:
                return "TEXT";
            case BOOLEAN:
                return "BOOLEAN";
        }
        return "";
    }


}
