package by.epam.pialetskialiaksei.filter;

import by.epam.pialetskialiaksei.Path;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "MessageFilter", urlPatterns = {"/*"})
public class MessageFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String com = request.getParameter("command");
        if (request.getParameter("command") != null && (httpRequest.getMethod().equalsIgnoreCase("POST")
                || request.getParameter("command").equals("view_registration")
                || request.getParameter("command").equals("viewLogin"))) {
            ((HttpServletRequest) request).getSession().removeAttribute("errorRu");
            ((HttpServletRequest) request).getSession().removeAttribute("errorEng");
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}

