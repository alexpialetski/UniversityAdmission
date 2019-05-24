package by.epam.pialetskialiaksei.command.subject;

import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.command.profile.LoginCommand;
import by.epam.pialetskialiaksei.entity.Subject;
import by.epam.pialetskialiaksei.exception.CommandException;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.sql.DAO.SubjectDAO;
import by.epam.pialetskialiaksei.util.ActionType;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddSubjectCommand implements Command {
    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = LogManager.getLogger(AddSubjectCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, CommandException {
        try {
            LOG.debug("Start executing Command");
            String nameEng = request.getParameter("nameEng");
            LOG.trace("Get the request attribute: 'nameEng' = "
                    + nameEng);
            String nameRu = request.getParameter("nameRu");
            LOG.trace("Get the request attribute: 'nameRu' = "
                    + nameRu);
            Subject subject = new Subject(nameRu, nameEng);
            LOG.trace("Created subject: " + subject);
            SubjectDAO subjectDAO = new SubjectDAO();

            subjectDAO.create(subject);
            return Path.REDIRECT_TO_VIEW_ALL_SUBJECTS;
        } catch (DaoException e) {
            throw new CommandException(e);
        }
    }
}
