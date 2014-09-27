package com.github.schnitker.logmgr;

import java.util.logging.Logger;

/**
 * Dummy factory, if there are no other implementations.
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
