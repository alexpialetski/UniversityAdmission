package by.epam.pialetskialiaksei.command;

import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.command.faculty.*;
import by.epam.pialetskialiaksei.command.profile.*;
import by.epam.pialetskialiaksei.command.registration.ClientRegistrationCommand;
import by.epam.pialetskialiaksei.command.registration.ConfirmRegistrationCommand;
import by.epam.pialetskialiaksei.command.registration.SendConfirmationRegistrationCommand;
import by.epam.pialetskialiaksei.command.registration.ViewRegistrationCommand;
import by.epam.pialetskialiaksei.command.subject.AddSubjectCommand;
import by.epam.pialetskialiaksei.command.subject.DeleteSubjectCommand;
import by.epam.pialetskialiaksei.command.subject.UpdateSubjectsCommand;
import by.epam.pialetskialiaksei.command.subject.ViewAllSubjectsCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private static final Logger LOG = LogManager.getLogger(CommandManager.class);

    private static Map<String, Command> commands = new HashMap<String, Command>();
    static {
        commands.put("login", new LoginCommand());
        commands.put("viewLogin", new ViewLoginCommand());
        commands.put("viewWelcome", new ViewWelcomeCommand());
        commands.put("viewProfile", new ViewProfileCommand());
        commands.put("client_registration", new ClientRegistrationCommand());
        commands.put("view_registration", new ViewRegistrationCommand());
        commands.put("editProfile", new EditProfileCommand());
        commands.put("getOtherSubjects", new GetOtherSubjectsCommand());
        commands.put("changeSubjects", new ChangeSubjectsCommand());
        commands.put("changeFacultySubjects", new ChangeFacultySubjectsCommand());
        commands.put("changeDiploma", new ChangeDiplomaCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("viewFaculties", new ViewFacultiesCommand());
        commands.put("viewFaculty", new ViewFacultyCommand());
        commands.put("getFacultySubjects", new GetFacultySubjectsCommand());
        commands.put("getAllFaculties", new GetAllFacultiesCommand());
        commands.put("applyOnFaculty", new ApplyFacultyCommand());
        commands.put("unApplyOnFaculty", new UnApplyFacultyCommand());
        commands.put("viewAllSubjects", new ViewAllSubjectsCommand());
        commands.put("updateSubjects", new UpdateSubjectsCommand());
        commands.put("deleteSubject", new DeleteSubjectCommand());
        commands.put("addSubject", new AddSubjectCommand());
        commands.put("sendConfirmation", new SendConfirmationRegistrationCommand());
        commands.put("confirm", new ConfirmRegistrationCommand());
        commands.put("getFacultyUsers", new GetFacultyUsersCommand());
        commands.put("editFaculty", new EditFacultyCommand());
    }
    public static Command get(String commandName) {
        return commands.get(commandName);
    }
}
