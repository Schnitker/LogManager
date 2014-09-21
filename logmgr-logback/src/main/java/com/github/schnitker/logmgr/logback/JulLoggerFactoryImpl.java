package com.github.schnitker.logmgr.logback;

import java.util.logging.Logger;

import com.github.schnitker.logmgr.JulLoggerFactory;

/**
 * Implementation for logger factory.
 * @author Schnitker
 */
public class JulLoggerFactoryImpl implements JulLoggerFactory {

    @Override
    public boolean isConfigured() {
        return true;
    }
    
    @Override
    public Logger getLogger ( String name ) {
        return new JulLoggerWrapper ( name );
    }
}
