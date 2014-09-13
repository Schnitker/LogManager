package com.github.schnitker.logmgr.logback.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.ILoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.CoreConstants;

import com.github.schnitker.logmgr.logback.JulLogManager;
import com.github.schnitker.logmgr.logback.JulLoggerWrapper;

public class TestLogback {

    private static LoggerContext loggerContext;
    
    @BeforeClass
    public static void init() {
        System.setProperty("java.util.logging.manager", JulLogManager.class.getName());

        ILoggerFactory factory = StaticLoggerBinder.getSingleton().getLoggerFactory();
        if ( factory instanceof LoggerContext ) {
            loggerContext = (LoggerContext)factory;
        } else {
            loggerContext = new LoggerContext();
            loggerContext.setName(CoreConstants.DEFAULT_CONTEXT_NAME);
        }
    }

    @Test
    public void testInstance() {
        java.util.logging.LogManager logManager = java.util.logging.LogManager.getLogManager();
        assertTrue("LogManager is instance of " + logManager, logManager instanceof JulLogManager);
    }

    @Test
    public void testLogger() {
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(getClass().getName());
        assertTrue(logger instanceof JulLoggerWrapper);
    }

    @Test
    public void testJdkToImplLevel() {

        java.util.logging.Logger julLogger = java.util.logging.Logger.getLogger(getClass().getName());
        Logger logger = loggerContext.getLogger(getClass().getName());

        julLogger.setLevel(java.util.logging.Level.SEVERE);
        assertEquals(Level.ERROR, logger.getLevel());

        julLogger.setLevel(java.util.logging.Level.WARNING);
        assertEquals(Level.WARN, logger.getLevel());

        julLogger.setLevel(java.util.logging.Level.INFO);
        assertEquals(Level.INFO, logger.getLevel());

        julLogger.setLevel(java.util.logging.Level.CONFIG);
        assertEquals(Level.DEBUG, logger.getLevel());

        julLogger.setLevel(java.util.logging.Level.FINE);
        assertEquals(Level.DEBUG, logger.getLevel());
        
        julLogger.setLevel(java.util.logging.Level.FINER);
        assertEquals(Level.TRACE, logger.getLevel());
        
        julLogger.setLevel(java.util.logging.Level.FINEST);
        assertEquals(Level.TRACE, logger.getLevel());
    }

    @Test
    public void testImplToJdkLevel() {

        java.util.logging.Logger julLogger = java.util.logging.Logger.getLogger(getClass().getName());
        Logger logger = loggerContext.getLogger(getClass().getName());

        logger.setLevel(Level.ERROR);
        assertEquals(java.util.logging.Level.SEVERE, julLogger.getLevel());

        logger.setLevel(Level.WARN);
        assertEquals(java.util.logging.Level.WARNING, julLogger.getLevel());

        logger.setLevel(Level.INFO);
        assertEquals(java.util.logging.Level.INFO, julLogger.getLevel());

        logger.setLevel(Level.DEBUG);
        assertEquals(java.util.logging.Level.FINE, julLogger.getLevel());
        
        logger.setLevel(Level.TRACE);
        assertEquals(java.util.logging.Level.FINEST, julLogger.getLevel());
    }

    @Test
    public void testlog() throws UnsupportedEncodingException {
        
        PrintStream old = System.out;
        
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(baos));
            // use default logback configuration
            
            java.util.logging.Logger logger = java.util.logging.Logger.getLogger(getClass().getName());
            logger.severe("test2");
            
            String s = baos.toString("utf-8");
            assertTrue("unexpected: " + s, s.contains("ERROR c.g.s.l.logback.tests.TestLogback")); // test class
        } finally {
            System.setOut(old);
        }
    }
}
