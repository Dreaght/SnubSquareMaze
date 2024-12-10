package org.dreaght.snubsquaremaze;

import org.dreaght.snubsquaremaze.maze.Maze;
import org.dreaght.snubsquaremaze.util.SVGUtil;

public class SnubSquareMaze {
    public static void main(String[] args) {
        int width = 20;
        int height = 20;
        String filePath = "maze.svg";
        boolean showSolution = false;
        double zoomFactor = 1;

        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("--width=")) {
                width = Integer.parseInt(args[i].substring(8));
            } else if (args[i].startsWith("--height=")) {
                height = Integer.parseInt(args[i].substring(9));
            } else if (args[i].startsWith("--output=")) {
                filePath = args[i].substring(9);
            } else if (args[i].startsWith("--zoom=")) {
                zoomFactor = Double.parseDouble(args[i].substring(7));
            } else if (args[i].equals("--solution")) {
                showSolution = true;
            }
        }

        Maze maze = new Maze(width, height);
        SVGUtil.saveToFile(showSolution ?
                        SVGUtil.convertMazeToSVGWithSolutionAndOptions(maze, zoomFactor, true) :
                        SVGUtil.convertMazeToSVGWithOptions(maze, zoomFactor, true),
                filePath);
    }
}
