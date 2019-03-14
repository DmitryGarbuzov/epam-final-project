package com.garbuzov.diary.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class MailSender {

    private static MailSender instance;
    private static Properties properties = new Properties();
    private static Session session;
    private final static String SMTP_PROPERTIES = "smtp.properties";
    private final static String USERNAME = "nivelada360@gmail.com";
    private final static String PASSWORD = "247l734F001";
    private final static String TITLE = "Регистрация в сервисе электронный дневник Doskort.com";
    private final static String MESSAGE_PART_1 = ",\nблагодарим, что выбрали наш сервис.\nВаш пароль : ";
    private final static String MESSAGE_PART_2 = "\nC уважением, команда Doskort.";
    private final static String SPACE = " ";

    private MailSender() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            properties.load(classLoader.getResourceAsStream(SMTP_PROPERTIES));
            session = Session.getInstance(properties, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(USERNAME, PASSWORD);
                }
            });
        } catch (IOException e) {

        }
    }

    public static MailSender getInstance() {
        if (instance == null) {
            instance = new MailSender();
        }
        return instance;
    }

    public void send(String toEmail, String firstName, String lastName, String password){
        toEmail = "garbuzov1999@inbox.ru";
        String text = lastName + SPACE + firstName + MESSAGE_PART_1 + password + MESSAGE_PART_2;
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(TITLE);
            message.setText(text);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}