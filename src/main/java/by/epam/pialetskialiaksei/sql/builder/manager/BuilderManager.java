package by.epam.pialetskialiaksei.sql.builder.manager;

import by.epam.pialetskialiaksei.sql.builder.*;
import by.epam.pialetskialiaksei.sql.builder.api.SetBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BuilderManager {

    private static final Logger LOG = LogManager.getLogger(BuilderManager.class);
    private static final Lock LOCK = new ReentrantLock();

    private static Map<String, SetBuilder> builders = new HashMap<>();
    static {
        builders.put("entrant", new EntrantBuilder());
        builders.put("clientSubject", new ClientSubjectBuilder());
        builders.put("faculty", new FacultyBuilder());
        builders.put("facultyEntrant", new FacultyEntrantBuilder());
        builders.put("facultySubject", new FacultySubjectBuilder());
        builders.put("mark", new MarkBuilder());
        builders.put("subject", new SubjectBuilder());
        builders.put("user", new UserBuilder());
    }
    public static SetBuilder get(String commandName) {
        LOCK.lock();
            SetBuilder builder = builders.get(commandName);
        LOCK.unlock();
        return builder;
    }
}
