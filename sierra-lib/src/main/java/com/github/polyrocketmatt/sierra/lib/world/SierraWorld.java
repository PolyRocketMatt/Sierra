package com.github.polyrocketmatt.sierra.lib.world;

import com.github.polyrocketmatt.sierra.engine.utils.manager.ConfigurationManager;
import com.github.polyrocketmatt.sierra.lib.grid.CellPosition;
import com.github.polyrocketmatt.sierra.lib.grid.WorldGrid;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class SierraWorld {

    private final String name;
    private final int seed;
    private final int scale;
    private final UUID worldUUID;
    private final WorldGrid worldGrid;

    public SierraWorld(File folder, String name, int seed, int scale, boolean initialise) {
        this.name = name;
        this.seed = seed;
        this.scale = scale;
        this.worldUUID = ConfigurationManager.add(folder, "world");
        this.worldGrid = new WorldGrid(folder, scale, seed);

        if (initialise) {
            YamlDocument document = ConfigurationManager.get(worldUUID);

            document.set("seed", seed);
            document.set("scale", scale);

            ConfigurationManager.save(worldUUID);
        }
    }

    public String getName() {
        return name;
    }

    public int getSeed() {
        return seed;
    }

    public int getScale() {
        return scale;
    }

    public UUID getWorldUUID() {
        return worldUUID;
    }

    public WorldGrid getWorldGrid() {
        return worldGrid;
    }

    @SuppressWarnings("unchecked")
    public static SierraWorld loadWorld(File folder, String name, UUID uuid) {
        YamlDocument document = ConfigurationManager.get(uuid);

        int seed = document.getInt("seed");
        int scale = document.getInt("scale");
        SierraWorld world = new SierraWorld(folder, name, seed, scale, false);
        List<CellPosition> positions;

        if (document.isSection("cells")) {
            Section cellSection = document.getSection("cells");
            positions = (List<CellPosition>) cellSection.getList("positions", List.of());
        } else
            positions = List.of();

        world.getWorldGrid().setCellPositions(positions);

        return world;
    }

}
