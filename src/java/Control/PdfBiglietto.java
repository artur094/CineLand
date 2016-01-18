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
//import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

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

    /**
     * Costruttore
     * @param nomeFile Nome del file PDF
     * @param prenotazione Oggetto prenotazione per la creazione del ticket
     */
    public PdfBiglietto(String nomeFile, Prenotazione prenotazione) {
        this.nomeFile = nomeFile;
        this.prenotazione = prenotazione;
        datiBiglietto = new ByteArrayOutputStream();
    }

    /**
     * Costruttore
     * @param prenotazione Oggetto prenotazione per la creazione del ticket
     */
    public PdfBiglietto(Prenotazione prenotazione) {
        this.prenotazione = prenotazione;
    }
    
    /**
     * Funzione che costruisce il file PDF
     * @param nomeFile Nome del filme
     * @param stream Stream
     * @throws DocumentException
     * @throws BadElementException
     * @throws IOException 
     */
    public void costruisciPdf(String nomeFile, OutputStream stream) throws DocumentException, BadElementException, IOException{
      SimpleDateFormat dataNormale = new SimpleDateFormat("dd/MM/YYYY");
      
      Document biglietto = new Document();
      //PdfWriter.getInstance(biglietto, new FileOutputStream(nomeFile));
      PdfWriter.getInstance(biglietto, stream);
      
      String stringaInfoSpettacolo = "prova";    //la stringa contiene tutte le informazioni dello spettacolo. Costruire con strinbuulder
      QRCode prova = new QRCode(stringaInfoSpettacolo);
      
      
      /*inserimento metadati*/
      biglietto.open();
      biglietto.addAuthor(autore);
      biglietto.addCreationDate();
      biglietto.addCreator(autore);
      biglietto.addTitle(titolo);
      
      Paragraph titolo = new Paragraph(prenotazione.getSpettacolo().getFilm().getTitolo());
      titolo.add("   Id Spettacolo: " + Integer.toString(prenotazione.getSpettacolo().getId()));
      
      Paragraph info = new Paragraph();
      info.add("Lo spettacolo si terrà il giorno: ");
      info.add  (dataNormale.format(prenotazione.getSpettacolo().getData_ora().getTime()));
      info.add ("ID Prenotazione: " + Integer.toString(prenotazione.getId()));
      Paragraph sala = new Paragraph();
      sala.add("Sala: " + prenotazione.getSala().getNome());
      sala.add(" Il tuo posto è nella riga: "+prenotazione.getPosto().getRiga() + " e colonna: " +prenotazione.getPosto().getColonna());
      
      Paragraph prezzo = new Paragraph("Pagamento:" + Double.toString(prenotazione.getPrezzo()));
      prezzo.add(" Euro");
      
      Paragraph fondo = new Paragraph("Biglietto emesso in data: " + dataNormale.format(new Date()));       
      
      Paragraph immagineQr = new Paragraph("Mostra questo qrCode all'addetto del cinema: ");
      immagineQr.add(Image.getInstance(prova.getQrcode().toByteArray()));
      
      biglietto.add(titolo);
      biglietto.add(info);
      biglietto.add(sala);
      biglietto.add(prezzo);
      biglietto.add(fondo);
      biglietto.add(immagineQr);
      biglietto.close();
    }

    /**
     * Funzione per il ritorno del PDF
     * @return PDF
     */
    public ByteArrayOutputStream getPDF() {
        return datiBiglietto;
    }
    
    
}
