package by.epam.pialetskialiaksei.command.profile;

import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.command.CommandManager;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.Entrant;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.sql.DAO.EntrantDAO;
import by.epam.pialetskialiaksei.sql.DAO.UserDAO;
import by.epam.pialetskialiaksei.util.ActionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EditProfileCommand extends Command {
    private static final Logger LOG = LogManager.getLogger(CommandManager.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType) throws IOException, ServletException {
        LOG.debug("Start executing Command");

        String result = null;

        switch (actionType){
            case GET: result = doGet(request, response);
                break;
            case POST: result = doPost(request, response);
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
        HttpSession session = request.getSession(false);
        String role = String.valueOf(session.getAttribute("userRole"));

        String firstName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");
        String email = request.getParameter("email");

        UserDAO userDAO = new UserDAO();
        User user = userDAO.find(email);

        String result = null;

        if(role.equals("client")){
            EntrantDAO entrantDAO = new EntrantDAO();
            Entrant entrant = entrantDAO.find(user);

            String city = request.getParameter("city");
            String district = request.getParameter("district");
            String school = request.getParameter("school");

            entrant.setCity(city);
            entrant.setDistrict(district);
            entrant.setSchool(school);
            entrantDAO.update(entrant);
        }
        user.setFirstName(firstName);
        user.setLastName(lastName);
        userDAO.update(user);

        return Path.REDIRECT_TO_PROFILE;
    }
}