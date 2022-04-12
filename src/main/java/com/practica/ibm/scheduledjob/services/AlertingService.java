package com.practica.ibm.scheduledjob.services;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Service
public class AlertingService {

    private final String MAIL_SESSION_USERNAME = "testpracticaibm@gmail.com";

    private final String MAIL_SESSION_PASSWORD = "qwerty123$%^";

    private Session session;

    @SneakyThrows
    public void sendEmailAlert(String recepient, int contractNumber, String clientName) {
        if (session == null) {
            createEmailSession();
        }

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(MAIL_SESSION_USERNAME));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
        message.setSubject("contract expiration");

        String emailText = "Contract with number " + contractNumber
                + ", belonging to client " + clientName + " is about to expire.";

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(emailText, "text; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);

        Transport.send(message);
    }

    private void createEmailSession() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.mailtrap.io");
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.ssl.trust", "smtp.mailtrust.io");

        this.session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MAIL_SESSION_USERNAME, MAIL_SESSION_PASSWORD);
            }
        });
    }

    public void sendSMSAlert() {
        // to be implemented some other time maybe
    }


}
