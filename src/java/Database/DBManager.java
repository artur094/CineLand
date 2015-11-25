/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import ClassiDB.Film;
import ClassiDB.Posto;
import ClassiDB.Prenotazione;
import ClassiDB.Sala;
import ClassiDB.Spettacolo;
import ClassiDB.Utente;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Ivan
 * 
 * Database: CineLand
 * Username: cineland
 * Password: cineland
 */
public class DBManager implements Serializable {
    // Transient: non serializza con
    private transient Connection con;
    // Stringa di collegamento al DB
    protected String URL_DB = "jdbc:derby//localhost:1527/CineLand";
    // Implemento Singleton
    protected static DBManager dbm = null;
    
    public static DBManager getDBManager() throws SQLException, ClassNotFoundException
    {
        if(dbm == null)
        {
            dbm = new DBManager();
        }
        return dbm;
    }
    
    protected DBManager() throws SQLException, ClassNotFoundException
    {
        Class.forName("org.apache.derby.jdbc.ClientDriver", true, getClass().getClassLoader());
        Connection con =  DriverManager.getConnection(URL_DB, "cineland", "cineland");
        this.con = con;
    }
    
    // VARIE FUNZIONI DI LOGIN - PAGAMENTI ...
    
    /**
     * Funzione che si occupa di controllare se esiste l'account email-password
     * Nel caso affermativo, ritornare la classe corrispondente (prendi l'ID e ritorni getUtente(id)
     * Nel caso contrario (o problemi), null
     * 
     * @param email Email dell'utente inserita nella form
     * @param password Password dell'utente inserita nella form
     * @return 
     */
    public Utente logIn(String email, String password) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT U.id_utente FROM Utente AS U WHERE U.email = ? AND U.password = ?");
        ps.setString(1, email);
        ps.setString(2, password);
        
        
        ResultSet rs = ps.executeQuery();
        
        if(!rs.next())
            return null;
        
        int id = rs.getInt("id_utente");
        
        return getUtente(id);
    }
    
    /**
     * Funzione che inserisce nel DB la tupla dell'utente, impostando credito = 0, e ruolo di utente
     * Prima di inserire, serve un controllo sull'esistenza della email
     * In caso di problemi (o account già esistente) ritorniamo null
     * Altrimenti ritorniamo l'utente
     * @param email Email inserita dall'utente nel momento della registrazione
     * @param password Password HASHATA inserita dall'utente
     * @param nome Nome dell'utente
     * @return 
     */
    public Utente registrazione(String email, String password, String nome)
    {
        return null;
    }
    
    /**
     * Funzione che abilita l'account se il codice dato in input è corretto
     * Per abilitare l'account, impostare id_ruolo a quello di user
     * se corretto, torna TRUE, altriment FALSE (da vedere)
     * @param email Email usata per l'account
     * @param code Codice ricevuto via email
     * @return 
     */
    public boolean enableAccount(String email, double code)
    {
        return false;
    }
    
    
    
    
    
    // FUNZIONI PER REPERIRE LE CLASSIDB ATTRAVERSO ID (o altre cose)
    
    // Prendere dal DB il film che ha tale l'id dato in input
    // Associare direttamente al film anche il genere (in stringa)
    public Film getFilm(int id)
    {
        return null;
    }
    
    // Prendere dal DB il posto
    public Posto getPosto(int id)
    {
        return null;
    }
    
    // Prendere dal DB il nome sala
    // Costruire la mappa della sala con tale ID
    public Sala getSala(int id)
    {
        return null;
    }
    
    // Prendere lo spettacolo dal DB
    // Prendere il film a cui si riferisce lo spettacolo
    // Prendere la sala a cui si riferisce lo spettacolo
    // Assegnare film e sala a spettacolo
    public Spettacolo getSpettacolo(int id)
    {
        return null;
    }
    
    // Prendere l'utente con tale ID
    // Prendere il ruolo in stringa e assegnarlo all'utente
    // ID, Nome, Email, Ruolo, Credito
    public Utente getUtente(int id) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT U.email, U.name, U.credito, R.ruolo FROM Utente AS U, Ruolo AS R WHERE R.id_ruolo = U.id_ruolo AND U.id_utente = ?");
        ps.setInt(1, id);
        
        ResultSet rs = ps.executeQuery();
        
        if(!rs.next())
            return null;
        
        Utente u = new Utente();
        u.setId(id);
        u.setEmail(rs.getString("email"));
        u.setNome(rs.getString("name"));
        u.setRuolo(rs.getString("ruolo"));
        u.setCredito(rs.getDouble("credito"));
        
        return u;
    }
    
    // Prendere l'utente con tale email
    // Usata per vedere se esiste un utente con tale email
    public Utente getUtente(String email) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT U.id_utente FROM Utente AS U WHERE U.email = ?");
        ps.setString(1, email);
        
        ResultSet rs = ps.executeQuery();
        
        if(!rs.next())
            return null;
        
        int id = rs.getInt("id_utente");
        
        return getUtente(id);
    }
    
    // Prendere la prenotazione
    // Prendere l'utente della prenotazione
    // Anche il posto e la sala corrispondente
    // Anche lo spettacolo
    // Il prezzo e l'ora
    public Prenotazione getPrenotazione(int id)
    {
        return null;
    }
    
}
