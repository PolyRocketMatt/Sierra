package com.github.polyrocketmatt.sierra.lib.exception;

import com.github.polyrocketmatt.sierra.lib.noise.NoiseType;
import com.github.polyrocketmatt.sierra.lib.noise.data.NoiseData;

public class SierraNoiseException extends SierraException {

    public SierraNoiseException(String message, NoiseType type, NoiseData data) {
        super(message);
    }

}
