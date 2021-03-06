/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassiDB;

import Database.DBManager;
import java.sql.SQLException;
import java.util.Calendar;

/**
 * Classe che rappresenta una prenotazione effettuata da un utente.
 * @author Ivan, Paolo
 */
public class Prenotazione {

    /**
     *
     */
    protected int id;

    /**
     *
     */
    protected Utente utente;

    /**
     *
     */
    protected Spettacolo spettacolo;

    /**
     *
     */
    protected Posto posto;

    /**
     *
     */
    protected Sala sala;

    /**
     *
     */
    protected double prezzo;

    /**
     *
     */
    protected String tipo_prezzo;

    /**
     *
     */
    protected boolean pagato;

    /**
     *
     */
    protected Calendar data_ora_operazione;

    /**
     * Costruttore vuoto per impostare l'oggetto con i set
     */
    public Prenotazione() {
    }

    /**
     * Costruttore che ritorna la prenotazione con quell'id; ricava i valori dal database.
     * @param id ID della prenotazione all'interno del database
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public Prenotazione(int id) throws SQLException, ClassNotFoundException{
        this.id = id;
        
        DBManager dbm = DBManager.getDBManager();
        Prenotazione p = dbm.getPrenotazione(id);
        
        this.utente = p.getUtente();
        this.spettacolo = p.getSpettacolo();
        this.posto = p.getPosto();
        this.sala = p.getSala();
        this.prezzo = p.getPrezzo();
        this.data_ora_operazione = p.getData_ora_operazione();
        this.pagato = p.isPagato();
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Utente getUtente() {
        return utente;
    }

    /**
     *
     * @param utente
     */
    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    /**
 *Ritorna l'oggetto spettacolo di quella prenotazione.
 * @return spettacolo prenotato da quella prenotazione
 */
    public Spettacolo getSpettacolo() {
        return spettacolo;
    }

    /**
     *
     * @param spettacolo
     */
    public void setSpettacolo(Spettacolo spettacolo) {
        this.spettacolo = spettacolo;
    }

    /**
 *Ritorna l'oggetto posto che è stato prenotato in quella prenotazione.
 * @return posto prenotato in quella prenotazione
 */
    public Posto getPosto() {
        return posto;
    }

    /**
     *
     * @param posto
     */
    public void setPosto(Posto posto) {
        this.posto = posto;
    }

    /**
     * Ritorna l'oggetto sala in cui si terrà lo spettacolo della prenotazione.
     * @return Sala in cui si terrà lo spettacolo.
     */
    public Sala getSala() {
        return sala;
    }

    /**
     *
     * @param sala
     */
    public void setSala(Sala sala) {
        this.sala = sala;
    }

    /**
     * Ritorna il prezzo in euro pagato per quella prenotazione.
     * @return prezzo in euro pagato per quella prenotazione
     */
    public double getPrezzo() {
        return prezzo;
    }

    /**
     *
     * @param prezzo
     */
    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }
/**
 * Ritorna la fascia di prezzo di quella prenotazione
 * @return fascia / tipo prezzo di quella prenotazione
 */
    public String getTipo_prezzo() {
        return tipo_prezzo;
    }

    /**
     *
     * @param tipo_prezzo
     */
    public void setTipo_prezzo(String tipo_prezzo) {
        this.tipo_prezzo = tipo_prezzo;
    }
    
    /**
     *
     * @return
     */
    public Calendar getData_ora_operazione() {
        return data_ora_operazione;
    }
/**
 *Ritorna l'oggetto Calendar rappresentante l'ora e la data in cui la prenotazione è stata creata.
     * @param data_ora_operazione
 */
    public void setData_ora_operazione(Calendar data_ora_operazione) {
        this.data_ora_operazione = data_ora_operazione;
    }
/**
 * Ritorna true se il prezzo della prenotazione è stato pagato.
 * @return true se la prenotazione è stata pagata
 */
    public boolean isPagato() {
        return pagato;
    }

    /**
     *
     * @param pagato
     */
    public void setPagato(boolean pagato) {
        this.pagato = pagato;
    }
    
    
}
