package by.epam.pialetskialiaksei.command.client.faculty;

import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.*;
import by.epam.pialetskialiaksei.exception.CommandException;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.socket.ScoreWebSocket;
import by.epam.pialetskialiaksei.sql.DAO.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


public class ApplyFacultyCommand implements Command {

    private static final long VersionUID = -3071536593627692473L;

    private static final Logger LOG = LogManager.getLogger(ApplyFacultyCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException, CommandException {
        LOG.debug("Command execution starts");
        try {
            String alreadyApplied = request.getParameter("applied");
            if (alreadyApplied != null) {
                return "{\"errorEng\":\"You are already applied.\"," +
                        "\"errorRu\":\"Вы уже подали документы на факультет.\"}";
            } else {
                HttpSession session = request.getSession(false);
                String userEmail = String.valueOf(session.getAttribute("email"));
                UserDAO userDAO = new UserDAO();
                User user = userDAO.find(userEmail);

                EntrantDAO entrantDAO = new EntrantDAO();
                Entrant entrant = entrantDAO.find(user);

                FacultyEntrantDAO facultyEntrantDAO = new FacultyEntrantDAO();

                int facultyId = Integer.parseInt(request.getParameter("facultyId"));

                MarkDAO markDAO = new MarkDAO();
                List<Subject> subjectsOfEntrant = markDAO.findSubjectsOfEntrant(entrant);
                FacultySubjectDAO facultySubjectDAO = new FacultySubjectDAO();
                List<Subject> facultySubjects = facultySubjectDAO.findById(facultyId);
                if ((subjectsOfEntrant.size() == facultySubjects.size()) && facultySubjects.containsAll(subjectsOfEntrant) && subjectsOfEntrant.containsAll(facultySubjects)) {
                    facultyEntrantDAO.create(new FacultyEntrant(facultyId, entrant.getId()));
                    ScoreWebSocket.updateScore(new FacultyDAO().find(facultyId));
                    return "{\"errorEng\":\"none\"}";
                } else {
                    return "{\"errorEng\":\"You havent got required subjects.\"," +
                            "\"errorRu\":\"У вас не совпадают предметы с этим факультетом.\"}";
                }
            }
        } catch (DaoException e) {
            throw new CommandException("Exception in ApplyFacultyCommand", e);
        }
    }
}

