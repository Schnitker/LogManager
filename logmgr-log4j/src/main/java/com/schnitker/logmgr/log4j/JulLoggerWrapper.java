package com.schnitker.logmgr.log4j;

import java.text.MessageFormat;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class JulLoggerWrapper extends java.util.logging.Logger {

    private final Logger logger;

    /**
     * Constructs LoggerWrapper from Log4j logger.
     * 
     * @param logger the Log4j logger
     */
    protected JulLoggerWrapper(final Logger logger) {
        super(logger.getName(), null);
        this.logger = logger;

        super.setLevel(JulLevels.toJavaLevel(this.logger.getEffectiveLevel()));
    }

    @Override
    public void log(final java.util.logging.LogRecord record) {
        if (record == null || record.getMessage() == null) {
            return;
        }
        final Level level = JulLevels.toLog4jLevel(record.getLevel());
        if (this.logger.isEnabledFor(level)) {
            String message = record.getMessage();
            try {
                Object parameters[] = record.getParameters();
                if (parameters != null && parameters.length != 0) {
                    message = MessageFormat.format(message, parameters);
                }
            } catch (Exception ex) {
            }
            this.logger.log(level, message, record.getThrown());
        }
    }

    @Override
    public void setLevel(final java.util.logging.Level newLevel)
            throws SecurityException {

        logger.setLevel(JulLevels.toLog4jLevel(newLevel));
        super.setLevel(newLevel);
    }

    @Override
    public java.util.logging.Level getLevel() {
        return JulLevels.toJavaLevel(this.logger.getLevel());
    }

    @Override
    public boolean isLoggable(final java.util.logging.Level level) {
        return this.logger.isEnabledFor(JulLevels.toLog4jLevel(level));
    }

    @Override
    public String getName() {
        return this.logger.getName();
    }

    // ================================================================
    // all other method should check the log4j level
    // ================================================================

    @Override
    public void log(java.util.logging.Level level, String msg) {
        this.logger.log(JulLevels.toLog4jLevel(level), msg);
    }

    @Override
    public void log(java.util.logging.Level level, String msg, Object param1) {
        Level log4jLevel = JulLevels.toLog4jLevel(level);

        if (this.logger.isEnabledFor(log4jLevel)) {
            String fmtMsg = MessageFormat.format(msg, param1);
            this.logger.log(log4jLevel, fmtMsg);
        }
    }

    @Override
    public void log(java.util.logging.Level level, String msg, Object params[]) {
        Level log4jLevel = JulLevels.toLog4jLevel(level);

        if (this.logger.isEnabledFor(log4jLevel)) {
            String fmtMsg = MessageFormat.format(msg, params);
            this.logger.log(log4jLevel, fmtMsg);
        }
    }

    @Override
    public void log(java.util.logging.Level level, String msg, Throwable thrown) {
        this.logger.log(JulLevels.toLog4jLevel(level), msg, thrown);
    }

    @Override
    public void throwing(String sourceClass, String sourceMethod,
            Throwable thrown) {
        this.logger.error(sourceClass + '.' + sourceMethod, thrown);
    }

    @Override
    public void severe(String msg) {
        this.logger.error(msg);
    }

    @Override
    public void warning(String msg) {
        this.logger.warn(msg);
    }

    @Override
    public void info(String msg) {
        this.logger.info(msg);
    }

    @Override
    public void config(String msg) {
        this.logger.debug(msg);
    }

    @Override
    public void fine(String msg) {
        this.logger.debug(msg);
    }

    @Override
    public void finer(String msg) {
        this.logger.trace(msg);
    }

    @Override
    public void finest(String msg) {
        this.logger.trace(msg);
    }
}