/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import ClassiDB.Spettacolo;
import ClassiDB.Utente;
import Database.DBManager;
import java.sql.SQLException;

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
    public static boolean signUp(String email, String nome, String password)
    {
        try {
            DBManager dbm = DBManager.getDBManager();
            String codice = dbm.registrazione(email, password, nome);
            //INVIO EMAIL
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
    public static boolean resetPassword(String email)
    {
        try {
            DBManager dbm = DBManager.getDBManager();
            String codice = dbm.passwordDimenticata(email);
            // INVIO EMAIL
            return true;
            
        } catch (Exception e) {
            return false;
        }
    }
    
    public static void prenotaFilm(int id_spettacolo, int id_utente, String posti)
    {
        try{
            DBManager dbm = DBManager.getDBManager();
            Spettacolo s = dbm.getSpettacolo(id_spettacolo);
            String[] posti_prenotati = posti.split(" ");
            for(String posto : posti_prenotati)
            {
                String[] info_posto = posto.split(",");
                
                //RIGA,COLONNA,PREZZO
                int riga = Integer.parseInt(info_posto[0]);
                int colonna = Integer.parseInt(info_posto[1]);
                String prezzo = info_posto[2];
                
                int id_posto = dbm.getIDPosto(s.getSala().getId(), riga, colonna);
                
                dbm.insertPrenotazione(id_utente, id_spettacolo, id_posto, prezzo);
            }
            
            //dbm.insertPrenotazione(id_utente, id_spettacolo, id_utente, posti)
        }
        catch(SQLException ex)
        {
            
        }
        catch(ClassNotFoundException ex)
        {
            
        }
    }
    
}
