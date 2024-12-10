package org.dreaght.snubsquaremaze.maze.util;

public class Util {

    /**
     * Generates a random permutation of an array.
     *
     * @param listSize the size of the input array
     * @return a new array representing a random permutation of the original array
     */
    public static int[] generateRandomPermutation(int listSize) {
        // Create a temporary copy of the original array, preserving its order
        int[] tempList = new int[listSize];
        for (int index = 0; index < listSize; index++) {
            tempList[index] = index;
        }

        // Iterate from the last element to the first one and swap each pair randomly
        for (int lastIndex = listSize - 1; lastIndex > 0; lastIndex--) {
            int randomIndex = (int) (Math.random() * (lastIndex + 1));
            if (randomIndex != lastIndex) {
                // Swap elements at randomIndex and lastIndex positions
                int tempValue = tempList[randomIndex];
                tempList[randomIndex] = tempList[lastIndex];
                tempList[lastIndex] = tempValue;
            }
        }

        return tempList;
    }

    /**
     * Generates a 2D rotation matrix.
     *
     * @return a 4x2 matrix representing the rotation
     */
    public static double[][][] getRotationMatrix() {
        // Define angles in radians
        double cos60MinusCos45 = Math.cos(60 * Math.PI / 180) - Math.cos(45 * Math.PI / 180);
        double sin60MinusSin45 = Math.sin(60 * Math.PI / 180) - Math.sin(45 * Math.PI / 180);

        // Calculate rotation matrix elements
        double[][][] rotationMatrix = new double[2][2][2];
        rotationMatrix[0][0][0] = -(cos60MinusCos45 / Math.sqrt(2));
        rotationMatrix[0][0][1] = -(sin60MinusSin45 / Math.sqrt(2));
        rotationMatrix[0][1][0] = -sin60MinusSin45;
        rotationMatrix[0][1][1] = cos60MinusCos45;

        rotationMatrix[1][0][0] = sin60MinusSin45;
        rotationMatrix[1][0][1] = -cos60MinusCos45;
        rotationMatrix[1][1][0] = cos60MinusCos45;
        rotationMatrix[1][1][1] = sin60MinusSin45;

        return rotationMatrix;
    }
}
