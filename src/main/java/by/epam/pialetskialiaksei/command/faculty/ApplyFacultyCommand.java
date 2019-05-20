package by.epam.pialetskialiaksei.command.faculty;

import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.*;
import by.epam.pialetskialiaksei.model.FacultyInfoModel;
import by.epam.pialetskialiaksei.sql.DAO.*;
import by.epam.pialetskialiaksei.util.ActionType;
import com.google.gson.Gson;
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
//public class ApplyFacultyCommand extends Command {
public class ApplyFacultyCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = LogManager.getLogger(ApplyFacultyCommand.class);

    @Override
    public String execute(HttpServletRequest request,
//                          HttpServletResponse response, ActionType actionType)
                          HttpServletResponse response)
            throws IOException, ServletException {
        LOG.debug("Command execution starts");
        String alreadyApplied = request.getParameter("applied");
        if (alreadyApplied != null) {
            return "{\"error\":\"You are already applied\"}";
        } else {
            HttpSession session = request.getSession(false);
            String userEmail = String.valueOf(session.getAttribute("user"));
            UserDAO userDAO = new UserDAO();
            User user = userDAO.find(userEmail);

            EntrantDAO entrantDAO = new EntrantDAO();
            Entrant entrant = entrantDAO.find(user);

            FacultyEntrantDAO facultyEntrantDAO = new FacultyEntrantDAO();

            int facultyId = Integer.parseInt(request.getParameter("facultyId"));

            try {
                MarkDAO markDAO = new MarkDAO();
                List<Subject> subjectsOfEntrant = markDAO.findSubjectsOfEntrant(entrant);
                FacultySubjectDAO facultySubjectDAO = new FacultySubjectDAO();
                List<Subject> facultySubjects = facultySubjectDAO.findById(facultyId);
                if (subjectsOfEntrant.equals(facultySubjects)) {
                    FacultyDAO facultyDAO = new FacultyDAO();
                    facultyEntrantDAO.create(new FacultyEntrant(facultyId, entrant.getId()));
                    return "{\"error\":\"none\"}";
                } else {
                    return "{\"error\":\"Not the same subjects\"}";
                }
            } catch (Exception ignored) {
                return "{error:\"Problems with applying, update page pls...\"}";
            }
        }
    }
}


//    protected String doAjax(HttpServletRequest request, HttpServletResponse response) {
//        String alreadyApplied = request.getParameter("applied");
//        if (alreadyApplied != null) {
//            return "{\"error\":\"You are already applied\"}";
//        } else {
//            HttpSession session = request.getSession(false);
//            String userEmail = String.valueOf(session.getAttribute("user"));
//            UserDAO userDAO = new UserDAO();
//            User user = userDAO.find(userEmail);
//
//            EntrantDAO entrantDAO = new EntrantDAO();
//            Entrant entrant = entrantDAO.find(user);
//
//            FacultyEntrantDAO facultyEntrantDAO = new FacultyEntrantDAO();
//
//            int facultyId = Integer.parseInt(request.getParameter("facultyId"));
//
//            try {
//                MarkDAO markDAO = new MarkDAO();
//                List<Subject> subjectsOfEntrant = markDAO.findSubjectsOfEntrant(entrant);
//                FacultySubjectDAO facultySubjectDAO = new FacultySubjectDAO();
//                List<Subject> facultySubjects = facultySubjectDAO.findById(facultyId);
//                if(subjectsOfEntrant.equals(facultySubjects)){
//                    FacultyDAO facultyDAO = new FacultyDAO();
//                    facultyEntrantDAO.create(new FacultyEntrant(facultyId, entrant.getId()));
//                    return "{\"error\":\"none\"}";
//                }else{
//                    return "{\"error\":\"Not the same subjects\"}";
//                }
//            }catch (Exception ignored){
//                return "{error:\"Problems with applying, update page pls...\"}";
//            }
//        }
//    }
