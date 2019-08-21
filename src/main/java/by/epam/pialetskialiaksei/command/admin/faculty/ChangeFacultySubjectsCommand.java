package by.epam.pialetskialiaksei.command.admin.faculty;

import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.EntrantReportSheet;
import by.epam.pialetskialiaksei.entity.FacultySubject;
import by.epam.pialetskialiaksei.exception.CommandException;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.sql.DAO.FacultySubjectDAO;
import by.epam.pialetskialiaksei.sql.DAO.ReportSheetDAO;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChangeFacultySubjectsCommand implements Command {
    private static final long VersionUID = -3071536593627692473L;

    private static final Logger LOG = LogManager.getLogger(ChangeFacultySubjectsCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, CommandException {
        LOG.debug("Start executing Command");
        try {
            int facultyId = Integer.parseInt(request.getParameter("facultyId"));

            ReportSheetDAO reportSheetDAO = new ReportSheetDAO();
            List<EntrantReportSheet> entrants = reportSheetDAO.getReport(facultyId);
            if(!entrants.isEmpty()){
                return "{\"errorEng\":\"You cant change subjects because somebody is already applied\"," +
                        "\"errorRu\":\"Вы не можете изменить предметы, так как уже кто-то подал документы\"}";
            }

            String jsonString = request.getParameter("subjects");
            jsonString = jsonString.replaceAll("\\?", facultyId + "");
            LOG.info("Json of subjects to change: " + jsonString);
            Gson gson = new Gson();

            FacultySubject[] facultySubjects = gson.fromJson(jsonString, FacultySubject[].class);
            FacultySubjectDAO facultySubjectDAO = new FacultySubjectDAO();

            ExecutorService executorService = Executors.newFixedThreadPool(facultySubjects.length);
            List<Callable<Void>> taskList = new ArrayList<>();

            if (facultySubjects[0].getId() != -1) {
                for (int i = 0; i < facultySubjects.length; i++) {
                    int finalI = i;
                    taskList.add(() -> {
                        facultySubjectDAO.updateById(facultySubjects[finalI].getSubjectId(), facultySubjects[finalI].getFacultyId(), facultySubjects[finalI].getId());
                        return null;
                    });
                }
            } else {
                for (int i = 0; i < facultySubjects.length; i++) {
                    int finalI = i;
                    taskList.add(() -> {
                        facultySubjectDAO.create(facultySubjects[finalI]);
                        return null;
                    });
                }
            }
            executorService.invokeAll(taskList);
            return "{\"errorEng\":\"none\"}";
        } catch (InterruptedException | DaoException e) {
            throw new CommandException("Exception in ChangeFacultySubjectsCommand", e);
        }
    }
}

