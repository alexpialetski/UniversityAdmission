package by.epam.pialetskialiaksei.command.client.registration;

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
import by.epam.pialetskialiaksei.util.validation.ProfileInputValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClientRegistrationCommand implements Command {

    private static final long VersionUID = -3071536593627692473L;

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

            boolean validUser = ProfileInputValidator.validateUserParameters(firstName,
                    lastName, email, password);

            boolean validEntrant = ProfileInputValidator.validateEntrantParameters(town, district,
                    school);
            if (!validUser||!validEntrant) {
                request.getSession().setAttribute("errorEng",
                        "Please fill all fields properly!");
                request.getSession().setAttribute("errorRu",
                        "Пожалуйста, заполните все поля правильно!");
                LOG.error("errorMessage: Not all fields are filled");
                return Path.REDIRECT_CLIENT_REGISTRATION_PAGE;
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

                return Path.LOGIN_PAGE;
            }
        } catch (DaoException e) {
            throw new CommandException(e);
        }
    }
}
