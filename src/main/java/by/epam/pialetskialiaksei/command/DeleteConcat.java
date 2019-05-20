package by.epam.pialetskialiaksei.command;

public class DeleteConcat {
    // Ctrl-Shift-J -> delete pluses
    public static void main(String[] args) {
        String s = "SELECT user.id as User_idUser, user.first_name, user.last_name, user.email, user.password, user.lang, user.role_id\nFROM university_admission.faculty_entrants\n       inner join university_admission.entrant e on faculty_entrants.Entrant_idEntrant = e.id\n       inner join user on e.User_idUser = user.id\nWHERE faculty_entrants.Faculty_idFaculty = ?;";
        //        System.out.println(s.replaceAll("\\n", ""));
        s = s.replaceAll("\\n", "");
//        System.out.println(s.replaceAll("\\\\n", ""));
        s = s.replaceAll("\\\\n", "");
//        System.out.println(s.replaceAll("\"", ""));
        s = s.replaceAll("\"", "");
        s = s.replaceAll("\'", "");
        System.out.println(s.replaceAll("\\+", ""));
//        System.out.println(s.replaceAll("[\\+|\\n|\\\\n|\"]", ""));
    }
}
