package by.epam.pialetskialiaksei.command.admin.report;

import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.exception.CommandException;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.sql.DAO.ReportSheetDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteResultsCommand implements Command {

    private static final long VersionUID = -3071536593627692473L;

    private static final Logger LOG = LogManager.getLogger(DeleteResultsCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException, CommandException {
        LOG.debug("Command execution starts");
        try {
            ReportSheetDAO reportSheetDAO = new ReportSheetDAO();
            reportSheetDAO.deleteResults();
            request.getSession().removeAttribute("results");
            return Path.REDIRECT_TO_PROFILE;
        } catch (DaoException e) {
            throw new CommandException(e);
        }
    }
}