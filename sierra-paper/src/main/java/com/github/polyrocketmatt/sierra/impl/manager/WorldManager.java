package com.github.polyrocketmatt.sierra.impl.manager;

import com.github.polyrocketmatt.sierra.engine.utils.logger.SierraLogger;
import com.github.polyrocketmatt.sierra.engine.utils.manager.ConfigurationManager;
import com.github.polyrocketmatt.sierra.lib.world.SierraWorld;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class WorldManager {

    private final UUID worldManagerDocument;
    private final File worldDataFolder;
    private final Map<String, SierraWorld> worlds;

    public WorldManager(File pluginFolder, File worldDataFolder) {
        SierraLogger.inform("Initialising world manager...", SierraLogger.LogType.PLATFORM);

        this.worldManagerDocument = ConfigurationManager.add(pluginFolder, "worlds");
        this.worldDataFolder = worldDataFolder;
        this.worlds = new HashMap<>();

        List<String> worldNames = ConfigurationManager.get(worldManagerDocument).getStringList("worlds", List.of());
        SierraLogger.inform("   -> Expecting to load %s worlds".formatted(worldNames.size()), SierraLogger.LogType.PLATFORM);
        for (String name : worldNames) {
            SierraLogger.inform("   -> Loading world '%s'...".formatted(name), SierraLogger.LogType.PLATFORM);
            this.worlds.put(name, loadWorld(name));
        }

        SierraLogger.inform("   -> Initialized %s worlds".formatted(this.worlds.size()), SierraLogger.LogType.PLATFORM);
    }

    public File getWorldDataFolder() {
        return worldDataFolder;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void createWorld(String name, int scale) {
        SierraLogger.inform("Creating world '%s'...".formatted(name), SierraLogger.LogType.PLATFORM);

        //  Create a folder for the world
        File worldFolder = new File(worldDataFolder, name);
        worldFolder.mkdirs();

        //  Create world
        int seed = new Random().nextInt();
        SierraWorld world = new SierraWorld(worldFolder, name, seed, scale, true);

        this.worlds.put(name, world);

        SierraLogger.inform("    -> Created world '%s'".formatted(name), SierraLogger.LogType.PLATFORM);
        List<String> worldNames = new ArrayList<>(worlds.keySet());
        ConfigurationManager.get(worldManagerDocument).set("worlds", worldNames);
        ConfigurationManager.save(worldManagerDocument);
        SierraLogger.inform("    -> Saved world '%s' to world index".formatted(name), SierraLogger.LogType.PLATFORM);
        SierraLogger.inform("    -> Starting BUKKIT world generation for %s".formatted(name), SierraLogger.LogType.PLATFORM);
    }

    public SierraWorld loadWorld(String name) {
        File worldFolder = new File(worldDataFolder, name);
        UUID worldDocument = ConfigurationManager.add(worldFolder, name);

        return SierraWorld.loadWorld(worldFolder, name, worldDocument);
    }

    public boolean worldExists(String name) {
        return worlds.containsKey(name);
    }

}
