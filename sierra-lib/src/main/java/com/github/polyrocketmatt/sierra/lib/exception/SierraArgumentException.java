package com.github.polyrocketmatt.sierra.lib.exception;

import com.github.polyrocketmatt.sierra.engine.utils.logger.SierraLogger;

import java.util.Arrays;

public class SierraArgumentException extends SierraLibException {

    public SierraArgumentException(String message) {
        super(message);

        String trace = Arrays.toString(getStackTrace());
        SierraLogger.error("An argument exception has occurred!");
        SierraLogger.error("    Message: %s".formatted(message));
        SierraLogger.error("    Stack Trace: %s".formatted(trace));
    }

}
