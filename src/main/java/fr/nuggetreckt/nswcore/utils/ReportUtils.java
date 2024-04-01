package fr.nuggetreckt.nswcore.utils;

import fr.noskillworld.api.NSWAPI;
import fr.noskillworld.api.reports.Report;
import fr.noskillworld.api.reports.ReportSortType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ReportUtils {

    private final NSWAPI nswapi;

    private final Map<Integer, Report> reportIds;
    private final Map<UUID, ReportSortType> sortReportMap;

    public ReportUtils(NSWAPI api) {
        this.nswapi = api;
        this.reportIds = new HashMap<>();
        this.sortReportMap = new HashMap<>();
    }

    public List<ItemStack> getReportItems(boolean maskResolvedReports, Player player) {
        List<ItemStack> items = new ArrayList<>();
        List<Report> reports = null;
        initReportSort(player);

        switch (sortReportMap.get(player.getUniqueId())) {
            case DATE_ASC -> reports = nswapi.getReportHandler().getReportsByDate();
            case DATE_DESC -> reports = nswapi.getReportHandler().getReportsByDateDesc();
            case PLAYER_ASC -> reports = nswapi.getReportHandler().getReportsByName();
            case PLAYER_DESC -> reports = nswapi.getReportHandler().getReportsByNameDesc();
        }
        if (maskResolvedReports) {
            reports = reports.stream().filter(report -> !report.isResolved()).toList();
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
        nswapi.getReportHandler().deleteReport(report.getId());
    }

    public void markReportResolved(@NotNull Report report) {
        if (!report.isResolved()) {
            nswapi.getReportHandler().markReportAsResolved(report.getId());
        } else {
            nswapi.getReportHandler().markReportAsUnresolved(report.getId());
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

    public ReportSortType getCurrentSortReport(@NotNull Player player) {
        return sortReportMap.get(player.getUniqueId());
    }

    public void initReportSort(@NotNull Player player) {
        sortReportMap.putIfAbsent(player.getUniqueId(), ReportSortType.DATE_ASC);
    }

    public void toggleReportSort(@NotNull Player player) {
        ReportSortType current = getCurrentSortReport(player);
        ReportSortType[] values = ReportSortType.values();

        if (current == values[values.length - 1]) {
            sortReportMap.replace(player.getUniqueId(), values[0]);
        } else {
            sortReportMap.replace(player.getUniqueId(), values[current.ordinal() + 1]);
        }
    }
}
