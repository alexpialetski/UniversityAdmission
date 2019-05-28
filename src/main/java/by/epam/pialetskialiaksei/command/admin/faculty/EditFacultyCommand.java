package by.epam.pialetskialiaksei.command.admin.faculty;

import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.Faculty;
import by.epam.pialetskialiaksei.exception.CommandException;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.sql.DAO.FacultyDAO;
import by.epam.pialetskialiaksei.util.validation.FacultyInputValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditFacultyCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(EditFacultyCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, CommandException {
        LOG.debug("Start executing Command");
        try {
            int facultyId = Integer.parseInt(request.getParameter("facultyId"));
            String nameRu = request.getParameter("nameRu");
            String nameEng = request.getParameter("nameEng");
            int totalSeats = Integer.parseInt(request.getParameter("totalSeats"));
            int budgetSeats = Integer.parseInt(request.getParameter("budgetSeats"));
            String infoRu = request.getParameter("infoRu");
            String infoEng = request.getParameter("infoEng");

            boolean valid = FacultyInputValidator.validateParameters(nameRu,
                    nameEng, budgetSeats, totalSeats, infoRu, infoEng);

            if (!valid) {
                request.getSession().setAttribute("errorEng",
                        "Please fill all fields properly!");
                request.getSession().setAttribute("errorRu",
                        "Пожалуйста, заполните все поля правильно!");
                LOG.error("errorMessage: Not all fields are properly filled");
                return Path.REDIRECT_TO_FACULTY.replace("=?", "=" + facultyId);
            } else {
                Faculty faculty = new Faculty(nameRu, nameEng, infoRu, infoEng,  budgetSeats, totalSeats);
                faculty.setId(facultyId);

                FacultyDAO facultyDAO = new FacultyDAO();
                facultyDAO.update(faculty);
            }
            return Path.REDIRECT_TO_FACULTY.replace("=?", "=" + facultyId);
        } catch (DaoException e) {
            throw new CommandException(e);
        }
    }
}
