package by.epam.pialetskialiaksei;

import by.epam.pialetskialiaksei.command.CommandManager;
import by.epam.pialetskialiaksei.command.api.Command;
import by.epam.pialetskialiaksei.util.ActionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LogManager.getLogger();

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        if (type == null) {
            process(request, response, ActionType.GET);
        } else {
            process(request, response, ActionType.valueOf(type));
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        process(request, response, ActionType.POST);
    }

    private void process(HttpServletRequest request,
                         HttpServletResponse response, ActionType actionType)
            throws IOException, ServletException {

        LOGGER.debug("Start processing in Controller");

        // extract command name from the request
        String commandName = request.getParameter("command");
        LOGGER.trace("Request parameter: 'command' = " + commandName);

        // obtain command object by its name
        Command command = CommandManager.get(commandName);
        LOGGER.trace("Obtained 'command' = " + command);

        // execute command and get forward address
        String path = null;
        try {
            path = command.execute(request, response, actionType);
            request.getSession().setAttribute("prevCommand", commandName);
        } catch (Exception e) {
            throw new ServletException(e);
        }
        if (path == null) {
            LOGGER.trace("Path is null");
            LOGGER.debug("Controller proccessing finished");
            RequestDispatcher disp = request.getRequestDispatcher(Path.WELCOME_PAGE);
            disp.forward(request, response);
        } else {
            if (actionType == ActionType.GET) {
                LOGGER.trace("Forward to address = " + path);
                LOGGER.debug("Controller proccessing finished");
                RequestDispatcher disp = request.getRequestDispatcher(path);
                disp.forward(request, response);
            } else if (actionType == ActionType.AJAX) {
                LOGGER.trace("AJAX with json= " + path);
                LOGGER.debug("Controller proccessing finished");
                PrintWriter writer = response.getWriter();
                writer.print(path);
            } else if (actionType == ActionType.POST) {
                LOGGER.trace("Redirect to address = " + path);
                LOGGER.debug("Controller proccessing finished");
                response.sendRedirect(path);
            }
        }
    }

    @Override
    public void destroy() {
//        ConnectionPoolManager.shutDown();
        super.destroy();
        LOGGER.debug(this.getServletInfo() + "is destroyed");

    }

    @Override
    public void init() throws ServletException {
        super.init();
        LOGGER.debug(this.getServletInfo() + "is initialized");
    }
}
