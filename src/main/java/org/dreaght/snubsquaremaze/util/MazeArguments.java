package org.dreaght.snubsquaremaze.util;

import lombok.Getter;

@Getter
public class MazeArguments {

    private int width = 20;
    private int height = 20;

    public MazeArguments(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("--width=")) {
                width = Integer.parseInt(args[i].substring(8));
            } else if (args[i].startsWith("--height=")) {
                height = Integer.parseInt(args[i].substring(9));
            }
        }
    }
}
