package com.github.polyrocketmatt.sierra.lib.grid;

import com.github.polyrocketmatt.sierra.engine.utils.io.YamlDocumentManager;
import dev.dejvokep.boostedyaml.YamlDocument;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WorldGrid {

    private final int scale;
    private final int seed;
    private final List<CellPosition> cells;
    private final YamlDocument gridDocument;

    public WorldGrid(File folder, int scale, int seed) {
        this.scale = scale;
        this.seed = seed;
        this.cells = new ArrayList<>();
        this.gridDocument = YamlDocumentManager.get(folder, "grid");
    }

    public void setCellPositions(List<CellPosition> positions) {
        this.cells.clear();
        this.cells.addAll(positions);
    }

    public Cell generateCell(int x, int z) {
        //  First we check if there is already an existing cell at the given coordinates
        if (cells.stream().anyMatch(cell -> cell.x() == x && cell.z() == z))
            return loadCell(x, z);
        return null;
    }

    private Cell loadCell(int x, int z) {
        return null;
    }

}
