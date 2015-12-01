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
 *
 * @author ivanmorandi
 */
public class Spettacoli {
    ArrayList<Spettacolo> listaSpettacoli;
    
    protected Spettacoli()
    {
        listaSpettacoli = new ArrayList<>();
    }
    
    // Ritorna una lista di spettacoli che devono ancora esser mostrati dall'ora di esecuzione
    public Spettacoli getAllSpettacoli() throws SQLException, ClassNotFoundException
    {
                Spettacoli s = new Spettacoli();
        DBManager dbm = DBManager.getDBManager();
        
        s.setListaSpettacoli(dbm.getAllSpettacoli());
        
        return s;
    }
    
    // Ritorna tutti i spettacoli programmati 
    public Spettacoli getSpettacoliFuturi() throws SQLException, ClassNotFoundException
    {
        Spettacoli s = new Spettacoli();
        DBManager dbm = DBManager.getDBManager();
        
        s.setListaSpettacoli(dbm.getSpettacoliFuturi());
        
        return s;
    }
    
    // Ritorna tutti i spettacoli programmati (ancora da vedere) che hanno il film
    public Spettacoli getSpettacoliFuturiFromFilm(int id_film) throws SQLException, ClassNotFoundException
    {
        Spettacoli s = new Spettacoli();
        DBManager dbm = DBManager.getDBManager();
        
        s.setListaSpettacoli(dbm.getSpettacoliFuturiFromFilm(id_film));
        
        return s;
    }

    public ArrayList<Spettacolo> getListaSpettacoli() {
        return listaSpettacoli;
    }

    public void setListaSpettacoli(ArrayList<Spettacolo> listaSpettacoli) {
        this.listaSpettacoli = listaSpettacoli;
    }
}
