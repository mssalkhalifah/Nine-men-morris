package com.groupg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.groupg.game.board.Board;
import com.groupg.game.board.PointsPosition;
import com.groupg.game.gameobject.CurrentTurn;
import com.groupg.game.player.Player;

import java.util.Stack;

public class GameState {
    private static final int NUMBER_OF_PHASES = 2;

    private Texture[] phasesTexture;
    private Texture winTexture;

    private Player whitePlayer;
    private Player blackPlayer;
    private Stack<State> gameStates;
    private GameRule gameRule;
    private Board board;
    private CurrentTurn currentTurn;

    public GameState(Player whitePlayer, Player blackPlayer, Board board) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.board = board;

        phasesTexture = new Texture[NUMBER_OF_PHASES];
        currentTurn = new CurrentTurn();
        gameStates = new Stack<>();
        gameRule = new GameRule(whitePlayer, blackPlayer, board, new PointsPosition());

        gameStates.push(State.FIRST_PHASE);
        phasesTexture[0] = new Texture(Gdx.files.internal("First_Phase.png"));
        phasesTexture[1] = new Texture(Gdx.files.internal("Second_Phase.png"));
    }

    public void update(float delta) {
        if (whitePlayer.maxNumberPlayed() && blackPlayer.maxNumberPlayed() && gameStates.peek() == State.FIRST_PHASE) {
            gameStates.clear();
            gameStates.push(State.SECOND_PHASE);
        }

        gameRule.update(delta);
        currentTurn.update(delta);
    }

    public void render(float delta, SpriteBatch batch) {
        if (winTexture != null) batch.draw(winTexture, 120 - winTexture.getWidth() / 2, 450 - winTexture.getHeight() / 2);
        Texture currentPhaseTexture = (gameStates.peek() == State.FIRST_PHASE) ? phasesTexture[0] : phasesTexture[1];
        batch.draw(currentPhaseTexture,  120 - currentPhaseTexture.getWidth() / 2, 250 - currentPhaseTexture.getHeight() / 2);
        gameRule.render(delta, batch);
        currentTurn.render(delta, batch);
    }

    public void dispose() {
        gameRule.dispose();
        currentTurn.dispose();
        phasesTexture[0].dispose();
        phasesTexture[1].dispose();
        winTexture.dispose();
    }

    public Stack<State> getGameStates() {
        return gameStates;
    }

    public boolean getCurrentTurn() {
        return currentTurn.getCurrentTurn();
    }

    public void firstPhase(Player player) {
        if (gameRule.checkAvailableLegalMove(player.getPieceColor(), this)) {
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
        } else {
            gameStates.push(State.FINISH);
        }
    }

    public void secondPhase(Player player) {
        if (gameRule.checkAvailableLegalMove(player.getPieceColor(), this)) {
            player.setSelectedPiece(board.getSelectPiece(player.getPieceColor(), player.getTouchPosition()));
            if (player.getSelectedPiece() != null) {
                gameStates.push(State.MOVE);
                player.setSelectedPiece(player.getSelectedPiece());
            }
        } else {
            gameStates.push(State.FINISH);
        }
    }

    public void movingState(Player player, boolean isAI) {
        if (!isAI) {
            player.setSelectedPiece(board.getSelectPiece(player.getPieceColor(), player.getTouchPosition()));
        }
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

    public void finishState() {
        if (whitePlayer.getCurrentNumberOfPieces() < 3 || gameRule.checkAvailableLegalMove(whitePlayer.getPieceColor(), this)) {
            winTexture = new Texture(Gdx.files.internal("Black_Player_Won.png"));
        } else if (blackPlayer.getCurrentNumberOfPieces() < 3 || gameRule.checkAvailableLegalMove(blackPlayer.getPieceColor(), this)) {
            winTexture = new Texture(Gdx.files.internal("White_Player_Won.png.png"));
        }
    }
}
