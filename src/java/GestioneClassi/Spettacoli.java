/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestioneClassi;

import ClassiDB.Spettacolo;
import Database.DBManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe con funzioni statiche per gestire più spettacoli
 * @author ivanmorandi
 */
public class Spettacoli {
    ArrayList<Spettacolo> listaSpettacoli;
    /**
     * Costruttore
     */
    protected Spettacoli()
    {
        listaSpettacoli = new ArrayList<>();
    }
    
    /**
     * Funzione per il recupero di tutti gli spettacoli avvenuti e futuri
     * @return Spettacoli con la lista di tutte le prenotazioni
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static Spettacoli getAllSpettacoli() throws SQLException, ClassNotFoundException
    {
        Spettacoli s = new Spettacoli();
        DBManager dbm = DBManager.getDBManager();
        
        s.setListaSpettacoli(dbm.getAllSpettacoli());
        
        return s;
    }
    
    /**
     * Funzione per il recupero degli spettacoli futuri
     * @return Spettacoli con la lista di spettacoli che non sono ancora stati presentati
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static Spettacoli getSpettacoliFuturi() throws SQLException, ClassNotFoundException
    {
        Spettacoli s = new Spettacoli();
        DBManager dbm = DBManager.getDBManager();
        
        s.setListaSpettacoli(dbm.getSpettacoliFuturi());
        
        return s;
    }
    
    /**
     * Funzione per il recupero di spettacoli che hanno avuto un film preciso
     * @param id_film ID film
     * @return Spettacoli con la lista dei spettacoli futuri che hanno il film dato in input
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static Spettacoli getSpettacoliFuturiFromFilm(int id_film) throws SQLException, ClassNotFoundException
    {
        Spettacoli s = new Spettacoli();
        DBManager dbm = DBManager.getDBManager();
        
        s.setListaSpettacoli(dbm.getSpettacoliFuturiFromFilm(id_film));
        
        return s;
    }

    /**
     *
     * @return
     */
    public ArrayList<Spettacolo> getListaSpettacoli() {
        return listaSpettacoli;
    }

    /**
     *
     * @param listaSpettacoli
     */
    public void setListaSpettacoli(ArrayList<Spettacolo> listaSpettacoli) {
        this.listaSpettacoli = listaSpettacoli;
    }
}
