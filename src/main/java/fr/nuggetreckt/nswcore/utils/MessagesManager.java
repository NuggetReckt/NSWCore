package fr.nuggetreckt.nswcore.utils;

public enum MessagesManager {
    //Error messages
    NO_PERMISSION_MESSAGE("§cVous n'avez pas la permission d'exécuter cette commande !"),
    WAIT_BEFORE_USING_MESSAGE("§cVeuillez patienter §3%s§c minute(s) avant de réutiliser cette commande !"),

    //Success messages
    SUCCESS_TP_MESSAGE("Téléporté avec succès !"),
    ;

    private final String message;

    MessagesManager(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
