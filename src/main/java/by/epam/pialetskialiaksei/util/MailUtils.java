package by.epam.pialetskialiaksei.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.Context;
import javax.naming.InitialContext;

import by.epam.pialetskialiaksei.command.client.registration.ClientRegistrationCommand;

import by.epam.pialetskialiaksei.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MailUtils {
    private static final Logger LOG = LogManager.getLogger(ClientRegistrationCommand.class);
    private static final Session SESSION = init();
    private static final String confirmSubjectRu = "Подтвердите регистрацию";
    private static final String confirmSubjectEng = "Confirm Registration";
    private final static String confirmationURL = "http://localhost:8080/SummaryTask4/controller?command=confirmRegistration&ID=";

    private static Session init() {
        Session session = null;
        try {
            Context initialContext = new InitialContext();
            Context envCtx = (Context) initialContext.lookup("java:comp/env");
            session = (Session) envCtx.lookup("mail/Session");
//            session = (Session) initialContext
//                    .lookup("java:comp/env/mail/Session");
        } catch (Exception ex) {
            LOG.error("mail session lookup error", ex);
        }
        return session;
    }

    public static String sendConfirmationEmail(User user) {
        String token = null;
        try {
            Message msg = new MimeMessage(SESSION);
            msg.setFrom(new InternetAddress(
                    "mail.university.admission@gmail.com"));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(user.getEmail()));

            token = new RandomString().nextString();

//            if (user.getLang() == null || user.getLang().equals("ru")) {
//            if (user.getLang() == null || user.getLang().equals("eng")) {
//                setContentToConfirmationEmailEng(msg, user, token);
//            } else if(user.getLang().equals("ru")){
//                setContentToConfirmationEmailRu(msg, user, token);
//            }
            setContentToConfirmationEmailEng(msg, user, token);

            msg.setSentDate(new Date());

            Transport.send(msg);
        } catch (MessagingException | UnsupportedEncodingException e) {
            LOG.error(e);
        }
        return token;
    }

    private static void setContentToConfirmationEmailRu(Message msg, User user, String token)
            throws MessagingException, UnsupportedEncodingException {
        msg.setSubject(confirmSubjectRu);

        Multipart multipart = new MimeMultipart();

        String encodedEmail = new String(Base64.getEncoder().encode(
                user.getEmail().getBytes(StandardCharsets.UTF_8)));

        InternetHeaders emailAndPass = new InternetHeaders();
        emailAndPass.addHeader("Content-type", "text/plain; charset=UTF-8");
        String hello = "Здравствуйте, " + user.getFirstName() + " "
                + user.getLastName() + " !\n"
                + "Вы успешно прошли регистрацию на нашем сайте.\n\n\n";

        String data = "\nВаш логин: " + user.getEmail() + "\nВаш пароль: "
                + user.getPassword() + "\n\n";

        MimeBodyPart greetingAndData = new MimeBodyPart(emailAndPass,
                (hello + data).getBytes(StandardCharsets.UTF_16));

        InternetHeaders headers = new InternetHeaders();
        headers.addHeader("Content-type", "text/html; charset=UTF-8");
        String confirmKey = "Ваш ключ: " + token;
        MimeBodyPart link = new MimeBodyPart(headers,
                confirmKey.getBytes(StandardCharsets.UTF_16));

        multipart.addBodyPart(greetingAndData);
        multipart.addBodyPart(link);

        msg.setContent(multipart);
    }

    private static void setContentToConfirmationEmailEng(Message msg, User user, String token)
            throws MessagingException, UnsupportedEncodingException {
        msg.setSubject(confirmSubjectEng);

        Multipart multipart = new MimeMultipart();

        String encodedEmail = new String(Base64.getEncoder().encode(
                user.getEmail().getBytes(StandardCharsets.UTF_8)));

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
                confirmKey.getBytes("UTF-8"));

        multipart.addBodyPart(greetingAndData);
        multipart.addBodyPart(link);

        msg.setContent(multipart);
    }
}
