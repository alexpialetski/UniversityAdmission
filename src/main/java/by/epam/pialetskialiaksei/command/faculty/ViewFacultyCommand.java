package by.epam.pialetskialiaksei.command.faculty;

import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.*;
import by.epam.pialetskialiaksei.model.FacultyInfoModel;
import by.epam.pialetskialiaksei.sql.DAO.*;
import by.epam.pialetskialiaksei.util.ActionType;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
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

		switch (actionType){
			case GET: result = doGet(request, response);
				break;
			case POST: result = doPost(request, response);
				break;
			case AJAX: result = doAjax(request, response);
				break;
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

		EntrantDAO entrantDAO = new EntrantDAO();
		Entrant entrant = entrantDAO.find(user);

		FacultyEntrantDAO facultyEntrantDAO = new FacultyEntrantDAO();
        FacultyEntrant facultyEntrant = facultyEntrantDAO.find(entrant);
        if(facultyEntrant != null){
            request.setAttribute("applied", facultyEntrant.getFacultyId());
        }

		String role = String.valueOf(session.getAttribute("userRole"));
		if ("client".equals(role)) {
			result = Path.FORWARD_FACULTY_VIEW_ALL_CLIENT;
		} else if ("admin".equals(role)) {
			result = Path.FORWARD_FACULTY_VIEW_ALL_CLIENT;
		}
		return result;
	}

	@Override
	protected String doPost(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	protected String doAjax(HttpServletRequest request, HttpServletResponse response) {
		FacultySubjectDAO facultySubjectDAO = new FacultySubjectDAO();
		List<FacultyInfoModel> facultyInfoModels = facultySubjectDAO.findAll();

        return new Gson().toJson(facultyInfoModels);
	}
}