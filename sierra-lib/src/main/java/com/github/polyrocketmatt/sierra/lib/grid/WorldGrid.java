package com.github.polyrocketmatt.sierra.lib.grid;

import com.github.polyrocketmatt.sierra.engine.utils.manager.ConfigurationManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorldGrid {

    private final int scale;
    private final int seed;
    private final List<CellPosition> cells;
    private final UUID gridDocument;

    public WorldGrid(File folder, int scale, int seed) {
        this.scale = scale;
        this.seed = seed;
        this.cells = new ArrayList<>();
        this.gridDocument = ConfigurationManager.add(folder, "grid");
    //YamlDocumentManager.get(folder, "grid");
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
