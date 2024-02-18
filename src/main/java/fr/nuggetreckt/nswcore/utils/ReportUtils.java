package fr.nuggetreckt.nswcore.utils;

import fr.noskillworld.api.reports.Report;
import fr.nuggetreckt.nswcore.NSWCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportUtils {

    private final Map<Integer, Report> reportIds;

    public ReportUtils() {
        this.reportIds = new HashMap<>();
    }

    public List<ItemStack> getReportItems(boolean maskResolvedReports) {
        List<ItemStack> items = new ArrayList<>();
        List<Report> reports;

        if (maskResolvedReports) {
            reports = NSWCore.getAPI().getReportHandler().getUnResolvedReports();
        } else {
            reports = NSWCore.getAPI().getReportHandler().getReports();
        }
        int i = 0;
        String resolved;
        reportIds.clear();

        for (Report report : reports) {
            Player creator = Bukkit.getPlayer(report.getCreatorUuid());
            Player reported = Bukkit.getPlayer(report.getReportedUuid());

            if (report.isResolved()) {
                resolved = " §8(§a§lRésolu§8)";
            } else {
                resolved = "";
            }

            assert creator != null;
            assert reported != null;

            ItemStack item = new ItemUtils(Material.PAPER).setName("§8§l»§r §c§l" + reported.getName() + " §8§l«" + resolved).hideFlags()
                    .setLore(" ", "§8| §fPar §3" + creator.getName(), "§8| §fPour §3" + report.getReportType().getDisplayName(), "§8| §fLe §3" +
                                    report.getReportDate() + " §fà §3" + report.getReportTime(), "§8| §fRaison : §7" + report.getReason(), " ",
                            " §8| §fClic gauche : §aMarquer comme résolu", " §8| §fClic droit : §cSupprimer")
                    .toItemStack();

            reportIds.put(i, report);
            items.add(item);
            i++;
        }
        return items;
    }

    public void deleteReport(@NotNull Report report) {
        NSWCore.getAPI().getReportHandler().deleteReport(report.getId());
    }

    public void markReportResolved(@NotNull Report report) {
        if (!report.isResolved()) {
            NSWCore.getAPI().getReportHandler().markReportAsResolved(report.getId());
        } else {
            NSWCore.getAPI().getReportHandler().markReportAsUnresolved(report.getId());
        }
    }

    public Report getReportBySlot(int slot) {
        return reportIds.get(slot);
    }

    public String getShownStatus(boolean bool) {
        if (bool) {
            return "§aOui";
        } else {
            return "§cNon";
        }
    }
}
