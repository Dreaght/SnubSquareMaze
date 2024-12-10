package org.dreaght.snubsquaremaze.maze;

import lombok.Getter;
import org.dreaght.snubsquaremaze.maze.util.Util;

import java.util.LinkedList;
import java.util.List;

@Getter
public class Maze {

    private final List<Point> points = new LinkedList<>();
    private final List<Wall> walls = new LinkedList<>();
    private final List<Cell> cells = new LinkedList<>();
    private Point startPoint;
    private Point endPoint;
    private final Wall startWall;
    private final Wall endWall;
    private int maxDepth;
    private final int endDepth;
    private final double minWidth = 3;
    private final double minHeight = 3;
    private final int maxFaceSize = 4;
    private double width;
    private double height;

    public Maze(double width, double height) {
        if (width < minWidth || height < minHeight) {
            throw new IllegalArgumentException("Minimal width and height: " +
                    minWidth + "x" + minHeight);
        }
        this.width = width;
        this.height = height;

        generate();
        sortWallsForPoints();

        for (Point p : points) {
            cells.addAll(p.findFaces(maxFaceSize));
        }

        this.startWall = findStartWall();
        this.endWall = findEndWall();

        generateRecursive();

        openCorners();
        findMaxDepth();
        this.endDepth = this.getEndCell().getDepth();
    }

    private void generate() {
        double[][][] rotationMatrix = Util.getRotationMatrix();

        double offsetFactor = 1 - Math.cos(15 * Math.PI / 180);
        Point[][] gridPoints = new Point[(int) (width + 1)][(int) (height + 1)];

        for (int x = 0; x <= width; x++) {
            for (int y = 0; y <= height; y++) {
                // Calculate point positions

                double px = x - 2 * Math.floor((double) x / 2) * offsetFactor +
                        rotationMatrix[x & 1][y & 1][0];
                double py = y - 2 * Math.floor((double) y / 2) * offsetFactor +
                        rotationMatrix[x & 1][y & 1][1];

                gridPoints[x][y] = new Point(px, py);
                // Add to the points list
                points.add(gridPoints[x][y]);

                if (x > 0) {
                    walls.add(new Wall(gridPoints[x - 1][y], gridPoints[x][y]));
                }
                if (y > 0) {
                    walls.add(new Wall(gridPoints[x][y - 1], gridPoints[x][y]));
                }
                if (x > 0 && (x & 1) == 0 && (y & 1) == 1) {
                    walls.add(new Wall(gridPoints[x][y], gridPoints[x - 1][y - 1]));
                }
                if (y > 0 && (x & 1) == 1 && (y & 1) == 0) {
                    walls.add(new Wall(gridPoints[x][y - 1], gridPoints[x - 1][y]));
                }
            }
        }

        // Set the start and end points
        startPoint = gridPoints[0][0];
        endPoint = gridPoints[(int) width][(int) height];

        // Set the final size of the maze
        width = (int) gridPoints[(int) width][(int) height].getX();
        height = (int) gridPoints[(int) width][(int) height].getY();
    }

    private void generateRecursive() {
        recursiveHelper(getStartCell(), null, 0);
    }

    private void recursiveHelper(Cell currentCell, Wall entryWall, int depth) {
        if (currentCell == null) return;

        currentCell.setDepth(depth); // Assuming `Cell` has a `setDepth` method
        currentCell.setEntryWall(entryWall); // Assuming `Cell` has a `setEntryWall` method

        for (int i : currentCell.getRandomPermutation()) { // `getPerm` returns an array of wall indices
            Wall wall = currentCell.getWalls().get(i);

            Cell neighbor = wall.getNeighbor(currentCell); // Get the neighboring cell

            if (neighbor != null && !neighbor.isVisited()) { // Check if neighbor has been visited
                wall.open(); // Open the wall
                recursiveHelper(neighbor, wall, depth + 1); // Recursive call for the neighbor
            }
        }
    }

    private void sortWallsForPoints() {
        for (Point p : points) {
            p.sortWalls();
        }
    }

    private Wall findStartWall() {
        // First loop: Check for walls meeting the first condition
        for (int e = 0; e < startPoint.getWalls().size(); e++) {
            Wall wall = startPoint.getWalls().get(e);
            if (wall.getCells().size() == 1 &&
                    !wall.isOpen() &&
                    wall.getOtherEnd(startPoint).getY() == startPoint.getY()) {
                return wall;
            }
        }

        // Second loop: Check for walls meeting the second condition
        for (int e = 0; e < startPoint.getWalls().size(); e++) {
            Wall wall = startPoint.getWalls().get(e);
            if (wall.getCells().size() == 1 &&
                    !wall.isOpen() &&
                    wall.getOtherEnd(startPoint).getX() <= startPoint.getX()) {
                return wall;
            }
        }

        // Third loop: Check for walls meeting the third condition
        for (int e = 0; e < startPoint.getWalls().size(); e++) {
            Wall wall = startPoint.getWalls().get(e);
            if (wall.getCells().size() == 1 &&
                    !wall.isOpen()) {
                return wall;
            }
        }

        // If no wall satisfies the conditions, return null
        return null;
    }

    private Wall findEndWall() {
        // First loop: Check for walls meeting the first condition
        for (int e = 0; e < endPoint.getWalls().size(); e++) {
            Wall wall = endPoint.getWalls().get(e);
            if (wall.getCells().size() == 1 &&
                    !wall.isOpen() &&
                    wall.getOtherEnd(endPoint).getY() == endPoint.getY()) {
                return wall;
            }
        }

        // Second loop: Check for walls meeting the second condition
        for (int e = 0; e < endPoint.getWalls().size(); e++) {
            Wall wall = endPoint.getWalls().get(e);
            if (wall.getCells().size() == 1 &&
                    !wall.isOpen() &&
                    wall.getOtherEnd(endPoint).getX() >= endPoint.getX()) {
                return wall;
            }
        }

        // Third loop: Iterate backward to check for walls meeting the third condition
        for (int e = endPoint.getWalls().size() - 1; e >= 0; e--) {
            Wall wall = endPoint.getWalls().get(e);
            if (wall.getCells().size() == 1 &&
                    !wall.isOpen()) {
                return wall;
            }
        }

        // If no wall satisfies the conditions, return null
        return null;
    }

    private void openCorners() {
        startWall.open();
        endWall.open();
    }

    private void findMaxDepth() {
        maxDepth = 0;
        for (Cell cell : cells) {
            if (cell.getDepth() > maxDepth) {
                maxDepth = cell.getDepth();
            }
        }
    }

    public Cell getStartCell() {
        return startWall.getCells().get(0);
    }

    public Cell getEndCell() {
        return endWall.getCells().get(0);
    }

}
