package by.epam.pialetskialiaksei.command.api;

import by.epam.pialetskialiaksei.util.ActionType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

public abstract class Command implements Serializable {
    private static final long serialVersionUID = 8879403039606311780L;

    public abstract String execute(HttpServletRequest request,
                                   HttpServletResponse response, ActionType actionType)
            throws IOException, ServletException;


    protected abstract String doGet(HttpServletRequest request,
                                  HttpServletResponse response) ;

    protected abstract String doPost(HttpServletRequest request,
                                   HttpServletResponse response) ;

    @Override
    public final String toString() {
        return getClass().getSimpleName();
    }
}
