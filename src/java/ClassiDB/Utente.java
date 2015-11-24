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
    protected String nome;
    protected String email;
    protected String ruolo;
    protected int credito;

    public Utente(int id) throws SQLException, ClassNotFoundException{
        this.id = id;
        
        DBManager dbm = DBManager.getDBManager();
        Utente u = dbm.getUtente(id);
        
        this.nome = u.nome;
        this.email = u.email;
        this.ruolo = u.ruolo;
        this.credito = u.credito;
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

    public int getCredito() {
        return credito;
    }

    public void setCredito(int credito) {
        this.credito = credito;
    }
    
    
    
    
}
