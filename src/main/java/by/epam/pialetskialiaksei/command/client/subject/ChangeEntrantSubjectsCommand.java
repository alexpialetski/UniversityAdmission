package by.epam.pialetskialiaksei.command.client.subject;

import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.Entrant;
import by.epam.pialetskialiaksei.entity.FacultyEntrant;
import by.epam.pialetskialiaksei.entity.Mark;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.exception.CommandException;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.sql.DAO.EntrantDAO;
import by.epam.pialetskialiaksei.sql.DAO.FacultyEntrantDAO;
import by.epam.pialetskialiaksei.sql.DAO.MarkDAO;
import by.epam.pialetskialiaksei.sql.DAO.UserDAO;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChangeEntrantSubjectsCommand implements Command {
    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = LogManager.getLogger(ChangeEntrantSubjectsCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, CommandException {
        LOG.debug("Start executing Command");
        try {
            HttpSession session = request.getSession(false);
            String userEmail = String.valueOf(session.getAttribute("email"));

            UserDAO userDAO = new UserDAO();
            User user = userDAO.find(userEmail);

            EntrantDAO entrantDAO = new EntrantDAO();
            Entrant entrant = entrantDAO.find(user);

            FacultyEntrantDAO facultyEntrantDAO = new FacultyEntrantDAO();
            FacultyEntrant facultyEntrant = facultyEntrantDAO.find(entrant);
            if (facultyEntrant != null) {
                return "";
            }

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
        } catch (DaoException e) {
            throw new CommandException(e);
        }
    }
}
