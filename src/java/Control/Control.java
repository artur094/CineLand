/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import ClassiDB.Prenotazione;
import ClassiDB.Spettacolo;
import ClassiDB.Utente;
import Database.DBManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ivanmorandi
 */
public class Control {

    protected Control() {
        
    }
    
    // Funzione che gestisce il login
    public static Utente logIn(String email, String password)
    {
        try{
            DBManager dbm = DBManager.getDBManager();
            Utente u = dbm.logIn(email, password);
            return u;
        }
        catch(Exception ex)
        {
            return null;
        }
    }
    
    // Funzione che gestisce la registrazione
    // Effettuare controllo se l'email è legale (xxx@yyy.zzz)
    public static boolean signUp(String email, String nome, String password, String url_cineland)
    {
        try {
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
            
        } catch (Exception e) {
            return false;
        }
    }
    
    public static boolean enableAccount(String email, String codice)
    {
        try {
            DBManager dbm = DBManager.getDBManager();
            return dbm.enableAccount(email, codice);
        } catch (Exception e) {
            return false;
        }
    }
    
    // Funzione che gestisce il reset della password
    public static boolean resetPassword(String email, String url_cineland)
    {
        try {
            DBManager dbm = DBManager.getDBManager();
            Utente u = dbm.getUtente(email);
            String codice = dbm.passwordDimenticata(email);
            // INVIO EMAIL
            String link = url_cineland + "?op=resetpsw&email="+email+"&codice="+codice;
            String oggetto = "Reset Password CineLand";
            String messaggio = "Gentile "+u.getNome()+ ",\n"+
                    "Per resettare la password prema il seguente link:\n"+
                    "\t\t"+ link + "\n"+
                    "Nel caso non sia il proprietario dell'account, basta che elimini la email";
            SendEmail sendEmail = new SendEmail();
            sendEmail.send(email, oggetto, messaggio);
            return true;
            
        } catch (Exception e) {
            return false;
        }
    }
    
    public static void prenotaFilm(int id_spettacolo, int id_utente, String posti)
    {
        try{
            List<Prenotazione> nuovePrenotazioni;
            Prenotazione p;
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
                
                switch(prezzo)
                {
                    case "N": prezzo = "normale"; break;
                    case "R": prezzo = "ridotto"; break;
                    case "S": prezzo = "studente";break;
                    case "M": prezzo = "militare";break;
                    case "D": prezzo = "disabile";break;
                    default: return;
                }
                
                id_posto = dbm.getIDPosto(s.getSala().getId(), riga, colonna);
                
                p = dbm.insertPrenotazione(id_utente, id_spettacolo, id_posto, prezzo);
                nuovePrenotazioni.add(p);
            }
            
            // CREAZIONE QRCODE
            // CREAZIONE PDF CON UN BIGLIETTO PER PAGINA (CON QRCODE)
            // INVIO EMAIL DEL PDF
        }
        catch(SQLException ex)
        {
            
        }
        catch(ClassNotFoundException ex)
        {
            
        }
    }
    
}
