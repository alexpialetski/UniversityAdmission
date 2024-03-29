package by.epam.pialetskialiaksei.command.client.profile;

import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.command.client.login.LoginCommand;
import by.epam.pialetskialiaksei.entity.Entrant;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.exception.CommandException;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.sql.DAO.EntrantDAO;
import by.epam.pialetskialiaksei.sql.DAO.UserDAO;
import by.epam.pialetskialiaksei.util.validation.FieldValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChangeDiplomaCommand implements Command {
    private static final long VersionUID = -3071536593627692473L;

    private static final Logger LOG = LogManager.getLogger(LoginCommand.class);

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

            String diplomaMark = request.getParameter("diploma");
            boolean valid = FieldValidation.isPositiveDecimalNumber(Integer.valueOf(diplomaMark));
            if(valid ){
                entrant.setDiplomaMark(Integer.valueOf(diplomaMark));
                entrantDAO.update(entrant);
            }else{
                return  "{\"errorEng\":\"Value of diploma mark is not valid\"," +
                        "\"errorRu\":\"Значение дипломной оценки не является действительным\"}";
            }
            return "{\"errorEng\":\"none\"}";
        } catch (DaoException e) {
            throw new CommandException(e);
        }
    }
}
