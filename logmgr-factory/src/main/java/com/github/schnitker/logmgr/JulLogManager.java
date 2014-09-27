package com.github.schnitker.logmgr;

import java.util.Iterator;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.WeakHashMap;

/**
 * LogManager that uses {@link JulLoggerFactory} to find other logging systems via {@link ServiceLoader}.<p>
 * 
 * This LogManager is intended for Servlet containers. You must define the system 
 * property "java.util.logging.manager" to use this class.
 * Every class loader has an own {@link JulLoggerFactory} to route JUL logging calls to other frameworks.
 * If the factory is not configured yet, the manager calls the underlying system. <p>
 * 
 * The {@link JulLoggerFactory} implementations should use ServletContextListener to enable the routing.  
 * <pre>
    +------------------+--------------------+ 
    |             Bootstrap                 | 
    |          (logmgr-factory)             | 
    +------------------+--------------------+ 
    |    Webapp1       |     Webapp2        | 
    |  (logmgr-log4j)  |  (logmgr-logback)  |
    +------------------+--------------------+ 
 * </pre>
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

        if (!factory.isConfigured()) {
            return super.getLogger(name);
        }

        return factory.getLogger(name);
    }
}
