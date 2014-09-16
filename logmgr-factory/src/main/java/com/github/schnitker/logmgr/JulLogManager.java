package com.github.schnitker.logmgr;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.WeakHashMap;

import com.github.schnitker.logmgr.JulLoggerFactory;

public class JulLogManager extends java.util.logging.LogManager {

    protected final Map<ClassLoader, JulLoggerFactory> classLoaderFactories = 
            new WeakHashMap<ClassLoader, JulLoggerFactory>();
    
    public JulLogManager() {
        super();
    }

    @Override
    public java.util.logging.Logger getLogger ( final String name ) {

        ClassLoader cl = Thread.currentThread ().getContextClassLoader ();

        JulLoggerFactory factory = classLoaderFactories.get ( cl );

        if ( factory == null ) {

            ServiceLoader < JulLoggerFactory > loader = ServiceLoader.load ( JulLoggerFactory.class, cl );

            try {
                factory = loader.iterator ().next ();

            } catch ( ServiceConfigurationError e ) {
                System.err.println ( "LogManager configuration error: " + e.getMessage () );
                factory = new JulLoggerFactoryNotFound ();
            }
            classLoaderFactories.put ( cl, factory );
        }

        return factory.getLogger ( name );
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
