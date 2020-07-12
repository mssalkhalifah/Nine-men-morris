package com.groupg.game.ai;

import com.groupg.game.GameState;
import com.groupg.game.State;
import com.groupg.game.board.Board;
import com.groupg.game.board.PointsPosition;
import com.groupg.game.gameobject.PieceColor;
import com.groupg.game.gameobject.Point;
import com.groupg.game.player.Player;

import java.util.ArrayList;
import java.util.BitSet;

public class MiniMax {
    public static int count = 0;

    public static int getBestOpeningPhaseMove(Board board, GameState gameState, Player blackPlayer) {
        int bestValue = Integer.MIN_VALUE;
        int bestMovePosition = -1;

        if (gameState.getGameStates().peek() == State.FIRST_PHASE) {
            for (int i = 0; i < PointsPosition.NUMBER_OF_POINTS; i++) {
                if (board.isEmptyPoint(i)) {
                    // AI makes a move
                    board.getBitBoard(PieceColor.BLACK).set(i);

                    // Compute the value of AI current move
                    int moveValue = miniMax(board, 5, Integer.MIN_VALUE, Integer.MAX_VALUE, false, gameState);

                    // Undo the move
                    board.getBitBoard(PieceColor.BLACK).clear(i);

                    if (moveValue > bestValue) {
                        bestMovePosition = i;
                        bestValue = moveValue;
                    }
                }
            }
            blackPlayer.setTouchPosition(PointsPosition.getPointPosition(bestMovePosition));
            gameState.firstPhase(blackPlayer);
        } else if (gameState.getGameStates().peek() == State.CAPTURE) {
            for (int i = 0; i < PointsPosition.NUMBER_OF_POINTS; i++) {
                if (!PointsPosition.checkMill(i, board.getBitBoard(PieceColor.WHITE))) {
                    // AI Captures a piece
                    board.getBitBoard(PieceColor.WHITE).clear(i);

                    // Compute the value of AI current move
                    int moveValue = miniMax(board, 5, Integer.MIN_VALUE, Integer.MAX_VALUE, false, gameState);

                    // Undo the move
                    board.getBitBoard(PieceColor.WHITE).set(i);

                    if (moveValue > bestValue) {
                        bestMovePosition = i;
                        bestValue = moveValue;
                    }
                }
            }
            blackPlayer.setTouchPosition(PointsPosition.getPointPosition(bestMovePosition));
            gameState.captureState(blackPlayer);
           // System.out.println(bestMovePosition + "<- Best Move");
        } else if (gameState.getGameStates().peek() == State.SECOND_PHASE) {
            int currentPosition = -1;
            for (int i = 0; i < PointsPosition.NUMBER_OF_POINTS; i++) {
                if (board.getBitBoard(PieceColor.BLACK).get(i)) {
                    ArrayList<Integer> adjacentPosition = PointsPosition.getAdjacentLocation(i);
                    for (int location : adjacentPosition) {
                        if (board.isEmptyPoint(location)) {
                            // AI moves the piece
                            board.getBitBoard(PieceColor.BLACK).set(location);
                            board.getBitBoard(PieceColor.BLACK).clear(i);

                            int moveValue = miniMax(board, 5, Integer.MIN_VALUE, Integer.MAX_VALUE, false, gameState);

                            // Undo the move
                            board.getBitBoard(PieceColor.BLACK).clear(location);
                            board.getBitBoard(PieceColor.BLACK).set(i);

                            if (moveValue > bestValue) {
                                currentPosition = i;
                                bestMovePosition = location;
                                bestValue = moveValue;
                            }
                        }
                    }
                }
            }
            blackPlayer.setTouchPosition(PointsPosition.getPointPosition(bestMovePosition));
            blackPlayer.setSelectedPiece(board.getSelectPiece(PieceColor.BLACK, PointsPosition.getPointPosition(currentPosition)));
            gameState.getGameStates().push(State.MOVE);
            gameState.movingState(blackPlayer, true);
        }
        System.out.println("States visited = " + count);
        count = 0;
        return bestMovePosition;
    }

