package org.dreaght.snubsquaremaze.maze;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@Getter
@AllArgsConstructor
public class Point {
    private final double x;
    private final double y;
    private final List<Wall> walls = new LinkedList<>();

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
}
