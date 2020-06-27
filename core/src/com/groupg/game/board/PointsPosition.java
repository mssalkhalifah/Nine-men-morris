package com.groupg.game.board;

import com.badlogic.gdx.math.Vector3;

import java.util.BitSet;

public class PointsPosition {
    public static final int NUMBER_OF_POINTS = 24;

    private Vector3[] pointPositions;

    public PointsPosition() {
        pointPositions = new Vector3[NUMBER_OF_POINTS];
        pointPositions[0] = new Vector3(3, 109, 0);
        pointPositions[1] = new Vector3(56, 109, 0);
        pointPositions[2] = new Vector3(109, 109, 0);
        pointPositions[3] = new Vector3(23, 89, 0);
        pointPositions[4] = new Vector3(56, 89, 0);
        pointPositions[5] = new Vector3(89, 89, 0);
        pointPositions[6] = new Vector3(43, 69, 0);
        pointPositions[7] = new Vector3(56, 69, 0);
        pointPositions[8] = new Vector3(69, 69, 0);
        pointPositions[9] = new Vector3(3, 56, 0);
        pointPositions[10] = new Vector3(23, 56, 0);
        pointPositions[11] = new Vector3(43, 56, 0);
        pointPositions[12] = new Vector3(69, 56, 0);
        pointPositions[13] = new Vector3(89, 56, 0);
        pointPositions[14] = new Vector3(109, 56, 0);
        pointPositions[15] = new Vector3(43, 43, 0);
        pointPositions[16] = new Vector3(56, 43, 0);
        pointPositions[17] = new Vector3(69, 43, 0);
        pointPositions[18] = new Vector3(23, 23, 0);
        pointPositions[19] = new Vector3(56, 23, 0);
        pointPositions[20] = new Vector3(89, 23, 0);
        pointPositions[21] = new Vector3(3, 3, 0);
        pointPositions[22] = new Vector3(56, 3, 0);
        pointPositions[23] = new Vector3(109, 3, 0);
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
}
