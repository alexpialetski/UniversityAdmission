package by.epam.pialetskialiaksei.command.profile;

import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.Entrant;
import by.epam.pialetskialiaksei.entity.Subject;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.model.SubjectsModel;
import by.epam.pialetskialiaksei.sql.DAO.*;
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

public class GetOtherSubjectsCommand implements Command {
    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = LogManager.getLogger(GetOtherSubjectsCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.debug("Start executing Command");
        List<Subject> subjects = new ArrayList<>();
        String target = request.getParameter("target");
        if ("entrant".equals(target)) {
            HttpSession session = request.getSession(false);
            String userEmail = String.valueOf(session.getAttribute("user"));

            UserDAO userDAO = new UserDAO();
            User user = userDAO.find(userEmail);

            EntrantDAO entrantDAO = new EntrantDAO();
            Entrant entrant = entrantDAO.find(user);
            MarkDAO markDAO = new MarkDAO();
            subjects = markDAO.findSubjectsOfEntrant(entrant);
        }else if("faculty".equals(target)){
            int facultyId = Integer.parseInt(request.getParameter("facultyId"));
            FacultySubjectDAO facultySubjectDAO = new FacultySubjectDAO();
            subjects = facultySubjectDAO.findById(facultyId);
        }

        SubjectDAO subjectDAO = new SubjectDAO();
        List<Subject> all_subjects = new ArrayList<>(subjectDAO.findAll());
        all_subjects.removeAll(subjects);

        SubjectsModel subjectsModel = new SubjectsModel(all_subjects);
        Gson gson = new Gson();
        return gson.toJson(subjectsModel);
    }
}
