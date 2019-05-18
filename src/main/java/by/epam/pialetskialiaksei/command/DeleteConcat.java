package by.epam.pialetskialiaksei.command;

public class DeleteConcat {
    // Ctrl-Shift-J -> delete pluses
    public static void main(String[] args) {
        String s = " <div id=\"sidebar\">\n        <div class=\"toggle-btn\" onclick=\"toggleSidebar()\">\n            <span></span>\n            <span></span>\n            <span></span>\n        </div>\n        <ul style=\"padding-left: 10px;\">\n            <li><a href=\"#\">Home</a></li>\n            <li><a href=\"#\">About</a></li>\n            <li><a href=\"#\">Contact</a></li>\n        </ul>\n    </div>";
        //        System.out.println(s.replaceAll("\\n", ""));
        s = s.replaceAll("\\n", "");
//        System.out.println(s.replaceAll("\\\\n", ""));
        s = s.replaceAll("\\\\n", "");
//        System.out.println(s.replaceAll("\"", ""));
        s = s.replaceAll("\"", "");
        System.out.println(s.replaceAll("\\+", ""));
//        System.out.println(s.replaceAll("[\\+|\\n|\\\\n|\"]", ""));
    }
}
