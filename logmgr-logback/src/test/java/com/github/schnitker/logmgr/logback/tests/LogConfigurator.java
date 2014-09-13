package com.github.schnitker.logmgr.logback.tests;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.filter.Filter;

public class LogConfigurator { // from BasicConfigurator

    public static LogConfigurator configure() {

        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        lc.reset();
        
        ConsoleAppender<ILoggingEvent> ca = new ConsoleAppender<ILoggingEvent>();
        ca.setContext(lc);
        ca.setName("console");

        PatternLayoutEncoder pl = new PatternLayoutEncoder();
        pl.setContext(lc);
        pl.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
        pl.start();

        ca.setEncoder(pl);
        ca.start();

        Logger rootLogger = lc.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.addAppender(ca);

        return new LogConfigurator(ca);
    }

    // -------------
    
    private ConsoleAppender<ILoggingEvent> ca;
    
    private LogConfigurator(ConsoleAppender<ILoggingEvent> ca) {
        this.ca = ca;
    }

    public void addFilter(Filter<ILoggingEvent> filter) {
        this.ca.addFilter(filter);
    }
}
