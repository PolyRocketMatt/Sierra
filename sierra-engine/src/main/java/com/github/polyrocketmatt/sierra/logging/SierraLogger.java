package com.github.polyrocketmatt.sierra.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SierraLogger {

    private static SierraLogger logger;

    private final SimpleDateFormat format;
    private final File logFile;
    private final BufferedWriter writer;

    private SierraLogger(File logFile, String format) {
        try {
            this.format = new SimpleDateFormat(format);
            this.logFile = logFile;
            this.writer = new BufferedWriter(new FileWriter(logFile, true));
        } catch (Exception ex) {
            throw new RuntimeException("Unable to create logger for file " + logFile.getName(), ex);
        }
    }

    private void writeLog(String message) {
        try {
            writer.write(message);
            writer.flush();
        } catch (Exception ex) {
            throw new RuntimeException("Unable to write to log file " + logFile.getName(), ex);
        }
    }

    private void logInform(String message) {
        this.logInform(message, LogType.UNKNOWN);
    }

    private void logInform(String message, LogType type) {
        this.writeLog("%s [%s] [%s] %s".formatted(format.format(new Date()), LogLevel.INFO, type.name(), message));
    }

    private void logWarn(String message) {
        this.logWarn(message, LogType.UNKNOWN);
    }

    private void logWarn(String message, LogType type) {
        this.writeLog("%s [%s] [%s] %s".formatted(format.format(new Date()), LogLevel.WARNING, type.name(), message));
    }

    private void logError(String message) {
        this.logError(message, LogType.UNKNOWN);
    }

    private void logError(String message, LogType type) {
        this.writeLog("%s [%s] [%s] %s".formatted(format.format(new Date()), LogLevel.WARNING, type.name(), message));
    }

    private void flush() {
        try {
            writer.flush();
        } catch (Exception ex) {
            throw new RuntimeException("Unable to flush log file " + logFile.getName(), ex);
        }
    }

    private void close() {
        try {
            writer.close();
        } catch (Exception ex) {
            throw new RuntimeException("Unable to close log file " + logFile.getName(), ex);
        }
    }

    public enum LogLevel {
        INFO,
        WARNING,
        ERROR
    }

    public enum LogType {
        UNKNOWN,
        ENGINE,
        CORE,
        PLATFORM
    }

    public static void initialiseEngineLogger(File logFile, String format) {
        //  This should never happen, but if it happens, we need to close the old logger
        if (logger != null)
            logger.close();

        logger = new SierraLogger(logFile, format);
    }

    public void inform(String message) {
        this.logInform(message);
    }

    public void inform(String message, LogType type) {
        this.logInform(message, type);
    }

    public void warn(String message) {
        this.logWarn(message);
    }

    public void warn(String message, LogType type) {
        this.logWarn(message, type);
    }

    public void error(String message) {
        this.logError(message);
    }

    public void error(String message, LogType type) {
        this.logError(message, type);
    }

}
