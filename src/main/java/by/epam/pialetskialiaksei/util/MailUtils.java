package by.epam.pialetskialiaksei.util;

import by.epam.pialetskialiaksei.command.client.registration.ClientRegistrationCommand;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Properties;

public class MailUtils {
    private static final Logger LOG = LogManager.getLogger(ClientRegistrationCommand.class);
    private static final String confirmSubjectRu = "Подтвердите регистрацию";
    private static final String confirmSubjectEng = "Confirm Registration";

    private static final Session SESSION = init();

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

        String token = new RandomString().nextString();
        MailUtils.sendEmail(user, token);

        return token;
    }


    private static void setContentToConfirmationEmailEng(Message msg, User user, String token)
            throws MessagingException, UnsupportedEncodingException {
        msg.setSubject(confirmSubjectEng);

        Multipart multipart = new MimeMultipart();

        InternetHeaders emailAndPass = new InternetHeaders();
        emailAndPass.addHeader("Content-type", "text/plain; charset=UTF-8");
        String hello = "Hello, " + user.getFirstName() + " "
                + user.getLastName() + " !\n"
                + " You should insert key down below to get registered on our web-site!\n\n\n";

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

    private static MimeMessage emailMessage(User user, String token) throws MessagingException, UnsupportedEncodingException {
        MimeMessage emailMessage = new MimeMessage(SESSION);
        emailMessage.setFrom(new InternetAddress(
                "mail.university.admission@gmail.com"));
        emailMessage.addRecipient(Message.RecipientType.TO,
                new InternetAddress(user.getEmail()));
        setContentToConfirmationEmailEng(emailMessage, user, token);
        emailMessage.setSentDate(new Date());
        return emailMessage;
    }

    private static void sendEmail(User user, String token) throws MessagingException, UnsupportedEncodingException, CommandException {
        String fromUser;
        String fromUserEmailPassword;
        String emailHost;
        String protocol;

        try (InputStream input = MailUtils.class.getClassLoader().getResourceAsStream(("mail.properties"))) {
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
    }
}
