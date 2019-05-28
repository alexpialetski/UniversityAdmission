package by.epam.pialetskialiaksei.command.client.registration;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.Mail;
import by.epam.pialetskialiaksei.exception.CommandException;
import by.epam.pialetskialiaksei.sql.DAO.MailDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ConfirmRegistrationCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = LogManager.getLogger(ConfirmRegistrationCommand.class);


    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException, CommandException {
        LOG.debug("Start executing Command");
        try {
            String email = request.getParameter(Fields.USER_EMAIL);
            String password = request.getParameter(Fields.USER_PASSWORD);
            String userKey = request.getParameter("key");

            MailDAO mailDAO = new MailDAO();
            Mail mail = new Mail();
            mail.setMailId(email + password);
            Mail dbMail = mailDAO.find(mail);

            if (dbMail.getKey() != null && userKey.equals(dbMail.getKey())) {
                mailDAO.delete(mail);
                return "{\"errorEng\":\"none\"}";
            } else {
                mailDAO.delete(mail);
                return "{\"errorEng\":\"Key is not valid\"," +
                        "\"errorRu\":\"Ключ не верен\"}";
            }
        } catch (Exception e) {
            throw new CommandException(e);
        }
    }
}
