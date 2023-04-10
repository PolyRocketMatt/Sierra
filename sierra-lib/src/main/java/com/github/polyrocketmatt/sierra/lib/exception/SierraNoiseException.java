package com.github.polyrocketmatt.sierra.lib.exception;

import com.github.polyrocketmatt.sierra.engine.utils.logger.SierraLogger;
import com.github.polyrocketmatt.sierra.lib.noise.NoiseType;
import com.github.polyrocketmatt.sierra.lib.noise.data.NoiseData;

import java.util.Arrays;

public class SierraNoiseException extends SierraLibException {

    public SierraNoiseException(String message, NoiseType type, NoiseData data) {
        super(message);

        String trace = Arrays.toString(getStackTrace());
        SierraLogger.error("An noise exception has occurred!");
        SierraLogger.error("    Message: %s".formatted(message));
        SierraLogger.error("    Noise Type: %s".formatted(type));
        SierraLogger.error("    Noise Data: %s".formatted(data));
        SierraLogger.error("    Stack Trace: %s".formatted(trace));
    }

}
