package by.epam.pialetskialiaksei.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeleteConcat {
    // Ctrl-Shift-J -> delete pluses
//    public static void main(String[] args) {
//        String s = "SELECT university_admission.subject.id as Subject_idSubject,\n       university_admission.subject.name_ru as Subject_name_ru,\n       university_admission.subject.name_eng as Subject_name_eng\nFROM university_admission.mark\n       inner join university_admission.subject on mark.Subject_idSubject = subject.id\nWHERE university_admission.mark.Entrant_idEntrant = ?;";
//        //        System.out.println(s.replaceAll("\\n", ""));
//        s = s.replaceAll("\\n", "");
////        System.out.println(s.replaceAll("\\\\n", ""));
//        s = s.replaceAll("\\\\n", "");
////        System.out.println(s.replaceAll("\"", ""));
//        s = s.replaceAll("\"", "");
//        s = s.replaceAll("\'", "");
//        System.out.println(s.replaceAll("\\+", ""));
////        System.out.println(s.replaceAll("[\\+|\\n|\\\\n|\"]", ""));
//    }
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(1,2,3,5,6,7,8));
        String regex = "[,\\[\\]]";
        System.out.println(list.toString().replaceAll(regex, ""));
    }
}
