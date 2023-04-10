package com.github.polyrocketmatt.sierra.impl.manager;

import com.github.polyrocketmatt.sierra.engine.utils.io.YamlDocumentManager;
import com.github.polyrocketmatt.sierra.lib.world.SierraWorld;
import dev.dejvokep.boostedyaml.YamlDocument;

import java.io.File;
import java.util.List;
import java.util.Random;

public class WorldManager {

    private final YamlDocument worldManagerDocument;
    private final File worldDataFolder;
    private final List<String> worlds;

    public WorldManager(File pluginFolder, File worldDataFolder) {
        this.worldManagerDocument = YamlDocumentManager.get(pluginFolder, "worlds");
        this.worldDataFolder = worldDataFolder;
        this.worlds = worldManagerDocument.getStringList("worlds", List.of());
    }

    public File getWorldDataFolder() {
        return worldDataFolder;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void createWorld(String name, int scale) {
        worlds.add(name);
        worldManagerDocument.set("worlds", worlds);

        //  Save and update
        YamlDocumentManager.save(worldManagerDocument);

        //  Create a folder for the world
        File worldFolder = new File(worldDataFolder, name);
        worldFolder.mkdirs();

        //  Create world
        int seed = new Random().nextInt();
        SierraWorld world = new SierraWorld(worldFolder, name, seed, scale);
    }

    public boolean worldExists(String name) {
        return worlds.contains(name);
    }

}
