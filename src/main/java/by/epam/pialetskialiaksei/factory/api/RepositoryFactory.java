package by.epam.pialetskialiaksei.factory.api;

import by.epam.pialetskialiaksei.commandrepository.api.CommandRepository;

public interface RepositoryFactory {
    CommandRepository getRepository (String command);
}
