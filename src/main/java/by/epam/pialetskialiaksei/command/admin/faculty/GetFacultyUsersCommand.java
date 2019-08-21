package by.epam.pialetskialiaksei.command.admin.faculty;

import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.EntrantReportSheet;
import by.epam.pialetskialiaksei.exception.CommandException;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.sql.DAO.ReportSheetDAO;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetFacultyUsersCommand implements Command {
    private static final long VersionUID = -3071536593627692473L;

    private static final Logger LOG = LogManager.getLogger(ViewFacultyCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, CommandException {
        LOG.debug("Command execution starts");
        try {
            int facultyId = Integer.parseInt(request.getParameter("facultyId"));
            LOG.trace("Get the request parameter: 'facultyId' = "
                    + facultyId);

            ReportSheetDAO reportSheetDAO = new ReportSheetDAO();
            List<EntrantReportSheet> entrants = reportSheetDAO.getReport(facultyId);

            return new Gson().toJson(entrants);
        } catch (DaoException e) {
            throw new CommandException("Exception in ApplyFacultyCommand", e);
        }
    }
}
