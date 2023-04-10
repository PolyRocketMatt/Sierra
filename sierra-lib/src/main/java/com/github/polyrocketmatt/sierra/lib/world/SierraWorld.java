package com.github.polyrocketmatt.sierra.lib.world;

import com.github.polyrocketmatt.sierra.engine.utils.io.YamlDocumentManager;
import com.github.polyrocketmatt.sierra.lib.exception.SierraWorldException;
import com.github.polyrocketmatt.sierra.lib.grid.CellPosition;
import com.github.polyrocketmatt.sierra.lib.grid.WorldGrid;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;

import java.io.File;
import java.util.List;

public class SierraWorld {

    private final String name;
    private final int seed;
    private final int scale;
    private final YamlDocument worldDocument;
    private final WorldGrid worldGrid;

    public SierraWorld(File folder, String name, int seed, int scale) {
        this.name = name;
        this.seed = seed;
        this.scale = scale;
        this.worldDocument = YamlDocumentManager.get(folder, name);
        this.worldGrid = new WorldGrid(folder, scale, seed);
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

    public WorldGrid getWorldGrid() {
        return worldGrid;
    }

    public YamlDocument getWorldDocument() {
        return worldDocument;
    }

    @SuppressWarnings("unchecked")
    public static SierraWorld loadWorld(File folder, String name, YamlDocument document) {
        int seed = document.getInt("seed");
        int scale = document.getInt("scale");
        SierraWorld world = new SierraWorld(folder, name, seed, scale);
        Section cellSection = document.getSection("cells");
        List<CellPosition> positions = (List<CellPosition>) cellSection.getList("positions", List.of());
        int cellCount = cellSection.getInt("count");

        if (cellCount != positions.size())
            throw new SierraWorldException("Expected %s cells, found %s".formatted(cellCount, positions.size()), world);

        world.getWorldGrid().setCellPositions(positions);

        return world;
    }

}
