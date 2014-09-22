package com.github.schnitker.logmgr.logback.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.ILoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

import com.github.schnitker.logmgr.logback.JulLogManager;
import com.github.schnitker.logmgr.logback.JulLoggerWrapper;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
    public void test04_ImplToJdkLevel() {

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

    static class TestFilter extends Filter<ILoggingEvent> {

        String msg;
        String thrownMsg;
        
        @Override
        public FilterReply decide(ILoggingEvent event) {
            msg = event.getFormattedMessage();
            thrownMsg = event.getThrowableProxy() != null ? event.getThrowableProxy().getMessage() : null;
            return FilterReply.NEUTRAL;
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
