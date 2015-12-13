/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

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
        catch(ClassNotFoundException ex)
        {
            return null;
        }
        catch(SQLException ex)
        {
            return null;
        }
    }
    
    // Funzione che gestisce la registrazione
    // Effettuare controllo se l'email è legale (xxx@yyy.zzz)
    public static Utente signUp(String email, String nome, String password)
    {
        return null;
    }
    
    // Funzione che gestisce il reset della password
    public static boolean resetPassword(String email)
    {
        return false;
    }
    
}