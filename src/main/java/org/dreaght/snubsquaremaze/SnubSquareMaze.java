package org.dreaght.snubsquaremaze;

import org.dreaght.snubsquaremaze.maze.Maze;
import org.dreaght.snubsquaremaze.util.SVGUtil;

public class SnubSquareMaze {
    public static void main(String[] args) {
        double width = args.length > 0 ? Double.parseDouble(args[0]) : 20;
        double height = args.length > 1 ? Double.parseDouble(args[1]) : 20;
        String filePath = args.length > 2 ? args[2] : "maze.svg";

        Maze maze = new Maze(width, height);
        SVGUtil.saveToFile(SVGUtil.convertMazeToSVGWithSolution(maze), filePath);
    }
}
