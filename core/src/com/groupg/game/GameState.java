package com.groupg.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.groupg.game.board.Board;
import com.groupg.game.board.PointsPosition;
import com.groupg.game.gameobject.CurrentTurn;
import com.groupg.game.player.Player;

import java.util.Stack;

public class GameState {
    private Stack<State> gameStates;
    private GameRule gameRule;
    private Board board;
    private CurrentTurn currentTurn;

    public GameState(Player whitePlayer, Player blackPlayer, Board board) {
        this.board = board;

        currentTurn = new CurrentTurn();
        gameStates = new Stack<>();
        gameRule = new GameRule(whitePlayer, blackPlayer, board, new PointsPosition());

        gameStates.push(State.FIRST_PHASE);
    }

    public void update(float delta) {
        gameRule.update(delta);
        currentTurn.update(delta);
    }

    public void render(float delta, SpriteBatch batch) {
        gameRule.render(delta, batch);
        currentTurn.render(delta, batch);
    }

    public void dispose() {
        gameRule.dispose();
        currentTurn.dispose();
    }

    public Stack<State> getGameStates() {
        return gameStates;
    }

    public boolean getCurrentTurn() {
        return currentTurn.getCurrentTurn();
    }

    public void firstPhase(Player player) {
        if (!player.maxNumberPlayed()) {
            if (board.addPiece(player.getPieceColor(), player.getTouchPosition())) {
                player.addPiece();
                if (gameRule.checkMill(board.getPointNumber(player.getTouchPosition()), board.getBitBoard(player.getPieceColor()))) {
                    gameStates.push(State.CAPTURE);
                } else {
                    currentTurn.changeCurrentTurn();
                }
            }
        }
    }

    public void secondPhase(Player player) {
        player.setSelectedPiece(board.getSelectPiece(player.getPieceColor(), player.getTouchPosition()));
        if (player.getSelectedPiece() != null) {
            gameStates.push(State.MOVE);
            player.setSelectedPiece(player.getSelectedPiece());
        }
    }

    public void movingState(Player player) {
        player.setSelectedPiece(board.getSelectPiece(player.getPieceColor(), player.getTouchPosition()));
        if (gameRule.checkMoveValid(player.getSelectedPiece().getPieceNumber(), player.getTouchPosition(), player.getPieceColor())) {
            if (gameRule.checkMill(player.getSelectedPiece().getPieceNumber(), board.getBitBoard(player.getPieceColor()))) {
                gameStates.pop();
                gameStates.push(State.CAPTURE);
            } else {
                gameStates.pop();
                currentTurn.changeCurrentTurn();
                player.clearSelectedPiece();
            }
        }
    }

    public void captureState(Player player) {
        if (gameRule.checkCaptureValid(player.getTouchPosition(), player.getPieceColor())) {
            player.decrementNumberOfPieces();
            gameStates.pop();
            currentTurn.changeCurrentTurn();
        }
    }
}
