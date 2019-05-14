package by.epam.pialetskialiaksei.command.faculty;

import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.*;
import by.epam.pialetskialiaksei.model.FacultyInfoModel;
import by.epam.pialetskialiaksei.sql.DAO.*;
import by.epam.pialetskialiaksei.util.ActionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * View profile command.
 *
 * @author Mark Norkin
 *
 */
public class ViewFacultyCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger LOG = LogManager.getLogger(ViewFacultyCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, ActionType actionType)
			throws IOException, ServletException {
		LOG.debug("Command execution starts");

		String result = null;

		if (actionType == ActionType.GET) {
			result = doGet(request, response);
		}

		LOG.debug("Command execution finished");

		return result;
	}

	/**
	 * Forwards user to his profile page, based on his role.
	 *
	 * @return path to user profile
	 */
	protected String doGet(HttpServletRequest request,
						   HttpServletResponse response) {
		String result = null;

		HttpSession session = request.getSession(false);
		String userEmail = String.valueOf(session.getAttribute("user"));
		UserDAO userDAO = new UserDAO();
		User user = userDAO.find(userEmail);

		FacultySubjectDAO facultySubjectDAO = new FacultySubjectDAO();
		List<FacultyInfoModel> facultyInfoModels = facultySubjectDAO.findAll();

		request.setAttribute("facultiesInfo", facultyInfoModels);
		LOG.trace("Set the request attribute: 'facultiesInfo' = "
				+ facultyInfoModels);

		String role = user.getRole();
		if ("client".equals(role)) {
			result = Path.FORWARD_FACULTY_VIEW_ALL_CLIENT;
		} else if ("admin".equals(role)) {
			result = Path.FORWARD_ADMIN_PROFILE;
		}
		return result;
	}

	@Override
	protected String doPost(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}
}