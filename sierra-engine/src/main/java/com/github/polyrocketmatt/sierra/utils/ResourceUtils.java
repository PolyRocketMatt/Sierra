package com.github.polyrocketmatt.sierra.utils;

import java.net.URL;

public class ResourceUtils {

    public static URL getEngineResource(String name) {
        return Thread.currentThread().getContextClassLoader().getResource(name);
    }

}
