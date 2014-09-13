package com.github.schnitker.logmgr.log4j.tests;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.Filter;

public class LogConfigurator {

    public static LogConfigurator configure() {
        
        LogManager.resetConfiguration();
        Logger root = Logger.getRootLogger();
        
        ConsoleAppender consoleAppender = new ConsoleAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN));
        root.addAppender(consoleAppender);
        
        return new LogConfigurator(consoleAppender);
    }

    // ----------
    
    private ConsoleAppender consoleAppender;

    private LogConfigurator( ConsoleAppender consoleAppender) {
        this.consoleAppender = consoleAppender;
    }
    
    public void addFilter(Filter filter) {
       this.consoleAppender.addFilter(filter);
    }
}
