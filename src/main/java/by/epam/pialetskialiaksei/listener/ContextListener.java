package by.epam.pialetskialiaksei.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import by.epam.pialetskialiaksei.sql.connection.ConnectionPoolManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Context listener. Initializes log4j, i18n and Command Manager for future use.
 *
 * @author Mark Norkin
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {

	private static final Logger LOG = LogManager.getLogger(ContextListener.class);

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		LOG.info("Servlet context initialization starts");
		ConnectionPoolManager.getInstance();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {
		LOG.info("Servlet context destruction starts");
		ConnectionPoolManager.shutDown();
		LOG.info("Servlet context destruction finished");
	}
}