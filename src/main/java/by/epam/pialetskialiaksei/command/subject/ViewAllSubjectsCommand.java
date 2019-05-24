package by.epam.pialetskialiaksei.command.subject;

import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.ClientSubject;
import by.epam.pialetskialiaksei.entity.Entrant;
import by.epam.pialetskialiaksei.entity.Subject;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.exception.CommandException;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.model.ClientSubjectsModel;
import by.epam.pialetskialiaksei.sql.DAO.EntrantDAO;
import by.epam.pialetskialiaksei.sql.DAO.MarkDAO;
import by.epam.pialetskialiaksei.sql.DAO.SubjectDAO;
import by.epam.pialetskialiaksei.sql.DAO.UserDAO;
import by.epam.pialetskialiaksei.util.ActionType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * View profile command.
 *
 * @author Mark Norkin
 */
public class ViewAllSubjectsCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

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

// TODO: 18.05.2019 delete client.equals
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
//		} else if ("client".equals(role)) {
//			result = Path.REDIRECT_TO_PROFILE;
//		}
            return result;
        } catch (DaoException e) {
            throw new CommandException(e);
        }
    }
}