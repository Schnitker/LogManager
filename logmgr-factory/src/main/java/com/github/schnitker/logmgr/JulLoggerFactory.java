package com.github.schnitker.logmgr;

public interface JulLoggerFactory {

    public java.util.logging.Logger getLogger(final String name);
}
