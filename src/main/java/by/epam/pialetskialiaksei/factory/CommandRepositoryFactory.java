package by.epam.pialetskialiaksei.factory;

import by.epam.pialetskialiaksei.commandrepository.AdminCommandRepository;
import by.epam.pialetskialiaksei.commandrepository.EntrantCommandRepository;
import by.epam.pialetskialiaksei.commandrepository.RepositoryEnum;
import by.epam.pialetskialiaksei.commandrepository.UserCommandRepository;
import by.epam.pialetskialiaksei.commandrepository.api.CommandRepository;
import by.epam.pialetskialiaksei.factory.api.RepositoryFactory;

import java.util.HashMap;
import java.util.Map;

public class CommandRepositoryFactory implements RepositoryFactory {
    private static final HashMap<RepositoryEnum, CommandRepository> commandRepositories = new HashMap<>();

    {
        commandRepositories.put(RepositoryEnum.ADMIN, new AdminCommandRepository());
        commandRepositories.put(RepositoryEnum.ENTRANT, new EntrantCommandRepository());
        commandRepositories.put(RepositoryEnum.USER, new UserCommandRepository());
    }

    @Override
    public CommandRepository getRepository(String repository) {
        RepositoryEnum repositoryEnum = RepositoryEnum.valueOf(repository);
        if(repository!= null){
            return commandRepositories.get(repositoryEnum);
        }
        return null;
    }
}
