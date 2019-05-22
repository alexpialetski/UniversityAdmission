package by.epam.pialetskialiaksei.command.registration;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.Path;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.entity.Entrant;
import by.epam.pialetskialiaksei.entity.Mail;
import by.epam.pialetskialiaksei.entity.Role;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.sql.DAO.EntrantDAO;
import by.epam.pialetskialiaksei.sql.DAO.MailDAO;
import by.epam.pialetskialiaksei.sql.DAO.MarkDAO;
import by.epam.pialetskialiaksei.sql.DAO.UserDAO;
import by.epam.pialetskialiaksei.util.ActionType;
import by.epam.pialetskialiaksei.util.validation.ProfileInputValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Invoked when client registers in system.
 *
 * @author Mark Norkin
 *
 */
public class ConfirmRegistrationCommand implements Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger LOG = LogManager.getLogger(ConfirmRegistrationCommand.class);


	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response)
			throws IOException, ServletException {
        LOG.debug("Start executing Command");

        String email = request.getParameter(Fields.USER_EMAIL);
        String password = request.getParameter(Fields.USER_PASSWORD);
        String userKey = request.getParameter("key");

        MailDAO mailDAO = new MailDAO();
        Mail mail = new Mail();
        mail.setMailId(email+password);
        Mail dbMail = mailDAO.find(mail);

        String result = null;
        if(dbMail!=null && userKey.equals(dbMail.getKey())){
            mailDAO.delete(mail);
            return "okay";
        }else {
            mailDAO.delete(mail);
            return "false";
        }
	}
}
