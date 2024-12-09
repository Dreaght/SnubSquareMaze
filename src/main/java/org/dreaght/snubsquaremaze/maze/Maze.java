package org.dreaght.snubsquaremaze.maze;

import lombok.Getter;
import lombok.Setter;
import org.dreaght.snubsquaremaze.maze.util.Util;

import java.util.LinkedList;
import java.util.List;

@Getter @Setter
public class Maze {

    private final List<Point> points = new LinkedList<>();
    private final List<Wall> walls = new LinkedList<>();
    private final List<Cell> cells = new LinkedList<>();
    private Point startPoint;
    private Point endPoint;
    private Wall startWall;
    private Wall endWall;
    private int maxDepth;
    private int endDepth;
    private final int min_width = 3;
    private final int min_height = 3;
    private final int xMultiplier = 1;
    private final int yMultiplier = 1;
    private final int maxFaceSize = 4;
    private int width;
    private int height;

    public Maze(int width, int height) {
        if (width <= min_width || height <= min_height) {
            throw new IllegalArgumentException("Minimal width and height: " +
                    min_width + "x" + min_height);
        }
        this.width = width * xMultiplier;
        this.height = height * yMultiplier;
        generate();
        sortWallsForPoints();
        for (Point p : points) {

        }
    }

    private void generate() {
        double[][] rotationMatrix = Util.getRotationMatrix();

        double offsetFactor = 1 - Math.cos(15 * Math.PI / 180);
        Point[][] gridPoints = new Point[width + 1][height + 1];

        for (int x = 0; x <= width; x++) {
            for (int y = 0; y <= height; y++) {
                // Calculate point positions
                double px, py;
                if ((x & 1) == 0 && (y & 1) == 0) { // Even, even
                    px = -2 * Math.floor((double) x / 2) * offsetFactor + rotationMatrix[0][0];
                    py = -2 * Math.floor((double) y / 2) * offsetFactor + rotationMatrix[0][1];
                } else if ((x & 1) == 0 && (y & 1) != 0) { // Even, odd
                    px = -2 * Math.floor((double) x / 2) * offsetFactor + rotationMatrix[0][0];
                    py = -2 * Math.floor((double) y / 2) * offsetFactor + rotationMatrix[1][1];
                } else if ((x & 1) != 0 && (y & 1) == 0) { // Odd, even
                    px = -2 * Math.floor((double) x / 2) * offsetFactor + rotationMatrix[0][0];
                    py = -2 * Math.floor((double) y / 2) * offsetFactor + rotationMatrix[0][1]
                            + rotationMatrix[1][1];
                } else { // Odd, odd
                    px = -2 * Math.floor((double) x / 2) * offsetFactor + rotationMatrix[1][0];
                    py = -2 * Math.floor((double) y / 2) * offsetFactor + rotationMatrix[1][1];
                }

                gridPoints[x][y] = new Point(px, py);
                if (x > 0) {
                    walls.add(new Wall(gridPoints[x - 1][y], gridPoints[x][y]));
                }
                if (y > 0) {
                    walls.add(new Wall(gridPoints[x][y - 1], gridPoints[x][y]));
                }
                if ((x & 1) == 0 && (y & 1) != 0) {
                    walls.add(new Wall(gridPoints[x][y], gridPoints[x - 1][y - 1]));
                }
                if ((x & 1) != 0 && (y & 1) != 0) {
                    walls.add(new Wall(gridPoints[x][y - 1], gridPoints[x - 1][y]));
                }

                // Add to the points list
                points.add(gridPoints[x][y]);
            }
        }

        // Set the start and end points
        startPoint = gridPoints[0][0];
        endPoint = gridPoints[width][height];

        // Set the final size of the maze
        width = (int) gridPoints[width][height].getX();
        height = (int) gridPoints[width][height].getY();
    }

    public void sortWallsForPoints() {
        for (Point p : points) {
            p.sortWalls();
        }
    }
}
