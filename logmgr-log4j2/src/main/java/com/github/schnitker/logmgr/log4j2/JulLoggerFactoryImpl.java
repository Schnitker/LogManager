package com.github.schnitker.logmgr.log4j2;

import com.github.schnitker.logmgr.JulLoggerFactory;

/**
 * Log4j2 implementation for {@link JulLoggerFactory}. <p>
 * 
 * Requires that {@link JulServletContextListener} initialize the value isStarted. Add the listener to the file web.xml for servlet api <= 2.5.
 * 
 * @author Schnitker
 */
public class JulLoggerFactoryImpl implements JulLoggerFactory {

    // singleton per classloader
    static boolean isStarted;
    
    public JulLoggerFactoryImpl() {
        // test simple java app: same classloader
        if (getClass().getClassLoader().equals(JulLoggerFactory.class.getClassLoader())) {
            isStarted = true;
        }
    }
    
    @Override
    public boolean isConfigured() {
        return isStarted;
    }
    
    @Override
    public java.util.logging.Logger getLogger ( String name ) {
        return new JulLoggerWrapper ( name );
    }
}
