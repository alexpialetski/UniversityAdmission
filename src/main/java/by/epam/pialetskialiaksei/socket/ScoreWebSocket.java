package by.epam.pialetskialiaksei.socket;

import by.epam.pialetskialiaksei.entity.Faculty;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.model.FacultyScoreModel;
import by.epam.pialetskialiaksei.sql.DAO.ReportSheetDAO;
import com.google.gson.Gson;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ServerEndpoint(value = "/score")
public class ScoreWebSocket {
    static List<Session> sessions = new CopyOnWriteArrayList<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
//        System.out.println("onOpen for Session: " + session);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
//        System.out.println("Closed something");
    }

    @OnError
    public void onError(Throwable exception, Session session) {
    }

    public static void updateScore(Faculty faculty) throws DaoException {
        ReportSheetDAO reportSheetDAO = new ReportSheetDAO();
        int budgetScore = reportSheetDAO.getScore(faculty.getId(), faculty.getBudgetSeats());
        int notBudgetScore = reportSheetDAO.getScore(faculty.getId(), faculty.getTotalSeats());
        FacultyScoreModel facultyScore = new FacultyScoreModel(faculty, budgetScore, notBudgetScore);
        Gson gson = new Gson();
        String json = gson.toJson(facultyScore);
        try {
            for (Session session : sessions) {
                session.getBasicRemote().sendText(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


