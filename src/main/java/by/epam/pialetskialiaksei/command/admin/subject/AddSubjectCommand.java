package by.epam.pialetskialiaksei.command.admin.subject;

import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.Subject;
import by.epam.pialetskialiaksei.exception.CommandException;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.sql.DAO.SubjectDAO;
import by.epam.pialetskialiaksei.util.validation.SubjectInputValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddSubjectCommand implements Command {
    private static final long VersionUID = -3071536593627692473L;

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

            boolean valid = SubjectInputValidator.validateParameters(
                    nameRu, nameEng);

            if (!valid) {
                request.getSession().setAttribute("errorEng",
                        "Please fill all fields properly!");
                request.getSession().setAttribute("errorRu",
                        "Пожалуйста, заполните все поля правильно!");
                LOG.error("errorMessage: Not all fields are properly filled");
            } else {
                SubjectDAO subjectDAO = new SubjectDAO();
                Subject subject = new Subject(nameRu, nameEng);
                LOG.trace("Created subject: " + subject);
                subjectDAO.create(subject);
            }
            return Path.REDIRECT_TO_VIEW_ALL_SUBJECTS;
        } catch (DaoException e) {
            throw new CommandException(e);
        }
    }
}
