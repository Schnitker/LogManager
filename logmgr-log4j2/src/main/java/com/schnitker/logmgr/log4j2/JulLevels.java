package com.schnitker.logmgr.log4j2;

import org.apache.logging.log4j.Level;

/**
 * Java Util Logging / Log4j Mapping:
 * 
 * <pre>
 * 
 *    JUL Level     |   Log4j Level
 *    ---------------------------------------
 *    ALL           |   ALL
 *    FINEST        |   TRACE
 *    FINER         |   TRACE
 *    FINE          |   DEBUG
 *    CONFIG        |   DEBUG
 *    INFO          |   INFO
 *    WARNING       |   WARN
 *    SEVERE        |   ERROR
 *    SEVERE        |   FATAL
 *    OFF           |   OFF
 * </pre>
 * 
 * @author Schnitker
 *
 */
public abstract class JulLevels {

    /**
     * Converts a JDK logging Level to a Log4j logging Level.
     *
     * @param level the JDK Level to convert.
     * @return converted Level.
     */
    public static Level toLog4jLevel(final java.util.logging.Level level) {
        switch (level.intValue()) {

        case Integer.MAX_VALUE: // OFF
            return Level.OFF;
        case Integer.MIN_VALUE: // ALL
            return Level.ALL;

        case 300: // FINEST
        case 400: // FINER
            return Level.TRACE;

        case 500: // FINE
        case 700: // CONFIG
            return Level.DEBUG;

        case 800: // INFO
            return Level.INFO;

        case 900: // WARNING
            return Level.WARN;

        case 1000: // SEVERE
            return Level.ERROR;

        default: // unknown
            return Level.WARN;
        }
    }

    /**
     * Converts a Log4j logging Level to a JDK logging Level.
     *
     * @param level
     *            Log4j Level to convert.
     * @return converted Level.
     */
    public static java.util.logging.Level toJavaLevel(final Level level) {

        switch (level.getStandardLevel()) {

        case ALL:
            return java.util.logging.Level.ALL;

        case OFF:
            return java.util.logging.Level.OFF;

        case TRACE:
            return java.util.logging.Level.FINEST;

        case DEBUG:
            return java.util.logging.Level.FINE;

        case INFO:
            return java.util.logging.Level.INFO;

        case WARN:
            return java.util.logging.Level.WARNING;

        case ERROR:
        case FATAL:
            return java.util.logging.Level.SEVERE;

        default: // unknown
            return java.util.logging.Level.WARNING;
        }
    }

    private JulLevels() {
    }
}
