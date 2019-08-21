package by.epam.pialetskialiaksei.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "MessageFilter", urlPatterns = {"/*"})
public class MessageFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = ((HttpServletRequest) request).getSession();
        if (request.getParameter("command") != null && (httpRequest.getMethod().equalsIgnoreCase("POST"))){
            session.removeAttribute("errorRu");
            session.removeAttribute("errorEng");
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}

