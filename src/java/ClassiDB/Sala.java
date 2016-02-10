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
    protected int n_righe;
    protected int n_colonne;
    protected List<Posto> posti_occupati;
    protected List<Posto> posti_inesistenti;
    //protected Posto[][] mappa;

    /**
     *Metodo statico per ottenere una sala dall'id dello spettacolo.
     * @return sala in cui si tiene lo spettacolo con quell'id
     */
    static public Sala getSalaBySpett(int id_spett) throws SQLException, ClassNotFoundException
    {
        DBManager dbm = DBManager.getDBManager();
        int id_sala = dbm.getIDSalaBySpett(id_spett);
        Sala s = dbm.getSala(id_sala);
        s.setPosti_occupati(dbm.getPostiOccupati(id_spett));
        return s;
    }
    /**
     * Costruttore vuoto per settare i dati con i set
     */
    public Sala() {
        
    }

    /**
     * Inizializza la sala in base all'ID sala
     * 
     * @param id_sala ID della sala
     * @throws SQLException In caso di errore SQL
     * @throws ClassNotFoundException In caso di mancata libreria per accedere al DB
     */
    public Sala(int id_sala) throws SQLException, ClassNotFoundException, Exception{
        
        DBManager dbm = DBManager.getDBManager();
        Sala s = dbm.getSala(id_sala);
        
        
        if(s==null)
            throw new Exception("Spettacolo inesistente");
        
        this.id = s.id;
        this.nome = s.nome;
        this.n_righe = s.getNumeroRighe();
        this.n_colonne = s.getNumeroColonne();
        this.posti_inesistenti = s.getPosti_inesistenti();
        //this.posti_occupati = dbm.getPostiOccupati(id_spettacolo);
        //this.mappa = s.mappa;
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
/**
     * Ritorna il numero di righe della sala 
     * @return numero righe della sala 
     */
    public int getNumeroRighe() {
        return n_righe;
    }

    public void setNumeroRighe(int n_righe) {
        this.n_righe = n_righe;
    }
/**
     * Ritorna il numero di colonne della sala 
     * @return numero colonne della sala 
     */
    public int getNumeroColonne() {
        return n_colonne;
    }

    public void setNumeroColonne(int n_colonne) {
        this.n_colonne = n_colonne;
    }

    public List<Posto> getPosti_occupati() {
        return posti_occupati;
    }

    public void setPosti_occupati(List<Posto> posti_occupati) {
        this.posti_occupati = posti_occupati;
    }

    public List<Posto> getPosti_inesistenti() {
        return posti_inesistenti;
    }

    public void setPosti_inesistenti(List<Posto> posti_inesistenti) {
        this.posti_inesistenti = posti_inesistenti;
    }
    
    
    
    // Cambiare, restituire una coppia (sicurezza)
    /**
     * Ritorna una matrice della mappa, composto da oggetti Posto, dove ognuno corrisponde al posto (i,j)
     * @return Posto[][] mappa, i posti della sala
     */
    /*public Posto[][] getMappa()
    {
        return mappa;
    }*/

    /**
     * Cambia la mappa dell'oggetto
     * @param mappa La nuova mappa dell'oggetto
     */
    /*public void setMappa(Posto[][] mappa) {
        this.mappa = mappa;
    }*/
     
    // Trasformare la mappa in matrice di stringhe
    /**
     * Funzione che ritorna una matrice di stringhe (più caratteri che stringhe, ma dettagli), per motivi di comodità
     * L'elemento (i,j) corrisponde al posto (i,j), e il carattere contenuto nella cella corrisponde a:
     * N --> non esiste
     * O --> occupato
     * L --> libero
     * @return String[][] relativa alla mappa
     */
    
    /*public String[][] getStringMappa()
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
    }*/
    
    /**
     * Ritorna una stringa contenente i posti occupati
     * @return stringa dei posti occupati
     */
    public String[] getVettorePostiOccupati(){
        ArrayList<String> tmp= new ArrayList<String>();
        
        for(Posto p : posti_occupati)
        {
            tmp.add(p.getRiga() + "_"+ p.getColonna());
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
        String[] str_vettore_sala = new String[n_righe];
        String tmp = "";
        int pos = 0;
        int indice = 0;
        for(int i=0; i<n_righe; i++)
        {
            for(int j=0; j<n_colonne; j++)
            {
                if(indice < posti_inesistenti.size() && posti_inesistenti.get(indice).getRiga() == i && posti_inesistenti.get(indice).getColonna() == j)
                {
                    tmp += "_";
                    indice++;
                }
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
    /*public String toString()
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
    }*/
    
    
    
}
