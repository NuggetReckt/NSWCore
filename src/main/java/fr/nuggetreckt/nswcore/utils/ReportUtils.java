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

    private final Map<Integer, ItemStack> reportItems;
    private final Map<Integer, Integer> reportIds;
    //TODO: algo de "routage" d'id afin d'éviter les erreurs

    private String reportedName;
    private String creatorName;
    private String reportType;
    private String reportReason;
    private Timestamp timestamp;
    private boolean isResolved;

    public ReportUtils() {
        this.reportItems = new HashMap<>();
        this.reportIds = new HashMap<>();
    }

    public void setReportItems(boolean maskResolvedReports) {
        resetReports();

        Requests req = NSWCore.getRequestsManager();

        final int reportsCount = req.getReportsCount();

        if (reportsCount == 0) return;

        String resolved;

        for (int i = 1; i <= reportsCount; i++) {
            if (i <= 44) {
                req.setReportData(i);

                if (isResolved && maskResolvedReports) {
                    //just works for read only and items stays at their initial slot
                    continue;
                }

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
        });
    }

    public void markReportAsResolved(int id) {
        NSWCore.getServerHandler().getExecutor().execute(() -> {
            if (isResolved(id)) return;
            NSWCore.getRequestsManager().markReportAsResolved(id);
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

    public boolean isResolved(int resolved) {
        return resolved == 1;
    }

    public String getShownStatus(boolean bool) {
        if (bool) {
            return "§aOui";
        } else {
            return "§cNon";
        }
    }

    public void setReportData(String creatorName, String reportedName, String reportType, String reportReason, Timestamp timestamp, int resolved) {
        this.creatorName = creatorName;
        this.reportedName = reportedName;
        this.reportType = reportType;
        this.reportReason = reportReason;
        this.timestamp = timestamp;
        this.isResolved = isResolved(resolved);
    }
}
