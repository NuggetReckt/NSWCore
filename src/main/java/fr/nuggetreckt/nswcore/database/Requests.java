package fr.nuggetreckt.nswcore.database;

import fr.nuggetreckt.nswcore.NSWCore;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class Requests {

    private final boolean isConnected;
    private Statement statement;
    private ResultSet resultSet;

    private String query;

    public Requests() {
        this.isConnected = NSWCore.getConnector().isConnected();
    }

    public void initPlayerData(@NotNull Player player) {
        query = "INSERT INTO core_playerdata (uuid, playerName, rankId, honorPoints) VALUES ('" + player.getUniqueId() +
                "', '" + player.getName() + "', 0, 0);";
        updateData(query);
        close();
    }

    public void updatePlayerData(@NotNull Player player, int rankId, long honorPoints) {
        query = "UPDATE core_playerdata SET rankId = " + rankId + ", honorPoints = " + ((int) honorPoints) +
                " WHERE uuid = '" + player.getUniqueId() + "';";
        updateData(query);
        close();
    }

    public int getPlayerRankId(@NotNull Player player) {
        query = "SELECT rankId FROM core_playerdata WHERE uuid = '" + player.getUniqueId() + "';";
        int result = 0;

        retrieveData(query);
        try {
            if (resultSet.next()) {
                result = resultSet.getInt("rankId");
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            close();
        }
        return result;
    }

    public long getPlayerPoints(@NotNull Player player) {
        query = "SELECT honorPoints FROM core_playerdata WHERE uuid = '" + player.getUniqueId() + "';";
        long result = 0;

        retrieveData(query);
        try {
            if (resultSet.next()) {
                result = resultSet.getLong("honorPoints");
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            close();
        }
        return result;
    }

    public Player getPlayer(@NotNull Player player) {
        query = "SELECT playerName FROM core_playerdata WHERE uuid = '" + player.getUniqueId() + "';";
        Player result = null;

        retrieveData(query);
        try {
            if (resultSet.next()) {
                result = NSWCore.getInstance().getPlayerByName(resultSet.getString("playerName"));
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            close();
        }
        return result;
    }

    public void setReport(@NotNull Player creator, @NotNull Player reported, int typeId, String reason) {
        query = "INSERT INTO core_reports (creatorUuid, creatorName, reportedUuid, reportedName, typeId, reason) " +
                "VALUES ('" + creator.getUniqueId() + "', '" + creator.getName() + "', '" + reported.getUniqueId() +
                "', '" + reported.getName() + "', '" + typeId + "', '" + reason + "');";
        updateData(query);
        close();
    }

    public void deleteReport(int id) {
        query = "DELETE * FROM core_reports WHERE id = " + id + ";";
        updateData(query);
        close();
    }

    public void markReportAsResolved(int id) {
        query = "UPDATE core_reports SET isResolved = 1 WHERE id = " + id + ";";
        updateData(query);
        close();
    }

    public int getResolved(int id) {
        query = "SELECT isResolved FROM core_reports WHERE id = " + id + ";";
        int result = 0;

        retrieveData(query);
        try {
            if (resultSet.next()) {
                result = resultSet.getInt("isResolved");
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            close();
        }
        return result;
    }

    public String getReportedName(int id) {
        query = "SELECT reportedName FROM core_reports WHERE id = " + id + ";";
        String result = null;

        retrieveData(query);
        try {
            if (resultSet.next()) {
                result = resultSet.getString("reportedName");
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            close();
        }
        return result;
    }

    public String getCreatorName(int id) {
        query = "SELECT creatorName FROM core_reports WHERE id = " + id + ";";
        String result = null;

        retrieveData(query);
        try {
            if (resultSet.next()) {
                result = resultSet.getString("creatorName");
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            close();
        }
        return result;
    }

    public int getReportType(int id) {
        query = "SELECT typeId FROM core_reports WHERE id = " + id + ";";
        int result = 0;

        retrieveData(query);
        try {
            if (resultSet.next()) {
                result = resultSet.getInt("typeId");
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            close();
        }
        return result;
    }

    public String getReportReason(int id) {
        query = "SELECT reason FROM core_reports WHERE id = " + id + ";";
        String result = null;

        retrieveData(query);
        try {
            if (resultSet.next()) {
                result = resultSet.getString("reason");
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            close();
        }
        return result;
    }

    public Timestamp getReportTime(int id) {
        query = "SELECT * FROM core_reports WHERE id = " + id + ";";
        Timestamp result = null;

        retrieveData(query);
        try {
            if (resultSet.next()) {
                result = resultSet.getTimestamp("date");
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            close();
        }
        return result;
    }

    public int getReportsCount() {
        query = "SELECT COUNT(*) FROM core_reports;";
        int result = 0;

        retrieveData(query);
        try {
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            close();
        }
        return result;
    }

    public void createTables() {
        NSWCore.getServerHandler().getExecutor().execute(() -> {
            createPlayerDataTable();
            createReportsTable();
            createPlayerTable();
        });
    }

    private void createPlayerDataTable() {
        query = """
                CREATE TABLE IF NOT EXISTS core_playerdata
                (
                    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
                    uuid VARCHAR(36) NOT NULL,
                    playerName VARCHAR(50) NOT NULL,
                    rankId INT(1) NOT NULL,
                    honorPoints INT(5) NOT NULL
                );
                """;
        updateData(query);
        close();
    }

    private void createReportsTable() {
        query = """
                CREATE TABLE IF NOT EXISTS core_reports
                (
                    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
                    creatorUuid VARCHAR(36) NOT NULL,
                    creatorName VARCHAR(50) NOT NULL,
                    reportedUuid VARCHAR(36) NOT NULL,
                    reportedName VARCHAR(50) NOT NULL,
                    typeId INT(1) NOT NULL,
                    reason TEXT NOT NULL,
                    isResolved TINYINT(1) DEFAULT 0,
                    date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
                );
                """;
        updateData(query);
        close();
    }

    private void createPlayerTable() {
        query = """
                CREATE TABLE IF NOT EXISTS core_players
                (
                    uuid VARCHAR(36) PRIMARY KEY NOT NULL,
                    playerName VARCHAR(36) NOT NULL,
                    lastLogin TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
                );
                """;
        updateData(query);
        close();
    }

    public boolean hasJoinedOnce(@NotNull Player player) {
        return getPlayer(player) != null;
    }

    private void retrieveData(String query) {
        if (isConnected) {
            try {
                statement = NSWCore.getConnector().getConn().createStatement();
                resultSet = statement.executeQuery(query);
            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }

    private void updateData(String query) {
        if (isConnected) {
            try {
                statement = NSWCore.getConnector().getConn().createStatement();
                statement.executeUpdate(query);
            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }

    private void close() {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException ignored) {
            } // ignore
            resultSet = null;
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ignored) {
            } // ignore
            statement = null;
        }
    }
}
