package by.epam.pialetskialiaksei.util;

import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.sql.DAO.UserDAO;
import by.epam.pialetskialiaksei.sql.connection.api.ConnectionPool;

public class mainmaon {
    public static void main(String[] args) {
        int subject_id = 1;
        for(int i=1; i<21;i++){
            for (int j=1; j<4;j++){
                if(subject_id > 8){
                    subject_id = 1;
                }
                String s = "INSERT INTO university_admission.faculty_subjects(Faculty_idFaculty, Subject_idSubject) VALUES ("+i+","+subject_id+");";
                System.out.println(s);
                ++subject_id;
            }
        }
    }
}
