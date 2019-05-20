package by.epam.pialetskialiaksei.command.faculty;

import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.Faculty;
import by.epam.pialetskialiaksei.entity.Subject;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.model.FacultyInfoModel;
import by.epam.pialetskialiaksei.sql.DAO.FacultyDAO;
import by.epam.pialetskialiaksei.sql.DAO.FacultyEntrantDAO;
import by.epam.pialetskialiaksei.sql.DAO.FacultySubjectDAO;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class GetFacultyUsersCommand implements Command {
    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = LogManager.getLogger(ViewFacultyCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.debug("Command execution starts");
        String result = null;

        int facultyId = Integer.parseInt(request.getParameter("facultyId"));
        LOG.trace("Get the request parameter: 'facultyId' = "
                + facultyId);

        FacultyEntrantDAO facultyEntrantDAO = new FacultyEntrantDAO();
        List<User> users = facultyEntrantDAO.findUsers(facultyId);

        return new Gson().toJson(users);
    }
}
