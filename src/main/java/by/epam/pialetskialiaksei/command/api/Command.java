package by.epam.pialetskialiaksei.command.api;

import by.epam.pialetskialiaksei.exception.CommandException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command{
    long VersionUID = 8879403039606311780L;

    String execute(HttpServletRequest request,
                                   HttpServletResponse response)
            throws IOException, ServletException, CommandException;

}
