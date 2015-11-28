package com.fasoo.logic.utils.mail;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtils {

    public static final String JICTORURL = "http://www.jictor.com";

    private static Message message;

    public static boolean setMail(String toEmail, String url) {

        if (!isValidMailAddress(toEmail)) {
            return false;
        }

        Session session = getSession();
        try {
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress("jictorgummybear@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("JICTOR");
            message.setText("CLICK THIS URL" + "\n\n" + url);
            return true;
        } catch (MessagingException e) {
            e.getStackTrace();
            return false;
        }
    }

    public static boolean sendMail() {

        try {
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean sendMaster(String toEmail, String url) {

        Session session = getSession();
        try {
            Message masterMessage = new MimeMessage(session);
            masterMessage.setFrom(new InternetAddress("jictorgummybear@gmail.com"));
            masterMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse("syoung0226@gmail.com"));
            masterMessage.setSubject("JICTOR");
            masterMessage.setText("User : " + toEmail + "\n\nNow Click this system" + "\n\nThis is URL\n\n" + url);
            Transport.send(masterMessage);
            return true;
        } catch (MessagingException e) {
            e.getStackTrace();
            return false;
        }
    }

    private static Session getSession() {

        final String username = "";
        final String password = "";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        return Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    private static boolean isValidMailAddress(String mail) {
        try {
            InternetAddress mailAddress = new InternetAddress(mail);
            mailAddress.validate();
            return true;
        } catch (AddressException ex) {
            return false;
        }
    }
}
