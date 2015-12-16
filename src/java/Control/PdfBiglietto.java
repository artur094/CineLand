/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import ClassiDB.Prenotazione;
import ClassiDB.Spettacolo;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

/**
 *
 * @author Utente
 */
public class PdfBiglietto {
    String nomeFile;
    Prenotazione prenotazione;
    
    //metadati
    String autore = "Sistema distribuzione biglietti CineLand";
    String subject = "Biglietto CineLand";
    String keywords = "biglietto,cineland,cinema";
    String titolo = "Biglietto CineLand";

    public PdfBiglietto(String nomeFile, Prenotazione prenotazione) {
        this.nomeFile = nomeFile;
        this.prenotazione = prenotazione;
    }

    public PdfBiglietto(Prenotazione prenotazione) {
        this.prenotazione = prenotazione;
    }
    
    private void costruisciPdf(String nomeFile) throws DocumentException, FileNotFoundException{
      Document biglietto = new Document();
      PdfWriter.getInstance(biglietto, new FileOutputStream(nomeFile));
      biglietto.open();
      Paragraph titolo = new Paragraph(prenotazione.getSpettacolo().getFilm().getTitolo());
      titolo.add(Integer.toString(prenotazione.getSpettacolo().getId()));
      
      Paragraph info = new Paragraph();
      info.add(prenotazione.getSpettacolo().getData_ora().toString()); //ricordarsi di convertire il calendare più avanti!!
      info.add(prenotazione.getSala().getNome());
      info.add(prenotazione.getPosto().getRiga() + " " +prenotazione.getPosto().getColonna());
      
      Paragraph prezzo = new Paragraph(Double.toString(prenotazione.getPrezzo()));
      prezzo.add("");
      
      Paragraph fondo = new Paragraph(new Date().toString());       //ricordarsi di inserire un formatter
     
      biglietto.close();
    }
}