/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import ClassiDB.Film;
import ClassiDB.Prenotazione;
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
    
    public List<Prenotazione> getPrenotazioniRimborsabili(int id_utente) throws SQLException
    {
        return dbm.getPrenotazioniUtenteRisarcibili(id_utente);
    }
    
    public List<Prenotazione> getPrenotazioniRimborsabili(String email) throws SQLException
    {
        int id_utente = dbm.getUtente(email).getId();
        return getPrenotazioniRimborsabili(id_utente);
    }
    
    public boolean rimborsaPrenotazione(int id_prenotazione) throws SQLException
    {
        return dbm.rimborsaPrenotazione(id_prenotazione);
    }
}
