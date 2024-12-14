package org.dreaght.snubsquaremaze.maze;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@Getter
public class Point {
    private final double x;
    private final double y;
    private final List<Wall> walls = new LinkedList<>();
    private final long seed;

    public Point(double x, double y) {
        this(x, y, (long) (Math.random() * Long.MAX_VALUE));
    }

    public Point(double x, double y, long mazeSeed) {
        this.x = x;
        this.y = y;
        this.seed = generateDeterministicSeed(x, y, mazeSeed);
    }

    private long generateDeterministicSeed(double x, double y, long mazeSeed) {
        // Use a hashing function to combine the mazeSeed and coordinates into a unique seed
        long xHash = Double.doubleToLongBits(x);
        long yHash = Double.doubleToLongBits(y);
        return Objects.hash(mazeSeed, xHash, yHash);
    }

    public List<Cell> findFaces(int maxFaceSize) {
        List<Cell> faces = new LinkedList<>();

        for (Wall wall : this.walls) {
            if (!wall.isTraversed(this, wall.getOtherEnd(this))) {
                List<Wall> wallsInFace = new LinkedList<>();
                Point point = this;
                double area = 0;
                Point other;

                do {
                    wallsInFace.add(wall);
                    other = wall.getOtherEnd(point);
                    area += point.getX() * other.getY() - other.getX() * point.getY();
                    point = wall.traverse(point, other);
                    wall = point.nextClockwiseWall(wall);

                } while (point != this && wall != null);

                if (wallsInFace.size() <= maxFaceSize && area > 0 && wall != null) {
                    faces.add(new Cell(wallsInFace, seed));
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
        List<WallAnglePair> wallAnglePairs = new ArrayList<>();

        for (Wall wall : this.walls) {
            double angle = this.angleTo(wall.getOtherEnd(this));
            wallAnglePairs.add(new WallAnglePair(wall, angle));
        }

        wallAnglePairs.sort(Comparator.comparingDouble(WallAnglePair::getAngle));

        this.walls.clear();
        for (WallAnglePair pair : wallAnglePairs) {
            this.walls.add(pair.getWall());
        }
    }

    private double angleTo(Point other) {
        double deltaX = other.getX() - this.x;
        double deltaY = other.getY() - this.y;
        return Math.atan2(deltaY, deltaX);
    }

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
