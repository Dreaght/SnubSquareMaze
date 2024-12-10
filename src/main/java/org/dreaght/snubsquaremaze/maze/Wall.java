package org.dreaght.snubsquaremaze.maze;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@Getter @Setter
@ToString
public class Wall {
    private final List<Point> points;
    private int state = 1;
    @ToString.Exclude private LinkedList<Cell> cells = new LinkedList<>();
    private boolean traversedDown = false;
    private boolean traversedUp = false;

    public Wall(Point startPoint, Point endPoint) {
        this.points = List.of(startPoint, endPoint);
        startPoint.getWalls().add(this);
        endPoint.getWalls().add(this);
    }

    public boolean isOpen() {
        return state == 0;
    }

    public void open() {
        state = 0;
    }

    public Point getOtherEnd(Point point) {
        return points.get(0) == point ? points.get(1) :
                points.get(1) == point ? points.get(0) : null;
    }

    public Point traverse(Point point, Point otherEnd) {
        if (point == points.get(0) && otherEnd == points.get(1)) {
            traversedDown = true;
            return otherEnd;
        } else if (point == points.get(1) && otherEnd == points.get(0)) {
            traversedUp = true;
            return otherEnd;
        } else {
            return null;
        }
    }

    public boolean isTraversed(Point point, Point otherEnd) {
        return point == this.points.get(0) && otherEnd == this.points.get(1)
                ? traversedDown : point == points.get(1) && otherEnd == points.get(0) && traversedUp;
    }

    public Cell getNeighbor(Cell currentCell) {
        if (currentCell == cells.get(0)) {
            return cells.size() == 2 ? cells.get(1) : null;
        }
        return cells.get(0);
    }

    public Point getCenter() {
        return new Point((points.get(0).getX() + points.get(1).getX()) / 2,
                         (points.get(0).getY() + points.get(1).getY()) / 2);
    }
}
