package org.dreaght.snubsquaremaze.maze;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@Getter
public class Point {
    private final double x;
    private final double y;
    private final List<Wall> walls = new LinkedList<>();

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public List<Cell> findFaces(int maxFaceSize) {
        List<Cell> faces = new LinkedList<>();

        // Loop through each wall
        for (Wall wall : this.walls) {
            // Check if the wall has been traversed
            if (!wall.isTraversed(this, wall.getOtherEnd(this))) {
                List<Wall> wallsInFace = new LinkedList<>();
                Point point = this; // Start at the current point
                double area = 0; // Variable to calculate area
                Point other;

                do {
                    wallsInFace.add(wall);
                    other = wall.getOtherEnd(point);
                    area += point.getX() * other.getY() - other.getX() * point.getY();
                    point = wall.traverse(point, other);
                    wall = point.nextClockwiseWall(wall);

                } while (point != this && wall != null); // Continue until we loop back or run out of walls

                // Add the cell if the face size is valid and area is positive
                if (wallsInFace.size() <= maxFaceSize && area > 0 && wall != null) {
                    faces.add(new Cell(wallsInFace));
                }
            }
        }

        return faces;
    }


    public Wall nextClockwiseWall(Wall wall) {
        for (int i = 0; i < walls.size(); i++) {
            if (walls.get(i) == wall) {
                int e = i;
                return e > 0 ? walls.get(e - 1) : walls.get(walls.size() - 1);
            }
        }
        return null;
    }

    public void sortWalls() {
        // Create a temporary list to store walls along with their angles
        List<WallAnglePair> wallAnglePairs = new ArrayList<>();

        for (Wall wall : this.walls) {
            double angle = this.angleTo(wall.getOtherEnd(this));
            wallAnglePairs.add(new WallAnglePair(wall, angle));
        }

        // Sort the pairs by angle
        wallAnglePairs.sort(Comparator.comparingDouble(WallAnglePair::getAngle));

        // Update the `walls` list in sorted order
        this.walls.clear();
        for (WallAnglePair pair : wallAnglePairs) {
            this.walls.add(pair.getWall());
        }
    }

    private double angleTo(Point other) {
        // Calculate the angle to another point (this method needs implementation)
        double deltaX = other.getX() - this.x;
        double deltaY = other.getY() - this.y;
        return Math.atan2(deltaY, deltaX);
    }

    // Helper class to store walls with their associated angles
    @Getter
    @AllArgsConstructor
    private static class WallAnglePair {
        private final Wall wall;
        private final double angle;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
