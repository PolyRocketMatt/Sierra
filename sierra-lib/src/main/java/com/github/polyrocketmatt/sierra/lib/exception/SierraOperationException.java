package com.github.polyrocketmatt.sierra.lib.exception;

import com.github.polyrocketmatt.sierra.engine.utils.logger.SierraLogger;

import java.util.Arrays;

public class SierraOperationException extends SierraLibException {

    public SierraOperationException(String message) {
        super(message);

        String trace = Arrays.toString(getStackTrace());
        SierraLogger.error("An operation exception has occurred!");
        SierraLogger.error("    Message: %s".formatted(message));
        SierraLogger.error("    Stack Trace: %s".formatted(trace));
    }

}
