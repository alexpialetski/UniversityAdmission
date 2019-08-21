package by.epam.pialetskialiaksei.command.client.score;

import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.Faculty;
import by.epam.pialetskialiaksei.exception.CommandException;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.model.FacultyScoreModel;
import by.epam.pialetskialiaksei.sql.DAO.FacultyDAO;
import by.epam.pialetskialiaksei.sql.DAO.ReportSheetDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewCurrentScoreCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(ViewCurrentScoreCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException, CommandException {
        LOG.debug("Command execution starts");
        try {
            FacultyDAO facultyDAO = new FacultyDAO();
            List<Faculty> faculties = facultyDAO.findAll();
            ReportSheetDAO reportSheetDAO = new ReportSheetDAO();
            List<FacultyScoreModel> scores = new ArrayList<>();
            for(Faculty faculty: faculties){
                int budgetScore = reportSheetDAO.getScore(faculty.getId(), faculty.getBudgetSeats());
                int notBudgetScore = reportSheetDAO.getScore(faculty.getId(), faculty.getTotalSeats());
                scores.add(new FacultyScoreModel(faculty, budgetScore, notBudgetScore));
            }
            request.setAttribute("scores", scores);
            return Path.FORWARD_SCORE_PAGE;
        } catch (DaoException e) {
            throw new CommandException(e);
        }
    }
}
