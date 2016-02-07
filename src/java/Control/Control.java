/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import ClassiDB.Film;
import ClassiDB.Posto;
import ClassiDB.Prenotazione;
import ClassiDB.Sala;
import ClassiDB.Spettacolo;
import ClassiDB.Utente;
import Database.DBManager;
import GestioneClassi.Films;
import com.itextpdf.text.DocumentException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.mail.MessagingException;
import net.sf.sojo.interchange.json.JsonSerializer;

/**
 * Classe contenete metodi di controllo; migliora la leggibilità di Controller.java.
 * @author ivanmorandi
 */
public class Control {

    /**
     * Costruttore
     */
    protected Control() {
        
    }
    
    /**
     * Funzione statica che esegue il login 
     * @param email Email dell'utente
     * @param password Password dell'utente
     * @return Oggetto Utente o null in caso di errore
     */
    public static Utente logIn(String email, String password) throws ClassNotFoundException, SQLException
    {
        DBManager dbm = DBManager.getDBManager();
        Utente u = dbm.logIn(email, password);
        return u;
    }
    
    /**
     * Funzione statica che esegue la registrazione ed invia la mail di avvenuta registrazione
     * @param email Email dell'utente
     * @param nome Nome utente
     * @param password Password utente
     * @param url_cineland URL del sito
     * @return True in caso sia andato tutto bene, altrimenti false
     */
    public static boolean signUp(String email, String nome, String password, String url_cineland) throws SQLException, ClassNotFoundException, MessagingException
    {
        DBManager dbm = DBManager.getDBManager();
        String codice = dbm.registrazione(email, password, nome);
        //INVIO EMAIL
        String link = url_cineland + "?op=enable&email="+email+"&codice="+codice;
        String oggetto = "Attivazione account CineLand";
        String messaggio = "Gentile "+nome+ ",\n"+
                "Per attivare l'account prema il seguente link:\n"+
                "\t\t"+ link + "\n"+
                "Nel caso non sia il proprietario dell'account, basta che elimini la email";
        SendEmail sendEmail = new SendEmail();
        sendEmail.send(email, oggetto, messaggio);

        return true;  
    }
    
    /**
     * Funzione statica che abilita l'account appena registrato
     * @param email Email dell'utente
     * @param codice Codice che è stato inviato tramite mail all'utente
     * @return Booleano, True se è OK, false altrimenti
     */
    public static boolean enableAccount(String email, String codice) throws ClassNotFoundException, SQLException
    {
        DBManager dbm = DBManager.getDBManager();
        return dbm.enableAccount(email, codice);
    }
    
    /**
     * Funzione statica per la gestione della password dimenticata, in cui viene inviata la mail di password persa
     * @param email Email dell'utente
     * @param url_cineland URL sito
     * @return True se c'è corrispondenza nel database e l'email è stata inviata, False altrimenti
     */
    public static boolean passwordDimenticata(String email, String url_cineland) throws SQLException, ClassNotFoundException, MessagingException
    {
        DBManager dbm = DBManager.getDBManager();
        Utente u = dbm.getUtente(email);
        if(u!=null){
            String codice = dbm.passwordDimenticata(email);
            // INVIO EMAIL
            String link = url_cineland + "?op=paginaresetpsw&email="+email+"&codice="+codice;
            String oggetto = "Reset Password CineLand";
            String messaggio = "Gentile "+u.getNome()+ ",\n"+
                    "Per resettare la password prema il seguente link:\n"+
                    "\t\t"+ link + "\n"+
                    "Nel caso non sia il proprietario dell'account, basta che elimini la email";
            SendEmail sendEmail = new SendEmail();
            sendEmail.send(email, oggetto, messaggio);
            return true;
        }
        else{
            return false;
        }
    }
    
    public static String getEmailFromCode_ForgottenPassword(String code)
    {
        try{
            DBManager dbm = DBManager.getDBManager();
            return dbm.getEmailFromCodeForForgottenPassword(code);
        }catch(Exception ex)
        {
            return "";
        }
    }
    
    /**
     * Funzione statica per il cambio password
     * @param email Email dell'utente
     * @param password Nuova password
     * @param codice Codice utente
     * @return True se OK, False altrimenti
     */
    public static boolean resetPassword(String email, String password, String codice) throws SQLException, ClassNotFoundException
    {
        DBManager dbm = DBManager.getDBManager();
        if(dbm.resettaPassword(email, password, codice))
        {
            return true;
        }

        return false;
    }
    
    /**
     * Funzione che richiama il cambia password del dbmanager
     * @param email Email dell'utente a cui cambiare la password
     * @param nome Nome utente (per l'invio dell'email)
     * @param oldPassword Vecchia password
     * @param newPassword Nuova password
     * @return TRUE se OK, FALSE altrimenti
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws MessagingException 
     */
    public static boolean cambiaPassword(String email,String nome,String oldPassword, String newPassword) throws SQLException, ClassNotFoundException, MessagingException
    {
        DBManager dbm = DBManager.getDBManager();
        if(dbm.cambiaPassword(email, oldPassword, newPassword))
        {
            SendEmail send = SendEmail.getInstance();
            String msg = "Gentile "+nome+
                            "La sua password è stata cambiata come da lei richiesto.\n"+
                            "In caso non sia stato lei, ci avverti immediatamente e le consigliamo di fare la richiesta di password dimenticata, "+
                            "in questo modo riceverà una email di reset password, potrà cambiare la password e riavere il suo account indietro.\n"+
                            "Inoltre le consigliamo di utilizzare una password più resistente.\n"+
                            "La ringraziamo per utilizzare il servizio CineLand, e le auguriamo una buona giornata!\n"+
                            "Staff Cineland.";
            send.send(email, "Cambio Password", msg);
            return true;
        }
        return false;
    }
    
