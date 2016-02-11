/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassiDB;

import Database.DBManager;
import java.sql.SQLException;

/**
 * classe che rappresenta un film, ID è chiave primaria
 * @author Ivan
 */
public class Film {

    /**
     *
     */
    protected int id;

    /**
     *
     */
    protected String titolo;

    /**
     *
     */
    protected String genere;

    /**
     *
     */
    protected int durata;

    /**
     *
     */
    protected String trama;

    /**
     *
     */
    protected String url_trailer;

    /**
     *
     */
    protected String url_locandina;

    /**
     *
     */
    protected String attori;

    /**
     *
     */
    protected String regista;

    /**
     *
     */
    protected String frase;

    /**
     *
     */
    protected double totaleIncassi = 0; //utile solo per amministrazione

    /**
     * Costruttore vuoto, in caso si voglia settare tutto da get e set
     */
    public Film() {
        
    }
    
    /**
     * Costruttore che reperisce il film dal database in base all'id
     * @param id ID del film
     * @throws SQLException Errore sintassi SQL
     * @throws ClassNotFoundException Errore caricamento della classe per l'accesso al DB
     */
    public Film(int id) throws SQLException, ClassNotFoundException{
        this.id = id;
        
        DBManager dbm = DBManager.getDBManager();
        Film f = dbm.getFilm(id);
        
        this.titolo = f.titolo;
        this.genere = f.genere;
        this.durata = f.durata;
        this.trama = f.trama;
        this.url_locandina = f.url_locandina;
        this.url_trailer = f.url_trailer;
        this.regista = f.regista;
        this.attori = f.attori;
        this.frase = f.frase;
    }

    /**
     * Costruttore che costruisce l'oggetto film con i dati di input
     * @param id ID del film
     * @param titolo Titolo del film
     * @param genere Genere del film
     * @param regista Regista del film
     * @param attori Attori del film
     * @param durata Durata del film
     * @param trama Trama del film
     * @param url_trailer Link del trailer del film
     * @param url_locandina Link per la locandina
     */
    public Film(int id, String titolo, String genere, String regista, String attori, int durata, String trama, String url_trailer, String url_locandina) {
        this.id = id;
        this.titolo = titolo;
        this.genere = genere;
        this.durata = durata;
        this.trama = trama;
        this.regista = regista;
        this.attori = attori;
        this.url_trailer = url_trailer;
        this.url_locandina = url_locandina;
    } 

    /**
     * metodo che imposta l'id a un film
     * @param id numero identificativo del film da inserire
    */
    public void setId(int id){
        this.id = id;
    }
   /**
     * metodo che dal film selezionato ritorna il suo numero identificativo
     * @return 
    */
    public int getId() {
        return id;
    }
    /**
     * metodo che dal film selezionato ritorna il suo titolo
     * @return 
    */
    public String getTitolo() {
        return titolo;
    }
   /**
     * metodo imposta un nuovo titolo del film selezionato
     * @param titolo
    */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
   /**
     * metodo che dal film selezionato ritorna il suo genere
     * @return 
    */
    public String getGenere() {
        return genere;
    }
   /**
     * metodo imposta un nuovo genere del film selezionato
     * @param genere
    */
    public void setGenere(String genere) {
        this.genere = genere;
    }
    /**
     * metodo che dal film selezionato ritorna il suo durata
     * @return 
    */
    public int getDurata() {
        return durata;
    }
   /**
     * metodo che imposta un nuova durata del film selezionato
     * @param durata
    */
    public void setDurata(int durata) {
        this.durata = durata;
    }
    /**
     * metodo che dal film selezionato ritorna il testo contentente la trama
     * @return 
    */
    public String getTrama() {
        return trama;
    }
   /**
     * metodo che imposta un nuova testo per la trama del film selezionato
     * @param trama
    */
    public void setTrama(String trama) {
        this.trama = trama;
    }
  /**
     * metodo che dal film selezionato ritorna l'indirizzo URL del trailer
     * @return 
    */
    public String getUrl_trailer() {
        return url_trailer;
    }
   /**
     * metodo che imposta un nuovo indirizzo URL per il film selezionato
     * @param url_trailer
    */
    public void setUrl_trailer(String url_trailer) {
        this.url_trailer = url_trailer;
    }
  /**
     * metodo che dal film selezionato ritorna l'indirizzo URL della locandina associata
     * @return 
    */
    public String getUrl_locandina() {
        return url_locandina;
    }
   /**
     * metodo che imposta un nuovo indirizzo URL per la locandina del film
     * @param url_locandina
    */
    public void setUrl_locandina(String url_locandina) {
        this.url_locandina = url_locandina;
    }
    /**
     * metodo che dal film selezionato ritorna la lista degli attori partecipanti al film 
     * @return 
    */
    public String getAttori() {
        return attori;
    }
   /**
     * metodo che imposta un nuovo set di attori per il film selezionato
     * @param attori
    */
    public void setAttori(String attori) {
        this.attori = attori;
    }
  /**
     * metodo che dal film selezionato ritorna il nome del regista
     * @return 
    */
    public String getRegista() {
        return regista;
    }
 /**
     * metodo che imposta un nuovo regista per il film selezionato
     * @param regista
    */
    public void setRegista(String regista) {
        this.regista = regista;
    }
  /**
     * metodo che dal film selezionato ritorna la stringa che viene visualizzata come descrizione ridotta della trama
     * @return 
    */
    public String getFrase() {
        return frase;
    }
 /**
     * metodo che imposta una nuova descrizione breve per il film selezionato
     * @param frase
    */
    public void setFrase(String frase) {
        this.frase = frase;
    }
  /**
     * metodo che dal film selezionato ritorna la quantita di incassi
     * @return 
    */
    public double getTotaleIncassi() {
        return totaleIncassi;
    }
 /**
     * metodo che imposta il numero di incassi globali per il film selezionato
     * @param totaleIncassi
    */
    public void setTotaleIncassi(double totaleIncassi) {
        this.totaleIncassi = totaleIncassi;
    }
}
