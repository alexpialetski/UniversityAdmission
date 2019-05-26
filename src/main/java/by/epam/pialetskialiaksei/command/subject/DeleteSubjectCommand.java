package by.epam.pialetskialiaksei.command.subject;

import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.command.profile.LoginCommand;
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

public class DeleteSubjectCommand implements Command {
    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = LogManager.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, CommandException {
        LOG.debug("Start executing Command");
        String result = "{\"errorEng\":\"none\"}";
        try {
            String subjectsIdJson = request.getParameter("subjects");
            LOG.trace("Get the request attribute: 'subjectsIdJson' = "
                    + subjectsIdJson);
            int[] subjectsId = new Gson().fromJson(subjectsIdJson, int[].class);

            MarkDAO markDAO = new MarkDAO();
            boolean canDelete = true;
            for(int subjectId: subjectsId){
                Mark mark = markDAO.findEntrantMark(subjectId);
                if(mark == null){
                    canDelete = false;
                    break;
                }
            }
            if(canDelete) {
                SubjectDAO subjectDAO = new SubjectDAO();
                subjectDAO.delete(subjectsId);
            }else{
                result = "{\"errorEng\":\"you cant delete subject, because it is used.\", \"errorRu\":\"\\u0412\u044b \u043d\u0435 \u043c\u043e\u0436\u0435\u0442\u0435 \u0443\u0434\u0430\u043b\u0438\u0442\u044c \u043f\u0440\u0435\u0434\u043c\u0435\u0442, \u0442\u0430\u043a \u043a\u0430\u043a \u043e\u043d \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u0443\u0435\u0442\u0441\u044f.\"}";
            }
            return result;
        } catch (DaoException e) {
            throw new CommandException(e);
        }
    }
}