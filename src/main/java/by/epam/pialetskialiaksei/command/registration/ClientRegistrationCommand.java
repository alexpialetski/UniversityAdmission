package by.epam.pialetskialiaksei.command.registration;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.Entrant;
import by.epam.pialetskialiaksei.entity.Role;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.exception.CommandException;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.sql.DAO.EntrantDAO;
import by.epam.pialetskialiaksei.sql.DAO.UserDAO;
import by.epam.pialetskialiaksei.util.ActionType;
import by.epam.pialetskialiaksei.util.validation.ProfileInputValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Invoked when client registers in system.
 *
 * @author Mark Norkin
 */
public class ClientRegistrationCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = LogManager.getLogger(ClientRegistrationCommand.class);


    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException, CommandException {
        LOG.debug("Start executing Command");
        try {
            String email = request.getParameter(Fields.USER_EMAIL);
            String password = request.getParameter(Fields.USER_PASSWORD);
            String firstName = request.getParameter(Fields.USER_FIRST_NAME);
            String lastName = request.getParameter(Fields.USER_LAST_NAME);

            String town = request.getParameter(Fields.ENTRANT_CITY);
            String district = request.getParameter(Fields.ENTRANT_DISTRICT);
            String school = request.getParameter(Fields.ENTRANT_SCHOOL);

            String result = null;

            boolean valid = ProfileInputValidator.validateUserParameters(firstName,
                    lastName, email, password, "ru");

            LOG.trace(valid);
            valid = ProfileInputValidator.validateEntrantParameters(town, district,
                    school);

            if (!valid) {
                request.setAttribute("errorMessage", "Please fill all fields!");
                LOG.error("errorMessage: Not all fields are filled");
                result = Path.REDIRECT_CLIENT_REGISTRATION_PAGE;
            } else {
                User user = new User(email, password, firstName, lastName,
                        Role.CLIENT);
                UserDAO userDAO = new UserDAO();
                userDAO.create(user);
                LOG.trace("User record created: " + user);

                Entrant entrant = new Entrant(town, district, school, user);
                EntrantDAO entrantDAO = new EntrantDAO();
                entrantDAO.create(entrant);
                LOG.trace("Entrant record created: " + entrant);

//			MailUtils.sendConfirmationEmail(user);
//			request.setAttribute("successfulMessage",
//					"Your account was created. Check your email and confirm your registration.");
                result = Path.WELCOME_PAGE;
            }
            return result;
        } catch (DaoException e) {
            throw new CommandException(e);
        }
    }
}
