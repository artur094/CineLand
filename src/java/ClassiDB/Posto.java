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
 * @author Ivan, Paolo
 */
public class Posto {
    protected int id;
    protected int id_sala;
    protected int riga;
    protected int colonna;
    protected boolean esiste;
    protected boolean occupato;
    protected double prezzoPagato = 0; //utile solo per amministrazione
    
    /**
     * Funzione che ritorna il posto dal database, utilizzando ID prenotazione
     * @param id_prenotazione ID di prenotazione per il recupero del posto
     * @return Posto prenotato
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static Posto getPostoFromPrenotazione(int id_prenotazione) throws SQLException, ClassNotFoundException
    {
        DBManager dbm = DBManager.getDBManager();
        Posto p = dbm.getPostoFromPrenotazione(id_prenotazione);
        return p;        
    }

    /**
     * Costruttore vuoto in caso si voglia settare l'oggetto con i set
     */
    public Posto() {
        
    }

    /**
     * Costruttore che costruisce l'oggetto in base all'ID del posto
     * @param id ID del posto
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public Posto(int id) throws SQLException, ClassNotFoundException{
        DBManager dbm = DBManager.getDBManager();
        Posto p = dbm.getPosto(id);
        
        this.id_sala = p.id_sala;
        this.riga = p.riga;
        this.colonna = p.colonna;
        this.esiste = p.esiste;
        this.occupato = false;
    }
    
    /**
     * Costruttore che costruisce l'oggetto secondo i dati di input
     * @param id ID posto
     * @param id_sala ID sala
     * @param riga Riga del posto
     * @param colonna Colonna del posto
     * @param esiste Esistenza del posto (ovvero se è rotto o no)
     */
    public Posto(int id, int id_sala, int riga, int colonna, boolean esiste) {
        this.id = id;
        this.id_sala = id_sala;
        this.riga = riga;
        this.colonna = colonna;
        this.esiste = esiste;
    }
    /**
     * Ritorna l'id del posto.
     * @return id del posto
    */
    public int getId() {
        return id;
    }
    
    /**
     * Imposta l'id del posto.
     * @return id del posto
    */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Ritorna l'id della sala in cui è contenuto il posto.
     * @return id della sala
    */
    public int getId_sala() {
        return id_sala;
    }

    /**
     * Imposta l'id della sala.
     * @param id id della sala
    */
    public void setId_sala(int id_sala) {
        this.id_sala = id_sala;
    }

    /**
     * Ritorna la riga del posto
     * @return id del posto
    */
    public int getRiga() {
        return riga;
    }

    /**
     * Imposta la riga del posto
     * @param riga riga del posto
    */
    public void setRiga(int riga) {
        this.riga = riga;
    }

    /**
     * Ritorna la colonna del posto.
     * @return colonna del posto
    */
    public int getColonna() {
        return colonna;
    }

    /**
     * Imposta la colonna del posto.
     * @param colonna nuovo valore della colonna del posto
    */
    public void setColonna(int colonna) {
        this.colonna = colonna;
    }

    /**
     * Ritorna se il posto esiste o meno.
     * @return booleano per l'esistenza del posto
    */
    public boolean isEsiste() {
        return esiste;
    }

    /**
     * Imposta se il posto esiste o meno..
     * @param esiste se il posto esiste o no
    */
    public void setEsiste(boolean esiste) {
        this.esiste = esiste;
    }

    /**
     * Ritorna se il posto è occupato o meno.
     * @return se il posto è occupato
    */
    public boolean isOccupato() {
        return occupato;
    }

    /**
     * Imposta se il posto è occupato.
     * @param occupato se il posto è occuato o no
    */
    public void setOccupato(boolean occupato) {
        this.occupato = occupato;
    }

    /**
     * Ritorna il prezzo in euro per quel posto.
     * @return id del posto
    */
    public double getPrezzoPagato() {
        return prezzoPagato;
    }

    /**
     * Imposta il prezzo del posto.
     * @param prezzoPagato prezzo in euro del posto
    */
    public void setPrezzoPagato(double prezzoPagato) {
        this.prezzoPagato = prezzoPagato;
    }
    
    
}
