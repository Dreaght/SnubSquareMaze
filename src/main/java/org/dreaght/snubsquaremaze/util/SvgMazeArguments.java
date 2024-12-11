package org.dreaght.snubsquaremaze.util;

import lombok.Getter;

@Getter
public class SvgMazeArguments extends MazeArguments {

    private String filePath = "maze.svg";
    private boolean showSolution = false;
    private double zoomFactor = 1;

    public SvgMazeArguments(String[] args) {
        super(args);

        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("--output=")) {
                filePath = args[i].substring(9);
            } else if (args[i].startsWith("--zoom=")) {
                zoomFactor = Double.parseDouble(args[i].substring(7));
            } else if (args[i].equals("--solution")) {
                showSolution = true;
            }
        }
    }
}
