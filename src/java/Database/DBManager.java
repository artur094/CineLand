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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

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
    protected String URL_DB = "jdbc:derby://localhost:1527/cineland";
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
    
    // FUNZIONI CHE RECUPERANO UN INSIEME DI CLASSIDB
    
    // CLASSE SPETTACOLI
    
    /**
     * Funzione che ritorna la lista di spettacoli futuri che hanno come film quello passato in input
     * @param id_film ID del film di cui vogliamo trovare i spettacoli
     * @return
     * @throws SQLException 
     */
    public ArrayList<Spettacolo> getSpettacoliFuturiFromFilm(int id_film) throws SQLException
    {
        ArrayList<Spettacolo> lista = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        int id;
        
        PreparedStatement ps = con.prepareStatement("SELECT id_spettacolo FROM spettacolo WHERE data_ora >= ? AND id_film = ?");
        ps.setTimestamp(1, new Timestamp(c.getTime().getTime()));
        ps.setInt(2, id_film);
        
        ResultSet rs = ps.executeQuery();
        
        while(rs.next())
        {
            id = rs.getInt("id_spettacolo");
            lista.add(getSpettacolo(id));
        }
        
        return lista;
    }
    
    
    /**
     * Funzione che ritorna una lista di tutti gli spettacoli, passati e futuri
     * @return
     * @throws SQLException 
     */
    public ArrayList<Spettacolo> getAllSpettacoli() throws SQLException
    {
        ArrayList<Spettacolo> lista = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        int id;
        
        PreparedStatement ps = con.prepareStatement("SELECT id_spettacolo FROM spettacolo");
        
        ResultSet rs = ps.executeQuery();
        
        while(rs.next())
        {
            id = rs.getInt("id_spettacolo");
            lista.add(getSpettacolo(id));
        }
        
        return lista;
    }
    
    
    
    
    /**
     * Funzione che ritorna la lista di tutti i spettacoli futuri
     * @return
     * @throws SQLException 
     */
    public ArrayList<Spettacolo> getSpettacoliFuturi() throws SQLException
    {
        ArrayList<Spettacolo> lista = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        int id;
        
        PreparedStatement ps = con.prepareStatement("SELECT id_spettacolo FROM spettacolo WHERE data_ora >= ?");
        ps.setTimestamp(1, new Timestamp(c.getTime().getTime()));
        
        ResultSet rs = ps.executeQuery();
        
        while(rs.next())
        {
            id = rs.getInt("id_spettacolo");
            lista.add(getSpettacolo(id));
        }
        
        return lista;
    }
    
    
    
    // CLASSE FILMS
    
    /**
     * Ritorna l'insieme di film che hanno uno spettacolo programmato ancora da vedere
     * @return
     * @throws SQLException 
     */
    public ArrayList<Film> getFilmFuturi() throws SQLException
    {
        ArrayList<Film> list = new ArrayList<>();
        int id; 
        PreparedStatement ps = con.prepareStatement("SELECT id_film FROM spettacolo WHERE data_ora >= ? GROUP BY id_film");
        
        Calendar c = Calendar.getInstance();
        ps.setTimestamp(1, new Timestamp(c.getTime().getTime()));
        
        ResultSet rs = ps.executeQuery();
        
        while(rs.next())
        {
            id = rs.getInt("id_film");
            list.add(getFilm(id));
        }
        
        return list;
    }
    
    /**
     * Funzione che ritorna tutti i film futuri e passati di Cineland
     * @return
     * @throws SQLException 
     */
    public ArrayList<Film> getAllFilms() throws SQLException
    {
        ArrayList<Film> list = new ArrayList<>();
        int id; 
        
        PreparedStatement ps = con.prepareStatement("SELECT id_film FROM film GROUP BY id_film");
        
        ResultSet rs = ps.executeQuery();
        
        while(rs.next())
        {
            id = rs.getInt("id_film");
            list.add(getFilm(id));
        }
        
        return list;
    }
    
    
    
    
    
    // FUNZIONI PER REPERIRE LE CLASSIDB ATTRAVERSO ID (o altre cose)
    
    // Prendere dal DB il film che ha tale l'id dato in input
    // Associare direttamente al film anche il genere (in stringa)
    public Film getFilm(int id) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT f.titolo, g.descrizione, u.url_trailer, u.durata,u.trama, u.url_locandina, u.attori, u.regista, u.frase FROM film as f, genere as g WHERE f.id_film = ? and f.id_genere = g.id_genere");
        ps.setInt(1, id);
        
        ResultSet rs = ps.executeQuery();
        
        if(rs.next())
        {
            Film f = new Film();
            f.setAttori(rs.getString("attori"));
            f.setDurata(rs.getInt("durata"));
            f.setFrase(rs.getString("frase"));
            f.setGenere(rs.getString("descrizione"));
            f.setId(id);
            f.setRegista(rs.getString("regista"));
            f.setTitolo(rs.getString("titolo"));
            f.setTrama(rs.getString("trama"));
            f.setUrl_locandina(rs.getString("url_locandina"));
            f.setUrl_trailer(rs.getString("url_trailer"));
            
            return f;
        }
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
    public Spettacolo getSpettacolo(int id) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM spettacolo WHERE id_spettacolo = ?");
        ps.setInt(1, id);
        
        ResultSet rs = ps.executeQuery();
        if(rs.next())
        {
            Spettacolo s = new Spettacolo();
            
            Timestamp ts = rs.getTimestamp("data_ora");
            Calendar c = Calendar.getInstance();
            c.setTime(ts);
            s.setData_ora(c);
            
            Film f = getFilm(rs.getInt("id_film"));
            s.setFilm(f);
            
            Sala sala = getSala(rs.getInt("id_sala"));
            s.setSala(sala);
            
            return s;
        }
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
