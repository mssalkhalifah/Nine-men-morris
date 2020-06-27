package com.groupg.game.board;

import com.badlogic.gdx.math.Vector3;
import com.groupg.game.gameobject.PieceColor;

import java.util.BitSet;

public class Board {
    private PointsPosition pointPosition;
    private BitSet blueBitBoard;
    private BitSet redBitBoard;

    public Board() {
        blueBitBoard = new BitSet(PointsPosition.NUMBER_OF_POINTS);
        redBitBoard = new BitSet(PointsPosition.NUMBER_OF_POINTS);
        pointPosition = new PointsPosition();
    }

    public boolean addPiece(PieceColor pieceColor, int pointNumber) {
        // Check if it is empty
        if (!(blueBitBoard.get(pointNumber) || redBitBoard.get(pointNumber))) {
            if (pieceColor == PieceColor.BLUE) {
                blueBitBoard.set(pointNumber);
            } else {
                redBitBoard.set(pointNumber);
            }
            return true;
        }

        return false;
    }

    // Point ranges from 0 ~ 23
    public Vector3 getPointPosition(int point) {
        return pointPosition.getPointPosition(point);
    }

    public boolean checkHorizontalMill(int pointPosition, PieceColor pieceColor) {
        return this.pointPosition.checkHorizontalMill(pointPosition, (pieceColor == PieceColor.BLUE) ? blueBitBoard : redBitBoard);
    }

    public boolean checkVerticalMill(int pointPosition, PieceColor pieceColor) {
        return this.pointPosition.checkVerticalMill(pointPosition, (pieceColor == PieceColor.BLUE) ? blueBitBoard : redBitBoard);
    }
}
