package com.github.schnitker.logmgr.logback;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.NoSuchElementException;

import org.slf4j.ILoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.CoreConstants;

public class JulLogManager extends java.util.logging.LogManager {

    private LoggerContext loggerContext;
    
    public JulLogManager() {
        super();
        
        ILoggerFactory factory = StaticLoggerBinder.getSingleton().getLoggerFactory();
        if ( factory instanceof LoggerContext ) {
            loggerContext = (LoggerContext)factory;
        } else {
            loggerContext = new LoggerContext();
            loggerContext.setName(CoreConstants.DEFAULT_CONTEXT_NAME);
        }
    }

    @Override
    public java.util.logging.Logger getLogger(final String name) {
        Logger logger = loggerContext.getLogger(name);
        return new JulLoggerWrapper(logger);
    }

    // --- dummy implementations ----

    @Override
    public void readConfiguration() throws IOException, SecurityException {
    }

    @Override
    public void readConfiguration(InputStream inputStream) throws IOException, SecurityException {
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener l) {
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener l) {
    }

    @Override
    public String getProperty(String name) {
        return null;
    }

    @Override
    public Enumeration<String> getLoggerNames() {
        return new Enumeration<String>() {
            @Override
            public boolean hasMoreElements() {
                return false;
            }

            @Override
            public String nextElement() {
                throw new NoSuchElementException("No elements");
            }
        };
    }

    @Override
    public boolean addLogger(final java.util.logging.Logger logger) {
        return false;
    }

    @Override
    public void reset() throws SecurityException {
    }
}
