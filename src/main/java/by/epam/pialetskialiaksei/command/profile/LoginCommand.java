package by.epam.pialetskialiaksei.command.profile;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.sql.DAO.UserDAO;
import by.epam.pialetskialiaksei.util.ActionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Invoked when user logins in the system.
 *
 * @author Mark Norkin.
 */
public class LoginCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = LogManager.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {

        LOG.debug("Start executing Command");
        String result = null;

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        User user = userDAO.find(email, password);
        LOG.trace("User found: " + user);
        if (user == null) {
            request.setAttribute("message",
                    "Cannot find user with such login/password");
            LOG.error("message: Cannot find user with such login/password");
            result = null;
//		} else if (!user.getActiveStatus()) {
//			request.setAttribute("errorMessage", "You are not registered!");
//			LOG.error("errorMessage: User is not registered or did not complete his registration.");
//			result = null;
        } else {
//			HttpSession oldSession = request.getSession(false);
//			HttpSession session = request.getSession(true);
//			if(oldSession != null){
//				String lang = (String) oldSession.getAttribute("lang");
//				session.setAttribute("lang", lang);
//			}else{
//				session.setAttribute("lang", "ru");
//			}
            HttpSession session = request.getSession(true);

            session.setAttribute("user", user.getEmail());
            LOG.trace("Set the session attribute 'user' = " + user.getEmail());

            session.setAttribute("userRole", user.getRole());
            LOG.trace("Set the session attribute: 'userRole' = "
                    + user.getRole());

            session.setAttribute("lang", user.getLang());
            LOG.trace("Set the session attribute 'lang' = " + user.getLang());

            LOG.info("User: " + user + " logged as " + user.getRole());

            result = Path.REDIRECT_TO_PROFILE;
        }
        return result;

    }
}