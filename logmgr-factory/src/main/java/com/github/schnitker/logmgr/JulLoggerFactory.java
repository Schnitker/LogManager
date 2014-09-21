package com.github.schnitker.logmgr;

public interface JulLoggerFactory {

    public boolean isConfigured();
    
    public java.util.logging.Logger getLogger(final String name);
}
