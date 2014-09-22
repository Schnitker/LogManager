package com.github.schnitker.logmgr.log4j.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.schnitker.logmgr.log4j.JulLogManager;
import com.github.schnitker.logmgr.log4j.JulLoggerWrapper;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestLog4j {

    @BeforeClass
    public static void init() {
        System.setProperty("java.util.logging.manager", JulLogManager.class.getName());
    }

    @Test
    public void test01_Instance() {
        java.util.logging.LogManager logManager = java.util.logging.LogManager.getLogManager();
        assertTrue("LogManager is instance of " + logManager, logManager instanceof JulLogManager);
    }

    @Test
    public void test02_Logger() {
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(getClass().getName());
        assertTrue(logger instanceof JulLoggerWrapper);
    }

    @Test
    public void test03_JdkToImplLevel() {

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
    public void test04_ImplToJdkLevel() {

        java.util.logging.Logger julLogger = java.util.logging.Logger.getLogger(getClass().getName());
        Logger logger = LogManager.getLogger(getClass().getName());

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

    static class TestFilter extends Filter {

        String msg;
        String thrownMsg;

        public int decide(LoggingEvent event) {
            this.msg = (String) event.getMessage();
            if (event.getThrowableInformation() != null && event.getThrowableInformation().getThrowable() != null) {
                this.thrownMsg = event.getThrowableInformation().getThrowable().getMessage();
            }
            return Filter.NEUTRAL;
        }
    }
    
    @Test
    public void testlog() throws UnsupportedEncodingException {

        LogConfigurator conf = LogConfigurator.configure();
        TestFilter filter = new TestFilter();
        conf.addFilter(filter);

        final String testMsg = "Hello world";

        java.util.logging.Logger julLogger = java.util.logging.Logger.getLogger(getClass().getName());
        julLogger.severe(testMsg);

        assertEquals(filter.msg, testMsg);
    }

    @Test
    public void testNoParam() throws UnsupportedEncodingException {

        LogConfigurator conf = LogConfigurator.configure();
        TestFilter filter = new TestFilter();
        conf.addFilter(filter);

        final String testMsg = "Hello world";
        
        java.util.logging.Logger julLogger = java.util.logging.Logger.getLogger(getClass().getName());
        julLogger.log(java.util.logging.Level.WARNING, testMsg);

        assertEquals(filter.msg, testMsg);
    }

    @Test
    public void testParam1() throws UnsupportedEncodingException {

        LogConfigurator conf = LogConfigurator.configure();
        TestFilter filter = new TestFilter();
        conf.addFilter(filter);

        java.util.logging.Logger julLogger = java.util.logging.Logger.getLogger(getClass().getName());
        julLogger.log(java.util.logging.Level.WARNING, "test {0}", "1234");

        assertEquals(filter.msg, "test 1234");
    }

    @Test
    public void testFmtMsg() throws UnsupportedEncodingException {

        LogConfigurator conf = LogConfigurator.configure();
        TestFilter filter = new TestFilter();
        conf.addFilter(filter);

        java.util.logging.Logger julLogger = java.util.logging.Logger.getLogger(getClass().getName());
        julLogger.log(java.util.logging.Level.WARNING, "test {1}, {0}", new Object[] { "1", "2" });

        assertEquals(filter.msg, "test 2, 1");
    }
    
    @Test
    public void testThrown() throws UnsupportedEncodingException {

        LogConfigurator conf = LogConfigurator.configure();
        TestFilter filter = new TestFilter();
        conf.addFilter(filter);

        final String testMsg = "Hello world";
        
        java.util.logging.Logger julLogger = java.util.logging.Logger.getLogger(getClass().getName());
        RuntimeException e = new RuntimeException(testMsg);
        julLogger.log(java.util.logging.Level.WARNING, testMsg, e);

        assertEquals(filter.thrownMsg, testMsg);
    }
}
