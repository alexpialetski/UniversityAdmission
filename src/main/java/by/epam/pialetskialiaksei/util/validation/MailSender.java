package by.epam.pialetskialiaksei.util.validation;

import by.epam.pialetskialiaksei.command.client.registration.ClientRegistrationCommand;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.exception.CommandException;
import by.epam.pialetskialiaksei.util.RandomString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

public class MailSender {
    private static final Logger LOG = LogManager.getLogger(ClientRegistrationCommand.class);
    private static final String confirmSubjectRu = "Подтвердите регистрацию";
    private static final String confirmSubjectEng = "Confirm Registration";

    private static final Session SESSION = init();

    public static void main(String[] args) throws UnsupportedEncodingException, MessagingException, CommandException {
        User user = new User();
        user.setEmail("surnamen746@gmail.com");
        user.setFirstName("Yoy");
        user.setLastName("HowIsIt");
        user.setPassword("1234");
//        user.setLang("ru");

//        MailSender javaEmail = new MailSender();
//        String token = new RandomString().nextString();
        MailSender.sendConfirmationEmail(user);
//        javaEmail.sendEmail(user, token);

//        return token;
    }

    private static Session init() {
        Session session = null;
        try {
            Properties emailProperties = System.getProperties();
            emailProperties.put("mail.smtp.port", "587");
            emailProperties.put("mail.smtp.auth", "true");
            emailProperties.put("mail.smtp.starttls.enable", "true");
            emailProperties.setProperty("mail.imap.partialfetch", "false");
            session = Session.getDefaultInstance(emailProperties, null);
        } catch (Exception ex) {
            LOG.error("mail session lookup error", ex);
        }
        return session;
    }

    public static String sendConfirmationEmail(User user) throws MessagingException, UnsupportedEncodingException, CommandException {

//        MailSender javaEmail = new MailSender();
        String token = new RandomString().nextString();
        MailSender.sendEmail(user, token);

        return token;
    }

    private static void setContentToConfirmationEmailRu(Message msg, User user, String token)
            throws MessagingException, UnsupportedEncodingException {
        msg.setSubject(confirmSubjectRu);

        Multipart multipart = new MimeMultipart();

        String encodedEmail = new String(Base64.getEncoder().encode(
                user.getEmail().getBytes(StandardCharsets.ISO_8859_1)));

        InternetHeaders emailAndPass = new InternetHeaders();
        emailAndPass.addHeader("Content-type", "text/plain; charset=ISO-8859-1");
        String hello = "Здравствуйте, " + user.getFirstName() + " "
                + user.getLastName() + " !\n"
                + "Вы успешно прошли регистрацию на нашем сайте.\n\n\n";

        String data = "\nВаш логин: " + user.getEmail() + "\nВаш пароль: "
                + user.getPassword() + "\n\n";

        MimeBodyPart greetingAndData = new MimeBodyPart(emailAndPass,
                (hello + data).getBytes(StandardCharsets.ISO_8859_1));

        InternetHeaders headers = new InternetHeaders();
        headers.addHeader("Content-type", "text/html; charset=ISO-8859-1");
        String confirmKey = "Ваш ключ: " + token;
        MimeBodyPart link = new MimeBodyPart(headers,
                confirmKey.getBytes(StandardCharsets.ISO_8859_1));

        multipart.addBodyPart(greetingAndData);
        multipart.addBodyPart(link);

        msg.setContent(multipart);
    }

    private static void setContentToConfirmationEmailEng(Message msg, User user, String token)
            throws MessagingException, UnsupportedEncodingException {
        msg.setSubject(confirmSubjectEng);

        Multipart multipart = new MimeMultipart();

//        String encodedEmail = new String(Base64.getEncoder().encode(
//                user.getEmail().getBytes(StandardCharsets.UTF_8)));

        InternetHeaders emailAndPass = new InternetHeaders();
        emailAndPass.addHeader("Content-type", "text/plain; charset=UTF-8");
        String hello = "Hello, " + user.getFirstName() + " "
                + user.getLastName() + " !\n"
                + " You successfuly registered on our web-site!\n\n\n";

        String data = "\nLogin: " + user.getEmail() + "\nPassword: "
                + user.getPassword() + "\n\n";

        MimeBodyPart greetingAndData = new MimeBodyPart(emailAndPass,
                (hello + data).getBytes(StandardCharsets.UTF_8));

        InternetHeaders headers = new InternetHeaders();
        headers.addHeader("Content-type", "text/html; charset=UTF-8");
        String confirmKey = "Your key: " + token;
        MimeBodyPart link = new MimeBodyPart(headers,
                confirmKey.getBytes(StandardCharsets.UTF_8));

        multipart.addBodyPart(greetingAndData);
        multipart.addBodyPart(link);

        msg.setContent(multipart);
    }

    private static MimeMessage emailMessage(User user, String token) throws  MessagingException, UnsupportedEncodingException {
        String[] toEmails = {"surnamen746@gmail.com"};
//        String emailSubject = "Test email subject";
//        String emailBody = "This is an email sent by <b>//howtodoinjava.com</b>.";
        MimeMessage emailMessage = new MimeMessage(SESSION);

        emailMessage.setFrom(new InternetAddress(
                "mail.university.admission@gmail.com"));
        emailMessage.addRecipient(Message.RecipientType.TO,
                new InternetAddress(user.getEmail()));

        setContentToConfirmationEmailEng(emailMessage, user, token);

//        if (user.getLang() == null || user.getLang().equals("en")) {
//            setContentToConfirmationEmailEng(emailMessage, user, token);
//        } else if(user.getLang().equals("ru")){
//            setContentToConfirmationEmailRu(emailMessage, user, token);
//        }

//        for (int i = 0; i < toEmails.length; i++)
//        {
//            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));
//        }

        emailMessage.setSentDate(new Date());
        return emailMessage;
    }

    private static void sendEmail(User user, String token) throws MessagingException, UnsupportedEncodingException, CommandException {
        String fromUser = null;
        String fromUserEmailPassword = null;
        String emailHost = null;
        String protocol = null;

        try (InputStream input = MailSender.class.getClassLoader().getResourceAsStream(("mail.properties"))) {
            Properties prop = new Properties();
            prop.load(input);
            fromUser = prop.getProperty("from");
            fromUserEmailPassword = prop.getProperty("password");
            emailHost = prop.getProperty("emailHost");
            protocol = prop.getProperty("protocol");
        } catch (IOException e) {
            throw new CommandException("can't load property file", e);
        }
        Transport transport = SESSION.getTransport(protocol);
        transport.connect(emailHost, fromUser, fromUserEmailPassword);
        MimeMessage emailMessage = emailMessage(user, token);
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
        System.out.println("Email sent successfully.");
    }
}
