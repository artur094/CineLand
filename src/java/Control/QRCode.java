/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.io.ByteArrayOutputStream;
import net.glxn.qrgen.image.ImageType;
/**
 *
 * @author ivanmorandi
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
    
    public QRCode(String testo)
    {
        this.testo = testo;
        qrcode = Conversione();
    }
    
    protected ByteArrayOutputStream Conversione()
    {
        return (net.glxn.qrgen.QRCode.from(testo).to(ImageType.JPG).stream());
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
        qrcode = Conversione();
    }

    public ByteArrayOutputStream getQrcode() {
        return qrcode;
    }
    
    
    
    
}
