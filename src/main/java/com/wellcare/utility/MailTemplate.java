package com.wellcare.utility;

public class MailTemplate {

    public static final String CONF_NEW_ACCOUNT = "Spett. Cliente,<br>"
            + "la procedura di registrazione è andata a buon fine e il suo account è stato creato correttamente.<br>"
            + "Può effettuare l'accesso al seguente <a href='%1s'>link</a>.<br><br>"
            + "Cordiali saluti.";

    public static final String CONF_NEW_ACCOUNT_TITLE = "Conferma Registrazione";

    public static final String GEN_PASS = "Spett. Cliente,<br>"
            + "come da sua richiesta, la contattiamo per informarla della generazione di una nuova pasword "
            + "%1s <br>"
            + "Le consigliamo di effettuare l'accesso al seguente <a href='%2s'>link</a> "
            + "e cambiare quanto prima la password.<br><br>"
            + "Cordiali saluti.";

    public static final String GEN_PASS_TITLE = "Richiesta recupero password";

    public static final String CONF_CHANGE_PASS = "Spett. Cliente %1s,<br>"
            + "come da sua richiesta, la password è stata cambiata con successo.<br>"
            + "Può effettuare l'accesso al seguente <a href='%2s'>link</a>.<br><br>"
            + "Cordiali saluti.";

    public static final String CONF_CHANGE_PASS_TITLE = "Conferma cambio password";

}