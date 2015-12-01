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
    protected int id;
    protected Film film;
    protected Sala sala;
    protected Calendar data_ora;

    public Spettacolo() {
    }
 
    public Spettacolo(int id) throws SQLException, ClassNotFoundException{
        this.id = id;
        
        DBManager dbm = DBManager.getDBManager();
        Spettacolo s = dbm.getSpettacolo(id);
        
        this.film = s.film;
        this.sala = s.sala;
        this.data_ora = s.data_ora;
    }

    public int getId() {
        return id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Calendar getData_ora() {
        return data_ora;
    }

    public void setData_ora(Calendar data_ora) {
        this.data_ora = data_ora;
    }
    
    
    
    
}
