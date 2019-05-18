package by.epam.pialetskialiaksei.command.subject;

import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.command.profile.LoginCommand;
import by.epam.pialetskialiaksei.entity.Entrant;
import by.epam.pialetskialiaksei.entity.Mark;
import by.epam.pialetskialiaksei.entity.Subject;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.sql.DAO.EntrantDAO;
import by.epam.pialetskialiaksei.sql.DAO.MarkDAO;
import by.epam.pialetskialiaksei.sql.DAO.SubjectDAO;
import by.epam.pialetskialiaksei.sql.DAO.UserDAO;
import by.epam.pialetskialiaksei.util.ActionType;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UpdateSubjectsCommand extends Command {
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
        return null;
    }

    protected String doAjax(HttpServletRequest request, HttpServletResponse response) {
        String jsonString = request.getParameter("subjects");
        LOG.info("Json of subjects to change: "+jsonString);

        Gson gson = new Gson();
        Subject[] subjects = gson.fromJson(jsonString, Subject[].class);

        SubjectDAO subjectDAO = new SubjectDAO();
        try {
            subjectDAO.update(subjects);
            return "{\"error\":\"none\"}";
        }catch (Exception ignored){
            return "{\"error\":\"Something is wrong\"}";
        }
    }
}
