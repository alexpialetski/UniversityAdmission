package by.epam.pialetskialiaksei.command.profile;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.util.ActionType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Invoked when user wants to logout from the system.
 *
 * @author Mark Norkin
 */
public class LogoutCommand implements Command {

    private static final long serialVersionUID = -2785976616686657267L;

    private static final Logger LOG = LogManager.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {
        LOG.debug("Start executing Command");

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        LOG.debug("Finished executing Command");

        return Path.WELCOME_PAGE;
    }
}