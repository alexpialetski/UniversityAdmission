package by.epam.pialetskialiaksei.command;

public class DeleteConcat {
    // Ctrl-Shift-J -> delete pluses
    public static void main(String[] args) {
        String s = "SELECT university_admission.subject.id as Subject_idSubject,\n       university_admission.subject.name_ru as Subject_name_ru,\n       university_admission.subject.name_eng as Subject_name_eng\nFROM university_admission.mark\n       inner join university_admission.subject on mark.Subject_idSubject = subject.id\nWHERE university_admission.mark.Entrant_idEntrant = ?;";
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
