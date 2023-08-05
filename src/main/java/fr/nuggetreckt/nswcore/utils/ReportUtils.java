package fr.nuggetreckt.nswcore.utils;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.database.Requests;

public class ReportUtils {

    public void markReportAsResolved(int id) {
        NSWCore.getServerHandler().getExecutor().execute(() -> {
            new Requests().markReportAsResolved(id);
            System.out.println("DEBUG: method markReportAsResolved() fired");
        });
    }

    public void deleteReport(int id) {
        NSWCore.getServerHandler().getExecutor().execute(() -> {
            new Requests().deleteReport(id);
            System.out.println("DEBUG: method deleteReport() fired");
        });
    }

    public boolean isResolved(int id) {
        int resolved = new Requests().getResolved(id);

        return resolved == 1;
    }
}
