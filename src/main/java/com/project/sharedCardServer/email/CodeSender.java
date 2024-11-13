package com.project.sharedCardServer.email;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import static com.project.sharedCardServer.email.Config.LOGIN;
import static com.project.sharedCardServer.email.Config.PASSWORD;

public class CodeSender {
    private static String SUBJECT = "Подтвердите вход";

    private static String MESSAGE(String code) {
        return "<p style=\"font-family:Arial,sans-serif;color:#000000;font-size:19px;margin-top:14px;margin-bottom:0\">Здравствуйте!</p>" +
                "<p style=\"font-family:Arial,sans-serif;color:#000000;font-size:14px;line-height:17px;margin-top:30px;margin-bottom:0\">Ваш одноразовый код подтверждения: " + code +"</p>";

    }

    private CodeSender() {

    }

    public static void send(String email,String code) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp-mail.outlook.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(LOGIN, PASSWORD);
                }


            });
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(LOGIN));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
            message.setContent(MESSAGE(code), "text/html; charset=utf-8");
            message.setSubject(SUBJECT);
            message.setSentDate(new Date());
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <6; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
