/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassiDB;

import Database.DBManager;
import java.sql.SQLException;

/**
 *
 * @author Ivan
 */
public class Posto {
    protected int id;
    protected int id_sala;
    protected int riga;
    protected int colonna;
    protected boolean esiste;
    protected boolean occupato;
    protected double prezzoPagato = 0; //utile solo per amministrazione
    
    public static Posto getPostoFromPrenotazione(int id_prenotazione) throws SQLException, ClassNotFoundException
    {
        DBManager dbm = DBManager.getDBManager();
        Posto p = dbm.getPostoFromPrenotazione(id_prenotazione);
        return p;        
    }

    public Posto() {
        
    }

    public Posto(int id) throws SQLException, ClassNotFoundException{
        DBManager dbm = DBManager.getDBManager();
        Posto p = dbm.getPosto(id);
        
        this.id_sala = p.id_sala;
        this.riga = p.riga;
        this.colonna = p.colonna;
        this.esiste = p.esiste;
        this.occupato = false;
    }
    
    public Posto(int id, int id_sala, int riga, int colonna, boolean esiste) {
        this.id = id;
        this.id_sala = id_sala;
        this.riga = riga;
        this.colonna = colonna;
        this.esiste = esiste;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getId_sala() {
        return id_sala;
    }

    public void setId_sala(int id_sala) {
        this.id_sala = id_sala;
    }

    public int getRiga() {
        return riga;
    }

    public void setRiga(int riga) {
        this.riga = riga;
    }

    public int getColonna() {
        return colonna;
    }

    public void setColonna(int colonna) {
        this.colonna = colonna;
    }

    public boolean isEsiste() {
        return esiste;
    }

    public void setEsiste(boolean esiste) {
        this.esiste = esiste;
    }

    public boolean isOccupato() {
        return occupato;
    }

    public void setOccupato(boolean occupato) {
        this.occupato = occupato;
    }

    public double getPrezzoPagato() {
        return prezzoPagato;
    }

    public void setPrezzoPagato(double prezzoPagato) {
        this.prezzoPagato = prezzoPagato;
    }
    
    
}
