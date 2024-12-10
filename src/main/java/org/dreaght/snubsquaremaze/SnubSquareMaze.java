package org.dreaght.snubsquaremaze;

import org.dreaght.snubsquaremaze.maze.Maze;
import org.dreaght.snubsquaremaze.util.SVGUtil;

public class SnubSquareMaze {
    public static void main(String[] args) {
        Maze maze = new Maze(10, 10);
        SVGUtil.saveToFile(SVGUtil.convertMazeToSVGWithSolution(maze));
    }
}
