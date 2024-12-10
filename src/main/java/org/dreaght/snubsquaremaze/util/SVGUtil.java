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

    /**
     * Converts a Maze object into its SVG representation.
     *
     * @param maze The Maze object containing walls to be rendered in the SVG.
     * @return A String containing the SVG markup representing the maze,
     *         with lines for each wall, styled according to their state (open or closed).
     */
    public static String convertMazeToSVG(Maze maze) {
        String svgContent = """
                <svg width="800" height="800" xmlns="http://www.w3.org/2000/svg">
                """;
        StringBuilder svgContentBuilder = new StringBuilder();
        svgContentBuilder.append(svgContent);

        int r = 20;
        int a = 5;

        for (Wall wall : maze.getWalls()) {
            double x1 = wall.getPoints().get(0).getX() * r + a;
            double y1 = wall.getPoints().get(0).getY() * r + a;
            double x2 = wall.getPoints().get(1).getX() * r + a;
            double y2 = wall.getPoints().get(1).getY() * r + a;

            String stroke = "black";
            String strokeWidth = "2";
            String strokeLinecap = "round";

            if (wall.isOpen()) {
                svgContentBuilder.append(String.format("""
                        <line x1="%s" y1="%s" x2="%s" y2="%s"\s
                        stroke-linecap="%s" stroke-width="%s"\s
                        class="open wall" />""", x1, y1, x2, y2, strokeLinecap, strokeWidth));
            } else {
                svgContentBuilder.append(String.format("""
                        <line x1="%s" y1="%s" x2="%s" y2="%s"\s
                        stroke="%s" stroke-linecap="%s"\s
                        stroke-width="%s" />""", x1, y1, x2, y2, stroke,
                        strokeLinecap, strokeWidth));
            }
        }
        svgContentBuilder.append("</svg>");

        return svgContentBuilder.toString();
    }

    public static String showMazeSolution(Maze maze, String svgContent) {
        int r = 20;
        int a = 3;

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
        for (int y = 0; y < points.size(); y++) {
            stringPoints.add(
                    points.get(y).getX() * r + 2 * a + ","
                            + (points.get(y).getY() * r + 2 * a));
            String finalPointsString = String.join(" ", stringPoints);
            String svgPolylineString = String.format("""
            <polyline fill="none" stroke="#ff0000" stroke-linecap="round"\s
            stroke-width="2" xmlns="http://www.w3.org/2000/svg" points="%s"/>
           """, finalPointsString);

            int svgEndingInd = svgContentBuilder.lastIndexOf("</svg>");
            svgContentBuilder.insert(svgEndingInd, svgPolylineString);
        }

        return svgContentBuilder.toString();
    }

    /**
     * Writes the given SVG content to a file named "maze.svg".
     *
     * @param svgContent The SVG content to write to the file.
     */
    public static void saveToFile(String svgContent) {
        try {
            java.io.File file = new java.io.File("maze.svg");
            java.io.FileWriter writer = new java.io.FileWriter(file);
            writer.write(svgContent);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
