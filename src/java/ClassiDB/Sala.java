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

    public Sala(int id) throws SQLException, ClassNotFoundException{
        this.id = id;
        
        DBManager dbm = DBManager.getDBManager();
        Sala s = dbm.getSala(id);
        
        this.nome = s.nome;
        this.mappa = s.mappa;
    }

    public int getId() {
        return id;
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
    
    // Trasformare la mappa in matrice di stringhe
    public String[][] getStringMappa()
    {
        return null;
    }
    
    // Trasformare la mappa in una stringa
    public String toString()
    {
        return null;
    }
    
    
    
}
