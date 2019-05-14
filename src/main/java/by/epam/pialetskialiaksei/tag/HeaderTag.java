package by.epam.pialetskialiaksei.tag;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class HeaderTag extends TagSupport {
    private String company;

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public int doStartTag() {
        try {
            JspWriter out = pageContext.getOut();
            out.write("<div class=\"header\">\n" +
                    "    <a href=\"#default\" class=\"logo default\">" + company + "</a>\n" +
                    "    <div class=\"header-right\">\n" +
                    "       <input class=\"active default\" type=\"submit\" value=\"Home\">\n" +
                    "       <input type=\"submit\" class=\"default\" value=\"Contact\">\n" +
                    "       <input type=\"submit\" class=\"default\" value=\"About\">\n" +
                    "       <form class=\"form\" action=\"controller\" method=\"POST\">\n" +
                    "           <input type=\"hidden\" name=\"command\" value=\"logout\">\n" +
                    "           <input class=\"default\" type=\"submit\" value=\"Logout\">\n" +
                    "       </form>    " +
                    "    </div>\n" +
                    "</div>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }
}
