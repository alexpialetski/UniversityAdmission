package by.epam.pialetskialiaksei.command.admin.subject;

import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.command.client.login.LoginCommand;
import by.epam.pialetskialiaksei.entity.Subject;
import by.epam.pialetskialiaksei.exception.CommandException;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.sql.DAO.SubjectDAO;
import by.epam.pialetskialiaksei.util.validation.SubjectInputValidator;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateSubjectsCommand implements Command {
    private static final long serialVersionUID = -3071536593627692473L;
    private static final String REGEX = "[,\\[\\]]";
    private static final Logger LOG = LogManager.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, CommandException {
        LOG.debug("Start executing Command");
        try {
            String jsonString = request.getParameter("subjects");

            LOG.info("Json of subjects to change: " + jsonString);
            Gson gson = new Gson();
            Subject[] subjectsArray = gson.fromJson(jsonString, Subject[].class);
            List<Subject> subjects = new ArrayList<>(Arrays.asList(subjectsArray));
            List<Subject> subjectsIdThatCantBeUpdated = new ArrayList<>();

            boolean valid;
            for (Subject subject : subjects) {
                valid = SubjectInputValidator.validateParameters(subject.getNameRu(),
                        subject.getNameEng());
                if (!valid) {
                    subjectsIdThatCantBeUpdated.add(subject);
                }
            }
            SubjectDAO subjectDAO = new SubjectDAO();
            if (subjectsIdThatCantBeUpdated.isEmpty()) {
                subjectDAO.update(subjects);
            } else {
                subjects.removeAll(subjectsIdThatCantBeUpdated);
                subjectDAO.update(subjects);
                return "{\"errorEng\":\"Check subjects with ids: " +
                        subjectsIdThatCantBeUpdated.toString().replaceAll(REGEX, "") +
                        ", they are not valid entered.\"," +
                        "\"errorRu\":\"Проверьте предметы с идентификаторами: " +
                        subjectsIdThatCantBeUpdated.toString().replaceAll(REGEX, "") +
                        ", они не действительны введены.\"}";
            }
            return "{\"errorEng\":\"none\"}";
        } catch (DaoException e) {
            throw new CommandException(e);
        }
    }
}
