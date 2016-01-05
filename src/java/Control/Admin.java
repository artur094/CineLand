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
    
    /**
     * Costruttore
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public Admin() throws ClassNotFoundException, SQLException
    {
        dbm = DBManager.getDBManager();
    }
    
    /**
     * Funzione per recuperare i top clienti (quelli che pagano di più)
     * @return List<Utente> dove ci sono i migliori clienti (in ordine di soldi spesi)
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public List<Utente> getTopClienti() throws SQLException, ClassNotFoundException
    {
        return dbm.topClienti();
    }
    
    /**
     * Funzione per recuperare i film con gli incassi totali
     * @return List<Film> che contiene i film, e per ogni film il relativo incasso totale
     * @throws SQLException 
     */
    public List<Film> getIncassiFilm() throws SQLException
    {
        List<Film> incassi = dbm.incassiFilm();
        List<Film> all_film = dbm.getAllFilms();
        
        for (int i = 0; i < all_film.size(); i++) {
            for (Film film : incassi) {
                if(all_film.get(i).getId() == film.getId())
                    all_film.set(i, film);
            }
        }
        return all_film;
    }
    
    /**
     * Funzione per prendere le prenotazioni rimborsabili di un utente (tutte alla fine)
     * @param id_utente ID utente per vedere le sue prenotazioni pagate
     * @return List<Prenotazione> che contiene tutte le prenotazioni rimborsabili dell'utente
     * @throws SQLException 
     */
    public List<Prenotazione> getPrenotazioniRimborsabili(int id_utente) throws SQLException
    {
        return dbm.getPrenotazioniUtenteRisarcibili(id_utente);
    }
    
    /**
     * Funzione per prendere le prenotazioni rimborsabili di un utente (tutte alla fine)
     * @param email Email per identificare l'utente, e per vedere le prenotazioni pagate
     * @return List<Prenotazione> che contiene tutte le prenotazioni rimborsabili dall'utente
     * @throws SQLException 
     */
    public List<Prenotazione> getPrenotazioniRimborsabili(String email) throws SQLException
    {
        int id_utente = dbm.getUtente(email).getId();
        return getPrenotazioniRimborsabili(id_utente);
    }
    
    /**
     * Funzione per rimborsare una prenotazione
     * @param id_prenotazione Per identificare la prenotazione, quindi l'utente, il prezzo ecc
     * @return Boolean, per sapere se il rimborso è andato a buon fine o no
     * @throws SQLException 
     */
    public boolean rimborsaPrenotazione(int id_prenotazione) throws SQLException
    {
        return dbm.rimborsaPrenotazione(id_prenotazione);
    }
}
