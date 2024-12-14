package org.dreaght.snubsquaremaze;

import org.dreaght.snubsquaremaze.maze.Maze;
import org.dreaght.snubsquaremaze.util.SVGUtil;
import org.dreaght.snubsquaremaze.util.SvgMazeArguments;

public class SnubSquareMaze {

    public static void main(String[] args) {
        SvgMazeArguments mazeArguments = new SvgMazeArguments(args);
        String filePath = mazeArguments.getFilePath();
        boolean showSolution = mazeArguments.isShowSolution();
        double zoomFactor = mazeArguments.getZoomFactor();

        Maze maze = new Maze(mazeArguments);
        SVGUtil.saveToFile(showSolution ?
                        SVGUtil.convertMazeToSVGWithSolutionAndOptions(maze, zoomFactor, true) :
                        SVGUtil.convertMazeToSVGWithOptions(maze, zoomFactor, true),
                filePath.replace("%SEED%", Long.toString(maze.getSeed()))
                         .replace("%WIDTH%", Integer.toString(maze.getWidth()))
                         .replace("%HEIGHT%", Integer.toString(maze.getHeight())));
    }
}
