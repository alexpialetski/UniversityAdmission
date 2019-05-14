package by.epam.pialetskialiaksei.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "SessionLocaleFilter", urlPatterns = {"/*"})
public class SessionLocaleFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        if(session.getAttribute("lang") == null){
            session.setAttribute("lang", "en");
        }
        if (req.getParameter("sessionLocale") != null) {
            session.setAttribute("lang", req.getParameter("sessionLocale"));
        }
        chain.doFilter(request, response);
    }
    public void destroy() {
    }

    public void init(FilterConfig arg0) throws ServletException {
    }
}