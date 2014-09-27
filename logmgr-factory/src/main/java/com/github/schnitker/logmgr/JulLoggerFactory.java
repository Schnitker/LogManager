package com.github.schnitker.logmgr;

/**
 * Logger factory for routing JUL logging to other frameworks.<p>
 * The factory is use by the {@link JulLogManager} to handle different class loaders.
 * Every class loader can use another logging framework.
 */
public interface JulLoggerFactory {

    /**
     * Is logging framework configured? <p>
     * 
     * @return true is routing should be enabled
     */
    public boolean isConfigured();
    
    /**
     * Get logger wrapper for JUL logger.
     * @param name logger name
     * @return JUL logger wrapper
     */
    public java.util.logging.Logger getLogger(final String name);
}
