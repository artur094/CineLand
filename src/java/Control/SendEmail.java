/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author ivanmorandi
 */
public class SendEmail extends javax.mail.Authenticator{
    String indirizzo = "progettoweb94@gmail.com";
    String password = "passwordMoltoSicura";
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
    
    public void send(String email_dest, String titolo, String messaggio) throws MessagingException
    {
        Session session = Session.getDefaultInstance(props, this);
        
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(indirizzo));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email_dest));
        msg.setSubject(titolo);
        msg.setText(messaggio);
        Transport.send(msg);
    }
    
    protected PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication(indirizzo, password);
    }
    
    
}