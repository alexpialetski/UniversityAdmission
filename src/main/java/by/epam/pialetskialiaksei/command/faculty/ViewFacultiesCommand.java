package by.epam.pialetskialiaksei.command.faculty;

import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.Entrant;
import by.epam.pialetskialiaksei.entity.FacultyEntrant;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.exception.CommandException;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.sql.DAO.EntrantDAO;
import by.epam.pialetskialiaksei.sql.DAO.FacultyEntrantDAO;
import by.epam.pialetskialiaksei.sql.DAO.ReportSheetDAO;
import by.epam.pialetskialiaksei.sql.DAO.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ViewFacultiesCommand implements Command {

    private static final long VersionUID = -3071536593627692473L;

    private static final Logger LOG = LogManager.getLogger(ViewFacultiesCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException, CommandException {
        LOG.debug("Command execution starts");
        try {
            HttpSession session = request.getSession(false);
            String userEmail = String.valueOf(session.getAttribute("email"));
            UserDAO userDAO = new UserDAO();
            User user = userDAO.find(userEmail);

            String role = String.valueOf(session.getAttribute("userRole"));

            ReportSheetDAO reportSheetDAO = new ReportSheetDAO();
            boolean results = reportSheetDAO.areResultExists();
            request.setAttribute("results", results);

            if (role.equals("client")) {
                EntrantDAO entrantDAO = new EntrantDAO();
                Entrant entrant = entrantDAO.find(user);

                FacultyEntrantDAO facultyEntrantDAO = new FacultyEntrantDAO();
                FacultyEntrant facultyEntrant = facultyEntrantDAO.find(entrant);
                if (facultyEntrant != null) {
                    request.setAttribute("applied", facultyEntrant.getFacultyId());
                }
            }
            return Path.FORWARD_FACULTY_VIEW_ALL_CLIENT;
        } catch (DaoException e) {
            throw new CommandException(e);
        }
    }
}