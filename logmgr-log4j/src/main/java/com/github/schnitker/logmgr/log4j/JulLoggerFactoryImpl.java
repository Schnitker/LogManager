package com.github.schnitker.logmgr.log4j;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.github.schnitker.logmgr.JulLoggerFactory;

/**
 * Implementation for logger factory.
 * 
 * @author Schnitker
 */
public class JulLoggerFactoryImpl implements JulLoggerFactory {

    public JulLoggerFactoryImpl() {
        // if not configured, prevent Log4j message (f.e. on servlet start by tomcat) 
        if (! LogManager.getRootLogger().getAllAppenders().hasMoreElements()) {
            BasicConfigurator.configure();
            Logger.getRootLogger().setLevel(Level.INFO);
        }
    }

    @Override
    public boolean isConfigured() {
        return true;
    }

    @Override
    public java.util.logging.Logger getLogger(String name) {
        return new JulLoggerWrapper(name);
    }
}
