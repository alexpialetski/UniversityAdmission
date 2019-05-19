package by.epam.pialetskialiaksei.command.subject;

import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.command.profile.LoginCommand;
import by.epam.pialetskialiaksei.entity.Subject;
import by.epam.pialetskialiaksei.sql.DAO.SubjectDAO;
import by.epam.pialetskialiaksei.util.ActionType;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteSubjectCommand extends Command {
    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = LogManager.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType) throws IOException, ServletException {
        LOG.debug("Start executing Command");

        String result = null;

        switch (actionType){
            case GET: result = doGet(request, response);
                break;
            case POST: result = doPost(request, response);
                break;
            case AJAX: result = doAjax(request, response);
                break;
        }

        LOG.debug("End executing command");
        return result;
    }

    @Override
    protected String doGet(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    protected String doPost(HttpServletRequest request, HttpServletResponse response) {
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
    }

    protected String doAjax(HttpServletRequest request, HttpServletResponse response) {
        String subjectsIdJson = request.getParameter("subjects");
        LOG.trace("Get the request attribute: 'subjectsIdJson' = "
                + subjectsIdJson);

        int[] subjectsId = new Gson().fromJson(subjectsIdJson, int[].class);

        SubjectDAO subjectDAO = new SubjectDAO();
        subjectDAO.delete(subjectsId);
        return "{\"error\":\"none\"}";
    }
}
