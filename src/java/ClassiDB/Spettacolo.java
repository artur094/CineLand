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
 *
 * @author Ivan
 */
public class Spettacolo {

    /**
     *
     */
    protected int id;

    /**
     *
     */
    protected Film film;

    /**
     *
     */
    protected Sala sala;

    /**
     *
     */
    protected Calendar data_ora;

    /**
     * Costruttore vuoto per settare i dati con i get e set
     */
    public Spettacolo() {
    }
 
    /**
     * Costruttore che crea l'oggetto spettacolo secondo l'id dato
     * @param id ID dello spettacolo
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public Spettacolo(int id) throws SQLException, ClassNotFoundException{
        this.id = id;
        
        DBManager dbm = DBManager.getDBManager();
        Spettacolo s = dbm.getSpettacolo(id);
        
        this.film = s.film;
        this.sala = s.sala;
        this.data_ora = s.data_ora;
    }
/**
 *Ritorna ID dello spettacolo.
 * @return id dello spettacolo
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
    public Film getFilm() {
        return film;
    }

    /**
     *
     * @param film
     */
    public void setFilm(Film film) {
        this.film = film;
    }
/**
 *Ritorna l'oggetto sala in cui si tiene quello spettacolo.
 * @return sala in cui si svolge quello spettacolo.
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
 *Ritorna l'oggetto Calendar rappresentante l'ora e la data in cui si tiene quello spettacolo.
 * @return Data e ora in cui si tiene lo spettacolo.
 */
    public Calendar getData_ora() {
        return data_ora;
    }

    /**
     *
     * @param data_ora
     */
    public void setData_ora(Calendar data_ora) {
        this.data_ora = data_ora;
    }
    
    
    
    
}
