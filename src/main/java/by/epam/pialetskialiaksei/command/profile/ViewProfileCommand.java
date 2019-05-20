package by.epam.pialetskialiaksei.command.profile;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.ClientSubject;
import by.epam.pialetskialiaksei.entity.Entrant;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.model.ClientSubjectsModel;
import by.epam.pialetskialiaksei.sql.DAO.EntrantDAO;
import by.epam.pialetskialiaksei.sql.DAO.MarkDAO;
import by.epam.pialetskialiaksei.sql.DAO.UserDAO;
import by.epam.pialetskialiaksei.util.ActionType;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * View profile command.
 *
 * @author Mark Norkin
 */
public class ViewProfileCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = LogManager.getLogger(ViewProfileCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {
        LOG.debug("Command execution starts");
        String result = null;

        HttpSession session = request.getSession(false);
        String userEmail = String.valueOf(session.getAttribute("user"));

        UserDAO userDAO = new UserDAO();
        User user = userDAO.find(userEmail);

        request.setAttribute("first_name", user.getFirstName());
        LOG.trace("Set the request attribute: 'first_name' = "
                + user.getFirstName());
        request.setAttribute("last_name", user.getLastName());
        LOG.trace("Set the request attribute: 'last_name' = "
                + user.getLastName());
        request.setAttribute("email", user.getEmail());
        LOG.trace("Set the request attribute: 'email' = " + user.getEmail());
        request.setAttribute("role", user.getRole());
        LOG.trace("Set the request attribute: 'role' = " + user.getRole());

        String role = user.getRole();

        if ("client".equals(role)) {
            // should not be null !!
            EntrantDAO entrantDAO = new EntrantDAO();
            Entrant entrant = entrantDAO.find(user);

            request.setAttribute("city", entrant.getCity());
            LOG.trace("Set the request attribute: 'city' = "
                    + entrant.getCity());
            request.setAttribute("district", entrant.getDistrict());
            LOG.trace("Set the request attribute: 'district' = "
                    + entrant.getDistrict());
            request.setAttribute("school", entrant.getSchool());
            LOG.trace("Set the request attribute: 'school' = "
                    + entrant.getSchool());
            request.setAttribute("diploma", entrant.getDiplomaMark());
            LOG.trace("Set the request attribute: 'diploma' = "
                    + entrant.getDiplomaMark());

            MarkDAO markDAO = new MarkDAO();
            List<ClientSubject> marks = markDAO.findMarks(entrant);
            ClientSubjectsModel jsonMarks = new ClientSubjectsModel(marks);
            Gson gson = new Gson();
//            request.setAttribute("jsonMarks", gson.toJson(jsonMarks).toString());
            request.setAttribute("jsonMarks", gson.toJson(jsonMarks));
            request.setAttribute("marks", marks);
            LOG.trace("Set the request attribute: 'marks' = "
                    + marks);

            result = Path.FORWARD_CLIENT_PROFILE;
        } else if ("admin".equals(role)) {
//            result = Path.FORWARD_ADMIN_PROFILE;
            result = Path.FORWARD_ADMIN_PROFILE;
        }
        return result;
    }

}