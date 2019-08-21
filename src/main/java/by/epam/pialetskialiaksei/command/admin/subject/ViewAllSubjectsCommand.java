package by.epam.pialetskialiaksei.command.admin.subject;

import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.Subject;
import by.epam.pialetskialiaksei.exception.CommandException;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.sql.DAO.SubjectDAO;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ViewAllSubjectsCommand implements Command {

    private static final long VersionUID = -3071536593627692473L;

    private static final Logger LOG = LogManager.getLogger(ViewAllSubjectsCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException, CommandException {
        LOG.debug("Command execution starts");
        try {
            String result = null;

            HttpSession session = request.getSession(false);

            String role = (String) session.getAttribute("userRole");

            if ("admin".equals(role) || "client".equals(role)) {
                SubjectDAO subjectDAO = new SubjectDAO();
                List<Subject> allSubjects = subjectDAO.findAll();

                LOG.trace("Set the request attribute: 'allSubjects' = "
                        + allSubjects);
                request.setAttribute("allSubjects", allSubjects);
                Gson gson = new Gson();

                String subjectsJson = gson.toJson(allSubjects);
                LOG.trace("Set the request attribute: 'subjectsGson' = "
                        + subjectsJson);
                request.setAttribute("subjectsGson", subjectsJson);

                result = Path.FORWARD_SUBJECT_VIEW_ALL_ADMIN;
            }
            return result;
        } catch (DaoException e) {
            throw new CommandException(e);
        }
    }
}