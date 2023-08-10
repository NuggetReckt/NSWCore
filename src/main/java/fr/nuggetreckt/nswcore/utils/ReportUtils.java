package fr.nuggetreckt.nswcore.utils;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.database.Requests;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class ReportUtils {

    private final Map<Integer, ItemStack> reportItems = new HashMap<>();
    private final Map<Integer, Integer> reportIds = new HashMap<>();
    //TODO: algo de "routage" d'id afin d'éviter les erreurs

    public void setReportItems() {
        resetReports();

        Requests req = NSWCore.getRequestsManager();

        final int reportsCount = req.getReportsCount();

        if (reportsCount == 0) return;

        String reportedName;
        String creatorName;
        String reportType;
        String reportReason;
        Timestamp timestamp;
        boolean isResolved;
        String resolved;

        for (int i = 1; i <= reportsCount; i++) {
            if (i <= 44) {
                reportedName = req.getReportedName(i);
                creatorName = req.getCreatorName(i);
                reportType = req.getReportType(i);
                reportReason = req.getReportReason(i);
                timestamp = req.getReportTime(i);
                isResolved = isResolved(i);

                System.out.println("DEBUG: report = " + reportedName + " - " + reportReason);
                System.out.println("DEBUG: isResolved = " + isResolved);
                System.out.println("DEBUG: iteration = " + i);

                if (isResolved) {
                    resolved = " §8(§a§lRésolu§8)";
                } else {
                    resolved = "";
                }

                String reportDate = new SimpleDateFormat("MM/dd/yyyy").format(timestamp);
                String reportTime = new SimpleDateFormat("HH:mm").format(timestamp);

                ItemStack item = new ItemUtils(Material.PAPER).setName("§8§l»§r §c§l" + reportedName + " §8§l«" + resolved).hideFlags()
                        .setLore(" ", "§8| §fPar §3" + creatorName, "§8| §fPour §3" + reportType, "§8| §fLe §3" +
                                        reportDate + " §fà §3" + reportTime, "§8| §fRaison : §7" + reportReason, " ",
                                " §8| §fClic gauche : §aMarquer comme résolu", " §8| §fClic droit : §cSupprimer")
                        .toItemStack();

                setReportItem(i, item);
            }
        }
    }

    public void deleteReport(int id) {
        NSWCore.getServerHandler().getExecutor().execute(() -> {
            NSWCore.getRequestsManager().deleteReport(id);
            reportItems.remove(id);

            System.out.println("DEBUG: method deleteReport() fired");
            System.out.println("DEBUG: id = " + id);
        });
    }

    public void markReportAsResolved(int id) {
        NSWCore.getServerHandler().getExecutor().execute(() -> {
            NSWCore.getRequestsManager().markReportAsResolved(id);

            System.out.println("DEBUG: method markReportAsResolved() fired");
            System.out.println("DEBUG: id = " + id);
        });
    }

    public ItemStack getReportItem(int key) {
        return this.reportItems.get(key);
    }

    public void resetReports() {
        reportItems.clear();
    }

    private void setReportItem(int key, ItemStack item) {
        this.reportItems.putIfAbsent(key, item);
    }

    public boolean isResolved(int id) {
        int resolved = new Requests().getResolved(id);

        return resolved == 1;
    }
}
