package com.github.polyrocketmatt.sierra.engine.utils.io;

import com.github.polyrocketmatt.sierra.engine.utils.logger.SierraLogger;
import dev.dejvokep.boostedyaml.YamlDocument;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class YamlDocumentManager {

    public static YamlDocument get(File folder, String name) {
        try {
            return YamlDocument.create(new File(folder, name + ".yml"));
        } catch (IOException ex) {
            SierraLogger.error("Failed to load YAML document: %s".formatted(ex.getMessage()), SierraLogger.LogType.ENGINE);
            SierraLogger.error("    Message: %s".formatted(ex.getMessage()), SierraLogger.LogType.ENGINE);
            SierraLogger.error("    Stack Trace: %s".formatted(Arrays.toString(ex.getStackTrace())), SierraLogger.LogType.ENGINE);
        }

        throw new NullPointerException("Failed to load YAML document: %s".formatted(name));
    }

    public static void save(YamlDocument document) {
        try {
            document.save();
            document.update();
        } catch (IOException ex) {
            SierraLogger.error("Failed to save and update YAML document: %s".formatted(ex.getMessage()), SierraLogger.LogType.ENGINE);
            SierraLogger.error("    Message: %s".formatted(ex.getMessage()), SierraLogger.LogType.ENGINE);
            SierraLogger.error("    Stack Trace: %s".formatted(Arrays.toString(ex.getStackTrace())), SierraLogger.LogType.ENGINE);
        }
    }

}
