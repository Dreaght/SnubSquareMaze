package org.dreaght.snubsquaremaze;

import org.dreaght.snubsquaremaze.maze.Maze;
import org.dreaght.snubsquaremaze.util.SVGUtil;
import org.dreaght.snubsquaremaze.util.SvgMazeArguments;

public class SnubSquareMaze {

    public static void main(String[] args) {
        SvgMazeArguments mazeArguments = new SvgMazeArguments(args);
        int width = mazeArguments.getWidth();
        int height = mazeArguments.getHeight();
        String filePath = mazeArguments.getFilePath();
        boolean showSolution = mazeArguments.isShowSolution();
        double zoomFactor = mazeArguments.getZoomFactor();

        Maze maze = new Maze(width, height);
        SVGUtil.saveToFile(showSolution ?
                        SVGUtil.convertMazeToSVGWithSolutionAndOptions(maze, zoomFactor, true) :
                        SVGUtil.convertMazeToSVGWithOptions(maze, zoomFactor, true),
                filePath);
    }
}
