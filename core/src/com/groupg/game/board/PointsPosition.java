package com.groupg.game.board;

import com.badlogic.gdx.math.Vector3;

import java.util.*;

public class PointsPosition {
    public static final int NUMBER_OF_POINTS = 24;

    private static final HashMap<Integer, Set<Integer>> verticalPositions = new HashMap<>();
    private static final HashMap<Integer, Set<Integer>> horizontalPositions = new HashMap<>();
    private static final HashMap<Integer, Set<Integer>> adjacentLocations = new HashMap<>();

    private static Vector3[] pointPositions;

    public PointsPosition() {
        int adjacentPositions = 0;

        // Initialize pre-defined positions
        for (int i = 0; i < NUMBER_OF_POINTS; i++) {
            if (i % 3 == 0) adjacentPositions = i;
            horizontalPositions.put(i, new LinkedHashSet<>(Arrays.asList(adjacentPositions, adjacentPositions + 1, adjacentPositions + 2)));
            verticalPositions.put(i, new LinkedHashSet<Integer>());
            adjacentLocations.put(i, new LinkedHashSet<Integer>());
        }

        verticalPositions.get(0).addAll(Arrays.asList(0, 9, 21));
        verticalPositions.get(9).addAll(Arrays.asList(0, 9, 21));
        verticalPositions.get(21).addAll(Arrays.asList(0, 9, 21));

        verticalPositions.get(3).addAll(Arrays.asList(3, 10, 18));
        verticalPositions.get(10).addAll(Arrays.asList(3, 10, 18));
        verticalPositions.get(18).addAll(Arrays.asList(3, 10, 18));

        verticalPositions.get(6).addAll(Arrays.asList(6, 11, 15));
        verticalPositions.get(11).addAll(Arrays.asList(6, 11, 15));
        verticalPositions.get(15).addAll(Arrays.asList(6, 11, 15));

        verticalPositions.get(1).addAll(Arrays.asList(1, 4, 7));
        verticalPositions.get(4).addAll(Arrays.asList(1, 4, 7));
        verticalPositions.get(7).addAll(Arrays.asList(1, 4, 7));

        verticalPositions.get(16).addAll(Arrays.asList(16, 19, 22));
        verticalPositions.get(19).addAll(Arrays.asList(16, 19, 22));
        verticalPositions.get(22).addAll(Arrays.asList(16, 19, 22));

        verticalPositions.get(8).addAll(Arrays.asList(8, 12, 17));
        verticalPositions.get(12).addAll(Arrays.asList(8, 12, 17));
        verticalPositions.get(17).addAll(Arrays.asList(8, 12, 17));

        verticalPositions.get(5).addAll(Arrays.asList(5, 13, 20));
        verticalPositions.get(13).addAll(Arrays.asList(5, 13, 20));
        verticalPositions.get(20).addAll(Arrays.asList(5, 13, 20));

        verticalPositions.get(2).addAll(Arrays.asList(2, 14, 23));
        verticalPositions.get(14).addAll(Arrays.asList(2, 14, 23));
        verticalPositions.get(23).addAll(Arrays.asList(2, 14, 23));

        adjacentLocations.get(0).addAll(Arrays.asList(9, 1));
        adjacentLocations.get(1).addAll(Arrays.asList(0, 2, 4));
        adjacentLocations.get(2).addAll(Arrays.asList(1, 14));
        adjacentLocations.get(3).addAll(Arrays.asList(4, 10));
        adjacentLocations.get(4).addAll(Arrays.asList(1, 3, 5, 7));
        adjacentLocations.get(5).addAll(Arrays.asList(4, 13));
        adjacentLocations.get(6).addAll(Arrays.asList(7, 11));
        adjacentLocations.get(7).addAll(Arrays.asList(4, 6, 8));
        adjacentLocations.get(8).addAll(Arrays.asList(7, 12));
        adjacentLocations.get(9).addAll(Arrays.asList(0, 10, 21));
        adjacentLocations.get(10).addAll(Arrays.asList(3, 11, 18));
        adjacentLocations.get(11).addAll(Arrays.asList(6, 10, 15));
        adjacentLocations.get(12).addAll(Arrays.asList(8, 13, 17));
        adjacentLocations.get(13).addAll(Arrays.asList(5, 12, 14, 20));
        adjacentLocations.get(14).addAll(Arrays.asList(2, 13, 23));
        adjacentLocations.get(15).addAll(Arrays.asList(11, 16));
        adjacentLocations.get(16).addAll(Arrays.asList(15, 17, 19));
        adjacentLocations.get(17).addAll(Arrays.asList(12, 16));
        adjacentLocations.get(18).addAll(Arrays.asList(10, 19));
        adjacentLocations.get(19).addAll(Arrays.asList(16, 18, 20, 22));
        adjacentLocations.get(20).addAll(Arrays.asList(13, 19));
        adjacentLocations.get(21).addAll(Arrays.asList(9, 22));
        adjacentLocations.get(22).addAll(Arrays.asList(19, 21, 23));
        adjacentLocations.get(23).addAll(Arrays.asList(14, 22));

        pointPositions = new Vector3[NUMBER_OF_POINTS];
        pointPositions[0] = new Vector3(307, 489, 0);
        pointPositions[1] = new Vector3(526, 489, 0);
        pointPositions[2] = new Vector3(745, 489, 0);
        pointPositions[3] = new Vector3(385, 414, 0);
        pointPositions[4] = new Vector3(526, 414, 0);
        pointPositions[5] = new Vector3(667, 414, 0);
        pointPositions[6] = new Vector3(463, 338, 0);
        pointPositions[7] = new Vector3(526, 338, 0);
        pointPositions[8] = new Vector3(588, 338, 0);
        pointPositions[9] = new Vector3(307, 277, 0);
        pointPositions[10] = new Vector3(385, 277, 0);
        pointPositions[11] = new Vector3(463, 277, 0);
        pointPositions[12] = new Vector3(588, 277, 0);
        pointPositions[13] = new Vector3(667, 277, 0);
        pointPositions[14] = new Vector3(745, 277, 0);
        pointPositions[15] = new Vector3(463, 217, 0);
        pointPositions[16] = new Vector3(526, 217, 0);
        pointPositions[17] = new Vector3(588, 217, 0);
        pointPositions[18] = new Vector3(385, 141, 0);
        pointPositions[19] = new Vector3(526, 141, 0);
        pointPositions[20] = new Vector3(667, 141, 0);
        pointPositions[21] = new Vector3(307, 65, 0);
        pointPositions[22] = new Vector3(526, 65, 0);
        pointPositions[23] = new Vector3(745, 65, 0);
    }

