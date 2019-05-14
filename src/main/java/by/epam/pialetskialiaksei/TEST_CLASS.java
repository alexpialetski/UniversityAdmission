package by.epam.pialetskialiaksei;

import by.epam.pialetskialiaksei.entity.ClientSubject;
import by.epam.pialetskialiaksei.entity.Entrant;
import by.epam.pialetskialiaksei.entity.Subject;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.model.ClientSubjectsModel;
import by.epam.pialetskialiaksei.model.SubjectsModel;
import by.epam.pialetskialiaksei.sql.DAO.EntrantDAO;
import by.epam.pialetskialiaksei.sql.DAO.MarkDAO;
import by.epam.pialetskialiaksei.sql.DAO.SubjectDAO;
import by.epam.pialetskialiaksei.sql.DAO.UserDAO;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class TEST_CLASS {
//    public static void main(String[] args) {
//        User user = new UserDAO().find("arcu@sodalesnisi.edu","7068");
//        EntrantDAO entrantDAO = new EntrantDAO();
//        Entrant entrant = entrantDAO.find(user);
//
//        SubjectDAO subjectDAO = new SubjectDAO();
//        List<Subject> all_subjects = new ArrayList<>(subjectDAO.findAll());
//
//        MarkDAO markDAO = new MarkDAO();
//        List<Subject> subjects = markDAO.findSubjectsOfEntrant(entrant);
//        all_subjects.removeAll(subjects);
//
//        SubjectsModel subjectsModel = new SubjectsModel(all_subjects);
//        Gson gson = new Gson();
//        System.out.println(gson.toJson(subjectsModel));
//    }
    public static void main(String[] args) {
        User user = new UserDAO().find("arcu@sodalesnisi.edu","7068");
        EntrantDAO entrantDAO = new EntrantDAO();
        Entrant entrant = entrantDAO.find(user);

        MarkDAO subjectDAO = new MarkDAO();
        List<ClientSubject> all_subjects = new ArrayList<>(subjectDAO.findMarks(entrant));

        ClientSubjectsModel subjectsModel = new ClientSubjectsModel(all_subjects);
        Gson gson = new Gson();
        System.out.println(gson.toJson(subjectsModel));
    }
}
