package by.epam.pialetskialiaksei.command.client.profile;

import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.*;
import by.epam.pialetskialiaksei.exception.CommandException;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.sql.DAO.*;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ViewProfileCommand implements Command {

    private static final long VersionUID = -3071536593627692473L;

    private static final Logger LOG = LogManager.getLogger(ViewProfileCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException, CommandException {
        LOG.debug("Command execution starts");
        try {
            String result = null;

            HttpSession session = request.getSession(false);
            String userEmail = String.valueOf(session.getAttribute("email"));

            UserDAO userDAO = new UserDAO();
            User user = userDAO.find(userEmail);

            ReportSheetDAO reportSheetDAO = new ReportSheetDAO();
            boolean results = reportSheetDAO.areResultExists();
            request.getSession().setAttribute("results", results);
            LOG.trace("Set the request attribute: 'results' = "
                    + results);

            request.setAttribute("first_name", user.getFirstName());
            LOG.trace("Set the request attribute: 'first_name' = "
                    + user.getFirstName());
            request.setAttribute("last_name", user.getLastName());
            LOG.trace("Set the request attribute: 'last_name' = "
                    + user.getLastName());
            request.setAttribute("email", user.getEmail());
            LOG.trace("Set the request attribute: 'email' = " + user.getEmail());
            request.setAttribute("role", user.getRole());
            LOG.trace("Set the request attribute: 'role' = " + user.getRole());

            String role = user.getRole();

            if ("client".equals(role)) {
                EntrantDAO entrantDAO = new EntrantDAO();
                Entrant entrant = entrantDAO.find(user);

                request.setAttribute("city", entrant.getCity());
                LOG.trace("Set the request attribute: 'city' = "
                        + entrant.getCity());
                request.setAttribute("district", entrant.getDistrict());
                LOG.trace("Set the request attribute: 'district' = "
                        + entrant.getDistrict());
                request.setAttribute("school", entrant.getSchool());
                LOG.trace("Set the request attribute: 'school' = "
                        + entrant.getSchool());
                request.setAttribute("diploma", entrant.getDiplomaMark());
                LOG.trace("Set the request attribute: 'diploma' = "
                        + entrant.getDiplomaMark());

                MarkDAO markDAO = new MarkDAO();
                List<ClientSubject> marks = markDAO.findMarks(entrant);
                Gson gson = new Gson();
                ClientSubject[] marksArray = marks.toArray(new ClientSubject[marks.size()]);
                request.setAttribute("jsonMarks", gson.toJson(marksArray));
                request.setAttribute("marks", marks);
                LOG.trace("Set the request attribute: 'marks' = "
                        + marks);

                if (results) {
                    FormOfEducation formOfEducation = reportSheetDAO.getFormOfEducation(user);
                    if (formOfEducation != null) {
                        request.setAttribute("formOfEducation", formOfEducation);
                    }
                }

                FacultyEntrantDAO facultyEntrantDAO = new FacultyEntrantDAO();
                Faculty faculty = facultyEntrantDAO.find(entrant.getId());
                if (faculty != null) {
                    request.setAttribute("faculty", faculty);
                }

                result = Path.FORWARD_CLIENT_PROFILE;
            } else if ("admin".equals(role)) {
                result = Path.FORWARD_ADMIN_PROFILE;
            }
            return result;
        } catch (DaoException e) {
            throw new CommandException(e);
        }
    }
}