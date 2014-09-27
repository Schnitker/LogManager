package com.github.schnitker.logmgr.logback;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * LogManager for logback. <p>
 * You must define the system property "java.util.logging.manager" to use this class.
 */
public class JulLogManager extends java.util.logging.LogManager {

    public JulLogManager() {
        super();
    }

    @Override
    public java.util.logging.Logger getLogger(final String name) {
        return new JulLoggerWrapper(name);
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
