/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.io.ByteArrayOutputStream;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

/**
 * Gestisce l'invio di una email.
 * @author ivanmorandi
 * @author Paolo
 */
public class SendEmail extends javax.mail.Authenticator{
    //questa email non esiste più, se volete usare questa cosa createvene un'altra!!
    String indirizzo = "progettoweb94@gmail.com";
    String password = "ok";
    Properties props;
    
    /**
     *
     */
    protected static SendEmail sendEmail;
    
    /**
     * Singleton, per avere una sola classe per l'invio di email
     * @return Oggetto per l'invio di email
     */
    public static SendEmail getInstance()
    {
        if(sendEmail == null)
        {
            sendEmail = new SendEmail();
        }
        return sendEmail;
    }
    
    /**
     * Costruttore
     */
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
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    }
    
    /**
     *Invia una email all'indirizzo specificato.
     * @param email_dest Email di destinazione
     * @param titolo Titolo della mail
     * @param messaggio Messaggio della mail
     * @throws MessagingException 
     */
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
    
    /**
     *Invia una email all'indirizzo specificato con l'allegato.
     * @param email_dest Email di destinazione
     * @param titolo Titolo della mail
     * @param messaggio Messaggio della mail
     * @param tickets Allegato dei ticket
     * @throws MessagingException 
     */
    public void send(String email_dest, String titolo, String messaggio, ByteArrayOutputStream tickets) throws MessagingException
    {
        Session session = Session.getDefaultInstance(props, this);
        
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(indirizzo));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email_dest));
        msg.setSubject(titolo);
        //msg.setText(messaggio);
        
        byte[] tickets_bytes = tickets.toByteArray();
        
        MimeBodyPart textBodyPart = new MimeBodyPart();
        textBodyPart.setText(messaggio);
        
        //construct the pdf body part
        DataSource dataSource = new ByteArrayDataSource(tickets_bytes, "application/pdf");
        MimeBodyPart pdfBodyPart = new MimeBodyPart();
        pdfBodyPart.setDataHandler(new DataHandler(dataSource));
        pdfBodyPart.setFileName("tickets.pdf");

        //construct the mime multi part
        MimeMultipart mimeMultipart = new MimeMultipart();
        mimeMultipart.addBodyPart(textBodyPart);
        mimeMultipart.addBodyPart(pdfBodyPart);
        
        msg.setContent(mimeMultipart);
        
        Transport.send(msg);
    }
    
    /**
     * Autenticazione con Gmail.
     * @return  autenticatore necessario alla connessione.
     */
    protected PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication(indirizzo, password);
    }
    
    
}
