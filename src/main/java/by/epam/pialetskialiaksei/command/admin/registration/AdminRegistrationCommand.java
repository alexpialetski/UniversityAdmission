package by.epam.pialetskialiaksei.command.admin.registration;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.Role;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.exception.CommandException;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.sql.DAO.UserDAO;
import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.util.validation.ProfileInputValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Invoked when administrator wants to add another admin user.
 *
 * @author Mark Norkin
 */
public class AdminRegistrationCommand implements Command {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LogManager.getLogger(AdminRegistrationCommand.class);

    public String execute(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException, CommandException {
        LOG.debug("Start executing Command");
        try {
            String email = request.getParameter(Fields.USER_EMAIL);
            String password = request.getParameter(Fields.USER_PASSWORD);
            String firstName = request.getParameter(Fields.USER_FIRST_NAME);
            String lastName = request.getParameter(Fields.USER_LAST_NAME);

            boolean valid = ProfileInputValidator.validateUserParameters(firstName,
                    lastName, email, password);

            String result = null;

            if (!valid) {
                request.setAttribute("errorMessage", "Please fill all fields!");
                LOG.error("errorMessage: Not all fields are filled");
                result = Path.REDIRECT_TO_PROFILE;
            } else if (valid) {
                User user = new User(email, password, firstName, lastName,
                        Role.ADMIN);

                UserDAO userDAO = new UserDAO();
                userDAO.create(user);

                LOG.trace("User record created: " + user);
                result = Path.REDIRECT_TO_PROFILE;
            }
            return result;
        } catch (DaoException e) {
            throw new CommandException(e);
        }
    }
}
