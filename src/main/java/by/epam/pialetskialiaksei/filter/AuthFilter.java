package by.epam.pialetskialiaksei.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"/controller"})
public class AuthFilter implements Filter {

	private final List<String> urls;
	/**
	 * accessible to all users
	 */
	private final Set<String> accessibleCommands;
	/**
	 * accessible only to logged in users
	 */
	private final Set<String> commonCommands;
	/**
	 * accessible only to client user
	 */
	private final Set<String> clientCommands;
	/**
	 * accessible only to administrator
	 */
	private final Set<String> adminCommands;
	
	public AuthFilter() {
		urls = new ArrayList<String>();
		accessibleCommands = new HashSet<>();
		commonCommands = new HashSet<>();
		clientCommands = new HashSet<>();
		adminCommands = new HashSet<>();

		accessibleCommands.add("login");
		accessibleCommands.add("viewLogin");
		accessibleCommands.add("viewWelcome");
		accessibleCommands.add("view_registration");
		accessibleCommands.add("client_registration");
		accessibleCommands.add("confirm");
		accessibleCommands.add("sendConfirmation");

		// common commands
		commonCommands.add("logout");
		commonCommands.add("viewProfile");
		commonCommands.add("editProfile");
		commonCommands.add("getOtherSubjects");
		commonCommands.add("changeSubjects");
		commonCommands.add("changeDiploma");
		commonCommands.add("viewFaculties");
		commonCommands.add("getAllFaculties");
		commonCommands.add("getFacultySubjects");
		commonCommands.add("viewCurrentScore");

		// client commands
		clientCommands.add("applyOnFaculty");
		clientCommands.add("unApplyOnFaculty");
		// admin commands
//		adminCommands.add("admin_registration");
		adminCommands.add("changeFacultySubjects");
		adminCommands.add("viewFaculty");
		adminCommands.add("viewAllSubjects");
		adminCommands.add("updateSubjects");
		adminCommands.add("deleteSubject");
		adminCommands.add("addSubject");
		adminCommands.add("getFacultyUsers");
		adminCommands.add("editFaculty");
		adminCommands.add("makeResult");
		adminCommands.add("deleteResults");
		adminCommands.add("viewResults");
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String command = req.getParameter("command");

		if (accessibleCommands.contains(command) || command == null) {
			chain.doFilter(req, res); // request for accessible url
		} else {
			HttpSession session = req.getSession(false);
			if (session == null || session.getAttribute("userRole") == null) {
				res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			} else {
				if (commonCommands.contains(command)) {
					chain.doFilter(req, res); // Logged-in user found, so
				} else {
					if ("client".equals(session.getAttribute("userRole"))
							&& clientCommands.contains(command)) {
						chain.doFilter(req, res); // Logged-in user found, so
													// just continue request.
					} else if ("admin".equals(session.getAttribute("userRole"))
							&& adminCommands.contains(command)) {

						chain.doFilter(req, res); // Logged-in user found, so
													// just continue request.
					} else {
						res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
					}
				}
			}
		}
	}

}
