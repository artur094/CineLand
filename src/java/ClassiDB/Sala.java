/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassiDB;

import Database.DBManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ivan
 */
public class Sala {
    protected int id;
    protected String nome;
    protected Posto[][] mappa;

    
    
    /**
     * Costruttore vuoto per settare i dati con i set
     */
    public Sala() {
        
    }

    /**
     * Inizializza la sala in base allo spettacolo, ovvero vengono prese le informazioni della sala,
     * come nome, id, e la mappa della sala, con i posti prenotati (e non) dello spettacolo relativo
     * 
     * @param id_spettacolo ID dello spettacolo
     * @throws SQLException In caso di errore SQL
     * @throws ClassNotFoundException In caso di mancata libreria per accedere al DB
     */
    public Sala(int id_spettacolo) throws SQLException, ClassNotFoundException, Exception{
        
        DBManager dbm = DBManager.getDBManager();
        Sala s = dbm.getSala(id_spettacolo);
        
        if(s==null)
            throw new Exception("Spettacolo inesistente");
        
        this.id = s.id;
        this.nome = s.nome;
        this.mappa = s.mappa;
    }

    /**
     * Ritorna l'id della sala
     * @return ID sala
     */
    public int getId() {
        return id;
    }

    /**
     * Cambia ID della sala (solo dell'oggetto)
     * @param id Nuovo id della sala
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Ritorna il nome della sala
     * @return Nome sala
     */
    public String getNome() {
        return nome;
    }

    /**
     * Cambia il nome alla sala (solo dell'oggetto)
     * @param nome 
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    // Cambiare, restituire una coppia (sicurezza)
    /**
     * Ritorna una matrice della mappa, composto da oggetti Posto, dove ognuno corrisponde al posto (i,j)
     * @return Posto[][] mappa, i posti della sala
     */
    public Posto[][] getMappa()
    {
        return mappa;
    }

    /**
     * Cambia la mappa dell'oggetto
     * @param mappa La nuova mappa dell'oggetto
     */
    public void setMappa(Posto[][] mappa) {
        this.mappa = mappa;
    }
     
    // Trasformare la mappa in matrice di stringhe
    /**
     * Funzione che ritorna una matrice di stringhe (più caratteri che stringhe, ma dettagli), per motivi di comodità
     * L'elemento (i,j) corrisponde al posto (i,j), e il carattere contenuto nella cella corrisponde a:
     * N --> non esiste
     * O --> occupato
     * L --> libero
     * @return String[][] relativa alla mappa
     */
    public String[][] getStringMappa()
    {
        String nonEsiste = "N";
        String occupato = "O";
        String libero = "L";
        String[][] str_map = new String[mappa.length][mappa[0].length];
        for(int i=0; i<mappa.length; i++)
        {
            for(int j=0; j<mappa[i].length; j++)
            {
                if(!mappa[i][j].isEsiste())
                    str_map[i][j] = nonEsiste;
                else if(mappa[i][j].isOccupato())
                {
                    str_map[i][j] = occupato;
                }
                else
                    str_map[i][j] = libero;
            }
        }
        return str_map;
    }
    
    /**
     * Ritorna una stringa contenente i posti occupati
     * @return stringa dei posti occupati
     */
    public String[] getVettorePostiOccupati(){
        ArrayList<String> tmp= new ArrayList<String>();
        
        for(int i=0; i<mappa.length; i++)
        {
            for(int j=0; j<mappa[i].length; j++)
            {
                if(mappa[i][j].isOccupato())
                  tmp.add(i + "_" + j);
                  
            }
        }
      String[] array = new String[tmp.size()];
      int insertion = 0;
      for(String s : tmp){
          array[insertion] = s;
          insertion++;
      }
      return array;   
    }
    
    /**
     * Ritorna un vettore contenente una mappa dei posti della sala
     * NON include i posti occupati.
     * @return String che contiene la mappa dei posti.
     */  
        public String[] getVettorePostiSala(){
        String[] str_vettore_sala = new String[mappa.length];
        String tmp = "";
        int pos = 0;
        for(int i=0; i<mappa.length; i++)
        {
            for(int j=0; j<mappa[i].length; j++)
            {
                if(!mappa[i][j].isEsiste())
                    tmp += "_";
                else
                {
                    tmp += "A";
                }
            }
            str_vettore_sala[pos] = tmp;
            tmp = "";
            pos++;
        }
        return str_vettore_sala;
    }
    
    // Trasformare la mappa in una stringa
    /**
     * Funzione che come la getStringMappa, è usata per comodità, e invece di esser una matrice di stringhe, è una sola stringa
     * Dove ogni carattere indica un posto, e le righe sono identificate dagli a capo (\n)
     * Stessa notazione di prima:
     * N --> non esiste
     * O --> occupato
     * L --> libero
     * @return String che contiene la mappa dei posti
     */
    public String toString()
    {
        String nonEsiste = "N";
        String occupato = "O";
        String libero = "L";
        String str_map = "";
        for(int i=0; i<mappa.length; i++)
        {
            for(int j=0; j<mappa[i].length; j++)
            {
                if(!mappa[i][j].isEsiste())
                    str_map += nonEsiste;
                else if(mappa[i][j].isOccupato())
                {
                    str_map += occupato;
                }
                else
                    str_map += libero;
            }
            str_map+="\n";
        }
        str_map = str_map.substring(0, str_map.length()-1);
        return str_map;
    }
    
    
    
}
