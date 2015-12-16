/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.util.Properties;

/**
 *
 * @author ivanmorandi
 */
public class SendEmail {
    String indirizzo_cineland = "progettoWeb@gmail.com";
    String password_email = "passwordMoltoSicura";
    Properties props;
    
    protected static SendEmail sendEmail;
    
    public static SendEmail getInstance()
    {
        if(sendEmail == null)
        {
            sendEmail = new SendEmail();
        }
        return sendEmail;
    }
    
    public SendEmail()
    {
        props = System.getProperties();
        props.setProperty("mail.tranport.protocol", "smtp");
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("isSSL","true");
        props.put( "mail.debug", "true" );
        
    }
    
    public void send(String email_dest, String titolo, String messaggio)
    {
        
    }
    
    
}

//private class SMTPAuthenticator extends javax.mail.Authenticator{
//    public
//}