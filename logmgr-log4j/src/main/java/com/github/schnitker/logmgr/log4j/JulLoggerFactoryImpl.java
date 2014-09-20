package com.github.schnitker.logmgr.log4j;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.github.schnitker.logmgr.JulLoggerFactory;

/**
 * Implementation for logger factory.
 * @author Schnitker
 */
public class JulLoggerFactoryImpl implements JulLoggerFactory {

    public JulLoggerFactoryImpl() {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.INFO);
    }

    @Override
    public java.util.logging.Logger getLogger(String name) {
        return new JulLoggerWrapper(name);
    }
}
