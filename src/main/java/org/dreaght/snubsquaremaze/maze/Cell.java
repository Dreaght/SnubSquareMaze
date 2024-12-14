package org.dreaght.snubsquaremaze.maze;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dreaght.snubsquaremaze.maze.util.Util;

import java.util.List;

@Getter
@Setter
@ToString()
public class Cell {
    @ToString.Exclude private final List<Wall> walls;
    private final int[] randomPermutation;
    private Wall entryWall;
    private int depth;
    private long seed;

    public Cell(List<Wall> walls, long mazeSeed) {
        this.walls = walls;
        this.seed = generateCellSeed(walls, mazeSeed);
        for (Wall wall : this.walls) {
            wall.getCells().add(this);
        }
        this.randomPermutation = Util.generateRandomPermutation(this.walls.size(), seed);
    }

    private long generateCellSeed(List<Wall> walls, long mazeSeed) {
        long combinedHash = mazeSeed;
        for (Wall wall : walls) {
            for (Point point : wall.getPoints()) {
                combinedHash = 31 * combinedHash + Double.doubleToLongBits(point.getX());
                combinedHash = 31 * combinedHash + Double.doubleToLongBits(point.getY());
            }
        }
        return combinedHash;
    }

    public Point getCenter() {
        double xSum = 0;
        double ySum = 0;

        for (Wall wall : walls) {
            xSum += wall.getPoints().get(0).getX();
            xSum += wall.getPoints().get(1).getX();
            ySum += wall.getPoints().get(0).getY();
            ySum += wall.getPoints().get(1).getY();
        }

        int totalPoints = 2 * walls.size();
        double centerX = xSum / totalPoints;
        double centerY = ySum / totalPoints;

        return new Point(centerX, centerY, seed);
    }

    public boolean isVisited() {
        for (Wall wall : walls) {
            if (wall.isOpen()) return true;
        }
        return false;
    }
}
