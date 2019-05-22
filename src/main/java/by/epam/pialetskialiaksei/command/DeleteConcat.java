package by.epam.pialetskialiaksei.command;

public class DeleteConcat {
    // Ctrl-Shift-J -> delete pluses
    public static void main(String[] args) {
        String s = "SELECT faculty_subjects.id,\n       faculty.id as Faculty_idFaculty, faculty.name_ru as Faculty_name_ru, faculty.name_eng as Faculty_name_eng, faculty.total_seats, faculty.budget_seats, faculty.infoEng, faculty.infoRu, faculty.passingScore,\n       subject.id as Subject_idSubject, subject.name_ru as Subject_name_ru, subject.name_eng as Subject_name_eng\nFROM university_admission.faculty_subjects\n       INNER JOIN subject on faculty_subjects.Subject_idSubject = subject.id\n       INNER JOIN faculty on faculty_subjects.Faculty_idFaculty = faculty.id\nORDER BY Faculty_idFaculty;";
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
