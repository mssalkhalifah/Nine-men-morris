package com.groupg.game.ai;

import com.groupg.game.board.Board;
import com.groupg.game.board.PointsPosition;
import com.groupg.game.gameobject.PieceColor;

import java.util.ArrayList;
import java.util.BitSet;

public class GenerateMove {
    public static ArrayList<Board> getMidPhasePossibleStates(Board currentBoardState, boolean isMaximizer) {
        ArrayList<Board> possibleBoardStates = new ArrayList<>();

        for (int i = 0; i < PointsPosition.NUMBER_OF_POINTS; i++) {
            if (isMaximizer) {

            }
        }
        return null;
    }

    public static ArrayList<Board> getOpeningPhasePossibleStates(Board currentBoardState, boolean isMaximizer) {
        ArrayList<Board> possibleBoardStates = new ArrayList<>();

        for (int i = 0; i < PointsPosition.NUMBER_OF_POINTS; i++) {
            if (currentBoardState.isEmptyPoint(i)) {
                Board copyCurrentBoard = currentBoardState.getCopy();

                if (isMaximizer) {
                    copyCurrentBoard.setPositionValue(PieceColor.BLACK, i);
                    if (PointsPosition.checkMill(i, copyCurrentBoard.getBitBoard(PieceColor.BLACK))) {
                        possibleBoardStates = (getAllRemoveStates(copyCurrentBoard, possibleBoardStates, true));
                    } else {
                        possibleBoardStates.add(copyCurrentBoard);
                    }
                } else {
                    copyCurrentBoard.setPositionValue(PieceColor.WHITE, i);
                    if (PointsPosition.checkMill(i, copyCurrentBoard.getBitBoard(PieceColor.WHITE))) {
                        possibleBoardStates = (getAllRemoveStates(copyCurrentBoard, possibleBoardStates, false));
                    } else {
                        possibleBoardStates.add(copyCurrentBoard);
                    }
                }
            }
        }
        return possibleBoardStates;
    }

    private static ArrayList<Board> getAllRemoveStates(Board boardCopy, ArrayList<Board> boardStates, boolean isMaximizer) {
        for (int i = 0; i < PointsPosition.NUMBER_OF_POINTS; i++) {
            Board newBoardCopy = boardCopy.getCopy();
            if (checkCaptureValid(i, newBoardCopy, (isMaximizer) ? PieceColor.BLACK : PieceColor.WHITE)) {
                boardStates.add(newBoardCopy);
            }
        }
        return boardStates;
    }

    public static boolean checkCaptureValid(int capturePosition, Board boardCopy, PieceColor pieceColor) {
        PieceColor capturedColor = (pieceColor == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
        BitSet capturedBitBoard = boardCopy.getBitBoard(capturedColor);

        boolean allPiecesMill = true;
        for (int i = 0; i < capturedBitBoard.size(); i++) {
            if (capturedBitBoard.get(i)) {
                if (!PointsPosition.checkMill(i, capturedBitBoard)) {
                    allPiecesMill = false;
                    break;
                }
            }
        }

        if (capturePosition >= 0 && (allPiecesMill || !PointsPosition.checkMill(capturePosition, boardCopy.getBitBoard(capturedColor))) && capturedBitBoard.get(capturePosition)) {
            capturedBitBoard.clear(capturePosition);
            return true;
        }

        return false;
    }
}