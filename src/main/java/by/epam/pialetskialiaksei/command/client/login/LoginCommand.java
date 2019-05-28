package by.epam.pialetskialiaksei.command.client.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.exception.CommandException;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.sql.DAO.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Invoked when user logins in the system.
 *
 */
public class LoginCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = LogManager.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException, CommandException {

        LOG.debug("Start executing Command");
        try {
            String result = null;

            String email = request.getParameter("email");
            String password = request.getParameter("password");

            UserDAO userDAO = new UserDAO();
            User user = userDAO.find(email, password);
            LOG.trace("User found: " + user);
            if (user == null) {
                LOG.error("message: Cannot find user with such login/password");
                request.getSession().setAttribute("errorEng",
                        "Cannot find user with such login/password");
                request.getSession().setAttribute("errorRu",
                        "Не удается найти пользователя с таким логином / паролем ");
                result = Path.REDIRECT_LOGIN_PAGE;
            } else {


                HttpSession session = request.getSession(true);

                session.setAttribute("email", user.getEmail());
                LOG.trace("Set the session attribute 'user' = " + user.getEmail());

                session.setAttribute("userRole", user.getRole());
                LOG.trace("Set the session attribute: 'userRole' = "
                        + user.getRole());


                LOG.info("User: " + user + " logged as " + user.getRole());

                result = Path.REDIRECT_TO_PROFILE;
            }
            return result;
        } catch (DaoException e) {
            throw new CommandException(e);
        }
    }
}