package by.epam.pialetskialiaksei.listener;

import by.epam.pialetskialiaksei.sql.connection.ConnectionPoolManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

	private static final Logger LOG = LogManager.getLogger(ContextListener.class);

	public void contextInitialized(ServletContextEvent event) {
		LOG.info("Servlet context initialization starts");
		ConnectionPoolManager.getInstance();
	}

	public void contextDestroyed(ServletContextEvent event) {
		LOG.info("Servlet context destruction starts");
		ConnectionPoolManager.shutDown();
		LOG.info("Servlet context destruction finished");
	}
}