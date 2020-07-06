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
    private BitSet whiteBitBoard;
    private BitSet blackBitBoard;

    public Board() {
        whiteBitBoard = new BitSet(PointsPosition.NUMBER_OF_POINTS);
        blackBitBoard = new BitSet(PointsPosition.NUMBER_OF_POINTS);
        pointPosition = new PointsPosition();
        pointArray = new Array<>();
        pieceArray = new Array<>();

        for (int i = 0; i < PointsPosition.NUMBER_OF_POINTS; i++) {
            pointArray.add(new Point(pointPosition.getPointPosition(i), this, i));
        }
    }

    public void update(float delta, Vector3 mousePosition) {
        for (Point point : pointArray) {
            point.update(delta, mousePosition);
        }

        for (Piece piece : pieceArray) {
            piece.update(delta, mousePosition);
        }
    }

    public void render(float delta, SpriteBatch batch) {
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
                whiteBitBoard.set(pointNumber);
            } else {
                blackBitBoard.set(pointNumber);
            }
            return true;
        }
        return false;
    }

    public boolean isMill(Vector3 position, PieceColor pieceColor) {
        int pointNumber = getPointNumber(position);
        BitSet bitSetColor = (pieceColor == PieceColor.WHITE) ? whiteBitBoard : blackBitBoard;
        return pointPosition.checkHorizontalMill(pointNumber, bitSetColor)
                || pointPosition.checkVerticalMill(pointNumber, bitSetColor);
    }

    public boolean isMill(int pointNumber, PieceColor pieceColor) {
        BitSet bitSetColor = (pieceColor == PieceColor.WHITE) ? whiteBitBoard : blackBitBoard;
        return pointPosition.checkHorizontalMill(pointNumber, bitSetColor)
                || pointPosition.checkVerticalMill(pointNumber, bitSetColor);
    }

    public boolean isCaptureValid(Vector3 position, PieceColor pieceColor) {
        int pointNumber = getPointNumber(position);

        if (pointNumber >= 0 && pieceColor == PieceColor.WHITE && blackBitBoard.get(pointNumber) && !isMill(position, PieceColor.BLACK)) {
            removePiece(pointNumber);
            blackBitBoard.clear(pointNumber);
            System.out.println(blackBitBoard);
            System.out.println(whiteBitBoard);
            return true;
        }

        if (pointNumber >= 0 && pieceColor == PieceColor.BLACK && whiteBitBoard.get(pointNumber) && !isMill(position, PieceColor.WHITE)) {
            removePiece(pointNumber);
            whiteBitBoard.clear(pointNumber);
            System.out.println(blackBitBoard);
            System.out.println(whiteBitBoard);
            return true;
        }
        return false;
    }

    public Piece getSelectPiece(PieceColor pieceColor, Vector3 touchPosition) {
        for (int i = 0; i < pieceArray.size; i++) {
            if (pieceArray.get(i).getPieceColor() == pieceColor && pieceArray.get(i).isCollide(touchPosition)) {
                clearSelected();
                pieceArray.get(i).setSelected(true);
                return pieceArray.get(i);
            }
        }
        return null;
    }

    public boolean isMoveValid(int currentPosition, Vector3 nextPointPosition, PieceColor pieceColor) {
            int nextPiecePosition = getPointNumber(nextPointPosition);

            if (nextPiecePosition >= 0
                    && pieceColor == PieceColor.WHITE
                    && isEmptyPoint(nextPiecePosition)
                    && pointPosition.checkValidMovePosition(currentPosition, nextPiecePosition)) {
                Piece currentSelectedPiece = getPiece(currentPosition, pieceColor);
                currentSelectedPiece.setPieceNumber(nextPiecePosition);
                currentSelectedPiece.setNextPosition(pointPosition.getPointPosition(nextPiecePosition));
                currentSelectedPiece.setMoving(true);
                whiteBitBoard.clear(currentPosition);
                whiteBitBoard.set(nextPiecePosition);
                clearSelected();
                System.out.println(whiteBitBoard);
                return true;
            }

        if (nextPiecePosition >= 0
                && pieceColor == PieceColor.BLACK
                && isEmptyPoint(nextPiecePosition)
                && pointPosition.checkValidMovePosition(currentPosition, nextPiecePosition)) {
            Piece currentSelectedPiece = getPiece(currentPosition, pieceColor);
            currentSelectedPiece.setPieceNumber(nextPiecePosition);
            currentSelectedPiece.setNextPosition(pointPosition.getPointPosition(nextPiecePosition));
            currentSelectedPiece.setMoving(true);
            blackBitBoard.clear(currentPosition);
            blackBitBoard.set(nextPiecePosition);
            clearSelected();
            System.out.println(blackBitBoard);
            return true;
        }
        return false;
    }

    public boolean isEmptyPoint(int pointNumber) {
        return !(whiteBitBoard.get(pointNumber) || blackBitBoard.get(pointNumber));
    }

    private Piece getPiece(int pieceNumber, PieceColor pieceColor) {
        for (Piece piece : pieceArray) {
            if (piece.getPieceNumber() == pieceNumber && piece.getPieceColor() == pieceColor) return piece;
        }
        throw new IllegalArgumentException("Piece number:" + pieceNumber + " not found");
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

    private void clearSelected() {
        for (Piece piece : pieceArray) {
            if (piece.isSelected()) {
                piece.setSelected(false);
            }
        }
    }
}
