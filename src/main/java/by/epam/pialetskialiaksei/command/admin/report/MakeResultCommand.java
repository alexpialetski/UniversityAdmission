package by.epam.pialetskialiaksei.command.admin.report;

import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.Faculty;
import by.epam.pialetskialiaksei.exception.CommandException;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.sql.DAO.FacultyDAO;
import by.epam.pialetskialiaksei.sql.DAO.ReportSheetDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * View profile command.
 *
 * @author Mark Norkin
 */
public class MakeResultCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = LogManager.getLogger(MakeResultCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException, CommandException {
        LOG.debug("Command execution starts");
        try {
            FacultyDAO facultyDAO = new FacultyDAO();
            List<Faculty> faculties = facultyDAO.findAll();

            ReportSheetDAO reportSheetDAO = new ReportSheetDAO();
            reportSheetDAO.makeResult(faculties);

            return Path.REDIRECT_TO_PROFILE;
        } catch (DaoException e) {
            throw new CommandException("Exception in ApplyFacultyCommand", e);
        }
    }
}