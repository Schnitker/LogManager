package com.github.schnitker.logmgr.log4j2.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;

public class LogConfigurator {

    public static LogConfigurator configure() {

        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        ctx.reconfigure();
        Configuration config = ctx.getConfiguration();

        return new LogConfigurator(config);
    }

    // ----------
    
    private Configuration config;

    private LogConfigurator(Configuration config) {
        this.config = config;
    }

    public void addFilter(Logger logger, Filter filter) {
        this.config.addLoggerFilter((org.apache.logging.log4j.core.Logger) logger, filter);
    }
}
