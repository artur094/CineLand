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
public class Sala {
    protected int id;
    protected String nome;
    protected Posto[][] mappa;

    public Sala() {
    }

    public Sala(int id_spettacolo) throws SQLException, ClassNotFoundException{
        
        DBManager dbm = DBManager.getDBManager();
        Sala s = dbm.getSala(id_spettacolo);
        
        this.id = s.id;
        this.nome = s.nome;
        this.mappa = s.mappa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    // Cambiare, restituire una coppia (sicurezza)
    public Posto[][] getMappa()
    {
        return mappa;
    }

    public void setMappa(Posto[][] mappa) {
        this.mappa = mappa;
    }
     
    // Trasformare la mappa in matrice di stringhe
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
    
    // Trasformare la mappa in una stringa
    public String toString()
    {
        return null;
    }
    
    
    
}
