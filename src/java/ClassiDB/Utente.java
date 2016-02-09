/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassiDB;

import Database.DBManager;
import java.sql.SQLException;

/**
 * Classe che rappresenta un utente del sito. 
 * @author Ivan, Paolo
 */
public class Utente {
    protected int id;
    protected String name;
    protected String email;
    protected String ruolo;
    protected double credito;

    /**
     * Costruttore vuoto per settare i dati con i set
     */
    public Utente() {
        id=-1;
        name="";
        email="";
        ruolo="";
        credito=0.0;
    }
    
    /**
     * Costruttore che crea l'utente in base all'id dato
     * @param id ID utente
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public Utente(int id) throws SQLException, ClassNotFoundException{
        this.id = id;
        
        DBManager dbm = DBManager.getDBManager();
        Utente u = dbm.getUtente(id);
        
        this.name = u.name;
        this.email = u.email;
        this.ruolo = u.ruolo;
        this.credito = u.credito;
        //this.totalePagato = dbm.totalePagato(id);
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }

    public String getNome() {
        return name;
    }

    public void setNome(String nome) {
        this.name = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public double getCredito() {
        try{
            DBManager dbm = DBManager.getDBManager();
            return dbm.getCreditoUtente(id);
        }
        catch(Exception ex)
        {
            return 0;
        }
    }

    public void setCredito(double credito) {
        this.credito = credito;
    }
    
    public double getCreditoLocale()
    {
        return credito;
    }

    public double getTotalePagato() {
        try{
            DBManager dbm = DBManager.getDBManager();
            return dbm.totalePagato(id);
        }
        catch(Exception ex)
        {
            return 0;
        }
    }    
}
