package com.github.schnitker.logmgr.logback;

import java.text.MessageFormat;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class JulLoggerWrapper extends java.util.logging.Logger {

    private final Logger logger;

    /**
     * Constructs LoggerWrapper from Log4j logger.
     * 
     * @param logger
     *            the Logback logger
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
        final Level level = JulLevels.toLogbackLevel(record.getLevel());
        if (this.logger.isEnabledFor(level)) {
            String message = record.getMessage();
            try {
                Object parameters[] = record.getParameters();
                if (parameters != null && parameters.length != 0) {
                    message = MessageFormat.format(message, parameters);
                }
            } catch (Exception ex) {
                // ignore parameter error
            }
            this.logger.log(null, Logger.FQCN, Level.toLocationAwareLoggerInteger(level), message, null, record.getThrown());
        }
    }

    @Override
    public void setLevel(final java.util.logging.Level newLevel) throws SecurityException {

        logger.setLevel(JulLevels.toLogbackLevel(newLevel));
        super.setLevel(newLevel);
    }

    @Override
    public java.util.logging.Level getLevel() {
        return JulLevels.toJavaLevel(this.logger.getLevel());
    }

    @Override
    public boolean isLoggable(final java.util.logging.Level level) {
        return this.logger.isEnabledFor(JulLevels.toLogbackLevel(level));
    }

    @Override
    public String getName() {
        return this.logger.getName();
    }

    // ================================================================
    // all other method should check the logback level
    // ================================================================

    @Override
    public void log(java.util.logging.Level level, String msg) {
        Level logbackLevel = JulLevels.toLogbackLevel(level);
        this.logger.log(null, Logger.FQCN, Level.toLocationAwareLoggerInteger(logbackLevel), msg, null, null);
    }

    @Override
    public void log(java.util.logging.Level level, String msg, Object param1) {
        Level logbackLevel = JulLevels.toLogbackLevel(level);
        
        if (this.logger.isEnabledFor(logbackLevel)) {
            String fmtMsg = MessageFormat.format(msg, param1);
            this.logger.log(null, Logger.FQCN, Level.toLocationAwareLoggerInteger(logbackLevel), fmtMsg, null, null);
        }
    }

    @Override
    public void log(java.util.logging.Level level, String msg, Object params[]) {
        Level logbackLevel = JulLevels.toLogbackLevel(level);

        if (this.logger.isEnabledFor(logbackLevel)) {
            String fmtMsg = MessageFormat.format(msg, params);
            this.logger.log(null, Logger.FQCN, Level.toLocationAwareLoggerInteger(logbackLevel), fmtMsg, null, null);
        }
    }

    @Override
    public void log(java.util.logging.Level level, String msg, Throwable thrown) {
        Level logbackLevel = JulLevels.toLogbackLevel(level);
        
        this.logger.log(null, Logger.FQCN, Level.toLocationAwareLoggerInteger(logbackLevel), msg, null, thrown);
    }

    @Override
    public void throwing(String sourceClass, String sourceMethod, Throwable thrown) {
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