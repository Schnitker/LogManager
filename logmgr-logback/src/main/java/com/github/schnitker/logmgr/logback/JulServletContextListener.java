package com.github.schnitker.logmgr.logback;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * ServletContextListener for web application only.
 * @author rainer
 *
 */
public class JulServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        JulLoggerFactoryImpl.isStarted = true;
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JulLoggerFactoryImpl.isStarted = false;
    }
}
