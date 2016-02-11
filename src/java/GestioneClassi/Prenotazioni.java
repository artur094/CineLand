/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestioneClassi;

import ClassiDB.Prenotazione;
import Database.DBManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe con funzioni statiche per gestire più prenotazioni
 * @author ivanmorandi
 */
public class Prenotazioni {
    ArrayList<Prenotazione> listaPrenotazioni;
    
    /**
     * Costruttore
     */
    protected void Prenotazioni()
    {
        listaPrenotazioni = new ArrayList<>();
    }
    
    /**
     * Funzione per recuperare le prenotazioni dell'utente
     * @param id_utente ID utente
     * @return Oggetto prenotazioni che contiene tutte le prenotazioni fatte dall'utente
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static Prenotazioni getPrenotazioniUtente(int id_utente) throws SQLException, ClassNotFoundException
    {
        DBManager dbm = DBManager.getDBManager();
        Prenotazioni pr = new Prenotazioni();
        pr.setListaPrenotazioni(dbm.getPrenotazioniUtente(id_utente));
        return pr;
    }
    
    /**
     * Funzione per recuperare le prenotazioni risarcibili dall'utente, ovvero pagate
     * @param id_utente ID utente
     * @return Prenotazioni con la lista di prenotazioni risarcibili
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static Prenotazioni getPrenotazioniRisarcibili(int id_utente) throws SQLException, ClassNotFoundException
    {
        DBManager dbm = DBManager.getDBManager();
        Prenotazioni pr = new Prenotazioni();
        pr.setListaPrenotazioni(dbm.getPrenotazioniUtenteRisarcibili(id_utente));
        return pr;
    }
    
    /**
     * Funzione per prendere le prenotazioni da pagare
     * @param id_utente ID utente
     * @return Prenotazioni che contiene la lista delle prenotazioni non pagate
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static Prenotazioni getPrenotazioniDaPagare(int id_utente) throws SQLException, ClassNotFoundException
    {
        DBManager dbm = DBManager.getDBManager();
        Prenotazioni pr = new Prenotazioni();
        pr.setListaPrenotazioni(dbm.getPrenotazioniUtenteDaPagare(id_utente));
        return pr;
    }

    /**
     *
     * @return
     */
    public ArrayList<Prenotazione> getListaPrenotazioni() {
        return listaPrenotazioni;
    }

    /**
     *
     * @param listaPrenotazioni
     */
    public void setListaPrenotazioni(ArrayList<Prenotazione> listaPrenotazioni) {
        this.listaPrenotazioni = listaPrenotazioni;
    }
}
