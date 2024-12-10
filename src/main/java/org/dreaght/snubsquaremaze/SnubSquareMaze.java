package org.dreaght.snubsquaremaze;

import org.dreaght.snubsquaremaze.maze.Maze;
import org.dreaght.snubsquaremaze.util.SVGUtil;

public class SnubSquareMaze {
    public static void main(String[] args) {
        Maze maze = new Maze(5, 5);
        System.out.println(maze.getWalls().size() + " " + maze.getCells().size());
        SVGUtil.saveToFile(SVGUtil.convertMazeToSVGWithSolution(maze));
    }
}