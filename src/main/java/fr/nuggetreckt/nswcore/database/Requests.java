package fr.nuggetreckt.nswcore.database;

import fr.noskillworld.api.database.RequestsHandler;
import fr.nuggetreckt.nswcore.NSWCore;

import java.sql.SQLException;
import java.sql.Timestamp;

public class Requests {

    public void setReportData(int id) {
        String query = "SELECT * FROM core_reports WHERE id = " + id + ";";
        RequestsHandler requestsHandler = NSWCore.getAPI().getDatabaseManager().getRequestHandler();

        int reportId = 0;
        String creatorName = null;
        String reportedName = null;
        String reportType = null;
        String reportReason = null;
        Timestamp timestamp = null;
        int isResolved = 0;

        requestsHandler.retrieveData(query);
        try {
            if (requestsHandler.resultSet.next()) {
                reportId = requestsHandler.resultSet.getInt("id");
                creatorName = requestsHandler.resultSet.getString("creatorName");
                reportedName = requestsHandler.resultSet.getString("reportedName");
                reportType = requestsHandler.resultSet.getString("typeName");
                reportReason = requestsHandler.resultSet.getString("reason");
                isResolved = requestsHandler.resultSet.getInt("isResolved");
                timestamp = requestsHandler.resultSet.getTimestamp("date");
            }
        } catch (SQLException e) {
            NSWCore.getInstance().getLogger().severe("SQLException: " + e.getMessage());
            NSWCore.getInstance().getLogger().severe("SQLState: " + e.getSQLState());
            NSWCore.getInstance().getLogger().severe("VendorError: " + e.getErrorCode());
        } finally {
            requestsHandler.close();
        }
        NSWCore.getReportUtils().setReportData(reportId, creatorName, reportedName, reportType, reportReason, timestamp, isResolved);
    }
}
