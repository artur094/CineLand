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
import ClassiDB.Utente;
import Database.DBManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Classe che gestisce le funzionalità della parte amministrativa.
 * @author ivanmorandi
 * @author Paolo
 */
public class Admin {
    protected DBManager dbm;
    
    /**
     * Costruttore.
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public Admin() throws ClassNotFoundException, SQLException
    {
        dbm = DBManager.getDBManager();
    }
    
    /**
     * Funzione per avere i posti (presi dalle prenotazioni) venduti per uno spettacolo
     * @param id_spettacolo ID spettacolo
     * @return Lista prenotazioni per spettacolo
     * @throws SQLException 
     */
    public List<Prenotazione> getPrenotazioniVendute(int id_spettacolo) throws SQLException
    {
        return dbm.prenotazioniPerSpettacolo(id_spettacolo);
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
     * Prende le prenotazioni rimborsabili di un utente (tutte alla fine)
     * @param email Email dell'utente di cui vuole sapere le prenotazioni rimborsabili
     * @return List<Prenotazione> che contiene tutte le prenotazioni rimborsabili dall'utente
     * @throws SQLException 
     */
    public List<Prenotazione> getPrenotazioniRimborsabili(String email) throws SQLException
    {
        int id_utente = dbm.getUtente(email).getId();
        return getPrenotazioniRimborsabili(id_utente);
    }
    
    /**
     * Rimborsa una prenotazione
     * @param id_prenotazione id della prenotazione (nel database) da rimborsare
     * @return Boolean, per sapere se il rimborso è andato a buon fine o no
     * @throws SQLException 
     */
    public boolean rimborsaPrenotazione(int id_prenotazione) throws SQLException
    {
        return dbm.rimborsaPrenotazione(id_prenotazione);
    }
    
    /**
     * Funzione che recupera la sala dato l'id
     * @param id_sala Id della sala
     * @return Sala sala dell'id
     * @throws SQLException
     * @throws Exception 
     */
    public Sala getSala(int id_sala) throws SQLException, Exception
    {
        return dbm.getSalaConPostiPiuPrenotati(id_sala);
    }
}
