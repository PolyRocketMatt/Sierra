package com.github.polyrocketmatt.sierra.lib.exception;

import com.github.polyrocketmatt.sierra.engine.utils.logger.SierraLogger;
import com.github.polyrocketmatt.sierra.lib.world.SierraWorld;

import java.util.Arrays;

public class SierraWorldException extends SierraLibException {

    public SierraWorldException(String message, SierraWorld world) {
        super(message);

        String trace = Arrays.toString(getStackTrace());
        SierraLogger.error("An world exception has occurred!", SierraLogger.LogType.LIB);
        SierraLogger.error("    Message: %s".formatted(message), SierraLogger.LogType.LIB);
        SierraLogger.error("    World: %s".formatted(world.getName()), SierraLogger.LogType.LIB);
        SierraLogger.error("    Stack Trace: %s".formatted(trace), SierraLogger.LogType.LIB);
    }

}
