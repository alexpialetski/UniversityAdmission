package by.epam.pialetskialiaksei.command.faculty;

import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.exception.CommandException;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.model.FacultyInfoModel;
import by.epam.pialetskialiaksei.sql.DAO.FacultySubjectDAO;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetAllFacultiesCommand implements Command {

    private static final long VersionUID = -3071536593627692473L;

    private static final Logger LOG = LogManager.getLogger(GetAllFacultiesCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException, CommandException {
        LOG.debug("Command execution starts");
        try {
            FacultySubjectDAO facultySubjectDAO = new FacultySubjectDAO();
            List<FacultyInfoModel> facultyInfoModels = facultySubjectDAO.findAll();

            return new Gson().toJson(facultyInfoModels);

        } catch (
                DaoException e) {
            throw new CommandException("Exception in ApplyFacultyCommand", e);
        }
    }
}