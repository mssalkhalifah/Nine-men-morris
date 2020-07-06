package com.groupg.game.board;

import com.badlogic.gdx.math.Vector3;

import java.util.*;

public class PointsPosition {
    public static final int NUMBER_OF_POINTS = 24;

    private Vector3[] pointPositions;

    private static final HashMap<Integer, Set<Integer>> verticalPositions = new HashMap<>();
    private static final HashMap<Integer, Set<Integer>> horizontalPositions = new HashMap<>();

    public PointsPosition() {
        int adjacentPositions = 0;

        // Initialize pre-defined positions
        for (int i = 0; i < NUMBER_OF_POINTS; i++) {
            if (i % 3 == 0) adjacentPositions = i;
            horizontalPositions.put(i, new LinkedHashSet<>(Arrays.asList(adjacentPositions, adjacentPositions + 1, adjacentPositions + 2)));
            verticalPositions.put(i, new LinkedHashSet<Integer>());
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
    public Vector3 getPointPosition(int point) {
        return pointPositions[point];
    }

    public boolean checkHorizontalMill(int pointPosition, BitSet bitBoard) {
        switch (pointPosition) {
            case 0:
            case 1:
            case 2:
                return bitBoard.get(0) && bitBoard.get(1) && bitBoard.get(2);

            case 3:
            case 4:
            case 5:
                return bitBoard.get(3) && bitBoard.get(4) && bitBoard.get(5);

            case 6:
            case 7:
            case 8:
                return bitBoard.get(6) && bitBoard.get(7) && bitBoard.get(8);

            case 9:
            case 10:
            case 11:
                return bitBoard.get(9) && bitBoard.get(10) && bitBoard.get(11);

            case 12:
            case 13:
            case 14:
                return bitBoard.get(12) && bitBoard.get(13) && bitBoard.get(14);

            case 15:
            case 16:
            case 17:
                return bitBoard.get(15) && bitBoard.get(16) && bitBoard.get(17);

            case 18:
            case 19:
            case 20:
                return bitBoard.get(18) && bitBoard.get(19) && bitBoard.get(20);

            case 21:
            case 22:
            case 23:
                return bitBoard.get(21) && bitBoard.get(22) && bitBoard.get(23);

            default:
                return false;
        }
    }

    public boolean checkVerticalMill(int pointPosition, BitSet bitBoard) {
        switch (pointPosition) {
            case 0:
            case 9:
            case 21:
                return bitBoard.get(0) && bitBoard.get(9) && bitBoard.get(21);

            case 3:
            case 10:
            case 18:
                return bitBoard.get(3) && bitBoard.get(10) && bitBoard.get(18);

            case 6:
            case 11:
            case 15:
                return bitBoard.get(6) && bitBoard.get(11) && bitBoard.get(15);

            case 1:
            case 4:
            case 7:
                return bitBoard.get(1) && bitBoard.get(4) && bitBoard.get(7);

            case 16:
            case 19:
            case 22:
                return bitBoard.get(16) && bitBoard.get(19) && bitBoard.get(22);

            case 8:
            case 12:
            case 17:
                return bitBoard.get(8) && bitBoard.get(12) && bitBoard.get(17);

            case 5:
            case 13:
            case 20:
                return bitBoard.get(5) && bitBoard.get(13) && bitBoard.get(20);

            case 2:
            case 14:
            case 23:
                return bitBoard.get(2) && bitBoard.get(14) && bitBoard.get(23);

            default:
                return false;
        }
    }

    public boolean checkValidMovePosition(int currentPosition, int nextPosition) {
        if (horizontalPositions.get(currentPosition).contains(nextPosition)) {
            return Math.abs(currentPosition - nextPosition) == 1;
        }

        if (verticalPositions.get(currentPosition).contains(nextPosition)) {
            System.out.println(Arrays.toString(verticalPositions.get(currentPosition).toArray()));
            ArrayList<Integer> verticalSetArray = new ArrayList<>(verticalPositions.get(currentPosition));
            int currentPositionIndex = verticalSetArray.indexOf(currentPosition);

            System.out.println(currentPosition +" "+ nextPosition);
            System.out.println(Arrays.toString(verticalSetArray.toArray()));

            // If current position index is in the middle
            if ((verticalSetArray.size() / 2) == currentPositionIndex)
                return nextPosition == verticalSetArray.get(currentPositionIndex + 1)
                        || nextPosition == verticalSetArray.get(currentPositionIndex - 1);

            // If current position index is at the first position
            if (currentPositionIndex == 0) return nextPosition == verticalSetArray.get(currentPositionIndex + 1);


            // If current position index is in the last position
            if (currentPositionIndex == verticalSetArray.size() - 1) return nextPosition == verticalSetArray.get(currentPositionIndex - 1);
        }
        return false;
    }
}
