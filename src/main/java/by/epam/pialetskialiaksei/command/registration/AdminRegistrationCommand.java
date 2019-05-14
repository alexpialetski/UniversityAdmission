package by.epam.pialetskialiaksei.command.registration;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.Role;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.sql.DAO.UserDAO;
import by.epam.pialetskialiaksei.util.ActionType;
import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.util.validation.ProfileInputValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Invoked when administrator wants to add another admin user.
 *
 * @author Mark Norkin
 *
 */
public class AdminRegistrationCommand extends Command {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LogManager.getLogger(AdminRegistrationCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, ActionType actionType)
			throws IOException, ServletException {
		LOG.debug("Start executing Command");

		String result = null;

		switch (actionType){
			case GET: result = doGet(request, response);
				break;
			case POST: result = doPost(request, response);
				break;
		}

		LOG.debug("Finished executing Command");
		return result;
	}

	/**
	 * Forwards user to registration admin page.
	 *
	 * @return path where lie this page
	 */
	protected String doGet(HttpServletRequest request,
						   HttpServletResponse response) {
		return Path.FORWARD_ADMIN_REGISTRATION_PAGE;
	}

	/**
	 * If validation is successful then admin record will be added in database.
	 *
	 * @return after registartion will be completed returns path to welcome
	 *         page, if not then doGet method will be called.
	 */
	protected String doPost(HttpServletRequest request,
							HttpServletResponse response) {

		String email = request.getParameter(Fields.USER_EMAIL);
		String password = request.getParameter(Fields.USER_PASSWORD);
		String firstName = request.getParameter(Fields.USER_FIRST_NAME);
		String lastName = request.getParameter(Fields.USER_LAST_NAME);
		String lang = request.getParameter(Fields.USER_LANG);

		boolean valid = ProfileInputValidator.validateUserParameters(firstName,
				lastName, email, password, lang);

		String result = null;

		if (!valid) {
			request.setAttribute("errorMessage", "Please fill all fields!");
			LOG.error("errorMessage: Not all fields are filled");
			result = Path.REDIRECT_ADMIN_REGISTRATION_PAGE;
		} else if (valid) {
			User user = new User(email, password, firstName, lastName,
					Role.ADMIN, lang);

			UserDAO userDAO = new UserDAO();
			userDAO.create(user);

			LOG.trace("User record created: " + user);
//			MailUtils.sendConfirmationEmail(user);
			request.setAttribute("successfulMessage",
					"This account was created. Check email and confirm registration.");
			result = Path.REDIRECT_TO_PROFILE;
		}
		return result;
	}
}
