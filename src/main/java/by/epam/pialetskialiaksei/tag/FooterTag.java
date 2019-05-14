package by.epam.pialetskialiaksei.tag;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class FooterTag extends TagSupport {
    private String company;

    public void setCompany(String company) {
        this.company = company;
    }
    @Override
    public int doStartTag() {
        try{
            JspWriter out = pageContext.getOut();
            out.write("<footer class=\"footer-distributed\">\n" +
                    "    <div class=\"footer-left\">\n" +
                    "        <h3>Company<span>logo</span></h3>\n" +
                    "        <p class=\"footer-links\">\n" +
                    "            <a href=\"#\">Home</a>\n" +
                    "            ·\n" +
                    "            <a href=\"#\">Blog</a>\n" +
                    "            ·\n" +
                    "            <a href=\"#\">Pricing</a>\n" +
                    "            ·\n" +
                    "            <a href=\"#\">About</a>\n" +
                    "            ·\n" +
                    "            <a href=\"#\">Faq</a>\n" +
                    "            ·\n" +
                    "            <a href=\"#\">Contact</a>\n" +
                    "        </p>\n" +
                    "        <p class=\"footer-company-name\">Company Name &copy; 2015</p>\n" +
                    "    </div>\n" +
                    "\n" +
                    "    <div class=\"footer-center\">\n" +
                    "        <div>\n" +
                    "            <i class=\"fa fa-map-marker\"></i>\n" +
                    "            <p><span>21 Revolution Street</span> Paris, France</p>\n" +
                    "        </div>\n" +
                    "        <div>\n" +
                    "            <i class=\"fa fa-phone\"></i>\n" +
                    "            <p>+1 555 123456</p>\n" +
                    "        </div>\n" +
                    "        <div>\n" +
                    "            <i class=\"fa fa-envelope\"></i>\n" +
                    "            <p><a href=\"mailto:support@company.com\">support@company.com</a></p>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "    <div class=\"footer-right\">\n" +
                    "        <p class=\"footer-company-about\">\n" +
                    "            <span>About the company</span>\n" +
                    "            Lorem ipsum dolor sit amet, consectateur adispicing elit. Fusce euismod convallis velit, eu auctor lacus\n" +
                    "            vehicula sit amet.\n" +
                    "        </p>\n" +
                    "        <div class=\"footer-icons\">\n" +
                    "            <a href=\"#\"><i class=\"fa fa-facebook\"></i></a>\n" +
                    "            <a href=\"#\"><i class=\"fa fa-twitter\"></i></a>\n" +
                    "            <a href=\"#\"><i class=\"fa fa-linkedin\"></i></a>\n" +
                    "            <a href=\"#\"><i class=\"fa fa-github\"></i></a>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</footer>");
        }catch (IOException e){
            e.printStackTrace();
        }
        return SKIP_BODY;
    }
}
