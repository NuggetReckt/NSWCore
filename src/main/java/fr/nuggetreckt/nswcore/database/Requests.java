package fr.nuggetreckt.nswcore.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class Requests {

    private final boolean isConnected;
    private Statement statement;
    private ResultSet resultSet;

    private String query;

    public Requests() {
        this.isConnected = new Connector().isConnected();
    }


    public void initPlayerRank() {
        query = "INSERT INTO";
        updateData(query);
    }

    public void updatePlayerRank() {
        query = "UPDATE";
        updateData(query);
    }

    public void initPlayerPoints() {
        query = "INSERT INTO";
        updateData(query);
    }

    public void updatePlayerPoints() {
        query = "UPDATE";
        updateData(query);
    }

    public int getPlayerRankId(UUID uuid) {
        query = "SELECT rankId FROM core_honorranks WHERE uuid = '" + uuid + "';";
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

    public long getPlayerPoints(UUID uuid) {
        query = "SELECT honorPoints FROM core_honorpoints WHERE uuid = '" + uuid + "';";
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


    public void createTable() {
        query = "CREATE TABLE IF NOT EXISTS core_honorranks";
        updateData(query);
        close();
    }

    private void retrieveData(String query) {
        if (isConnected) {
            try {
                statement = new Connector().getConn().createStatement();
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
                statement = new Connector().getConn().createStatement();
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
