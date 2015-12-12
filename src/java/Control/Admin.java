/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import ClassiDB.Film;
import ClassiDB.Utente;
import Database.DBManager;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ivanmorandi
 */
public class Admin {
    protected DBManager dbm;
    
    public Admin() throws ClassNotFoundException, SQLException
    {
        dbm = DBManager.getDBManager();
    }
    
    public List<Utente> getTopClienti() throws SQLException, ClassNotFoundException
    {
        return dbm.topClienti();
    }
    
    public List<Film> getIncassiFilm() throws SQLException
    {
        return dbm.incassiFilm();
    }
}
