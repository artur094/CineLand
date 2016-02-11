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
import java.util.Date;
import java.util.List;
import java.util.Random;

//TODO : 



/**
 * Classe per il controllo del database.
 * @author Ivan
 * 
 * Impostazioni consigliate per il database (se cambiate, sarà necessario modificare i parametri del DBManager
 * Database: CineLand
 * Username: cineland
 * Password: cineland
 */
public class DBManager implements Serializable {
    // Transient: non serializza con
    private transient Connection con;
    // Stringa di collegamento al DB

    /**
     *
     */
    protected String URL_DB = "jdbc:derby://localhost:1527/cineland";
    // Implemento Singleton

    /**
     *
     */
    protected static DBManager dbm = null;
    
    /**
     * Singleton
     * @return Oggetto unico per la connessione al DB
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static DBManager getDBManager() throws SQLException, ClassNotFoundException
    {
        if(dbm == null)
        {
            dbm = new DBManager();
        }
        return dbm;
    }
    
    /**
     * Costruttore
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    protected DBManager() throws SQLException, ClassNotFoundException
    {
        Class.forName("org.apache.derby.jdbc.ClientDriver", true, getClass().getClassLoader());
        Connection con =  DriverManager.getConnection(URL_DB, "cineland", "cineland");
        this.con = con;
        
        //con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
    }
    
    /**
     * Funzione per la generazione di una chiave random
     * @param length Lunghezza chiave
     * @return Chiave
     */
    public String generateRandomKey(int length){
        Random random = new Random(new Date().getTime());
        String alphabet = 
                new String("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"); //9
        int n = alphabet.length(); //10

        String result = new String(); 

        for (int i=0; i<length; i++) //12
            result = result + alphabet.charAt(random.nextInt(n)); //13

        return result;
    }
    
    // VARIE FUNZIONI DI AMMINISTRAZIONE 
    /**
     * Funzione per creare posti vuoti
     * @param id_sala ID sala
     * @param riga Riga del posto
     * @param colonna Colonna del posto
     * @return True se OK, False altrimenti
     * @throws SQLException 
     */
    public boolean creaPostoVuoto(int id_sala, int riga, int colonna) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT id_posto FROM posto WHERE id_sala = ? AND riga = ? AND colonna = ?");
        ps.setInt(1, id_sala);
        ps.setInt(2, riga);
        ps.setInt(3, colonna);
        
        ResultSet rs = ps.executeQuery();
        
