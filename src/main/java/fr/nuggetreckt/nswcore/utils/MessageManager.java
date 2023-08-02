package fr.nuggetreckt.nswcore.utils;

import fr.nuggetreckt.nswcore.NSWCore;

public enum MessageManager {
    //Error messages
    NO_PERMISSION("§cVous n'avez pas la permission."),
    NO_PERMISSION_CMD("§cVous n'avez pas la permission d'exécuter cette commande !"),
    NO_HR_PERMISSION("§cVous n'avez pas le Rang d'Honneur requis pour exécuter cette commande ! §8(§3/hr §7pour plus d'infos§8)"),
    NOT_ENOUGH_ARGS("§cArguments insuffisants. §8(§cUtilisation : §3%s§8)"),
    UNKNOWN_SUBCOMMAND("§cSous commande inconnue."),
    UNKNOWN_PLAYER("§cCe joueur n'existe pas."),
    PLAYER_MOVED_TP("§cVous avez bougé, la téléportation a été annulée !"),
    PLAYER_NOT_TP("§cVous n'avez pas été téléporté, car la localisation n'est pas sûre !"),
    NO_ITEMS_TO_FURNACE("§cVous n'avez aucun item à cuire dans votre main !"),
    NOT_ENOUGH_ROOM_INV("§cVous n'avez pas assez de place dans votre inventaire !"),
    WAIT_BEFORE_USE("§cVeuillez patienter §3%s minute(s) §cavant de réutiliser cette commande !"),
    WAIT_BEFORE_USE_SECONDS("§cVeuillez patienter §3%s secondes(s) §cavant de réutiliser cette commande !"),
    WAIT_BEFORE_KIT("§cVeuillez patienter §3%s heures(s) §cavant de récupérer votre kit !"),
    COMMAND_ONLY_AVAILABLE_FARMZONE("§cCette commande n'est disponible qu'en FarmZone !"),
    COMMAND_ONLY_AVAILABLE_SURVIVAL("§cCette commande n'est disponible qu'en serveur survie !"),
    NO_ENOUGH_HONORPOINTS("§cVous n'avez pas assez de Points d'Honneur ! §8(§3%s§8/§3%s§8)"),
    MAX_HONORRANK("§cVous êtes déjà au Rang d'Honneur maximum ! §8(§36§8)"),
    PLAYER_ALREADY_FROZEN("§cLe joueur est déjà freeze !"),
    PLAYER_NOT_FROZEN("§cLe joueur n'est pas freeze !"),
    PLAYER_FREEZE_HIMSELF("§cVous ne pouvez pas vous freeze vous-même !"),
    PLAYER_UNFREEZE_HIMSELF("§cVous ne pouvez pas vous unfreeze vous-même !"),
    COMMANDS_DISABLED("§cLes commandes ont été désactivées."),

    //Success messages
    SUCCESS_TP("Téléporté avec succès !"),
    SUCCESS_TP_OTHER("Vous avez téléporté §3%s §favec succès !"),
    SUCCESS_GIVEHP("Vous avez give §3%s §fPoints d'Honneur à §3%s"),
    SUCCESS_GIVEHP_OTHER("§3%s §fvous à give §3%s §fPoints d'Honneur"),
    SUCCESS_UPGRADE("Vous avez fait passer §3%s §fau Rang d'Honneur supérieur"),
    SUCCESS_UPGRADE_OTHER("§3%s §fvous à fait passer au Rang d'Honneur supérieur !"),
    SUCCESS_SPAWN_TP("Téléporté au spawn avec succès !"),
    ITEMS_COOKED("Les items dans votre main ont été cuits"),
    KIT_RECEIVED("Vous avez reçu votre kit !"),
    STAFF_MODE_ENTER("Entrée dans le mode staff"),
    STAFF_MODE_LEAVE("Sortie du mode staff"),
    PLAYER_FREEZE_STAFF("Vous avez freeze le joueur §3%s"),
    PLAYER_UNFREEZE_STAFF("Vous avez unfreeze le joueur §3%s"),

    //Other messages
    PRE_TP("Téléportation dans §32 §fsecondes..."),
    PLAYER_DEATH("§3%s §fest mort."),
    PLAYER_DEATH2("§3%s §fa mis fin à ses jours."),
    PLAYER_DEATH_MOB("§3%s §fs'est fait tuer par un(e) §3%s§f."),
    PLAYER_DEATH_CREEPER("§3%s §fs'est fait exploser par un §3Creeper§f."),
    PLAYER_DEATH_DROWNED("§3%s §fs'est noyé."),
    PLAYER_DEATH_BURNED("§3%s §fest mort brûlé vif."),
    PLAYER_DEATH_FALL("§3%s §fa découvert la gravité."),
    PLAYER_DEATH_VOID("§3%s §fest tombé dans le vide."),
    PLAYER_KILL("§3%s §fs'est fait tuer par §3%s§f."),
    PLAYER_KILL2("§3%s §fs'est fait atomiser par §3%s§f."),
    PRE_SPAWN_TP("Téléportation dans §35 §fsecondes... §cNe bougez pas !"),
    WELCOME_PLAYER_JOIN("Bienvenue à §3%s §fsur %s §f!"),
    HONORRANKS_RANKLIST("Liste des Rangs d'Honneur :\n%s"),
    HONORRANKS_RANK("Vous êtes %s"),
    HONORRANKS_POINTS("Vous avez actuellement §3%s §fPoints d'Honneur"),
    HONORRANKS_RANKINFO("""
            Informations sur votre Rang d'Honneur :
             §8| §fRang d'Honneur actuel : §3%s
             §8| §fProchain Rang d'Honneur : §3%s §8(§3%s§8/§3%s §7Points d'Honneur requis§8)"""),
    HONORRANKS_RANKINFO_MAX("""
            Informations sur votre Rang d'Honneur :
             §8| §fRang d'Honneur actuel : §3%s
             §8| §fProchain Rang d'Honneur : §fVous êtes déjà au Rang d'Honneur Maximum !"""),
    HONORRANKS_UPRANK_BROADCAST("§3%s §fest passé Rang d'Honneur §3%s §f!"),
    HONORANKS_UPRANK_REWARDS("§fFélicitations ! Vous êtes monté au Rang d'Honneur niveau %s §r! \n%s"),
    PLAYER_FREEZE_TARGET("Vous avez été freeze par §3%s§f. " +
            "Veuillez vous rendre sur le discord immédiatement. §8(§3/discord§8) " +
            "\n§cNe vous déconnectez pas ou vous serez banni définitivement."),
    PLAYER_UNFREEZE_TARGET("Vous avez été unfreeze par §3%s§f."),
    PLAYER_FREEZED_QUIT("§cLe joueur §3%s §cs'est déconnecté en étant freeze"),
    ;

    private final String message;

    MessageManager(String s) {
        this.message = s;
    }

    public String getMessage() {
        return NSWCore.getInstance().getPrefix() + this.message;
    }

    public String getBroadcastMessage() {
        return "\n §8§l» §r" + this.message + "\n";
    }

    public String getDeathMessage() {
        return " §8§l» §7☠ §r" + this.message;
    }

    public String getWarnMessage() {
        return " §8§l» §6⚠ §r" + this.message;
    }
}