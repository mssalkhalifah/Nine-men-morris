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
    private BitSet whiteBitBoard;
    private BitSet blackBitBoard;

    public Board() {
        whiteBitBoard = new BitSet(PointsPosition.NUMBER_OF_POINTS);
        blackBitBoard = new BitSet(PointsPosition.NUMBER_OF_POINTS);
        pointArray = new Array<>();
        pieceArray = new Array<>();

        PointsPosition pointPosition = new PointsPosition();
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
            pieceArray.add(new Piece(new PointsPosition().getPointPosition(pointNumber), pieceColor, pointNumber));
            if (pieceColor == PieceColor.WHITE) {
                whiteBitBoard.set(pointNumber);
            } else {
                blackBitBoard.set(pointNumber);
            }
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

    public BitSet getBitBoard(PieceColor pieceColor) {
        return (pieceColor == PieceColor.WHITE) ? whiteBitBoard : blackBitBoard;
    }

    public int getPointNumber(Vector3 position) {
        int pointNumber = -1;
        for (Point point : pointArray) {
            if (point.isCollide(position)) pointNumber = point.getPointNumber();
        }
        return pointNumber;
    }

    public boolean isEmptyPoint(int pointNumber) {
        return !(whiteBitBoard.get(pointNumber) || blackBitBoard.get(pointNumber));
    }

    public Piece getPiece(int pieceNumber, PieceColor pieceColor) {
        for (Piece piece : pieceArray) {
            if (piece.getPieceNumber() == pieceNumber && piece.getPieceColor() == pieceColor) return piece;
        }
        throw new IllegalArgumentException("Piece number:" + pieceNumber + " not found");
    }

    public void clearSelected() {
        for (Piece piece : pieceArray) {
            if (piece.isSelected()) {
                piece.setSelected(false);
            }
        }
    }

    public void removePiece(int pieceNumber) {
        for (int i = 0; i < pieceArray.size; i++) {
            if (pieceArray.get(i).getPieceNumber() == pieceNumber) {
                pieceArray.removeIndex(i);
                break;
            }
        }
    }
}
