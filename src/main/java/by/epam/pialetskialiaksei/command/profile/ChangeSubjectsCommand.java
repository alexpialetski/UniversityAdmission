package by.epam.pialetskialiaksei.command.profile;

import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.Entrant;
import by.epam.pialetskialiaksei.entity.Mark;
import by.epam.pialetskialiaksei.entity.Subject;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.model.SubjectsModel;
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
import java.util.ArrayList;
import java.util.List;

public class ChangeSubjectsCommand implements Command {
    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = LogManager.getLogger(ChangeSubjectsCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.debug("Start executing Command");
        HttpSession session = request.getSession(false);
        String userEmail = String.valueOf(session.getAttribute("user"));

        UserDAO userDAO = new UserDAO();
        User user = userDAO.find(userEmail);

        EntrantDAO entrantDAO = new EntrantDAO();
        Entrant entrant = entrantDAO.find(user);

        String jsonString = request.getParameter("subjects");
        jsonString = jsonString.replaceAll("\\?", entrant.getId() + "");
        LOG.info("Json of subjects to change: " + jsonString);
        Gson gson = new Gson();

        Mark[] marks = gson.fromJson(jsonString, Mark[].class);

        MarkDAO markDAO = new MarkDAO();

        if (marks[0].getId() != -1) {
            markDAO.update(marks);
        } else {
            markDAO.create(marks);
        }
        return "";
    }
}
