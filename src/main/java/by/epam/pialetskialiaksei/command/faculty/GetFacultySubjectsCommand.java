package by.epam.pialetskialiaksei.command.faculty;

import by.epam.pialetskialiaksei.command.admin.faculty.EditFacultyCommand;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.Subject;
import by.epam.pialetskialiaksei.exception.CommandException;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.sql.DAO.FacultySubjectDAO;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetFacultySubjectsCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(EditFacultyCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, CommandException {
        LOG.debug("Command execution starts");
        try {
            String result = null;

            int facultyId = Integer.parseInt(request.getParameter("facultyId"));
            LOG.trace("Get the request parameter: 'facultyId' = "
                    + facultyId);

            FacultySubjectDAO facultySubjectDAO = new FacultySubjectDAO();
            List<Subject> subjects = facultySubjectDAO.findById(facultyId);
            return new Gson().toJson(subjects);
        } catch (DaoException e) {
            throw new CommandException("Exception in ApplyFacultyCommand", e);
        }
    }
}
