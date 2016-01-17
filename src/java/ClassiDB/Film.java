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
public class Film {
    protected int id;
    protected String titolo;
    protected String genere;
    protected int durata;
    protected String trama;
    protected String url_trailer;
    protected String url_locandina;
    protected String attori;
    protected String regista;
    protected String frase;
    protected double totaleIncassi = 0; //utile solo per amministrazione

    /**
     * Costruttore vuoto, in caso si voglia settare tutto da get&set
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

    private void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public String getTrama() {
        return trama;
    }

    public void setTrama(String trama) {
        this.trama = trama;
    }

    public String getUrl_trailer() {
        return url_trailer;
    }

    public void setUrl_trailer(String url_trailer) {
        this.url_trailer = url_trailer;
    }

    public String getUrl_locandina() {
        return url_locandina;
    }

    public void setUrl_locandina(String url_locandina) {
        this.url_locandina = url_locandina;
    }

    public String getAttori() {
        return attori;
    }

    public void setAttori(String attori) {
        this.attori = attori;
    }

    public String getRegista() {
        return regista;
    }

    public void setRegista(String regista) {
        this.regista = regista;
    }

    public String getFrase() {
        return frase;
    }

    public void setFrase(String frase) {
        this.frase = frase;
    }

    public double getTotaleIncassi() {
        return totaleIncassi;
    }

    public void setTotaleIncassi(double totaleIncassi) {
        this.totaleIncassi = totaleIncassi;
    }
}
