package by.epam.pialetskialiaksei.command;

import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.command.faculty.ViewFacultyCommand;
import by.epam.pialetskialiaksei.command.profile.*;
import by.epam.pialetskialiaksei.command.registration.ClientRegistrationCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private static final Logger LOG = LogManager.getLogger(CommandManager.class);

    private static Map<String, Command> commands = new HashMap<String, Command>();
    static {
        commands.put("login", new LoginCommand());
        commands.put("viewProfile", new ViewProfileCommand());
        commands.put("client_registration", new ClientRegistrationCommand());
        commands.put("editProfile", new EditProfileCommand());
        commands.put("getAllSubjects", new GetAllSubjectsCommand());
        commands.put("changeSubjects", new ChangeSubjectsCommand());
        commands.put("changeDiploma", new ChangeDiplomaCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("faculties", new ViewFacultyCommand());
    }
    public static Command get(String commandName) {
        return commands.get(commandName);
    }
}
