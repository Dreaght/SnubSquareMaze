package org.dreaght.snubsquaremaze.maze.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public final class Util {

    private static final AtomicLong seedUniquifier = new AtomicLong(8682522807148012L);

    private Util() {
    }

    private static long generateSeed(long seed) {
        long uniqueSeed;
        do {
            long current = seedUniquifier.get();
            uniqueSeed = current * 181783497276652981L;
        } while (!seedUniquifier.compareAndSet(seedUniquifier.get(), uniqueSeed));
        return uniqueSeed ^ seed;
    }

    public static int[] generateRandomPermutation(int listSize, long seed) {
        Random random = new Random(generateSeed(seed));

        List<Integer> list = new ArrayList<>(listSize);
        for (int i = 0; i < listSize; i++) {
            list.add(i);
        }

        Collections.shuffle(list, random);

        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * Generates a 2D rotation matrix.
     *
     * @return a 2x2x2 matrix representing the rotation
     */
    public static double[][][] getRotationMatrix() {
        double cos60 = Math.cos(Math.toRadians(60));
        double sin60 = Math.sin(Math.toRadians(60));
        double cos45 = Math.cos(Math.toRadians(45));
        double sin45 = Math.sin(Math.toRadians(45));

        double deltaCos = (cos60 - cos45) / Math.sqrt(2);
        double deltaSin = (sin60 - sin45) / Math.sqrt(2);

        return new double[][][] {
                {
                        {-deltaCos, -deltaSin},
                        {-deltaSin, deltaCos}
                },
                {
                        {deltaSin, -deltaCos},
                        {deltaCos, deltaSin}
                }
        };
    }
}