    // Point ranges from 0 ~ 23
    public static Vector3 getPointPosition(int point) {
        return pointPositions[point];
    }

    public static ArrayList<Integer> getAdjacentLocation(int pointPosition) {
        return new ArrayList<>(adjacentLocations.get(pointPosition));
    }

    public static boolean checkMill(int pointPosition, BitSet bitBoard) {
        ArrayList<Integer> horizontalPointSet = new ArrayList<>(horizontalPositions.get(pointPosition));
        ArrayList<Integer> verticalPointSet = new ArrayList<>(verticalPositions.get(pointPosition));

        boolean horizontalMill = bitBoard.get(horizontalPointSet.get(0)) && bitBoard.get(horizontalPointSet.get(1)) && bitBoard.get(horizontalPointSet.get(2));
        boolean verticalMill = bitBoard.get(verticalPointSet.get(0)) && bitBoard.get(verticalPointSet.get(1)) && bitBoard.get(verticalPointSet.get(2));

        return horizontalMill || verticalMill;
    }

    public static boolean checkValidMovePosition(int currentPosition, int nextPosition) {
        if (horizontalPositions.get(currentPosition).contains(nextPosition)) {
            return Math.abs(currentPosition - nextPosition) == 1;
        }

        if (verticalPositions.get(currentPosition).contains(nextPosition)) {
            ArrayList<Integer> verticalSetArray = new ArrayList<>(verticalPositions.get(currentPosition));
            int currentPositionIndex = verticalSetArray.indexOf(currentPosition);

            // If current position index is at the first position
            if (currentPositionIndex == 0) return nextPosition == verticalSetArray.get(currentPositionIndex + 1);

            // If current position index is in the last position
            if (currentPositionIndex == verticalSetArray.size() - 1) return nextPosition == verticalSetArray.get(currentPositionIndex - 1);

            // If current position index is in between the first and last position
            return nextPosition == verticalSetArray.get(currentPositionIndex + 1)
                    || nextPosition == verticalSetArray.get(currentPositionIndex - 1);
        }
        return false;
    }

    public static ArrayList<Integer> getHorizontalSet(int position) {
        return new ArrayList<>(horizontalPositions.get(position));
    }

    public static ArrayList<Integer> getVerticalSet(int position) {
        return new ArrayList<>(verticalPositions.get(position));
    }
}
