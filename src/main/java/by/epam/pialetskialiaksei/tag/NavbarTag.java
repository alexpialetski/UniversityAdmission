package by.epam.pialetskialiaksei.tag;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class NavbarTag extends TagSupport {
    @Override
    public int doStartTag() {
        try{
            JspWriter out = pageContext.getOut();
            out.write(" <div id=\"sidebar\">\n" +
                    "        <div class=\"toggle-btn\" onclick=\"toggleSidebar()\">\n" +
                    "            <span></span>\n" +
                    "            <span></span>\n" +
                    "            <span></span>\n" +
                    "        </div>\n" +
                    "        <ul style=\"padding-left: 10px;\">\n" +
                    "            <li><a href=\"#\">Home</a></li>\n" +
                    "            <li><a href=\"#\">About</a></li>\n" +
                    "            <li><a href=\"#\">Contact</a></li>\n" +
                    "        </ul>\n" +
                    "    </div>");
        }catch (IOException e){
            e.printStackTrace();
        }
        return SKIP_BODY;
    }
}
