/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openbravo.pos.smj;

import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * clase para envío de email
 * class for sending email
 * @author Carlos Prieto - SmartJSP S.A.S.
 */
public class EmailSender {
    Session session;
    Properties props;

    public EmailSender(Properties props){
        session = Session.getDefaultInstance(props);
        session.setDebug(true);
        this.props =props;
        
    }
    
    /**
     * send an email to a list of accounts
     * @param toList
     * @param from
     * @param subject
     * @param text 
     */
    public void send (List<String> toList, String from, String subject, String text){
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            for(String to: toList){
                message.addRecipient(RecipientType.TO, new InternetAddress(to));
            }
            
            message.setSubject(subject);
            text = "<html><body>"+text+"</body></html>";
            message.setContent(text, "text/html");
            
            Transport t = session.getTransport("smtp");
            t.connect((String)props.get("mail.smtp.user"), (String)props.get("mail.smtp.password"));
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (Exception ex) {
            Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
