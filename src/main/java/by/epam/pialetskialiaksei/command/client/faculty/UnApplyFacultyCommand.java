package by.epam.pialetskialiaksei.command.client.faculty;

import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.Entrant;
import by.epam.pialetskialiaksei.entity.FacultyEntrant;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.exception.CommandException;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.socket.ScoreWebSocket;
import by.epam.pialetskialiaksei.sql.DAO.EntrantDAO;
import by.epam.pialetskialiaksei.sql.DAO.FacultyDAO;
import by.epam.pialetskialiaksei.sql.DAO.FacultyEntrantDAO;
import by.epam.pialetskialiaksei.sql.DAO.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UnApplyFacultyCommand implements Command {

    private static final long VersionUID = -3071536593627692473L;

    private static final Logger LOG = LogManager.getLogger(UnApplyFacultyCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException, CommandException {
        LOG.debug("Command execution starts");
        try {
            HttpSession session = request.getSession(false);
            String userEmail = String.valueOf(session.getAttribute("email"));
            UserDAO userDAO = new UserDAO();
            User user = userDAO.find(userEmail);

            EntrantDAO entrantDAO = new EntrantDAO();
            Entrant entrant = entrantDAO.find(user);

            int facultyId = Integer.parseInt(request.getParameter("facultyId"));
            FacultyEntrantDAO facultyEntrantDAO = new FacultyEntrantDAO();
            facultyEntrantDAO.delete(new FacultyEntrant(facultyId, entrant.getId()));
            ScoreWebSocket.updateScore(new FacultyDAO().find(facultyId));
            return "{\"errorEng\":\"none\"}";
        }catch (DaoException e){
            throw new CommandException(e);
        }
    }
}