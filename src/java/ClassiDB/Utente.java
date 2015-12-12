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
public class Utente {
    protected int id;
    protected String name;
    protected String email;
    protected String ruolo;
    protected double credito;
    protected double totalePagato;

    public Utente() {
    }
    
    public Utente(int id) throws SQLException, ClassNotFoundException{
        this.id = id;
        
        DBManager dbm = DBManager.getDBManager();
        Utente u = dbm.getUtente(id);
        
        this.name = u.name;
        this.email = u.email;
        this.ruolo = u.ruolo;
        this.credito = u.credito;
        this.totalePagato = dbm.totalePagato(id);
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
        return credito;
    }

    public void setCredito(double credito) {
        this.credito = credito;
    }

    public double getTotalePagato() {
        return totalePagato;
    }
    
    
    
    
}
