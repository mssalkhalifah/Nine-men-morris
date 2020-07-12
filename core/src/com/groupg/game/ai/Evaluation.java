package com.groupg.game.ai;

import com.groupg.game.board.Board;
import com.groupg.game.board.PointsPosition;
import com.groupg.game.gameobject.PieceColor;

public class Evaluation {
    private int evaluation;
    private int positionEvaluated;
    private Board board;

    public Evaluation() {
    }

    public Evaluation(int evaluation, Board board) {
        this.evaluation = evaluation;
        this.board = board;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public int getPositionEvaluated() {
        return positionEvaluated;
    }

    public void setPositionEvaluated(int positionEvaluated) {
        this.positionEvaluated = positionEvaluated;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public int getAddedPiece(Board board) {
        for (int i = 0; i < PointsPosition.NUMBER_OF_POINTS; i++) {
            if (this.board.getBitBoard(PieceColor.BLACK).get(i) != board.getBitBoard(PieceColor.BLACK).get(i)) return i;
        }

        return -1;
    }
}
