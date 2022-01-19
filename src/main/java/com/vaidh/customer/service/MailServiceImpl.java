package com.vaidh.customer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;

@Service
public class MailServiceImpl implements MailService {
    Logger logger = Logger.getLogger(MailServiceImpl.class.getName());
    @Value("${vaidh.email}")
    private String fromEmail;

    @Value("${vaidh.mail.password}")
    private String fromPassword;

    @Override
    public void sendMail(String body, String toAddress, String subject) {

        //Get properties object
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        //get Session
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail,fromPassword);
                    }
                });
        //compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(toAddress));
            message.setSubject(subject);
            message.setText(body);
            //send message
            Transport.send(message);
            logger.info("message sent successfully to " + toAddress);
        } catch (MessagingException e) {throw new RuntimeException(e);}

    }
}
