package com.github.schnitker.logmgr;

import java.util.Iterator;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.WeakHashMap;

/**
 * LogManager that uses factory to find other logging systems
 */
public class JulLogManager extends java.util.logging.LogManager {

    protected final Map<ClassLoader, JulLoggerFactory> classLoaderFactories = new WeakHashMap<ClassLoader, JulLoggerFactory>();

    @Override
    public java.util.logging.Logger getLogger(final String name) {

        ClassLoader cl = Thread.currentThread().getContextClassLoader();

        JulLoggerFactory factory = classLoaderFactories.get(cl);

        if (factory == null) {

            ServiceLoader<JulLoggerFactory> loader = ServiceLoader.load(JulLoggerFactory.class, cl);

            try {
                Iterator<JulLoggerFactory> iter = loader.iterator();
                if (iter.hasNext()) {
                    factory = loader.iterator().next();
                }
            } catch (ServiceConfigurationError e) {
                System.err.println("LogManager configuration error: " + e.getMessage());
            }

            // no implementation found
            if (factory == null) {
                factory = new JulLoggerFactoryNotFound();
            }
            classLoaderFactories.put(cl, factory);
        }

        if (factory.getClass().equals(JulLoggerFactoryNotFound.class)) {
            return super.getLogger(name);
        }

        return factory.getLogger(name);
    }
}
