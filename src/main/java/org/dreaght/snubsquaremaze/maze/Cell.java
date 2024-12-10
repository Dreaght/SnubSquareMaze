package org.dreaght.snubsquaremaze.maze;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dreaght.snubsquaremaze.maze.util.Util;

import java.util.List;

@Getter @Setter
@ToString()
public class Cell {
    @ToString.Exclude private final List<Wall> walls;
    private final int[] randomPermutation;
    private Wall entryWall;
    private int depth;

    public Cell(List<Wall> walls) {
        this.walls = walls;
        for (Wall wall : this.walls) {
            wall.getCells().add(this);
        }
        this.randomPermutation = Util.generateRandomPermutation(this.walls.size());
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

        int totalPoints = 2 * walls.size(); // Each wall contributes 2 points
        double centerX = xSum / totalPoints;
        double centerY = ySum / totalPoints;

        return new Point(centerX, centerY);
    }

    public boolean isVisited() {
        for (Wall wall : walls) {
            if (wall.isOpen()) return true;
        }
        return false;
    }
}
