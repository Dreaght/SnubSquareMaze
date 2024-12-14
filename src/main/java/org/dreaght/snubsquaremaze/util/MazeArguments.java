package org.dreaght.snubsquaremaze.util;

import lombok.Getter;

@Getter
public class MazeArguments {

    private int width = 20;
    private int height = 20;

    private long seed = (long) (Math.random() * Long.MAX_VALUE);

    public MazeArguments(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("--width=")) {
                width = Integer.parseInt(args[i].substring(8));
            } else if (args[i].startsWith("--height=")) {
                height = Integer.parseInt(args[i].substring(9));
            } else if (args[i].startsWith("--seed=")) {
                seed = Long.parseLong(args[i].substring(7));
            }
        }
    }
}
