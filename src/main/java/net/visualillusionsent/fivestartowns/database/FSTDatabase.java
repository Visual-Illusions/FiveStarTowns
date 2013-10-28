package net.visualillusionsent.fivestartowns.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import net.visualillusionsent.fivestartowns.FiveStarTowns;

/**
 *
 * @author Somners
 */
public abstract class FSTDatabase {

    private static Connection conn;
    /** Takes: Table Name, Column Names, Values */
    private final String INSERT = "INSERT INTO `%s` (%s) VALUES (%s)";
    /** Takes: Table Name, Conditions, Limit */
    private final String SELECT = "SELECT * FROM `%s` WHERE %s LIMIT %s";
    /** Takes: Table Name, Limit */
    private final String SELECT_ALL = "SELECT * FROM `%s` LIMIT %s";
    /** Takes: Table Name, Column Data */
    private final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `%s` (%s) ENGINE = INNODB";
    /** Takes: Table Name, Column Name, JDBC Data Type Syntax */
    private final String INSERT_COLUMN = "ALTER TABLE `%s` ADD `%s` %s";
    /** Takes: Table Name, Column Name */
    private final String DELETE_COLUMN = "ALTER TABLE `%s` DROP `%s`";

    public List<String> getTownPlayerNames(String town) {

    }

    public boolean hasTownPlayer(String name) {

    }

    public ResultSet getResultSet(String table, Where where, int limit) {
        PreparedStatement ps;
        ResultSet toRet = null;

        try {
            if (where.get().size() > 0) {
                StringBuilder sb = new StringBuilder();

                for (QueryEntry entry : where.get()) {
                    sb.append(" AND `").append(entry.key).append("`=?");
                }
                sb.delete(0, 5);

                ps = conn.prepareStatement(String.format(SELECT, table, sb, limit));

                int i = 0;
                for (QueryEntry entry : where.get()) {
                    ps.setObject(i + 1, JDBCHelper.convert(entry.value));
                    i++;
                }
                toRet = ps.executeQuery();
            }
            else {
                ps = conn.prepareStatement(String.format(SELECT, table, "", limit));
                toRet = ps.executeQuery();
            }
        }
        catch (SQLException ex) {
            FiveStarTowns.get().getPluginLogger().warning("Error Querying MySQL ResultSet in "
                    + table);
        }
        catch (Exception ex) {
            FiveStarTowns.get().getPluginLogger().warning("Error Querying MySQL ResultSet in "
                    + table);
        }
        return toRet;
    }

    public boolean updateEntry(String table, Where where, Update update) {

    }

    public boolean deleteEntry(String table, Where where) {

    }

    /**
* Safely Close a ResultSet.
*
* @param rs
* ResultSet to close.
*/
    public void closeRS(ResultSet rs) {
        if (rs != null) {
            try {
                if (!rs.isClosed()) {
                    rs.close();
                }
            }
            catch (SQLException sqle) {
                FiveStarTowns.get().getPluginLogger().log(Level.WARNING, "Error closing ResultSet in MySQL database.", sqle);
            }
        }
    }

    /**
* Safely Close a PreparedStatement.
*
* @param ps
* PreparedStatement to close.
*/
    public void closePS(Statement ps) {
        if (ps != null) {
            try {
                if (!ps.isClosed()) {
                    ps.close();
                }
            }
            catch (SQLException sqle) {
                FiveStarTowns.get().getPluginLogger().log(Level.WARNING, "Error closing PreparedStatement in MySQL database.", sqle);
            }
        }
    }

    public abstract void closeConn();


    public class Where {
        private List<QueryEntry> where = new ArrayList<QueryEntry>();

        public Where add(String key, Object value) {
            QueryEntry entry = new QueryEntry();
            entry.key = key;
            entry.value = value;
            where.add(entry);
            return this;
        }

        public List<QueryEntry> get() {
            return where;
        }
    }

    public class Update {
        private List<QueryEntry> update = new ArrayList<QueryEntry>();

        public Update add(String key, Object value) {
            QueryEntry entry = new QueryEntry();
            entry.key = key;
            entry.value = value;
            update.add(entry);
            return this;
        }

        public List<QueryEntry> get() {
            return update;
        }
    }

    public class QueryEntry {
        public String key = "";
        public Object value = null;
    }
}
