package by.epam.pialetskialiaksei.command.admin.subject;

import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.command.client.login.LoginCommand;
import by.epam.pialetskialiaksei.entity.Mark;
import by.epam.pialetskialiaksei.exception.CommandException;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.sql.DAO.MarkDAO;
import by.epam.pialetskialiaksei.sql.DAO.SubjectDAO;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeleteSubjectCommand implements Command {
    private static final long VersionUID = -3071536593627692473L;
    private static final String REGEX = "[,\\[\\]]";
    private static final Logger LOG = LogManager.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, CommandException {
        LOG.debug("Start executing Command");
        String result = "{\"errorEng\":\"none\"}";
        try {
            String subjectsIdJson = request.getParameter("subjects");
            LOG.trace("Get the request attribute: 'subjectsIdJson' = "
                    + subjectsIdJson);
            int[] subjectsIdArray = new Gson().fromJson(subjectsIdJson, int[].class);
            List<Integer> subjectsId = new ArrayList<>();
            for (int subId : subjectsIdArray) {
                subjectsId.add(subId);
            }
            List<Integer> subjectsIdThatCantBeDeleted = new ArrayList<>();
            MarkDAO markDAO = new MarkDAO();
            for (Integer subjectId : subjectsId) {
                Mark mark = markDAO.findEntrantMark(subjectId);
                if (mark != null) {
                    subjectsIdThatCantBeDeleted.add(subjectId);
                }
            }
            SubjectDAO subjectDAO = new SubjectDAO();
            if (subjectsIdThatCantBeDeleted.isEmpty()) {
                subjectDAO.delete(subjectsId);
            } else {
                subjectsId.removeAll(subjectsIdThatCantBeDeleted);
                if (!subjectsId.isEmpty()) {
                    subjectDAO.delete(subjectsId);
                }
                result = "{\"errorEng\":\"You cant delete subjects with ids: " +
                        subjectsIdThatCantBeDeleted.toString().replaceAll(REGEX, "") +
                        ", because they are already used.\"," +
                        "\"errorRu\":\"Вы не можете удалить предметы с id: " +
                        subjectsIdThatCantBeDeleted.toString().replaceAll(REGEX, "") +
                        ", так как они уже используется\"}";
            }
            return result;
        } catch (DaoException e) {
            throw new CommandException(e);
        }
    }
}