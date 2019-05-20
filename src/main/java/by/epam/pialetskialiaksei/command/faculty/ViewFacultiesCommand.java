package by.epam.pialetskialiaksei.command.faculty;

import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.*;
import by.epam.pialetskialiaksei.sql.DAO.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * View profile command.
 *
 * @author Mark Norkin
 */
public class ViewFacultiesCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = LogManager.getLogger(ViewFacultiesCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {
        LOG.debug("Command execution starts");
        String result = null;

        HttpSession session = request.getSession(false);
        String userEmail = String.valueOf(session.getAttribute("user"));
        UserDAO userDAO = new UserDAO();
        User user = userDAO.find(userEmail);

        EntrantDAO entrantDAO = new EntrantDAO();
        Entrant entrant = entrantDAO.find(user);

        FacultyEntrantDAO facultyEntrantDAO = new FacultyEntrantDAO();
        FacultyEntrant facultyEntrant = facultyEntrantDAO.find(entrant);
        if (facultyEntrant != null) {
            request.setAttribute("applied", facultyEntrant.getFacultyId());
        }

        String role = String.valueOf(session.getAttribute("userRole"));
        if ("client".equals(role)) {
            result = Path.FORWARD_FACULTY_VIEW_ALL_CLIENT;
        } else if ("admin".equals(role)) {
            result = Path.FORWARD_FACULTY_VIEW_ALL_CLIENT;
        }
        return result;
    }
}