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
    /** Table Names */
    private final String TOWN_PLAYER_TABLE = "town_Players";
    private final String TOWN_TABLE = "towns";
    private final String RANK_TABLE = "ranks";
    private final String PLOT_TABLE = "plots";

    public List<String> getTownPlayerNames(String town) {
        List<String> players = new ArrayList<String>();
        ResultSet rs = this.getResultSet(TOWN_PLAYER_TABLE, new Query().add("town_name", town), 100);
        try {
            if (rs != null) {
                while (rs.next()) {
                        players.add(rs.getString("name"));
                }
            }
        } catch (SQLException ex) {
            FiveStarTowns.get().getPluginLogger().log(Level.WARNING,
                    "Error retreiving Town Player List.", ex);
        }
        return players;
    }

    public boolean doesEntryExist(String table, Query where) {
        ResultSet rs = null;

        try {
            rs = this.getResultSet(table, where, 1);

            if (rs != null) {
                return rs.next();
            }
        } catch (SQLException ex) {
            FiveStarTowns.get().getPluginLogger().warning("Error Querying MySQL ResultSet in "
                    + table);
        }
        return false;
    }

    public ResultSet getResultSet(String table, Query where, int limit) {
        PreparedStatement ps;
        ResultSet toRet = null;

        try {
            if (where != null && where.get().size() > 0) {
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
            } else {
                ps = conn.prepareStatement(String.format("SELECT * FROM `%s` LIMIT %s", table, limit));
                toRet = ps.executeQuery();
            }
        } catch (SQLException ex) {
            FiveStarTowns.get().getPluginLogger().warning("Error Querying MySQL ResultSet in "
                    + table);
        } catch (Exception ex) {
            FiveStarTowns.get().getPluginLogger().warning("Error Querying MySQL ResultSet in "
                    + table);
        }
        return toRet;
    }

    public boolean updateEntry(String table, Query where, Query update) {
        ResultSet rs = null;

        try {
            rs = this.getResultSet(table, where, 1);

            if (rs != null && rs.next()) {
                for (QueryEntry entry : update.get()) {
                    if (entry.isList) {
                        rs.updateObject(entry.key, JDBCHelper.getListString((List<?>) entry.value));
                    } else {
                        rs.updateObject(entry.key, entry.value);
                    }
                }
                rs.updateRow();
            } else {
                return false;
            }
        } catch (SQLException ex) {
            FiveStarTowns.get().getPluginLogger().log(Level.WARNING, ex.getMessage(), ex);
        } finally {
            try {
                PreparedStatement st = rs != null && rs.getStatement() instanceof PreparedStatement ? (PreparedStatement) rs.getStatement() : null;
                this.closeRS(rs);
                this.closePS(st);
            } catch (SQLException ex) {
                FiveStarTowns.get().getPluginLogger().log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return true;
    }

    public void insertEntry(String table, Query query) {
        PreparedStatement ps = null;

        try {
            StringBuilder fields = new StringBuilder(), values = new StringBuilder();

            /* Generates field and value Strings */
            for (QueryEntry q : query.get()) {
                fields.append("`").append(q.key).append("`").append(",");
                values.append("?").append(",");
            }
            /* Deletes the trailing comma's for proper syntax */
            fields.deleteCharAt(fields.length() - 1);
            values.deleteCharAt(values.length() - 1);

            ps = conn.prepareStatement(String.format(INSERT, table, fields, values));

            /* Inserts values to columns */
            int i = 1;
            for (QueryEntry q : query.get()) {
                if (q.isList) {
                    ps.setObject(i, JDBCHelper.getListString((List<?>) q.value));
                } else {
                    ps.setObject(i, JDBCHelper.convert(q.value));
                }
                i++;
            }

            if (ps.executeUpdate() == 0) {
                // TODO: no rows inserted, do something.
            }
        } catch (SQLException ex) {
            FiveStarTowns.get().getPluginLogger().log(Level.WARNING, ex.getMessage(), ex);
        } finally {
            this.closePS(ps);
        }
    }

    public boolean deleteEntry(String table, Query where) {
        ResultSet rs = null;

        try {
            rs = this.getResultSet(table, where, 1);

            if (rs != null) {
                if (rs.next()) {
                    rs.deleteRow();
                }
            }

        } catch (SQLException ex) {
            FiveStarTowns.get().getPluginLogger().log(Level.WARNING, ex.getMessage(), ex);
            return false;
        } finally {
            this.closeRSAndPS(rs);
        }
        return true;
    }

    /**
     * Safely Close a ResultSet.
     *
     * @param rs ResultSet to close.
     */
    public void closeRS(ResultSet rs) {
        if (rs != null) {
            try {
                if (!rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException sqle) {
                FiveStarTowns.get().getPluginLogger().log(Level.WARNING, "Error closing ResultSet in MySQL database.", sqle);
            }
        }
    }

    /**
     * Safely Close a PreparedStatement.
     *
     * @param ps PreparedStatement to close.
     */
    public void closePS(Statement ps) {
        if (ps != null) {
            try {
                if (!ps.isClosed()) {
                    ps.close();
                }
            } catch (SQLException sqle) {
                FiveStarTowns.get().getPluginLogger().log(Level.WARNING, "Error closing PreparedStatement in MySQL database.", sqle);
            }
        }
    }

    /**
     * Safely Close a ResultSet.
     *
     * @param rs ResultSet to close.
     */
    public void closeRSAndPS(ResultSet rs) {
        if (rs != null) {
            try {
                PreparedStatement st = rs != null && rs.getStatement() instanceof PreparedStatement ? (PreparedStatement) rs.getStatement() : null;
                this.closeRS(rs);
                this.closePS(st);
            } catch (SQLException sqle) {
                FiveStarTowns.get().getPluginLogger().log(Level.WARNING, "Error closing ResultSet in MySQL database.", sqle);
            }
        }
    }

    public abstract void closeConn();

    public class Query {

        private List<QueryEntry> query = new ArrayList<QueryEntry>();

        public Query add(String key, Object value) {
            query.add(new QueryEntry(key, value, false));
            return this;
        }

        public Query add(String key, Object value, boolean isList) {
            query.add(new QueryEntry(key, value, isList));
            return this;
        }

        public List<QueryEntry> get() {
            return query;
        }
    }

    public class QueryEntry {

        public String key = "";
        public Object value = null;
        public boolean isList = false;

        public QueryEntry(String key, Object value, boolean isList) {
            this.key = key;
            this.value = value;
            this.isList = isList;
        }
    }

    public Query newQuery() {
        return new Query();
    }
}
