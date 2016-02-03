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
import java.util.ArrayList;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

/**
 *
 * @author Paolo
 * @author Ivan
 */

/**
 * Classe che rappresenta un file pdf contentente uno o più biglietti.
 * @author Paolo
 */
public class PdfBiglietto {
    String nomeFile;
    ArrayList<Prenotazione> prenotazioni = new ArrayList<>();
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
        this.prenotazioni.add(prenotazione);
        datiBiglietto = new ByteArrayOutputStream();
    }

    /**
     * Costruttore
     * @param prenotazione Oggetto prenotazione per la creazione del ticket
     */
    public PdfBiglietto(Prenotazione prenotazione) {
        this.prenotazioni.add(prenotazione);
    }
    
    /**
     * Costruttore
     */
    public PdfBiglietto() {
        
    }
    
    /**
     * Aggiunge una prenotazione al biglietto. In presenza di più di una prenotazione, vengono stampati tutti in un unico file.
     * @param p prenotazione da aggiungere al biglietto 
     */
    public void aggiungiPrenotazione(Prenotazione p){
        if(p!=null)
            this.prenotazioni.add(p);
    }
    
    /**
     * Funzione che costruisce il file PDF
     * @param nomeFile Nome del file PDF
     * @param stream Stream sul quale verrà scritto il pdf
     * @throws DocumentException
     * @throws BadElementException
     * @throws IOException 
     */
    public void costruisciPdf(String nomeFile, OutputStream stream) throws DocumentException, BadElementException, IOException{
      SimpleDateFormat dataNormale = new SimpleDateFormat("dd/MM/YYYY");
      
      Document biglietto = new Document();
      //PdfWriter.getInstance(biglietto, new FileOutputStream(nomeFile));
      PdfWriter.getInstance(biglietto, stream);
      
      biglietto.open();
        biglietto.addAuthor(autore);
        biglietto.addCreationDate();
        biglietto.addCreator(autore);
        biglietto.addTitle(titolo);
      //dati inseriti nel QRCode
      for(Prenotazione p : prenotazioni){
        StringBuilder sb = new StringBuilder();
        sb.append(p.getId());
        sb.append("|");
        sb.append(p.getUtente().getNome());
        sb.append("|");
        sb.append(p.getPrezzo());
        sb.append("|");
        sb.append(p.getSala().getId());
        sb.append("|");
        sb.append(p.getPosto().getRiga());
        sb.append("|");
        sb.append(p.getPosto().getColonna());
        sb.append("|");
        sb.append(p.getSpettacolo().getFilm().getTitolo());
        sb.append("|");
        sb.append(dataNormale.format(p.getSpettacolo().getData_ora().getTime()));
        
        Paragraph completo = new Paragraph();
        completo.setSpacingAfter(80.0f);
        //in caso di problemi al QRcode (dimensioni eccessive, aspetti strani, etc), controllare la stringa in ingresso.
        QRCode qrBiglietto = new QRCode(sb.toString());
     

        Paragraph titolo = new Paragraph(p.getSpettacolo().getFilm().getTitolo());
        titolo.add("\nPrenotazione a nome dell'utente:  " + p.getUtente().getNome());
        titolo.add("   Id Spettacolo: " + Integer.toString(p.getSpettacolo().getId()));

        Paragraph info = new Paragraph();
        info.add("Lo spettacolo si terrà il giorno: ");
        info.add  (dataNormale.format(p.getSpettacolo().getData_ora().getTime()));
        info.add ("\nID Prenotazione: " + Integer.toString(p.getId()));
        Paragraph sala = new Paragraph();
        sala.add("Sala: " + p.getSala().getNome());
        sala.add(" Il tuo posto è nella riga: "+p.getPosto().getRiga() + " e colonna: " +p.getPosto().getColonna());

        Paragraph prezzo = new Paragraph("Pagamento:" + Double.toString(p.getPrezzo()));
        prezzo.add(" Euro");

        Paragraph fondo = new Paragraph("Biglietto emesso in data: " + dataNormale.format(new Date()));       
        fondo.add("\n Mostra questo qrCode all'addetto del cinema: ");
        
        Image qrCode = Image.getInstance(qrBiglietto.getQrcode().toByteArray());
        qrCode.setAlignment(Image.TOP);
        
        completo.add(titolo);
        completo.add(info);
        completo.add(sala);
        completo.add(prezzo);
        completo.add(fondo);
        completo.add(qrCode);
        
        biglietto.add(completo);
      }
      biglietto.close();
    }

    /**
     * Ritorna il PDF
     * @return output stream contenente il PDF
     */
    public ByteArrayOutputStream getPDF() {
        return datiBiglietto;
    }

    public void setPrenotazioni(ArrayList<Prenotazione> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }
    
    
}
