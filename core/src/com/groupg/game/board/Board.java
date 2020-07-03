package com.groupg.game.board;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.groupg.game.gameobject.Piece;
import com.groupg.game.gameobject.PieceColor;
import com.groupg.game.gameobject.Point;

import java.util.BitSet;

public class Board {
    private Array<Point> pointArray;
    private Array<Piece> pieceArray;
    private PointsPosition pointPosition;
    private BitSet blueBitBoard;
    private BitSet redBitBoard;

    public Board() {
        blueBitBoard = new BitSet(PointsPosition.NUMBER_OF_POINTS);
        redBitBoard = new BitSet(PointsPosition.NUMBER_OF_POINTS);
        pointPosition = new PointsPosition();
        pointArray = new Array<>();
        pieceArray = new Array<>();

        for (int i = 0; i < PointsPosition.NUMBER_OF_POINTS; i++) {
            pointArray.add(new Point(pointPosition.getPointPosition(i), this, i));
        }
    }

    public void update(double delta, Vector3 mousePosition) {
        for (Point point : pointArray) {
            point.update(delta, mousePosition);
        }

        for (Piece piece : pieceArray) {
            piece.update(delta, mousePosition);
        }
    }

    public void render(double delta, SpriteBatch batch) {
        for (Point point : pointArray) {
            point.render(delta, batch);
        }

        for (Piece piece : pieceArray) {
            piece.render(delta, batch);
        }
    }

    public void dispose() {
        for (Point point : pointArray) {
            point.dispose();
        }

        for (Piece piece : pieceArray) {
            piece.dispose();
        }
    }

    public boolean addPiece(PieceColor pieceColor, Vector3 position) {
        int pointNumber = getPointNumber(position);

        // Check if it is empty
        if (pointNumber >= 0 && isEmptyPoint(pointNumber)) {
            pieceArray.add(new Piece(pointPosition.getPointPosition(pointNumber), pieceColor, pointNumber));
            if (pieceColor == PieceColor.WHITE) {
                blueBitBoard.set(pointNumber);
            } else {
                redBitBoard.set(pointNumber);
            }
            return true;
        }
        return false;
    }

    public boolean isMill(Vector3 position, PieceColor pieceColor) {
        int pointNumber = getPointNumber(position);
        BitSet bitSetColor = (pieceColor == PieceColor.WHITE) ? blueBitBoard : redBitBoard;
        return pointPosition.checkHorizontalMill(pointNumber, bitSetColor)
                || pointPosition.checkVerticalMill(pointNumber, bitSetColor);
    }

    public boolean isCaptureValid(Vector3 position, PieceColor pieceColor) {
        int pointNumber = getPointNumber(position);

        if (pointNumber >= 0 && pieceColor == PieceColor.WHITE && redBitBoard.get(pointNumber) && !isMill(position, PieceColor.BLACK)) {
            removePiece(pointNumber);
            redBitBoard.clear(pointNumber);
            System.out.println(redBitBoard);
            System.out.println(blueBitBoard);
            return true;
        }

        if (pointNumber >= 0 && pieceColor == PieceColor.BLACK && blueBitBoard.get(pointNumber) && !isMill(position, PieceColor.WHITE)) {
            removePiece(pointNumber);
            blueBitBoard.clear(pointNumber);
            System.out.println(redBitBoard);
            System.out.println(blueBitBoard);
            return true;
        }

        return false;
    }

    public boolean isMoveValid(Vector3 currentPosition, Vector3 nextPosition, PieceColor pieceColor) {
        return false;
    }

    public boolean isEmptyPoint(int pointNumber) {
        return !(blueBitBoard.get(pointNumber) || redBitBoard.get(pointNumber));
    }

    private int getPointNumber(Vector3 position) {
        int pointNumber = -1;
        for (Point point : pointArray) {
            if (point.isCollide(position)) pointNumber = point.getPointNumber();
        }
        return pointNumber;
    }

    private void removePiece(int pieceNumber) {
        for (int i = 0; i < pieceArray.size; i++) {
            if (pieceArray.get(i).getPieceNumber() == pieceNumber) {
                pieceArray.removeIndex(i);
                break;
            }
        }
    }
}
