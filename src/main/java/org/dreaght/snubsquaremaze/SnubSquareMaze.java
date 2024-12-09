package org.dreaght.snubsquaremaze;

import org.dreaght.snubsquaremaze.maze.Maze;

public class SnubSquareMaze {
    public static void main(String[] args) {
        Maze maze = new Maze(10, 10);
        System.out.println(maze.getWalls().size() + " " + maze.getCells().size());
    }
}