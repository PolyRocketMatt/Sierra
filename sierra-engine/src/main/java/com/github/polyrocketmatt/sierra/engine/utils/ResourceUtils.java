package com.github.polyrocketmatt.sierra.engine.utils;

import java.io.InputStream;

public class ResourceUtils {

    public static InputStream getEngineResourceIS(String name) {
        return ResourceUtils.class.getClassLoader().getResourceAsStream(name);
    }

}
