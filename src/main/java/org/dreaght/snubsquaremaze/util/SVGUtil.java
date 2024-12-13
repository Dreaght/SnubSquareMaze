package org.dreaght.snubsquaremaze.util;

import org.dreaght.snubsquaremaze.maze.Cell;
import org.dreaght.snubsquaremaze.maze.Maze;
import org.dreaght.snubsquaremaze.maze.Point;
import org.dreaght.snubsquaremaze.maze.Wall;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SVGUtil {

    public static String convertMazeToSVGWithSolution(Maze maze) {
        return showMazeSolution(maze, convertMazeToSVG(maze));
    }

    public static String convertMazeToSVGWithSolutionAndOptions(Maze maze, double zoomFactor, boolean optimize) {
        String svgContent = convertMazeToSVGWithOptions(maze, zoomFactor, optimize);
        return showMazeSolutionWithZoom(maze, svgContent, zoomFactor);
    }

    public static String convertMazeToSVG(Maze maze) {
        return convertMazeToSVGWithZoom(maze, 1.0);
    }

    private static String convertMazeToSVGWithZoom(Maze maze, double zoomFactor) {
        StringBuilder svgContentBuilder = new StringBuilder();

        int r = (int) (20 * zoomFactor);
        int a = (int) (5 * zoomFactor);

        int width = calculateMazeDimension(maze.getWidth(), r, a);
        int height = calculateMazeDimension(maze.getHeight(), r, a);

        // Adjust dimensions to ensure all parts of the maze fit
        int maxX = 0;
        int maxY = 0;
        for (Wall wall : maze.getWalls()) {
            for (Point point : wall.getPoints()) {
                maxX = Math.max(maxX, (int) (point.getX() * r + a));
                maxY = Math.max(maxY, (int) (point.getY() * r + a));
            }
        }

        width = Math.max(width, maxX + a);
        height = Math.max(height, maxY + a);

        svgContentBuilder.append("<svg width=\"").append(width).append("\" height=\"").append(height)
                .append("\" xmlns=\"http://www.w3.org/2000/svg\">\n");

        for (Wall wall : maze.getWalls()) {
            double x1 = wall.getPoints().get(0).getX() * r + a;
            double y1 = wall.getPoints().get(0).getY() * r + a;
            double x2 = wall.getPoints().get(1).getX() * r + a;
            double y2 = wall.getPoints().get(1).getY() * r + a;

            String stroke = "black";
            String strokeWidth = String.valueOf(2 * zoomFactor);
            String strokeLinecap = "round";

            if (wall.isOpen()) {
                svgContentBuilder.append("<line x1=\"").append(x1).append("\" y1=\"").append(y1).append("\" x2=\"")
                        .append(x2).append("\" y2=\"").append(y2).append("\" stroke-linecap=\"").append(strokeLinecap)
                        .append("\" stroke-width=\"").append(strokeWidth).append("\" class=\"open wall\" />\n");
            } else {
                svgContentBuilder.append("<line x1=\"").append(x1).append("\" y1=\"").append(y1).append("\" x2=\"")
                        .append(x2).append("\" y2=\"").append(y2).append("\" stroke=\"").append(stroke)
                        .append("\" stroke-linecap=\"").append(strokeLinecap).append("\" stroke-width=\"").append(strokeWidth)
                        .append("\" />\n");
            }
        }
        svgContentBuilder.append("</svg>");

        return svgContentBuilder.toString();
    }

    public static String showMazeSolution(Maze maze, String svgContent) {
        return showMazeSolutionWithZoom(maze, svgContent, 1.0);
    }

    public static String showMazeSolutionWithZoom(Maze maze, String svgContent, double zoomFactor) {
        int r = (int) (20 * zoomFactor);
        int a = (int) (3 * zoomFactor);

        List<Point> points = new LinkedList<>();
        Point endWallCenter = maze.getEndWall().getCenter();
        Cell endCell = maze.getEndCell();
        points.add(endWallCenter);

        Point startWallCenter = maze.getStartWall().getCenter();
        Cell startCell = maze.getStartCell();
        Cell currentCell = endCell;

        do {
            if (currentCell == null) System.exit(1);
            points.add(currentCell.getCenter());

            currentCell = currentCell.getEntryWall().getNeighbor(currentCell);
        } while (currentCell != startCell);

        points.add(startCell.getCenter());
        points.add(startWallCenter);

        StringBuilder svgContentBuilder = new StringBuilder(svgContent);

        List<String> stringPoints = new ArrayList<>();
        for (Point point : points) {
            stringPoints.add((point.getX() * r + 2 * a) + "," + (point.getY() * r + 2 * a));
        }
        String finalPointsString = String.join(" ", stringPoints);
        String svgPolylineString = "<polyline fill=\"none\" stroke=\"#ff0000\" stroke-linecap=\"round\" stroke-width=\""
                + (2 * zoomFactor) + "\" xmlns=\"http://www.w3.org/2000/svg\" points=\"" + finalPointsString + "\"/>\n";

        int svgEndingInd = svgContentBuilder.lastIndexOf("</svg>");
        svgContentBuilder.insert(svgEndingInd, svgPolylineString);

        return svgContentBuilder.toString();
    }

    public static void saveToFile(String svgContent, String filePath) {
        try {
            java.io.File file = new java.io.File(filePath);
            java.io.FileWriter writer = new java.io.FileWriter(file);
            writer.write(svgContent);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String optimizeSVG(String svgContent) {
        return svgContent.replaceAll("\\s+", " ").trim();
    }

    public static int calculateMazeDimension(int mazeDimension, int r, int a) {
        return mazeDimension * r + 2 * a;
    }

    public static String convertMazeToSVGWithOptions(Maze maze, double zoomFactor, boolean optimize) {
        String svgContent = convertMazeToSVGWithZoom(maze, zoomFactor);

        if (optimize) {
            svgContent = optimizeSVG(svgContent);
        }

        return svgContent;
    }
}
