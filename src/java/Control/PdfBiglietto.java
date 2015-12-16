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
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 *
 * @author Utente
 */
public class PdfBiglietto {
    String nomeFile;
    Prenotazione prenotazione;
    ByteArrayOutputStream datiBiglietto;
    
    //metadati
    String autore = "Sistema distribuzione biglietti CineLand";
    String subject = "Biglietto CineLand";
    String keywords = "biglietto,cineland,cinema";
    String titolo = "Biglietto CineLand";

    public PdfBiglietto(String nomeFile, Prenotazione prenotazione) {
        this.nomeFile = nomeFile;
        this.prenotazione = prenotazione;
        datiBiglietto = new ByteArrayOutputStream();
    }

    public PdfBiglietto(Prenotazione prenotazione) {
        this.prenotazione = prenotazione;
    }
    
    public void costruisciPdf(String nomeFile, OutputStream stream) throws DocumentException{
      Document biglietto = new Document();
      //PdfWriter.getInstance(biglietto, new FileOutputStream(nomeFile));
      PdfWriter.getInstance(biglietto, stream);
      
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
      
      biglietto.add(titolo);
      biglietto.add(info);
      biglietto.add(prezzo);
      biglietto.add(fondo);
     
      biglietto.close();
    }

    public ByteArrayOutputStream getPDF() {
        return datiBiglietto;
    }
    
    
}