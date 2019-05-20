package by.epam.pialetskialiaksei.command.registration;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.Entrant;
import by.epam.pialetskialiaksei.entity.Role;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.sql.DAO.EntrantDAO;
import by.epam.pialetskialiaksei.sql.DAO.UserDAO;
import by.epam.pialetskialiaksei.util.ActionType;
import by.epam.pialetskialiaksei.util.MailUtils;
import by.epam.pialetskialiaksei.util.validation.ProfileInputValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Invoked when client registers in system.
 *
 * @author Mark Norkin
 */
public class SendConfirmationRegistrationCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = LogManager.getLogger(SendConfirmationRegistrationCommand.class);


    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {
        LOG.debug("Start executing Command");
        String email = request.getParameter(Fields.USER_EMAIL);
        LOG.trace("Get the request attribute: 'email' = "
                + email);
        String password = request.getParameter(Fields.USER_PASSWORD);
        LOG.trace("Get the request attribute: 'password' = "
                + password);
        String firstName = request.getParameter(Fields.USER_FIRST_NAME);
        LOG.trace("Get the request attribute: 'firstName' = "
                + firstName);
        String lastName = request.getParameter(Fields.USER_LAST_NAME);
        LOG.trace("Get the request attribute: 'lastName' = "
                + lastName);
        String lang = request.getParameter(Fields.USER_LANG);
        LOG.trace("Get the request attribute: 'lang' = "
                + lang);


        String result = null;

        boolean valid = ProfileInputValidator.validateUserParameters(firstName,
                lastName, email, password, lang);

        LOG.trace(valid);

        if (!valid) {
            request.setAttribute("errorMessage", "Please fill all fields!");
            LOG.error("errorMessage: Not all fields are filled");
            result = Path.REDIRECT_CLIENT_REGISTRATION_PAGE;
        } else {
            User user = new User(email, password, firstName, lastName,
                    Role.CLIENT, lang);
            String token = MailUtils.sendConfirmationEmail(user);
            LOG.trace("Confirmation token:" + token);
        }
        return "{\"error\":\"none\"}";
    }
}