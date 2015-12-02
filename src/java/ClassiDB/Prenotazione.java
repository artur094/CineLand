/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassiDB;

import Database.DBManager;
import java.sql.SQLException;
import java.util.Calendar;

/**
 *
 * @author Ivan
 */
public class Prenotazione {
    protected int id;
    protected Utente utente;
    protected Spettacolo spettacolo;
    protected Posto posto;
    protected Sala sala;
    protected double prezzo;
    protected Calendar data_ora_operazione;

    public Prenotazione() {
    }

    public Prenotazione(int id) throws SQLException, ClassNotFoundException{
        this.id = id;
        
        DBManager dbm = DBManager.getDBManager();
        Prenotazione p = dbm.getPrenotazione(id);
        
        this.utente = p.utente;
        this.spettacolo = p.spettacolo;
        this.posto = p.posto;
        this.sala = p.sala;
        this.prezzo = p.prezzo;
        this.data_ora_operazione = p.data_ora_operazione;
    }

    public int getId() {
        return id;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Spettacolo getSpettacolo() {
        return spettacolo;
    }

    public void setSpettacolo(Spettacolo spettacolo) {
        this.spettacolo = spettacolo;
    }

    public Posto getPosto() {
        return posto;
    }

    public void setPosto(Posto posto) {
        this.posto = posto;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public Calendar getData_ora_operazione() {
        return data_ora_operazione;
    }

    public void setData_ora_operazione(Calendar data_ora_operazione) {
        this.data_ora_operazione = data_ora_operazione;
    }
    
    
    
    
    
    
}
