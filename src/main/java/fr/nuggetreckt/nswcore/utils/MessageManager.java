package fr.nuggetreckt.nswcore.utils;

import fr.nuggetreckt.nswcore.NSWCore;

public enum MessageManager {
    //Error messages
    NO_PERMISSION_MESSAGE("§cVous n'avez pas la permission d'exécuter cette commande !"),
    WAIT_BEFORE_USE_MESSAGE("§cVeuillez patienter §3%s minute(s) §cavant de réutiliser cette commande !"),
    WAIT_BEFORE_KIT_MESSAGE("§cVeuillez patienter §3%s heures(s) §cavant de récupérer votre kit !"),
    COMMAND_NOT_AVAILABLE_FARMZONE_MESSAGE("§cCette commande n'est disponible qu'en FarmZone !"),
    NO_ENOUGH_HONORPOINTS("§cVous n'avez pas assez de Points d'Honneur ! §8(§3%s§8/§3%s§8)"),
    MAX_HONORRANK_MESSAGE("§cVous êtes déjà au Rang d'Honneur maximum ! §8(§36§8)"),

    //Success messages
    SUCCESS_TP_MESSAGE("Téléporté avec succès !"),
    KIT_RECEIVED("Vous avez reçu votre kit !"),
    STAFF_MODE_ENTER_MESSAGE("Entrée dans le mode staff"),
    STAFF_MODE_LEAVE_MESSAGE("Sortie du mode staff"),

    //Others
    HONORRANKS_RANKLIST_MESSAGE("Liste des Rangs d'Honneur : \n%s"),
    HONORRANKS_RANK_MESSAGE("Vous êtes %s"),
    HONORRANKS_RANKINFO_MESSAGE("Information sur votre Rang d'Honneur :\n" +
            " §8| §fRang d'Honneur actuel : §3%s\n" +
            " §8| §fProchain Rang d'Honneur : §3%s §8(§3%s§8/§3%s §fPoints d'Honneur requis§8)"),
    HONORRANKS_UPRANK_BROADCASTMESSAGE("§3%s §fest passé Rang d'Honneur §3%s §f!"),
    ;

    private final String message;

    MessageManager(String s) {
        this.message = s;
    }

    public String getMessage() {
        return NSWCore.getPrefix() + this.message;
    }

    public String getBroadcastMessage() {
        return " §8§l» §r" + this.message;
    }
}