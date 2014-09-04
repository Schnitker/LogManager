package com.schnitker.logmgr.log4j;

import static org.junit.Assert.*;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.Test;

import com.schnitker.logmgr.log4j2.JulLogManager;
import com.schnitker.logmgr.log4j2.JulLoggerWrapper;

public class TestLog4j2 {

    @BeforeClass
    public static void init() {
        System.setProperty("java.util.logging.manager", JulLogManager.class.getName());
    }

    @Test
    public void testInstance() {

        assertTrue(LogManager.getLogManager() instanceof JulLogManager);
    }

    @Test
    public void testLogger() {

        Logger logger = Logger.getLogger(getClass().getName());
        assertTrue(logger instanceof JulLoggerWrapper);
    }

    @Test
    public void testJdkToLog4jLevel() {

        Logger logger = Logger.getLogger(getClass().getName());
        org.apache.logging.log4j.Logger log4jLogger = org.apache.logging.log4j.LogManager.getLogger(getClass().getName());

        logger.setLevel(Level.SEVERE);
        assertEquals(org.apache.logging.log4j.Level.ERROR, log4jLogger.getLevel());

        logger.setLevel(Level.WARNING);
        assertEquals(org.apache.logging.log4j.Level.WARN, log4jLogger.getLevel());

        logger.setLevel(Level.INFO);
        assertEquals(org.apache.logging.log4j.Level.INFO, log4jLogger.getLevel());

        logger.setLevel(Level.CONFIG);
        assertEquals(org.apache.logging.log4j.Level.DEBUG, log4jLogger.getLevel());
        
        logger.setLevel(Level.FINE);
        assertEquals(org.apache.logging.log4j.Level.DEBUG, log4jLogger.getLevel());
        
        logger.setLevel(Level.FINER);
        assertEquals(org.apache.logging.log4j.Level.TRACE, log4jLogger.getLevel());
        
        logger.setLevel(Level.FINEST);
        assertEquals(org.apache.logging.log4j.Level.TRACE, log4jLogger.getLevel());
    }

    @Test
    public void testLog4jToJulLevel() {

        Logger logger = Logger.getLogger(getClass().getName());
        org.apache.logging.log4j.core.Logger log4jLogger = (org.apache.logging.log4j.core.Logger) org.apache.logging.log4j.LogManager.getLogger(getClass().getName());

        log4jLogger.setLevel(org.apache.logging.log4j.Level.ERROR);
        assertEquals(Level.SEVERE, logger.getLevel());

        log4jLogger.setLevel(org.apache.logging.log4j.Level.WARN);
        assertEquals(Level.WARNING, logger.getLevel());

        log4jLogger.setLevel(org.apache.logging.log4j.Level.INFO);
        assertEquals(Level.INFO, logger.getLevel());

        log4jLogger.setLevel(org.apache.logging.log4j.Level.DEBUG);
        assertEquals(Level.FINE, logger.getLevel());
        
        log4jLogger.setLevel(org.apache.logging.log4j.Level.TRACE);
        assertEquals(Level.FINEST, logger.getLevel());
    }
}
