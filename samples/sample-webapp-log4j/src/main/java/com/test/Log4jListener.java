package com.test;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.PropertyConfigurator;

/**
 * Application Lifecycle Listener implementation class Log4jListener
 *
 */
@WebListener
public class Log4jListener implements ServletContextListener {

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event)  { 
        ServletContext context = event.getServletContext();
        String fullPath = context.getRealPath("/WEB-INF/log4j.properties");
         
        PropertyConfigurator.configure(fullPath);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent event)  { 
    }
}
