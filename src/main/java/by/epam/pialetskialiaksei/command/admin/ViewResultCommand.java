package by.epam.pialetskialiaksei.command.admin;

import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.Entrant;
import by.epam.pialetskialiaksei.entity.Faculty;
import by.epam.pialetskialiaksei.entity.FacultyEntrant;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.exception.CommandException;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.model.EntrantResultModel;
import by.epam.pialetskialiaksei.model.FacultyResultModel;
import by.epam.pialetskialiaksei.sql.DAO.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewResultCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = LogManager.getLogger(ViewResultCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException, CommandException {
        LOG.debug("Command execution starts");
        try {
            FacultyDAO facultyDAO = new FacultyDAO();
            List<Faculty> faculties = facultyDAO.findAll();
            List<FacultyResultModel> facultyResults = new ArrayList<>();

            ReportSheetDAO reportSheetDAO = new ReportSheetDAO();
            for (Faculty faculty : faculties) {
                List<EntrantResultModel> entrantResults = reportSheetDAO.getResultReport(faculty.getId());
                facultyResults.add(new FacultyResultModel(faculty, entrantResults));
            }
            request.setAttribute("facultyResults", facultyResults);
            return Path.FORWARD_RESULT_PAGE;
        } catch (DaoException e) {
            throw new CommandException(e);
        }
    }
}