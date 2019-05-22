package by.epam.pialetskialiaksei.command.faculty;

import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.Entrant;
import by.epam.pialetskialiaksei.entity.Faculty;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.sql.DAO.EntrantDAO;
import by.epam.pialetskialiaksei.sql.DAO.FacultyDAO;
import by.epam.pialetskialiaksei.sql.DAO.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EditFacultyCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(EditFacultyCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.debug("Start executing Command");

        String nameRu = request.getParameter("nameRu");
        String nameEng = request.getParameter("nameEng");
        int totalSeats = Integer.parseInt(request.getParameter("totalSeats"));
        int budgetSeats = Integer.parseInt(request.getParameter("budgetSeats"));
        int facultyId  = Integer.parseInt(request.getParameter("facultyId"));
        String infoRu = request.getParameter("infoRu");
        String infoEng = request.getParameter("infoEng");

        Faculty faculty = new Faculty(nameRu, nameEng, infoRu, infoEng, (byte) budgetSeats, (byte) totalSeats);
        faculty.setId(facultyId);

        FacultyDAO facultyDAO = new FacultyDAO();
        facultyDAO.update(faculty);

        return Path.REDIRECT_TO_FACULTY.replace("=?", "=" + facultyId);
    }
}
