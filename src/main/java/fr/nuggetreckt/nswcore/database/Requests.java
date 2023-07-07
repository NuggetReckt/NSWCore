package fr.nuggetreckt.nswcore.database;

import fr.nuggetreckt.nswcore.NSWCore;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
                result = NSWCore.getPlayerByName(resultSet.getString("playerName"));
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

    public void createTables() {
        NSWCore.getServerHandler().getExecutor().execute(() -> {
            createPlayerDataTable();
            createReportsTable();
        });
    }

    private void createPlayerDataTable() {
        query = """
                CREATE TABLE IF NOT EXISTS core_playerdata
                (
                    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
                    uuid VARCHAR(36),
                    playerName VARCHAR(50),
                    rankId INT(1),
                    reason TEXT,
                    honorPoints INT(5)
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
                    creatorUuid VARCHAR(36),
                    creatorName VARCHAR(50),
                    reportedUuid VARCHAR(36),
                    reportedName VARCHAR(50),
                    typeId INT(1),
                    date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
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
