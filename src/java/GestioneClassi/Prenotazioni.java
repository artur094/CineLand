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
 *
 * @author ivanmorandi
 */
public class Prenotazioni {
    ArrayList<Prenotazione> listaPrenotazioni;
    
    protected void Prenotazioni()
    {
        listaPrenotazioni = new ArrayList<>();
    }
    
    public Prenotazioni getPrenotazioniUtente(int id_utente) throws SQLException, ClassNotFoundException
    {
        DBManager dbm = DBManager.getDBManager();
        Prenotazioni pr = new Prenotazioni();
        pr.setListaPrenotazioni(dbm.getPrenotazioniUtente(id_utente));
        return pr;
    }
    
    public Prenotazioni getPrenotazioniRisarcibili()
    {
        return null;
    }
    
    public Prenotazioni getPrenotazioniDaPagare(int id_utente) throws SQLException, ClassNotFoundException
    {
        Prenotazioni pr = getPrenotazioniUtente(id_utente);
        ArrayList<Prenotazione> lista = pr.getListaPrenotazioni();
        for (int i = 0; i < lista.size(); i++) {
            if(lista.get(i).isPagato())
            {
                lista.remove(i);
            }
        }
        pr.setListaPrenotazioni(lista);
        return pr;
    }

    public ArrayList<Prenotazione> getListaPrenotazioni() {
        return listaPrenotazioni;
    }

    public void setListaPrenotazioni(ArrayList<Prenotazione> listaPrenotazioni) {
        this.listaPrenotazioni = listaPrenotazioni;
    }
}
