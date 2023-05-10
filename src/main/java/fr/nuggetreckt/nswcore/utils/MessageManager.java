package fr.nuggetreckt.nswcore.utils;

import fr.nuggetreckt.nswcore.NSWCore;

public enum MessageManager {
    //Error messages
    NO_PERMISSION_MESSAGE("§cVous n'avez pas la permission d'exécuter cette commande !"),
    WAIT_BEFORE_USING_MESSAGE("§cVeuillez patienter §3%s minute(s) §cavant de réutiliser cette commande !"),

    //Success messages
    SUCCESS_TP_MESSAGE("Téléporté avec succès !"),

    //Others
    ;

    private final String message;

    MessageManager(String s) {
        this.message = s;
    }

    public String getMessage() {
        return NSWCore.getPrefix() + this.message;
    }
}