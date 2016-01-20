/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.io.ByteArrayOutputStream;
import net.glxn.qrgen.image.ImageType;
/**
 * Singolo QRCode contenente una stringa di testo. Wrapper per la libreria net.glxn.qrgen
 * @author ivanmorandi
 * @author Paolo
 */

/*
                QRCode qrcode = new QRCode("TEST");
                byte[] array = qrcode.getQrcode().toByteArray();
                response.setContentType("image/jpg");
                response.setContentLength(array.length);
                response.getOutputStream().write(array);

*/
public class QRCode {
    protected String testo;
    protected ByteArrayOutputStream qrcode;
    
    /**
     * Costruttore
     * @param testo Testo da inserire nel QRCode
     */
    public QRCode(String testo)
    {
        this.testo = testo;
        qrcode = Conversione();
    }
    
    /**
     * Funzione che converte il testo in QRCode
     * @return QRCode
     */
    protected ByteArrayOutputStream Conversione()
    {
        return (net.glxn.qrgen.QRCode.from(testo).to(ImageType.JPG).stream());
    }

    /**
     * Ritorna il testo
     * @return Testo stringa
     */
    public String getTesto() {
        return testo;
    }

    /**
     * Cambia il testo del QRCode
     * @param testo Nuovo testo per QRCode
     */
    public void setTesto(String testo) {
        this.testo = testo;
        qrcode = Conversione();
    }

    /**
     * Ritorna il QRCode della stringa
     * @return ByteArrayOutputStream contenente il qrcode
     */
    public ByteArrayOutputStream getQrcode() {
        return qrcode;
    }
    
    
    
    
}
