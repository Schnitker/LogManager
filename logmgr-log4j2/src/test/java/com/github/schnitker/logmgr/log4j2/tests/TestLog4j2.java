package com.github.schnitker.logmgr.log4j2.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.schnitker.logmgr.log4j2.JulLogManager;
import com.github.schnitker.logmgr.log4j2.JulLoggerWrapper;

public class TestLog4j2 {

    @BeforeClass
    public static void init() {
        System.setProperty("java.util.logging.manager", JulLogManager.class.getName());
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
        Logger logger = LogManager.getLogger(getClass().getName());

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
        org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger) LogManager.getLogger(getClass().getName());

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

    static class TestFilter extends AbstractFilter {

        String msg;

        @Override
        public Result filter(final LogEvent event) {
            this.msg = event.getMessage().getFormattedMessage();
            return Result.NEUTRAL;
        }
    }
    
    @Test
    public void testlog() throws UnsupportedEncodingException {

        LogConfigurator conf = LogConfigurator.configure();
        TestFilter filter = new TestFilter();
        conf.addFilter(LogManager.getLogger(getClass()),filter);

        final String testMsg = "Hello world";

        java.util.logging.Logger julLogger = java.util.logging.Logger.getLogger(getClass().getName());
        julLogger.severe(testMsg);

        assertEquals(filter.msg, testMsg);
    }
}
