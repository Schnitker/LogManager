package com.github.schnitker.logmgr.log4j2;

import java.util.logging.Logger;

import com.github.schnitker.logmgr.JulLoggerFactory;

/**
 * Implementation for logger factory. <p>
 * Requires that JulServletContextListener initialize the value isStarted. Add to web.xml for servlet api <= 2.5.
 * @author Schnitker
 */
public class JulLoggerFactoryImpl implements JulLoggerFactory {

    static boolean isStarted;
    
    @Override
    public boolean isConfigured() {
        return isStarted;
    }
    
    @Override
    public Logger getLogger ( String name ) {
        return new JulLoggerWrapper ( name );
    }
}
