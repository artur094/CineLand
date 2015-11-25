/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestioneClassi;

import ClassiDB.Spettacolo;
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
    public Spettacoli getSpettacoliDiOggi()
    {
        return null;
    }
    
    // Ritorna tutti i spettacoli programmati 
    public Spettacoli getSpettacoliOggiFuturi()
    {
        return null;
    }
    
    // Ritorna tutti i spettacoli programmati (ancora da vedere) che hanno il film
    public Spettacoli getSpettacoliFromFilm(int id_film)
    {
        return null;
    }
}
