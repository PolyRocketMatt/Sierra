package com.github.polyrocketmatt.sierra.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class SierraLogger {

    private final File logFile;
    private final BufferedWriter writer;

    public SierraLogger(File logFile, String prefix) {
        try {
            this.logFile = logFile;
            this.writer = new BufferedWriter(new FileWriter(logFile, true));
        } catch (Exception ex) {
            throw new RuntimeException("Unable to create logger for file " + logFile.getName(), ex);
        }
    }

    public void write(String message) {
        try {
            writer.write(message);
            writer.flush();
        } catch (Exception ex) {
            throw new RuntimeException("Unable to write to log file " + logFile.getName(), ex);
        }
    }

    public void close() {
        try {
            writer.close();
        } catch (Exception ex) {
            throw new RuntimeException("Unable to close log file " + logFile.getName(), ex);
        }
    }

}
