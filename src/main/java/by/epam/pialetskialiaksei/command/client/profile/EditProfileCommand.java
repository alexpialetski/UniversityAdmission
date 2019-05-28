package by.epam.pialetskialiaksei.command.client.profile;

import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.Entrant;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EditProfileCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(EditProfileCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, CommandException {
        LOG.debug("Start executing Command");
        try {
            HttpSession session = request.getSession(false);
            String role = String.valueOf(session.getAttribute("userRole"));

            String firstName = request.getParameter("first-name");
            String lastName = request.getParameter("last-name");
            String email = (String) session.getAttribute("email");

            boolean valid = ProfileInputValidator.validateUserParameters(firstName, email);

            if (!valid) {
                request.getSession().setAttribute("errorEng",
                        "Please fill all fields properly!");
                request.getSession().setAttribute("errorRu",
                        "Пожалуйста, заполните все поля правильно!");
                LOG.error("errorMessage: Not all fields are properly filled");
                return Path.REDIRECT_TO_PROFILE;
            } else {
                UserDAO userDAO = new UserDAO();
                User user = userDAO.find(email);

                if (role.equals("client")) {
                    EntrantDAO entrantDAO = new EntrantDAO();
                    Entrant entrant = entrantDAO.find(user);

                    String city = request.getParameter("city");
                    String district = request.getParameter("district");
                    String school = request.getParameter("school");

                    valid = ProfileInputValidator.validateEntrantParameters(city,
                            district, school);

                    if (!valid) {
                        request.getSession().setAttribute("errorEng",
                                "Please fill all fields properly!");
                        request.getSession().setAttribute("errorRu",
                                "Пожалуйста, заполните все поля правильно!");
                        LOG.error("errorMessage: Not all fields are properly filled");
                        return Path.REDIRECT_TO_PROFILE;
                    }

                    entrant.setCity(city);
                    entrant.setDistrict(district);
                    entrant.setSchool(school);
                    entrantDAO.update(entrant);
                }
                user.setFirstName(firstName);
                user.setLastName(lastName);
                userDAO.update(user);

                return Path.REDIRECT_TO_PROFILE;
            }
        } catch (DaoException e) {
            throw new CommandException(e);
        }
    }
}
