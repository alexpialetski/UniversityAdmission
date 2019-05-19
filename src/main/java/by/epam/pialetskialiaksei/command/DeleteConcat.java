package by.epam.pialetskialiaksei.command;

public class DeleteConcat {
    // Ctrl-Shift-J -> delete pluses
    public static void main(String[] args) {
        String s = "'<form class=\"form-input\" method=\"POST\" action=\"controller\">' +\n                '<h4>Fill all fields, pls</h4>' +\n                '<input type=\"hidden\" name=\"command\" value=\"addSubject\">' +\n                '<input class=\\\"input-field\\\" type=\\\"text\\\" name=\"nameEng\" \\n' +\n                '                    placeholder=\\\"Subject name\" required>' +\n                '<input class=\\\"input-field\\\" type=\\\"text\\\" name=\"nameRu\"\\n' +\n                '                    placeholder=\\\"Имя предмета\" required>' +\n                '<input type=\"submit\" class=\"btn\" value=\"Click\">'+\n                '</form>'";
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
