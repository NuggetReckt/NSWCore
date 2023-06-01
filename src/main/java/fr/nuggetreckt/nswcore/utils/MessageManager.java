package fr.nuggetreckt.nswcore.utils;

import fr.nuggetreckt.nswcore.NSWCore;

public enum MessageManager {
    //Error messages
    NO_PERMISSION_MESSAGE("§cVous n'avez pas la permission d'exécuter cette commande !"),
    PLAYER_MOVED_TP("§cVous avez bougé, la téléportation a été annulée !"),
    WAIT_BEFORE_USE_MESSAGE("§cVeuillez patienter §3%s minute(s) §cavant de réutiliser cette commande !"),
    WAIT_BEFORE_KIT_MESSAGE("§cVeuillez patienter §3%s heures(s) §cavant de récupérer votre kit !"),
    COMMAND_ONLY_AVAILABLE_FARMZONE_MESSAGE("§cCette commande n'est disponible qu'en FarmZone !"),
    COMMAND_ONLY_AVAILABLE_SURVIVAL_MESSAGE("§cCette commande n'est disponible qu'en serveur survie !"),
    NO_ENOUGH_HONORPOINTS("§cVous n'avez pas assez de Points d'Honneur ! §8(§3%s§8/§3%s§8)"),
    MAX_HONORRANK_MESSAGE("§cVous êtes déjà au Rang d'Honneur maximum ! §8(§36§8)"),

    //Success messages
    SUCCESS_TP_MESSAGE("Téléporté avec succès !"),
    SUCCESS_SPAWN_TP_MESSAGE("Téléporté au spawn avec succès !"),
    KIT_RECEIVED("Vous avez reçu votre kit !"),
    STAFF_MODE_ENTER_MESSAGE("Entrée dans le mode staff"),
    STAFF_MODE_LEAVE_MESSAGE("Sortie du mode staff"),

    //Others
    PRE_TP_MESSAGE("Téléportation dans §32 §fsecondes..."),
    PLAYER_DEATH_MESSAGE("§3%s §fest mort."),
    PLAYER_DEATH2_MESSAGE("§3%s §fa mis fin à ses jours."),
    PLAYER_DEATH_MOB_MESSAGE("§3%s §fs'est fait tuer par un(e) §3%s§f."),
    PLAYER_DEATH_CREEPER_MESSAGE("§3%s §fs'est fait exploser par un §3Creeper§f."),
    PLAYER_DEATH_DROWNED_MESSAGE("§3%s §fs'est noyé."),
    PLAYER_DEATH_BURNED_MESSAGE("§3%s §fest mort brûlé vif."),
    PLAYER_DEATH_FALL_MESSAGE("§3%s §fa découvert la gravité."),
    PLAYER_DEATH_VOID_MESSAGE("§3%s §fest tombé dans le vide."),
    PLAYER_KILL_MESSAGE("§3%s §fs'est fait tuer par §3%s§f."),
    PRE_SPAWN_TP_MESSAGE("Téléportation dans §35 §fsecondes... §cNe bougez pas !"),
    WELCOME_PLAYER_JOIN_MESSAGE("Bienvenue à §3%s §fsur %s §f!"),
    HONORRANKS_RANKLIST_MESSAGE("Liste des Rangs d'Honneur : \n%s"),
    HONORRANKS_RANK_MESSAGE("Vous êtes %s"),
    HONORRANKS_RANKINFO_MESSAGE("""
            Information sur votre Rang d'Honneur :
             §8| §fRang d'Honneur actuel : §3%s
             §8| §fProchain Rang d'Honneur : §3%s §8(§3%s§8/§3%s §fPoints d'Honneur requis§8)"""),
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
        return "\n §8§l» §r" + this.message + "\n";
    }

    public String getDeathMessage() {
        return " §8§l» §7☠ §r" + this.message;
    }
}