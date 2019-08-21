package by.epam.pialetskialiaksei.tag;

import by.epam.pialetskialiaksei.entity.ClientSubject;
import com.google.gson.Gson;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Custom tag class which needed in apply for faculty user form. The main
 * purpose of this class is remove the need of validation in
 * <code>ApplyFacultyViewCommand</code>.
 * `
 * For now the maximum mark in some subject is equal to 12, the lower one is
 * zero.
 */
public class TotalScoreTag extends SimpleTagSupport {
    private String marks;
    private String lang;
    private int diploma;

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getDiploma() {
        return diploma;
    }

    public void setDiploma(int diploma) {
        this.diploma = diploma;
    }

    @Override
    public void doTag() throws JspException, IOException {
        Gson gson = new Gson();
        ClientSubject[] clientSubjects = gson.fromJson(marks, ClientSubject[].class);
        int totalSum = diploma;
        for (ClientSubject clientSubject : clientSubjects) {
            totalSum += clientSubject.getMark().getMark();
        }
        JspWriter out = getJspContext().getOut();
        String message = lang.equals("en") ? "Total sum with diploma score: " : "Общая сумма вместе с оценкой по диплому: ";
        out.println("<h3>" + message + totalSum + "</h3>");
    }

}
