package by.epam.pialetskialiaksei.command.client.registration;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.Mail;
import by.epam.pialetskialiaksei.entity.Role;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.exception.CommandException;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.sql.DAO.MailDAO;
import by.epam.pialetskialiaksei.sql.DAO.UserDAO;
import by.epam.pialetskialiaksei.util.MailUtils;
import by.epam.pialetskialiaksei.util.validation.ProfileInputValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SendConfirmationRegistrationCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = LogManager.getLogger(SendConfirmationRegistrationCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, CommandException {
        LOG.debug("Start executing Command");
        try {
            String email = request.getParameter(Fields.USER_EMAIL);
            String password = request.getParameter(Fields.USER_PASSWORD);
            String firstName = request.getParameter(Fields.USER_FIRST_NAME);
            String lastName = request.getParameter(Fields.USER_LAST_NAME);

            boolean valid = ProfileInputValidator.validateUserParameters(firstName,
                    lastName, email, password);

            LOG.trace(valid);

            if (!valid) {
                LOG.error("errorMessage: Not all fields are filled");
                return "{\"errorEng\":\"Please fill all fields properly.\"," +
                        "\"errorRu\":\"Пожалуйста, заполните все поля правильно.\"}";
            } else {
                User user = new User(email, password, firstName, lastName,
                        Role.CLIENT);
                UserDAO userDAO = new UserDAO();
                User user1 = userDAO.find(user.getEmail());
                if(user1!=null){
                    return "{\"errorEng\":\" There is already user with such email.\"," +
                            "\"errorRu\":\" Уже есть пользователь с таким эл. адресом.\"}";
                }
                MailDAO mailDAO = new MailDAO();
                String key = MailUtils.sendConfirmationEmail(user);
                Mail mail = new Mail();
                mail.setMailId(email + password);
                mail.setKey(key);
                mailDAO.create(mail);
            }
            return "{\"errorEng\":\"none\"}";
        } catch (MessagingException | DaoException e) {
            throw new CommandException(e);
        }
    }
}
