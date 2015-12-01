/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestioneClassi;

import ClassiDB.Film;
import Database.DBManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ivanmorandi
 */
public class Films {
    ArrayList<Film> listaFilm;
    
    protected Films()
    {
        listaFilm = new ArrayList<>();
    }
    
    public static Films getAllFilms() throws SQLException, ClassNotFoundException
    {
        Films fs = new Films();
        DBManager dbm = DBManager.getDBManager();
        
        ArrayList<Film> lista = dbm.getAllFilms();
        fs.setListaFilm(lista);
        
        return fs;
    }
    
    public static Films getFutureFilms() throws SQLException, ClassNotFoundException
    {
        Films fs = new Films();
        DBManager dbm = DBManager.getDBManager();
        
        ArrayList<Film> lista = dbm.getFilmFuturi();
        fs.setListaFilm(lista);
        
        return fs;
    }

    public void setListaFilm(ArrayList<Film> listaFilm) {
        this.listaFilm = listaFilm;
    }

    public ArrayList<Film> getListaFilm() {
        return listaFilm;
    }
}
