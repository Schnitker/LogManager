package com.github.schnitker.logmgr;

import java.util.logging.Logger;

/**
 * J.U.L. factory, if there are no other implementations.
 * 
 * @author Schnitker
 */
public class JulLoggerFactoryNotFound implements JulLoggerFactory {

    @Override
    public boolean isConfigured() {
        return false;
    }
    
    @Override
    public Logger getLogger(String name) {
        throw new RuntimeException("recursive call");
    }
}
