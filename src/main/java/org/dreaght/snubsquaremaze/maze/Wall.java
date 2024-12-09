package org.dreaght.snubsquaremaze.maze;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

@AllArgsConstructor
@Getter @Setter
public class Wall {
    private final Point[] points;
    private int state = 1;
    private LinkedList<Cell> cells;
    private boolean traversedDown = false;
    private boolean traversedUp = false;

    public Wall(Point startPoint, Point endPoint) {
        this.points = new Point[]{startPoint, endPoint};
        startPoint.getWalls().add(this);
        endPoint.getWalls().add(this);
    }

    public Point getOtherEnd(Point point) {
        return points[0] == point ? points[1] :
                points[1] == point ? points[0] : null;
    }
}