        if(rs.next())
        {
            ps = con.prepareStatement("UPDATE posto SET esiste = false WHERE id_posto = ?");
            ps.setInt(1, rs.getInt("id_posto"));
            
            int rows = ps.executeUpdate();
            if(rows > 0)
                return true;
        }
        return false;
    }
    
    /**
     * Funzione per inserire spettacoli
     * @param id_sala ID sala
     * @param id_film ID film
     * @param data Data e ora
     * @return True se OK, false altrimenti
     * @throws SQLException 
     */
    public boolean insertSpettacolo(int id_sala, int id_film, Timestamp data) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO spettacolo (id_film, id_sala, data_ora) VALUES (?,?,?)"
        );
        ps.setInt(1, id_film);
        ps.setInt(2, id_sala);
        ps.setTimestamp(3, data);
        
        int rows = ps.executeUpdate();
        if(rows < 1)
            return false;
        return true;
    }
    
    /**
     * Ritorna le prenotazioni per lo spettacolo, quindi i posti prenotati
     * @param id_spettacolo ID spettacolo
     * @return Lista di prenotazioni per lo spettacolo
     * @throws SQLException 
     */
    public List<Prenotazione> prenotazioniPerSpettacolo(int id_spettacolo) throws SQLException
    {
        List<Prenotazione> lista = new ArrayList<>();
        
        PreparedStatement ps = con.prepareStatement(
                "SELECT id_prenotazione FROM prenotazione WHERE id_spettacolo = ?"
        );
        ps.setInt(1, id_spettacolo);
        
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {            
            lista.add(getPrenotazione(rs.getInt("id_prenotazione")));
        }
        
        return lista;
    }
    
    /**
     * Funzione per ottenere la lista in ordine decrescente dei posti più prenotati
     * @param id_sala ID sala
     * @return Lista posti in ordine decrescente per numero di prenotazioni
     * @throws SQLException 
     */
    public List<Posto> postiPiuPrenotati(int id_sala) throws SQLException
    {
        List<Posto> lista = new ArrayList<>();
        
        PreparedStatement ps = con.prepareStatement(
                "SELECT p.id_posto, count(*) as tot FROM prenotazione AS p, posto AS po WHERE po.id_sala=? AND po.id_posto = p.id_posto GROUP BY p.id_posto ORDER BY tot desc"
        );
        ps.setInt(1,id_sala);
        ResultSet rs = ps.executeQuery();
        
        for (int i = 0; i < 10 && rs.next(); i++) {
            lista.add(getPosto(rs.getInt("id_posto")));
        }
        
        return lista;
    }

    /**
     * Funzione per recuperare la lista dei clienti migliori
     * @return Lista dei clienti migliori
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
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
    
    /**
     * Funzione che recupera la lista di film con gli incassi
     * @return Lista di film con gli incassi
     * @throws SQLException 
     */
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
    
    /**
     * Funzione per il rimborso della prenotazione
     * @param id_prenotazione ID prenotazione (Da cui si recupera utente)
     * @return True se OK, False altrimenti
     */
    public boolean rimborsaPrenotazione(int id_prenotazione)
    {
        try{
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement(
                    "SELECT pr.prezzo, p.id_utente FROM prenotazione AS p, prezzo AS pr WHERE p.id_prezzo = pr.id_prezzo AND p.id_prenotazione = ?"
            );
            ps.setInt(1,id_prenotazione);

            ResultSet rs = ps.executeQuery();

            if(!rs.next())
            {
                con.rollback();
                con.setAutoCommit(true);
                return false;
            }

            int id_utente = rs.getInt("id_utente");
            double prezzo = rs.getDouble("prezzo") * 0.8;

            ps = con.prepareStatement("DELETE FROM prenotazione WHERE id_prenotazione = ?");
            ps.setInt(1,id_prenotazione);

            int rows = ps.executeUpdate();

            if(rows<1)
            {
                con.rollback();
                con.setAutoCommit(true);
                return false;
            }

            //Utente u = getUtente(id_utente);
            //u.setCredito(u.getCredito() + prezzo);

            ps = con.prepareStatement(
                    "UPDATE utente "+
                    "SET credito = credito + ? "+
                    "WHERE id_utente = ?"
            );
            ps.setDouble(1, prezzo);
            ps.setInt(2, id_utente);

            if(ps.executeUpdate() > 0)
            {
                con.commit();
                con.setAutoCommit(true);
                return true;
            }
            con.rollback();
            con.setAutoCommit(true);
            return false;
        }catch(SQLException ex)
        {
            try{
                con.rollback();
                con.setAutoCommit(true);
            }
            catch(SQLException sqlex)
            {
                
            }
            return false;
        }
    }
            
    
    // VARIE FUNZIONI DI LOGIN  ...
    
    /**
     * Funzione che si occupa di controllare se esiste l'account email-password
     * Nel caso affermativo, ritornare la classe corrispondente (prendi l'ID e ritorni getUtente(id)
     * Nel caso contrario (o problemi), null
     * 
     * @param email Email dell'utente inserita nella form
     * @param password Password dell'utente inserita nella form
     * @return Oggetto utente
     * @throws java.sql.SQLException
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
     * @return stringa del codice di registrazione
     * @throws java.sql.SQLException
     */
    public String registrazione(String email, String password, String nome) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM utente WHERE email = ?");
        ps.setString(1, email);
        
        ResultSet rs = ps.executeQuery();
        
        if(rs.next())
            return null;
        

        int id_ruolo = getIDRuolo("verificare");
        if(id_ruolo<0)
            return "";
        String codice = generateRandomKey(64);
        
        ps = con.prepareStatement("INSERT INTO utente(email,password,nome,credito,id_ruolo, codice_attivazione, data_invio_codice_attivazione) "+
                "VALUES (?,?,?,?,?,?, CURRENT_TIMESTAMP)");
        ps.setString(1, email);
        ps.setString(2, password);
        ps.setString(3, nome);
        ps.setDouble(4, 0);
        ps.setInt(5, id_ruolo);
        ps.setString(6, codice);
        
        ps.executeUpdate();
        
        PreparedStatement selezione = con.prepareStatement("SELECT * FROM utente WHERE email = ? AND password = ?");
        selezione.setString(1, email);
        selezione.setString(2, password);
        rs = selezione.executeQuery();
        if(rs.next())
            return codice;
        return "";
    }
    /**
     * Funzione che abilita l'account se il codice dato in input è corretto
     * Per abilitare l'account, impostare id_ruolo a quello di user
     * se corretto, torna TRUE, altriment FALSE (da vedere)
     * @param email Email usata per l'account
     * @param code Codice ricevuto via email
     * @return True se OK, False altrimenti
     * @throws java.sql.SQLException
     */
    public boolean enableAccount(String email, String code) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM utente WHERE email = ? and codice_attivazione = ?");
        ps.setString(1, email);
        ps.setString(2, code);
        ResultSet rs = ps.executeQuery();
        int id_ruolo = getIDRuolo("user");
        if(id_ruolo < 0)
            return false;
        //getDouble ritorna 0 se il valore è null (ovvero nel resut set non c'è nessun utente con quel codice di attivazione)
        if(rs.next()){
            if(!rs.getString("codice_attivazione").equals("")){
                PreparedStatement confermaUtente = con.prepareStatement("UPDATE utente SET id_ruolo = ?, codice_attivazione = ? WHERE email = ? AND codice_attivazione = ?");
                confermaUtente.setInt(1, id_ruolo);
                confermaUtente.setString(2,"");
                confermaUtente.setString(3, email);
                confermaUtente.setString(4, code);
                if(confermaUtente.executeUpdate() > 0)
                    return true;
            }
        }
        return false;
    }
    
    /**
     * Funzione per la richiesta della password dimenticata
     * @param email Email dell'utente
     * @return Codice per il cambio password
     * @throws SQLException 
     */
    public String passwordDimenticata(String email) throws SQLException
    {
        PreparedStatement ps;
        ps=con.prepareStatement("SELECT * FROM utente WHERE email=?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        
        if(!rs.next())
            return "";
                
        ps = con.prepareStatement("DELETE FROM password_dimenticata WHERE email = ?");
        ps.setString(1, email);
        ps.executeUpdate();
        String codice;
        do{
            codice = generateRandomKey(64);
        }while(!getEmailFromCodeForForgottenPassword(codice).equals(""));
        
        ps = con.prepareStatement("INSERT INTO password_dimenticata (email, codice, data) VALUES (?,?, CURRENT_TIMESTAMP)");
        ps.setString(1, email);
        ps.setString(2, codice);
        ps.executeUpdate();
        
        return codice;
    }
    
    /**
     * Funzione per cambiare la password di un utente
     * @param email Email dell'utente
     * @param old_password Vecchia password
     * @param new_password Nuova password
     * @return True se OK, False altrimenti
     * @throws SQLException 
     */
    public boolean cambiaPassword(String email, String old_password, String new_password) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT id_utente FROM utente WHERE email=? AND password=?");
        ps.setString(1,email);
        ps.setString(2,old_password);
        
        ResultSet rs = ps.executeQuery();
        if(!rs.next())
        {
            return false;            
        }
        int id = rs.getInt("id_utente");
        
        ps = con.prepareStatement("UPDATE utente SET password = ? WHERE id_utente = ?");
        ps.setString(1,new_password);
        ps.setInt(2,id);
        
        int rows = ps.executeUpdate();
        if(rows>0)
            return true;
        return false;
    }
    
    /**
     * Funzione per il reset della password
     * Cancella tutte le richieste i password dimenticata in attesa da più di 5 minuti
     * @param email Email utente
     * @param password Password nuova
     * @param codice Codice dato tramite mail
     * @return True se OK, False altrimenti
     * @throws SQLException 
     */
    public boolean resettaPassword(String email, String password, String codice) throws SQLException
    {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, -5);
        
        PreparedStatement ps = con.prepareStatement("DELETE FROM password_dimenticata WHERE data < ?");
        Timestamp t = new Timestamp(now.getTimeInMillis());
        ps.setTimestamp(1,t);
        ps.executeUpdate();
        
        
        ps = con.prepareStatement("SELECT * FROM password_dimenticata WHERE email = ? and codice = ?");
        ps.setString(1, email);
        ps.setString(2, codice);
        
        ResultSet rs = ps.executeQuery();
        
        if(!rs.next())
            return false;
        
        ps = con.prepareStatement("UPDATE utente SET password = ? WHERE email = ?");
        ps.setString(1, password);
        ps.setString(2, email);
        
        if(ps.executeUpdate() < 1)
            return false;
        
        ps = con.prepareStatement("DELETE FROM password_dimenticata WHERE email = ?");
        ps.setString(1, email);
        ps.executeUpdate();
        
        return true;
    }

    /**
     * Funzione per ottenere la email dal codice della password
     * @param code Codice per il rinnovo password
     * @return Stringa vuota se non è stata ritrovata, altrimenti l'email dell'utente
     * @throws SQLException 
     */
    public String getEmailFromCodeForForgottenPassword(String code) throws SQLException
    {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, -5);
        
        PreparedStatement ps = con.prepareStatement("DELETE FROM password_dimenticata WHERE data < ?");
        Timestamp t = new Timestamp(now.getTimeInMillis());
        ps.setTimestamp(1,t);
        ps.executeUpdate();
        
        ps = con.prepareStatement("SELECT email FROM password_dimenticata WHERE codice = ?");
        ps.setString(1,code);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return rs.getString("email");
        return "";
    }
    
    /**
     * Funzione che ritorna i soldi spesi dall'utente
     * @param id_utente Utente
     * @return Soldi totali pagati dall'utente
     * @throws SQLException 
     */
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
    /**
     * Funzione per inserire prenotazioni
     * @param id_utente ID utente
     * @param id_spettacolo ID spettacolo
     * @param id_posto ID posto
     * @param tipo_prezzo Tipologia del prezzo
     * @return Prenotazione ottenuta, o null in caso di errore
     * @throws SQLException 
     */
    public Prenotazione insertPrenotazione(int id_utente, int id_spettacolo, int id_posto, String tipo_prezzo) throws SQLException
    {
        Prenotazione p = getPrenotazione(id_spettacolo, id_utente, id_posto);
        
        if(p != null)
            return null;
        
        PreparedStatement ps = con.prepareStatement("SELECT id_prezzo FROM prezzo WHERE tipo = ?");
        ps.setString(1, tipo_prezzo);
        
        ResultSet rs = ps.executeQuery();
        
        if(!rs.next())
            return null;
        int id_prezzo = rs.getInt("id_prezzo");
        
        ps = con.prepareStatement("INSERT INTO prenotazione (id_utente, id_spettacolo, id_prezzo, id_posto, pagato, data_ora_operazione) values (?,?,?,?,?,current_timestamp)");
        ps.setInt(1, id_utente);
        ps.setInt(2, id_spettacolo);
        ps.setInt(3, id_prezzo);
        ps.setInt(4, id_posto);
        ps.setBoolean(5, true);
        
        int righeModificate = ps.executeUpdate();
        
        //una riga modificata equivale a una prenotazione inserita che rispetta le costraint del database.
        if(righeModificate == 1){
            ps = con.prepareStatement("SELECT id_prenotazione FROM prenotazione WHERE id_utente = ? AND id_spettacolo = ? AND id_posto = ?");
            ps.setInt(1, id_utente);
            ps.setInt(2, id_spettacolo);
            ps.setInt(3, id_posto);

            rs = ps.executeQuery();
            if(rs.next())
                return getPrenotazione(rs.getInt("id_prenotazione"));
            return null;
        }else{
            return null;
        }
    }
    
    /**
     * Funzione usata per cancellare una lista di prenotazioni, Da usare solo in caso di errore
     * Altrimenti usare rimborsaPrenotazione
     * @param lista Lista di prenotazioni da eliminare
     * @return TRUE se è andata a buon fine, altrimenti false
     * @throws SQLException 
     */
    public boolean removePrenotazioni(List<Prenotazione> lista) throws SQLException
    {
        int rows = 0;
        for(Prenotazione p : lista)
        {
            if(removePrenotazione(p.getId()))
                rows++;
        }
        if(rows == lista.size())
            return true;
        return false;
    }
    
    /**
     * Funzione usata per cancellare una prenotazione, Da usare solo in caso di errore
     * Altrimenti usare rimborsaPrenotazione
     * @param id_pren ID prenotazione
     * @return TRUE se è andata a buon fine, false altrimenti
     * @throws SQLException 
     */
    public boolean removePrenotazione(int id_pren) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("DELETE FROM prenotazione WHERE id_prenotazione=?");
        ps.setInt(1,id_pren);
        
        int rows = ps.executeUpdate();
        
        if(rows>0)
            return true;
        return false;
    }
    
    //USELESS - Prenotazione inserita al momento del pagamento
   /* public boolean pagaPrenotazione(int id_utente, int id_spettacolo, int id_posto) throws SQLException
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
    
    //USELESS
    public boolean pagaPrenotazione(int id_prenotazione) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("UPDATE prenotazione SET pagato = true WHERE id_prenotazione = ?");
        ps.setInt(1, id_prenotazione);
        int row = ps.executeUpdate();
        if(row < 1)
            return false;
        return true;
    }*/
    
    // FUNZIONI CHE RECUPERANO UN INSIEME DI CLASSIDB
    
    // CLASSE PRENOTAZIONI
    
    /**
     * Funzione che ritorna la lista delle prenotazioni dell'utente (sia pagate che non)
     * 
     * @param id_utente ID utente 
     * @return Lista di prenotazioni dell'utente
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
    
    /**
     * Funzione per il recupero delle prenotazioni disponibili
     * @param id_utente ID utente
     * @return Lista delle prenotazioni risarcibili
     * @throws SQLException 
     */
    public ArrayList<Prenotazione> getPrenotazioniUtenteRisarcibili(int id_utente) throws SQLException
    {
        ArrayList<Prenotazione> lista = new ArrayList<>();
        
        PreparedStatement ps = con.prepareStatement("SELECT id_prenotazione FROM prenotazione WHERE id_utente = ? AND pagato = true");
        ps.setInt(1, id_utente);
        
        ResultSet rs = ps.executeQuery();
        
        while(rs.next())
        {
            int id = rs.getInt("id_prenotazione");
            Prenotazione p = getPrenotazione(id);
            if(p.getSpettacolo().getData_ora().compareTo(Calendar.getInstance())>0)
                lista.add(getPrenotazione(id));
            
        }
        
        return lista;
    }
    
    /**
     * Funzione per il recupero delle prenotazioni da pagare
     * @param id_utente ID utente
     * @return Lista Prenotazioni da pagare
     * @throws SQLException 
     */
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
     * @return lista spettacoli futuri che visualizzeranno il film in input
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
     * @return Lista di tutti gli spettacoli
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
     * @return Lista di tutti i spettacoli futuri
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
     * @return lista di tutti i film futuri
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
     * @return Lista di tutti i film
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
    /**
     * Funzione per il recupero del film
     * @param id ID film
     * @return Oggetto Film
     * @throws SQLException 
     */
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
    /**
     * Funzione per il recupero del posto
     * @param id ID posto
     * @return Oggetto posto
     * @throws SQLException 
     */
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
    
    /**
     * Funzione per il recupero del posto da una prenotazione
     * @param id_prenotazione ID prenotazione
     * @return Oggetto posto
     * @throws SQLException 
     */
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
    
    /**
     * getSala richiede uno spettacolo, e dato che la sala resta sempre uguale (dimensioni)
     * e al front serve la sala senza spettacoli, mi semplifico la vita prendendo un id_spett a caso
     * e costruisco la sala usando quello
     * Poi pulisco la sala e ci metto come posti occupati i 10 posti più occupati
     * @param id_sala ID della sala, perchè voglio recuperare 
     * @return Sala (vuota)
     * @throws java.sql.SQLException
     * @throws java.lang.Exception
     */
    public Sala getSalaConPostiPiuPrenotati(int id_sala) throws SQLException,Exception
    {
        PreparedStatement ps = con.prepareStatement("SELECT id_spettacolo FROM spettacolo WHERE id_sala = ?");
        ps.setInt(1,id_sala);
        
        ResultSet rs = ps.executeQuery();
        if(rs.next())
        {
            int id_spett = rs.getInt("id_spettacolo");
            List<Posto> postiPiuPrenotati = postiPiuPrenotati(id_sala);
            
            Sala s = Sala.getSalaBySpett(id_spett);
            s.setPosti_occupati(postiPiuPrenotati);
            
            return s;
            
        }
        return null;
    }
    
    
    
    // Prendere dal DB il nome sala
    // Costruire la mappa della sala con tale ID
    /**
     * Crea l'oggetto sala in base all'ID
     * @param id_sala ID della sala
     * @return Oggetto Sala che corrisponde all'id dato in ingresso
     * @throws SQLException 
     */
    public Sala getSala(int id_sala) throws SQLException
    {
        // PRIMA QUERY: recupero informazioni base della sala
        PreparedStatement ps = con.prepareStatement("SELECT s.id_sala, s.descrizione FROM sala as s WHERE  s.id_sala = ?");
        ps.setInt(1, id_sala);
        
        ResultSet rs = ps.executeQuery();
        if(!rs.next())
            return null;
        
        Sala s = new Sala();
        s.setId(rs.getInt("id_sala"));
        s.setNome(rs.getString("descrizione"));
        
        // SECONDA QUERY: recupero numero righe e numero colonne sala
        ps = con.prepareStatement("SELECT MAX(p.riga) AS riga, MAX(p.colonna) AS colonna FROM posto AS p WHERE  p.id_sala = ?");
        ps.setInt(1, id_sala);
        
        rs = ps.executeQuery();
        
        if(!rs.next())
            return null;
        
        s.setNumeroRighe(rs.getInt("riga"));
        s.setNumeroColonne(rs.getInt("colonna"));
        s.setPosti_inesistenti(getPostiInesistenti(s.getId()));
        
        return s;
    }
    
    /**
     * Ritorna i posti occupati di uno spettacolo
     * @param id_spettacolo 
     * @return
     * @throws SQLException 
     */
    public List<Posto> getPostiOccupati(int id_spettacolo) throws SQLException
    {
        List<Posto> lista = new ArrayList<>();
        PreparedStatement ps= con.prepareStatement("SELECT id_prenotazione FROM prenotazione WHERE id_spettacolo = ?");
        ps.setInt(1,id_spettacolo);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {            
            Posto p = getPostoFromPrenotazione(rs.getInt("id_prenotazione"));
            lista.add(p);
        }
        return lista;
    }
    
    /**
     * Ritorna lista dei posti inesistenti (rotti)
     * @param id_sala ID della sala
     * @return Lista di posti che non esistono (rotti) della mappa
     * @throws SQLException 
     */
    public List<Posto> getPostiInesistenti(int id_sala) throws SQLException
    {
        List<Posto> lista = new ArrayList<>();
        PreparedStatement ps= con.prepareStatement("SELECT id_posto FROM posto WHERE esiste = false AND id_sala = ? ORDER BY riga,colonna");
        ps.setInt(1,id_sala);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {            
            Posto p = getPosto(rs.getInt("id_posto"));
            lista.add(p);
        }
        return lista;
    }
    
    /**
     * Funzione che ritorna le sale del database
     * @return Lista di sale
     * @throws SQLException 
     */
    public List<Sala> getSale() throws SQLException
    {
        List<Sala> sale = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement("SELECT id_sala FROM sala");
        ResultSet rs = ps.executeQuery();
        
        while(rs.next())
        {
            int id_sala = rs.getInt("id_sala");
            
            Sala s = getSala(id_sala);
            s.setPosti_inesistenti(getPostiInesistenti(id_sala));
            sale.add(s);
        }
        return sale;
    }
    
    /**
     * Ritorna l'id della sala dato lo spettacolo
     * @param id_spettacolo ID dello spettacolo
     * @return ID della sala
     * @throws SQLException 
     */
    public int getIDSalaBySpett(int id_spettacolo) throws SQLException
    {
        int id_sala = -1;
        PreparedStatement ps = con.prepareStatement("SELECT id_sala FROM spettacolo WHERE id_spettacolo = ?");
        ps.setInt(1, id_spettacolo);
        ResultSet rs = ps.executeQuery();
        
        if(rs.next())
        {
            id_sala = rs.getInt("id_sala");
        }
        return id_sala;
    }
    
    
    
    
    /**
     * Funzione per il recupero della sala per un determinato spettacolo
     * @param id_spettacolo ID spettacolo
     * @return Oggetto sala
     * @throws SQLException 
     */
    /*public Sala getSala(int id_spettacolo) throws SQLException
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
    }*/
    
    /**
     * Funzione per il recupero dell'id sala dal nome
     * @param nome Nome sala
     * @return ID sala
     * @throws SQLException 
     */
    public int getIdSala(String nome) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT id_sala FROM sala WHERE LOWER(descrizione) = ?");
        ps.setString(1,nome.toLowerCase());
        
        ResultSet rs = ps.executeQuery();
        
        if(rs.next())
        {
            return rs.getInt("id_sala");
        }
        return -1;
    }
    
    /**
     * Funzione per il recupero degli id delle sale
     * @return Lista di id delle sale
     * @throws SQLException 
     */
    public List<Integer> getListaIDSale() throws SQLException
    {
        List<Integer> listaSale = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement("SELECT id_sala FROM sala");
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            listaSale.add(rs.getInt("id_sala"));
        }
        return listaSale;
    }
    
    // Prendere lo spettacolo dal DB
    // Prendere il film a cui si riferisce lo spettacolo
    // Prendere la sala a cui si riferisce lo spettacolo
    // Assegnare film e sala a spettacolo
    /**
     * Funzione per il recupero dello spettacolo
     * @param id ID spettacolo
     * @return Oggetto Spettacolo
     * @throws SQLException 
     */
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
            
            int id_sala = getIDSalaBySpett(id);
            Sala sala = getSala(id_sala);
            s.setSala(sala);
            
            return s;
        }
        return null;
    }
    
    // Prendere l'utente con tale ID
    // Prendere il ruolo in stringa e assegnarlo all'utente
    // ID, Nome, Email, Ruolo, Credito
    /**
     * Funzione per il recupero dell'utente
     * @param id ID utente
     * @return Oggetto utente
     * @throws SQLException 
     */
    public Utente getUtente(int id) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT U.email, U.nome, U.credito, R.ruolo FROM Utente AS U, Ruolo AS R WHERE R.id_ruolo = U.id_ruolo AND U.id_utente = ?");
        ps.setInt(1, id);
        
        ResultSet rs = ps.executeQuery();
        
        if(!rs.next())
            return null;
        
        Utente u = new Utente();
        u.setId(id);
        u.setEmail(rs.getString("email"));
        u.setNome(rs.getString("nome"));
        u.setRuolo(rs.getString("ruolo"));
        u.setCredito(rs.getDouble("credito"));
        
        return u;
    }
    
    // Prendere l'utente con tale email
    // Usata per vedere se esiste un utente con tale email
    /**
     * Funzione per il recupero dell'utente in base alla mail
     * @param email Email utente
     * @return Oggetto utente
     * @throws SQLException 
     */
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
    
    /**
     * Funzione per prendere la prenotazione dato spettacolo, utente e posto
     * @param id_spett ID spettacolo
     * @param id_utente ID utente
     * @param id_posto ID posto
     * @return Prenotazione
     * @throws SQLException 
     */
    public Prenotazione getPrenotazione(int id_spett, int id_utente, int id_posto) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT id_prenotazione FROM prenotazione WHERE id_spettacolo = ? AND id_utente = ? AND id_posto = ?");
        ps.setInt(1, id_spett);
        ps.setInt(2, id_utente);
        ps.setInt(3, id_posto);
        
        ResultSet rs = ps.executeQuery();
        
        if(rs.next())
            return getPrenotazione(rs.getInt("id_prenotazione"));
        return null;
    }
    
    // Prendere la prenotazione
    // Prendere l'utente della prenotazione
    // Anche il posto e la sala corrispondente
    // Anche lo spettacolo
    // Il prezzo e l'ora
    /**
     * Funzione per il recupero della prenotazione
     * @param id ID prenotazione
     * @return Oggetto prenotazione
     * @throws SQLException 
     */
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
            Posto posto = getPosto(rs.getInt("id_posto"));
            boolean pagato = rs.getBoolean("pagato");
            
            Timestamp ts = rs.getTimestamp("data_ora_operazione");
            Calendar c = Calendar.getInstance();
            c.setTime(ts);
            
            Sala sala = getSala(posto.getId_sala());
            
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
    
    /**
     * Funzione per il recupero dell'id ruolo
     * @param ruolo Nome del ruolo
     * @return ID ruolo
     * @throws SQLException 
     */
    public int getIDRuolo(String ruolo) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT id_ruolo FROM ruolo WHERE ruolo = ?");
        ps.setString(1, ruolo);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return rs.getInt("id_ruolo");
        return -1;
    }
    
    /**
     * Funzione per recupero dell'id del posto in base alla posizione e sala
     * @param id_sala ID sala
     * @param riga Riga
     * @param colonna Colonna 
     * @return ID posto
     * @throws SQLException 
     */
    public int getIDPosto(int id_sala, int riga, int colonna) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT id_posto FROM posto WHERE id_sala = ? AND riga=? AND colonna=?");
        ps.setInt(1, id_sala);
        ps.setInt(2, riga);
        ps.setInt(3, colonna);
        
        ResultSet rs = ps.executeQuery();
        
        if(rs.next())
        {
            int id_posto = rs.getInt("id_posto");
            return id_posto;
        }
        return -1;
    }
    
    /**
     * Funzione per ottenere l'id del prezzo dal tipo prezzo
     * @param tipo_prezzo Tipo prezzo
     * @return ID del tipo prezzo
     * @throws SQLException 
     */
    public int getIDPrezzo(String tipo_prezzo) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT id_prezzo FROM prezzo WHERE tipo = ?");
        ps.setString(1, tipo_prezzo);
        
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return rs.getInt("id_prezzo");
        return -1;
    }
    
    /**
     * Funzione che inserisce le prenotazioni usando una transazione
     * @param lista Lista di prenotazioni
     * @return resto del prezzo da pagare (scalato dall'utente)
     */
    public int testInsertPrenotazioni(List<Prenotazione> lista)
    {
        PreparedStatement ps;
        int resto = 0;
        int id_utente = -1;
        try{
            con.setAutoCommit(false);

            for(Prenotazione p:lista)
            {
                ps = con.prepareStatement("INSERT INTO prenotazione (id_utente, id_spettacolo, id_prezzo, id_posto, pagato, data_ora_operazione) values (?,?,?,?,?,current_timestamp)");
                ps.setInt(1, p.getUtente().getId());
                ps.setInt(2, p.getSpettacolo().getId());
                ps.setInt(3, getIDPrezzo(p.getTipo_prezzo()));
                ps.setInt(4, p.getPosto().getId());
                ps.setBoolean(5, true);

                ps.execute();
                if(ps.getUpdateCount()<=0)
                {
                    con.rollback();
                    con.setAutoCommit(true);
                    return -1;
                }


                ps = con.prepareStatement("UPDATE utente SET credito = credito - ? WHERE id_utente = ?");
                ps.setDouble(1, p.getPrezzo());
                ps.setInt(2, p.getUtente().getId());

                ps.execute();
                if(ps.getUpdateCount()<=0)
                {
                    resto +=p.getPrezzo();
                }
                id_utente = p.getUtente().getId();
            }
            ps = con.prepareStatement("UPDATE utente SET credito = 0 WHERE id_utente = ? AND credito<0");
            ps.setInt(1, id_utente);
            ps.execute();
            con.commit();
            
            con.setAutoCommit(true);
        }catch(SQLException ex)
        {
            try{
                con.rollback();
                con.setAutoCommit(true);
            }catch(SQLException sqlex)
            {
                return -1;
            }
        }
        
        return resto;
    }
    
    /**
     * Funzione per ottenere il prezzo double dal tipo
     * @param prezzo Tipo prezzo
     * @return Prezzo del tipo prezzo
     * @throws SQLException 
     */
    public double getPrezzo(String prezzo) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT prezzo FROM prezzo WHERE tipo = ?");
        ps.setString(1,prezzo);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return rs.getDouble("prezzo");
        return 0;
    }
    
    /**
     * Funzione che ritorna il credito dell'utente
     * @param id_utente ID utente
     * @return Credito dell'utente
     * @throws SQLException 
     */
    public double getCreditoUtente(int id_utente) throws SQLException
    {
        PreparedStatement ps = con.prepareStatement("SELECT credito FROM utente WHERE id_utente = ?");
        ps.setInt(1, id_utente);
        ResultSet rs = ps.executeQuery();
        
        if(rs.next())
            return rs.getDouble("credito");
        return 0;
    }
}
