package by.epam.pialetskialiaksei.command.faculty;

import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.*;
import by.epam.pialetskialiaksei.exception.CommandException;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.model.FacultyInfoModel;
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

/**
 * View profile command.
 *
 * @author Mark Norkin
 */
public class ViewFacultyCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = LogManager.getLogger(ViewFacultyCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException, CommandException {
        LOG.debug("Command execution starts");
        try {
            String result = null;

            HttpSession session = request.getSession(false);

            int facultyId = Integer.parseInt(request.getParameter("facultyId"));
            LOG.trace("Get the request parameter: 'facultyId' = "
                    + facultyId);
            session.setAttribute("prevCommand", "viewFaculty&facultyId=" + facultyId);

            FacultyDAO facultyDAO = new FacultyDAO();
            Faculty faculty = facultyDAO.find(facultyId);
            request.setAttribute("faculty", faculty);
            LOG.trace("Set request parameter: 'faculty' = "
                    + faculty);

            FacultySubjectDAO facultySubjectDAO = new FacultySubjectDAO();
            List<Subject> subjects = facultySubjectDAO.findById(facultyId);
            String temp = new Gson().toJson(subjects);
            request.setAttribute("subjects", temp);
//        LOG.trace("Set request parameter: 'subjects' = "
//                + subjects);

            FacultyInfoModel facultyInfoModel = new FacultyInfoModel(faculty, subjects);
            request.setAttribute("facultyInfo", facultyInfoModel);
            LOG.trace("Set request parameter: 'facultyInfoModel' = "
                    + facultyInfoModel);
//        FacultyEntrantDAO facultyEntrantDAO = new FacultyEntrantDAO();
//        List<User> users = facultyEntrantDAO.findUsers(facultyId);
//        request.setAttribute("users", users);
//        LOG.trace("Set request parameter: 'users' = "
//                + users);

//Entrant entrant = new Entrant();
////        FacultyEntrantDAO facultyEntrantDAO = new FacultyEntrantDAO();
//        FacultyEntrant facultyEntrant = facultyEntrantDAO.find(entrant);
//        if (facultyEntrant != null) {
//            request.setAttribute("applied", facultyEntrant.getFacultyId());
//        }

            String role = String.valueOf(session.getAttribute("userRole"));
            if ("client".equals(role)) {
//            result = Path.FORWARD_FACULTY_VIEW_CLIENT;
                result = Path.FORWARD_FACULTY_VIEW_ADMIN;
            } else if ("admin".equals(role)) {
                result = Path.FORWARD_FACULTY_VIEW_ADMIN;
            }
            return result;
        } catch (DaoException e) {
            throw new CommandException(e);
        }
    }
}
