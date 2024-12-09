package org.dreaght.snubsquaremaze.maze;

import lombok.Getter;
import org.dreaght.snubsquaremaze.maze.util.Util;

@Getter
public class Cell {
    private final Wall[] walls;
    private final int[] randomPermutation;
    private Wall entryWall;

    public Cell(Wall[] wallArray) {
        this.walls = wallArray;
        for (int i = 0; i < this.walls.length; i++) {
            this.walls[i].getCells().add(this);

        }
        this.randomPermutation = Util.generateRandomPermutation(this.walls.length);
    }
}