    /**
     * Funzione che invia la richiesta al DBManager di 'cancellare' dei posti (perchè rotti)
     * @param posti Stringa che contiene i posti da 'cancellare'. Formato stringa: "riga,colonna riga,colonna ... riga,colonna"
     * @param nome_sala Nome della sala a cui effettuare la modifica
     * @return intero che indica quante operazioni sono andate a buon fine
     */
    public static int creaBuchiSala(String nome_sala, String posti) throws SQLException, ClassNotFoundException
    {
        String[] array;
        String[] posto;
        int riga;
        int colonna;
        int risultato = 0;

        DBManager dbm = DBManager.getDBManager();
        int id_sala = dbm.getIdSala(nome_sala);

        if(id_sala < 0)
            return 0;

        array = posti.split(" ");
        for (String s : array) {
            posto = s.split(",");
            riga = Integer.parseInt(posto[0]);
            colonna = Integer.parseInt(posto[1]);

            if(dbm.creaPostoVuoto(id_sala, riga, colonna))
                risultato++;

        }
        return risultato;
    }
    
    /**
     * Funzione statica per la prenotazione di posti per uno spettacolo.
     * @param id_spettacolo ID spettacolo 
     * @param id_utente ID utente
     * @param posti Stringa contenente un insieme di posti 'RIGA,COLONNA,PREZZO' divisi per spazio
     * @return esito della operazione; true se andata a buon fine, false altrimenti. 
     */
    public static boolean prenotaFilm(int id_spettacolo, Utente utente, String posti) 
            throws SQLException, ClassNotFoundException, MessagingException, DocumentException, IOException
    {
        ArrayList<Prenotazione> nuovePrenotazioni;
        Prenotazione p = null;      //cambiato, altrimenti dava problemi con l'if
        DBManager dbm;
        Spettacolo s;
        String prezzo;
        String[] info_posto;
        String[] posti_prenotati;
        int riga;
        int colonna;
        int id_posto;

        dbm = DBManager.getDBManager();
        nuovePrenotazioni = new ArrayList<>();
        s = dbm.getSpettacolo(id_spettacolo);
        posti_prenotati = posti.split(" ");

        for(String posto : posti_prenotati)
        {
            info_posto = posto.split(",");

            //RIGA,COLONNA,PREZZO
            riga = Integer.parseInt(info_posto[0]);
            colonna = Integer.parseInt(info_posto[1]);

            prezzo = info_posto[2];

            switch(prezzo.toUpperCase())
            {
                case "N": prezzo = "normale"; break;
                case "A": prezzo = "ridotto"; break;
                case "R": prezzo = "ridotto"; break;
                case "S": prezzo = "studente";break;
                case "M": prezzo = "militare";break;
                case "D": prezzo = "disabile";break;
                default: 
                    dbm.removePrenotazioni(nuovePrenotazioni);
                    return false;
            }

            id_posto = dbm.getIDPosto(s.getSala().getId(), riga, colonna);

            p = dbm.insertPrenotazione(utente.getId(), id_spettacolo, id_posto, prezzo);
            
            if(p == null)
            {
                //errore inserimento quindi rollback
                dbm.removePrenotazioni(nuovePrenotazioni);
                return false;
            }
            
            nuovePrenotazioni.add(p);
        }

        // CREAZIONE QRCODE
        // CREAZIONE PDF CON UN BIGLIETTO PER PAGINA (CON QRCODE)
        // INVIO EMAIL DEL PDF

        PdfBiglietto tickets = new PdfBiglietto();
        tickets.setPrenotazioni(nuovePrenotazioni);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        tickets.costruisciPdf("tickets.pdf", os);
        SendEmail send = SendEmail.getInstance();
        send.send(
                utente.getEmail(),
                "Prenotazione Cineland",
                "In allegato ci sono i biglietti del cinema",
                os);
        return true;
    }
    
    /**
     * Funzione statica per la creazione di spettacoli per l'esame
     * @param X Distanza di tempo tra una prenotazione e l'altra
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static void script(int X) throws SQLException, ClassNotFoundException
    {
        /*
        DBManager dbm = DBManager.getDBManager();
        List<Film> lista_film = Films.getAllFilms().getListaFilm();
        List<Integer> lista_sale = dbm.getListaIDSale();
        Random r = new Random((new Date()).getTime());
        for (int i = 0; i < lista_sale.size(); i++) {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.MINUTE, r.nextInt(X+1));
            
            for (int j = 0; j < 15; j++) {
                Timestamp ts = new Timestamp(c.getTime().getTime());
                dbm.insertSpettacolo(lista_sale.get(i), lista_film.get(i).getId(), ts);
                c.add(Calendar.MINUTE, X);
            }
        }
        */  
        DBManager dbm = DBManager.getDBManager();
        List<Film> lista_film = Films.getAllFilms().getListaFilm();
        List<Integer> lista_sale = dbm.getListaIDSale();
        Random r = new Random((new Date()).getTime());
        for (int i = 0; i < lista_film.size(); i++) {
            int indice_sala = i % lista_sale.size();
            Calendar c = Calendar.getInstance();
            c.add(Calendar.MINUTE, r.nextInt(X+1));
            
            for (int j = 0; j < 15; j++) {
                Timestamp ts = new Timestamp(c.getTime().getTime());
                dbm.insertSpettacolo(lista_sale.get(indice_sala), lista_film.get(i).getId(), ts);
                c.add(Calendar.MINUTE, X);
            }
        }
    }
    
    public static boolean checkEmail(String email)
    {
        String[] vett = email.split("@");
        if(vett.length!=2)
            return false;
        if(!vett[1].contains("."))
            return false;
        return true;
    }

}
