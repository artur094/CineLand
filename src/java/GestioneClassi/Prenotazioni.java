/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestioneClassi;

import ClassiDB.Prenotazione;
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
    
    public ArrayList<Prenotazione> getPrenotazioniUtente()
    {
        return null;
    }
    
    public ArrayList<Prenotazione> getPrenotazioniRisarcibili()
    {
        return null;
    }
    
    public ArrayList<Prenotazione> getPrenotazioniDaPagare()
    {
        return null;
    }
}
