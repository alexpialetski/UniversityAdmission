package by.epam.pialetskialiaksei.filter;

import by.epam.pialetskialiaksei.Path;

import javax.servlet.annotation.WebFilter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

@WebFilter(filterName = "LoginFilter", urlPatterns = {"/*"})
public class LoginFilter implements Filter {
    private static final String PARAM_NAME = "command";
    private static final String PARAM = "login";

    public void doFilter(ServletRequest request,
                         ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String parameter = request.getParameter(PARAM_NAME);
        if (parameter == null || !parameter.equals(PARAM)) {
            filterChain.doFilter(request, response);
        }else{
            Principal principal = ((HttpServletRequest)request).getUserPrincipal();
            if(((HttpServletRequest)request).getUserPrincipal() != null){
                request.setAttribute("message", "User is already logged in!");
                request.getRequestDispatcher(Path.WELCOME_PAGE).forward(request,response);
            }else{
                filterChain.doFilter(request, response);
            }
        }
    }

    public void init(FilterConfig filterConfig){
    }

    public void destroy() {
    }

}
