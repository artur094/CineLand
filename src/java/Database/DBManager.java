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
import java.util.List;

//TODO : 



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
    
    // VARIE FUNZIONI DI AMMINISTRAZIONE 
    public List<Posto> postiVendutiperSpettacolo() throws SQLException
    {
        List<Posto> lista = new ArrayList<>();
        
        PreparedStatement ps = con.prepareStatement(
                "SELECT "
        );
        
        return lista;
    }
    
    public List<Utente> topClienti() throws SQLException, ClassNotFoundException
    {
        List<Utente> lista = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement(
                "SELECT u.id_utente, SUM(pr.prezzo) as totale "+
                "FROM utente AS u, prenotazione AS p, prezzo AS pr "+ 
                "WHERE u.ID_UTENTE = p.ID_UTENTE AND p.ID_PREZZO = pr.ID_PREZZO "+
                "GROUP BY u.ID_UTENTE "+
                "ORDER BY totale"
        );
        
        ResultSet rs = ps.executeQuery();
        
        while(rs.next())
        {
            Utente u = new Utente(rs.getInt("id_utente"));
            lista.add(u);
        }
                
        return lista;
    }
    
    public List<Film> incassiFilm() throws SQLException
    {
        List<Film> lista = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement(
                "SELECT s.ID_FILM, SUM(pr.prezzo) AS totale "+
                "FROM spettacolo AS s, prenotazione AS p, prezzo AS pr "+
                "WHERE p.id_spettacolo = s.id_spettacolo AND p.id_prezzo = pr.id_prezzo "+
                "GROUP BY s.id_film "+
                "ORDER BY totale"
        );
        
        ResultSet rs = ps.executeQuery();
        
        while(rs.next())
        {
            Film f = getFilm(rs.getInt("id_film"));
            f.setTotaleIncassi(rs.getDouble("totale"));
            
            lista.add(f);
        }
                
        return lista;
    }
            
    
    // VARIE FUNZIONI DI LOGIN  ...
    
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
    public Utente registrazione(String email, String password, String nome) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM utente WHERE email = ?");
        ps.setString(1, email);
        
        ResultSet rs = ps.executeQuery();
        
        if(rs.next())
            return null;
        
        ps = con.prepareStatement("SELECT id_ruolo FROM ruolo WHERE ruolo = 'verificare'");
        rs = ps.executeQuery();
        
        if(!rs.next())
            return null;
        int id_ruolo = rs.getInt("id_ruolo");
        
        ps = con.prepareStatement("INSERT INTO utente(email,password,nome,ruolo) VALUES (?,?,?,?)");
        ps.setString(1, email);
        ps.setString(2, password);
        ps.setString(3, nome);
        ps.setInt(4, id_ruolo);
        
        PreparedStatement selezione = con.prepareStatement("SELECT * FROM utente WHERE email = ? AND password = ?");
        rs = selezione.executeQuery();
        
        return  getUtente(rs.getString("id_utente"));
    }
    /**
     * Funzione che abilita l'account se il codice dato in input è corretto
     * Per abilitare l'account, impostare id_ruolo a quello di user
     * se corretto, torna TRUE, altriment FALSE (da vedere)
     * @param email Email usata per l'account
     * @param code Codice ricevuto via email
     * @return 
     */
    public boolean enableAccount(String email, double code) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM utente WHERE email = ? and codice_attivazione = ?");
        ps.setString(1, email);
        ps.setDouble(2, code);
        ResultSet rs = ps.executeQuery();
        //getDouble ritorna 0 se il valore è null (ovvero nel resut set non c'è nessun utente con quel codice di attivazione)
        if(rs.getDouble("CODICE_ATTIVAZIONE") != 0){
            PreparedStatement confermaUtente = con.prepareStatement("UPDATE utente SET id_ruolo = 1 WHERE email = ? AND codice_attivazione = ?");
            confermaUtente.setString(1, email);
            confermaUtente.setDouble(2, code);
            return true;
        }
        else
            return false;
    }
    
    public double totalePagato(int id_utente) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement(
                "SELECT SUM(pr.prezzo) as totale "+
                "FROM prenotazione AS p, prezzo AS pr "+ 
                "WHERE p.ID_UTENTE = ? AND p.ID_PREZZO = pr.ID_PREZZO "+
                "GROUP BY p.ID_UTENTE "
        );
        ps.setInt(1, id_utente);
        
        ResultSet rs = ps.executeQuery();
        
        if(rs.next())
        {
            return rs.getDouble("totale");
        }
        return 0;
    }
    
    
    // FUNZIONI RIGUARDO PRENOTAZIONI - PAGAMENTI
    
    public Prenotazione insertPrenotazione(int id_utente, int id_spettacolo, int id_posto, String tipo_prezzo) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT id_prezzo FROM prezzo WHERE tipo_prezzo = ?");
        ps.setString(1, tipo_prezzo);
        
        ResultSet rs = ps.executeQuery();
        
        if(!rs.next())
            return null;
        int id_prezzo = rs.getInt("id_prezzo");
        
        ps = con.prepareStatement("INSERT INTO prenotazione (id_utente, id_spettacolo, id_prezzo, id_posto, pagato, data_ora_operazione) values (?,?,?,?,false,current_timestamp)");
        ps.setInt(1, id_utente);
        ps.setInt(2, id_spettacolo);
        ps.setInt(3, id_prezzo);
        ps.setInt(4, id_posto);
        
        ps.executeUpdate();
        
        ps = con.prepareStatement("SELECT id_prenotazione FROM prenotazione WHERE id_utente = ? AND id_spettacolo = ? AND id_posto = ?");
        ps.setInt(1, id_utente);
        ps.setInt(2, id_spettacolo);
        ps.setInt(3, id_posto);
        
        rs = ps.executeQuery();
        if(rs.next())
            return getPrenotazione(rs.getInt("id_prenotazione"));
        return null;
    }
    
    public boolean pagaPrenotazione(int id_utente, int id_spettacolo, int id_posto) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT id_prenotazione FROM prenotazione WHERE id_utente = ? AND id_spettacolo = ? AND id_posto = ?");
        ps.setInt(1, id_utente);
        ps.setInt(2, id_spettacolo);
        ps.setInt(3, id_posto);
        
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return pagaPrenotazione(rs.getInt("id_prenotazione"));
        return false;
    }
    
    public boolean pagaPrenotazione(int id_prenotazione) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("UPDATE prenotazione SET pagato = true WHERE id_prenotazione = ?");
        ps.setInt(1, id_prenotazione);
        int row = ps.executeUpdate();
        if(row < 1)
            return false;
        return true;
    }
    
    // FUNZIONI CHE RECUPERANO UN INSIEME DI CLASSIDB
    
    // CLASSE PRENOTAZIONI
    
    /**
     * Funzione che ritorna la lista delle prenotazioni dell'utente (sia pagate che non)
     * 
     * @param id_utente ID utente 
     * @return
     * @throws SQLException 
     */
    public ArrayList<Prenotazione> getPrenotazioniUtente(int id_utente) throws SQLException
    {
        ArrayList<Prenotazione> lista = new ArrayList<>();
        
        PreparedStatement ps = con.prepareStatement("SELECT id_prenotazione FROM prenotazione WHERE id_utente = ?");
        ps.setInt(1, id_utente);
        
        ResultSet rs = ps.executeQuery();
        
        while(rs.next())
        {
            int id = rs.getInt("id_prenotazione");
            lista.add(getPrenotazione(id));
            
        }
        
        return lista;
    }
    
    public ArrayList<Prenotazione> getPrenotazioniUtenteRisarcibili(int id_utente) throws SQLException
    {
        ArrayList<Prenotazione> lista = new ArrayList<>();
        
        PreparedStatement ps = con.prepareStatement("SELECT id_prenotazione FROM prenotazione WHERE id_utente = ? AND pagato = true");
        ps.setInt(1, id_utente);
        
        ResultSet rs = ps.executeQuery();
        
        while(rs.next())
        {
            int id = rs.getInt("id_prenotazione");
            lista.add(getPrenotazione(id));
            
        }
        
        return lista;
    }
    
    public ArrayList<Prenotazione> getPrenotazioniUtenteDaPagare(int id_utente) throws SQLException
    {
        ArrayList<Prenotazione> lista = new ArrayList<>();
        
        PreparedStatement ps = con.prepareStatement("SELECT id_prenotazione FROM prenotazione WHERE id_utente = ? AND pagato = false");
        ps.setInt(1, id_utente);
        
        ResultSet rs = ps.executeQuery();
        
        while(rs.next())
        {
            int id = rs.getInt("id_prenotazione");
            lista.add(getPrenotazione(id));
            
        }
        
        return lista;
    }
    
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
        
        PreparedStatement ps = con.prepareStatement("SELECT id_spettacolo FROM spettacolo WHERE data_ora >= ? AND id_film = ? ORDER BY data_ora");
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
        PreparedStatement ps = con.prepareStatement("SELECT f.titolo, g.descrizione, f.URL_TRAILER, f.durata,f.trama, f.url_locandina, f.attori, f.regista, f.frase FROM film as f, genere as g WHERE f.id_film = ? and f.id_genere = g.id_genere");
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
    public Posto getPosto(int id) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM posto WHERE id_posto = ?");
        ps.setInt(1, id);
        
        ResultSet rs = ps.executeQuery();
        
        if(rs.next())
        {
            Posto p = new Posto();
            p.setId(id);
            p.setColonna(rs.getInt("colonna"));
            p.setRiga(rs.getInt("riga"));
            p.setEsiste(rs.getBoolean("esiste"));
            p.setId_sala(rs.getInt("id_sala"));
            return p;
        }
        return null;
    }
    
    public Posto getPostoFromPrenotazione(int id_prenotazione) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM prenotazione WHERE id_prenotazione = ?");
        ps.setInt(1, id_prenotazione);
        
        ResultSet rs = ps.executeQuery();
        
        if(rs.next())
        {
            Posto p = getPosto(rs.getInt("id_posto"));
            p.setOccupato(rs.getBoolean("pagato"));
            return p;
        }
        return null;
    }
    
    // Prendere dal DB il nome sala
    // Costruire la mappa della sala con tale ID
    public Sala getSala(int id_spettacolo) throws SQLException
    {
        // PRIMA QUERY: recupero informazioni base della sala
        PreparedStatement ps = con.prepareStatement("SELECT s.id_sala, s.descrizione FROM sala as s, spettacolo AS sp WHERE sp.id_spettacolo = ? AND s.id_sala = sp.id_sala");
        ps.setInt(1, id_spettacolo);
        
        ResultSet rs = ps.executeQuery();
        if(!rs.next())
            return null;
        
        Sala s = new Sala();
        s.setId(rs.getInt("id_sala"));
        s.setNome(rs.getString("descrizione"));
        
        // SECONDA QUERY: recupero numero righe e numero colonne sala
        ps = con.prepareStatement("SELECT MAX(p.riga) AS riga, MAX(p.colonna) AS colonna FROM spettacolo AS c, sala as s, posto AS p WHERE c.id_spettacolo = ? AND c.id_sala = s.id_sala AND s.id_sala = p.id_sala");
        ps.setInt(1, id_spettacolo);
        
        rs = ps.executeQuery();
        
        if(!rs.next())
            return null;
        
        int n_rows = rs.getInt("riga");
        int n_cols = rs.getInt("colonna");
        
        Posto[][] mappa = new Posto[n_rows+1][n_cols+1];
        
        // TERZA QUERY: costruzione mappa
        ps = con.prepareStatement("SELECT id_posto FROM posto WHERE id_sala = ?");
        ps.setInt(1, s.getId());
        
        rs = ps.executeQuery();
        
        while(rs.next())
        {
             int id_posto = rs.getInt("id_posto");
             Posto p = getPosto(id_posto);
             mappa[p.getRiga()][p.getColonna()] = p;
        }
        
        // QUARTA QUERY: cerco posti occupati
        ps= con.prepareStatement("SELECT id_prenotazione, pagato FROM prenotazione WHERE id_spettacolo = ?");
        ps.setInt(1,id_spettacolo);
        
        rs = ps.executeQuery();
        
        while (rs.next()) {            
            Posto p = getPostoFromPrenotazione(rs.getInt("id_prenotazione"));
            boolean pagato = rs.getBoolean("pagato");
            if(p.isOccupato() && pagato)
            {
                mappa[p.getRiga()][p.getColonna()].setOccupato(true);
            }
        }
        s.setMappa(mappa); //fine??? thanks god!
        return s;
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
            s.setId(id);
            
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
    public Prenotazione getPrenotazione(int id) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM prenotazione AS p, prezzo AS pr WHERE p.id_prenotazione = ? AND p.id_prezzo = pr.id_prezzo");
        ps.setInt(1, id);
        
        ResultSet rs = ps.executeQuery();
        
        if(rs.next())
        {
            Prenotazione p = new Prenotazione();
            
            Utente u = getUtente(rs.getInt("id_utente"));
            Spettacolo s = getSpettacolo(rs.getInt("id_spettacolo"));
            double prezzo = rs.getDouble("prezzo");
            Posto posto = getPosto(rs.getInt("id_prezzo"));
            boolean pagato = rs.getBoolean("pagato");
            
            Timestamp ts = rs.getTimestamp("data_ora_operazione");
            Calendar c = Calendar.getInstance();
            c.setTime(ts);
            
            Sala sala = getSala(rs.getInt("id_sala"));
            
            p.setId(id);
            p.setData_ora_operazione(c);
            p.setPosto(posto);
            p.setPrezzo(prezzo);
            p.setSala(sala);
            p.setSpettacolo(s);
            p.setUtente(u);
            p.setPagato(pagato);
            
            return p;
        }
        
        return null;
    }
    
}