    private static int miniMax(Board board, int depth, int alpha, int beta, boolean isMaximizer, GameState gameState) {
        switch (gameState.getGameStates().peek()) {
            case FIRST_PHASE:
                return miniMaxFirstPhase(board, depth, alpha, beta, isMaximizer, gameState);

            case SECOND_PHASE:
                return miniMaxSecondPhase(board, depth, alpha, beta, isMaximizer, gameState);

            default:
                if (gameState.getGameStates().peek() == State.CAPTURE) {
                    State temp = gameState.getGameStates().pop();
                    int moveValue = 0;
                    if (gameState.getGameStates().peek() == State.FIRST_PHASE) {
                        moveValue = miniMaxFirstPhase(board, depth, alpha, beta, isMaximizer, gameState);
                    } else if (gameState.getGameStates().peek() == State.SECOND_PHASE) {
                        moveValue = miniMaxSecondPhase(board, depth, alpha, beta, isMaximizer, gameState);
                    }
                    gameState.getGameStates().push(temp);
                    return moveValue;
                }
        }
        throw new IllegalArgumentException("Invalid State");
    }

    private static int miniMaxFirstPhase(Board board, int depth, int alpha, int beta, boolean isMaximizer, GameState gameState) {
        // If reached depth limit or no available legal move return the evaluation
        if (depth == 0 || !checkAvailableLegalMove(board, isMaximizer, gameState)) {
            return openingPhaseStaticEvaluation(board);
        }

        ArrayList<Board> currentPossibleBoardStates;

        if (isMaximizer) {
            int maxEvaluation = Integer.MIN_VALUE;
            currentPossibleBoardStates = GenerateMove.getOpeningPhasePossibleStates(board, true);
            for (Board state : currentPossibleBoardStates) {
                int evaluation = miniMax(state, depth - 1, alpha, beta, false, gameState);
                maxEvaluation = Math.max(maxEvaluation, evaluation);
                alpha = Math.max(alpha, evaluation);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxEvaluation;
        } else {
            int minEvaluation = Integer.MAX_VALUE;
            currentPossibleBoardStates = GenerateMove.getOpeningPhasePossibleStates(board, false);
            for (Board state : currentPossibleBoardStates) {
                int evaluation = miniMax(state, depth - 1, alpha, beta, true, gameState);
                minEvaluation = Math.min(minEvaluation, evaluation);
                beta = Math.min(beta, evaluation);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEvaluation;
        }
    }

    private static int miniMaxSecondPhase(Board board, int depth, int alpha, int beta, boolean isMaximizer, GameState gameState) {
        // If reached depth limit or no available legal move return the evaluation
        if (depth == 0 || !checkAvailableLegalMove(board, isMaximizer, gameState)) {
            return midPhaseStaticEvaluation(board);
        }

        ArrayList<Board> currentPossibleBoardStates;

        if (isMaximizer) {
            int maxEvaluation = Integer.MIN_VALUE;
            currentPossibleBoardStates = GenerateMove.getMidPhasePossibleStates(board, true);
            for (Board state : currentPossibleBoardStates) {
                int evaluation = miniMax(state, depth - 1, alpha, beta, false, gameState);
                maxEvaluation = Math.max(maxEvaluation, evaluation);
                alpha = Math.max(alpha, evaluation);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxEvaluation;
        } else {
            int minEvaluation = Integer.MAX_VALUE;
            currentPossibleBoardStates = GenerateMove.getMidPhasePossibleStates(board, false);
            for (Board state : currentPossibleBoardStates) {
                int evaluation = miniMax(state, depth - 1, alpha, beta, true, gameState);
                minEvaluation = Math.min(minEvaluation, evaluation);
                beta = Math.min(beta, evaluation);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEvaluation;
        }
    }

    private static int midPhaseStaticEvaluation(Board board) {
        return openingPhaseStaticEvaluation(board);
    }

    private static int openingPhaseStaticEvaluation(Board board) {
        count++;

        int numberOfBlackPieces = 0;
        int numberOfWhitePieces = 0;
        for (int i = 0; i < PointsPosition.NUMBER_OF_POINTS; i++) {
            if (board.getBitBoard(PieceColor.WHITE).get(i)) numberOfWhitePieces++;
            if (board.getBitBoard(PieceColor.BLACK).get(i)) numberOfBlackPieces++;
        }

        int numberOfWhiteMills = getNumberOfMills(board, false);
        int numberOfBlackMills = getNumberOfMills(board, true);

        int numberOfBlockedBlack = getNumberOfBlockedOpponentPieces(board, false);
        int numberOfBlockedWhite = getNumberOfBlockedOpponentPieces(board, true);

        //int numberOfTwoPieceConfigWhite = getNumberOfTwoPieceConfiguration(board, false);
        //int numberOfTwoPieceConfigBlack = getNumberOfTwoPieceConfiguration(board, true);

        int lastMorris;
        if (numberOfBlackMills - numberOfWhiteMills > 0) {
            lastMorris = 1;
        } else if (numberOfBlackMills - numberOfWhiteMills < 0) {
            lastMorris = -1;
        } else {
            lastMorris = 0;
        }

        return (18 * lastMorris + 26 * (numberOfBlackMills - numberOfWhiteMills)
        + (numberOfBlockedWhite - numberOfBlockedBlack) + 9 * (numberOfBlackPieces - numberOfWhitePieces));
    }

    private static int getNumberOfMills(Board board, boolean isMaximizer) {
        int count = 0;
        for (int i = 0; i < PointsPosition.NUMBER_OF_POINTS; i++) {
            if (isMaximizer) {
                if (PointsPosition.checkMill(i, board.getBitBoard(PieceColor.BLACK))) count++;
            } else {
                if (PointsPosition.checkMill(i, board.getBitBoard(PieceColor.WHITE))) count++;
            }
        }

        return count / 3;
    }

    private static int getNumberOfBlockedOpponentPieces(Board board, boolean isMaximizer) {
        int count = 0;
        int numberOfBlockedPieces = 0;

        if (isMaximizer) {
            for (int i = 0; i < board.getBitBoard(PieceColor.WHITE).size(); i++) {
                if (board.getBitBoard(PieceColor.WHITE).get(i)) {
                    ArrayList<Integer> adjacentPieces = PointsPosition.getAdjacentLocation(i);
                    for (int location : adjacentPieces) {
                        if (board.getBitBoard(PieceColor.BLACK).get(location)) numberOfBlockedPieces++;
                    }
                    if (numberOfBlockedPieces == adjacentPieces.size()) count++;
                    numberOfBlockedPieces = 0;
                }
            }
        } else {
            for (int i = 0; i < board.getBitBoard(PieceColor.BLACK).size(); i++) {
                if (board.getBitBoard(PieceColor.BLACK).get(i)) {
                    ArrayList<Integer> adjacentPieces = PointsPosition.getAdjacentLocation(i);
                    for (int location : adjacentPieces) {
                        if (board.getBitBoard(PieceColor.WHITE).get(location)) numberOfBlockedPieces++;
                    }
                    if (numberOfBlockedPieces == adjacentPieces.size()) count++;
                    numberOfBlockedPieces = 0;
                }
            }
        }
        return count;
    }

    private static int getNumberOfTwoPieceConfiguration(Board board, boolean isMaximizer) {
        int count = 0;
        int numberOfPieces = 0;

        if (isMaximizer) {
            BitSet blackBitBoard = board.getBitBoard(PieceColor.BLACK);
            for (int i = 0; i < PointsPosition.NUMBER_OF_POINTS; i++) {
                if (blackBitBoard.get(i)) {
                    ArrayList<Integer> horizontalSet = PointsPosition.getHorizontalSet(i);
                    ArrayList<Integer> verticalSet = PointsPosition.getVerticalSet(i);
                    for (int location : horizontalSet) {
                        if (board.getBitBoard(PieceColor.BLACK).get(location)) numberOfPieces++;
                    }
                    if (numberOfPieces == 2) count++;
                    numberOfPieces = 0;
                    for (int location : verticalSet) {
                        if (board.getBitBoard(PieceColor.BLACK).get(location)) numberOfPieces++;
                    }
                    if (numberOfPieces == 2) count++;
                }
            }
        } else {
            BitSet whiteBitBoard = board.getBitBoard(PieceColor.WHITE);
            for (int i = 0; i < PointsPosition.NUMBER_OF_POINTS; i++) {
                ArrayList<Integer> horizontalSet = PointsPosition.getHorizontalSet(i);
                ArrayList<Integer> verticalSet = PointsPosition.getVerticalSet(i);
                for (int location : horizontalSet) {
                    if (board.getBitBoard(PieceColor.WHITE).get(location)) numberOfPieces++;
                }
                if (numberOfPieces == 2) count++;
                numberOfPieces = 0;
                for (int location : verticalSet) {
                    if (board.getBitBoard(PieceColor.WHITE).get(location)) numberOfPieces++;
                }
                if (numberOfPieces == 2) count++;
            }
        }
        return count;
    }

    private static boolean checkAvailableLegalMove(Board board, boolean isMaximizer, GameState gameState) {
        BitSet checkAvailableMoveBitBoard = board.getBitBoard((isMaximizer) ? PieceColor.BLACK : PieceColor.WHITE);

        if (checkAvailableMoveBitBoard.isEmpty()) return true;

        int whitePieces = 0;
        int blackPieces = 0;
        for (int i = 0; i < PointsPosition.NUMBER_OF_POINTS; i++) {
            if (board.getBitBoard(PieceColor.WHITE).get(i)) whitePieces++;
            if (board.getBitBoard(PieceColor.BLACK).get(i)) blackPieces++;
        }

        if ((whitePieces < 3 || blackPieces < 3) && gameState.getGameStates().peek() == State.SECOND_PHASE) return false;

        boolean[] availableMoves = new boolean[checkAvailableMoveBitBoard.size()];

        for (int i = 0; i < checkAvailableMoveBitBoard.size(); i++) {
            if (gameState.getGameStates().peek() == State.FIRST_PHASE && board.isEmptyPoint(i)) return true;

            if (checkAvailableMoveBitBoard.get(i)) {
                ArrayList<Integer> verticalPositionSet = PointsPosition.getVerticalSet(i);
                ArrayList<Integer> horizontalPositionSet = PointsPosition.getHorizontalSet(i);

                int currentHorizontalIndex = horizontalPositionSet.indexOf(i);
                int currentVerticalIndex = verticalPositionSet.indexOf(i);

                boolean horizontalMoveAvailable;
                boolean verticalMoveAvailable;

                if (currentVerticalIndex == 0) {
                    verticalMoveAvailable = board.isEmptyPoint(verticalPositionSet.get(currentVerticalIndex + 1));
                } else if (currentVerticalIndex == 1) {
                    verticalMoveAvailable = board.isEmptyPoint(verticalPositionSet.get(currentVerticalIndex + 1))
                            || board.isEmptyPoint(verticalPositionSet.get(currentVerticalIndex - 1));
                } else {
                    verticalMoveAvailable = board.isEmptyPoint(verticalPositionSet.get(currentVerticalIndex - 1));
                }

                if (currentHorizontalIndex == 0) {
                    horizontalMoveAvailable = board.isEmptyPoint(horizontalPositionSet.get(currentHorizontalIndex + 1));
                } else if (currentHorizontalIndex == 1) {
                    horizontalMoveAvailable = board.isEmptyPoint(horizontalPositionSet.get(currentHorizontalIndex + 1))
                            || board.isEmptyPoint(horizontalPositionSet.get(currentHorizontalIndex - 1));
                } else {
                    horizontalMoveAvailable = board.isEmptyPoint(horizontalPositionSet.get(currentHorizontalIndex - 1));
                }

                if (horizontalMoveAvailable || verticalMoveAvailable) return true;
            }
        }
        return false;
    }
}
