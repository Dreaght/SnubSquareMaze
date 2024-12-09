package org.dreaght.snubsquaremaze.util;

import org.dreaght.snubsquaremaze.maze.Maze;
import org.dreaght.snubsquaremaze.maze.Wall;

public class SVGUtil {
    public static String convertMazeToSVG(Maze maze) {
        String svgContent = """
                <svg width="800" height="800" xmlns="http://www.w3.org/2000/svg">
                """;
        StringBuilder svgContentBuilder = new StringBuilder();
        svgContentBuilder.append(svgContent);

        int r = 20;
        int a = 5;

        for (Wall wall : maze.getWalls()) {
            double x1 = wall.getPoints().get(0).getX() * r + a;
            double y1 = wall.getPoints().get(0).getY() * r + a;
            double x2 = wall.getPoints().get(1).getX() * r + a;
            double y2 = wall.getPoints().get(1).getY() * r + a;

            String stroke = "black";
            String strokeWidth = "2";
            String strokeLinecap = "round";

            if (wall.isOpen()) {
                svgContentBuilder.append(String.format("""
                        <line x1="%s" y1="%s" x2="%s" y2="%s"\s
                        stroke-linecap="%s" stroke-width="%s"\s
                        class="open wall" />""", x1, y1, x2, y2, strokeLinecap, strokeWidth));
            } else {
                svgContentBuilder.append(String.format("""
                        <line x1="%s" y1="%s" x2="%s" y2="%s"\s
                        stroke="%s" stroke-linecap="%s"\s
                        stroke-width="%s" />""", x1, y1, x2, y2, stroke,
                        strokeLinecap, strokeWidth));
            }
        }
        svgContentBuilder.append("</svg>");

        return svgContentBuilder.toString();
    }
}
