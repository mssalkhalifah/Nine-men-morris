package com.groupg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.groupg.game.board.Board;
import com.groupg.game.gameobject.PieceColor;
import com.groupg.game.player.Player;

import java.util.Stack;

public class Game {
    private Texture millTexture;
    private Board gameBoard;
    private Player whitePlayer;
    private Player blackPlayer;
    private boolean whitePlayerTurn = true; // Temporary!
    private Stack<Phase> gamePhaseStack;

    public Game(MyGame myGame) {
        millTexture = new Texture(Gdx.files.internal("Mill.png"));
        whitePlayer = new Player(myGame.getCamera());
        blackPlayer = new Player(myGame.getCamera());
        gameBoard = new Board();
        gamePhaseStack = new Stack<>();
        gamePhaseStack.push(Phase.FIRST_PHASE);
    }

    public void update(float delta) {
        if (whitePlayerTurn) whitePlayer.update(delta); else blackPlayer.update(delta);

        switch (gamePhaseStack.peek()) {
            case FIRST_PHASE:
                firstPhase(delta);
                break;

            case SECOND_PHASE:
                secondPhase(delta);
                break;

            case CAPTURE:
                capturePhase(delta);
                break;

            case MOVE:
                movingPhase(delta);
        }
    }

    public void render(float delta, SpriteBatch batch) {
        gameBoard.render(delta, batch);
    }

    public void dispose() {
        millTexture.dispose();
        gameBoard.dispose();
    }

    private void capturePhase(float delta) {
        if (whitePlayerTurn) {
            gameBoard.update(delta, whitePlayer.getMousePosition());
            if (whitePlayer.isPlay()) {
                if (gameBoard.isCaptureValid(whitePlayer.getTouchPosition(), PieceColor.WHITE)) {
                    gamePhaseStack.pop();
                    whitePlayerTurn = false;
                    System.out.println("CAPTURED!");
                }
            }
        } else {
            gameBoard.update(delta, blackPlayer.getMousePosition());
            if (blackPlayer.isPlay()) {
                if (gameBoard.isCaptureValid(blackPlayer.getTouchPosition(), PieceColor.BLACK)) {
                    gamePhaseStack.pop();
                    whitePlayerTurn = true;
                    System.out.println("CAPTURED!");
                }
            }
        }
    }

    private void movingPhase(float delta) {
        if (whitePlayerTurn) {
            gameBoard.update(delta, whitePlayer.getMousePosition());
            if (whitePlayer.isPlay()) {
                whitePlayer.setSelectedPiece(gameBoard.getSelectPiece(PieceColor.WHITE, whitePlayer.getTouchPosition()));
                System.out.println("White piece selected!");
                if (gameBoard.isMoveValid(whitePlayer.getSelectedPiece().getPieceNumber(), whitePlayer.getTouchPosition(), PieceColor.WHITE)) {
                    if (gameBoard.isMill(whitePlayer.getSelectedPiece().getPieceNumber(), PieceColor.WHITE)) {
                        gamePhaseStack.pop();
                        gamePhaseStack.push(Phase.CAPTURE);
                        System.out.println("White player Mill!");
                    } else {
                        gamePhaseStack.pop();
                        whitePlayerTurn = false;
                        whitePlayer.clearSelectedPiece();
                        System.out.println("White piece moved!");
                    }
                }
            }
        } else {
            gameBoard.update(delta, blackPlayer.getMousePosition());
            if (blackPlayer.isPlay()) {
                blackPlayer.setSelectedPiece(gameBoard.getSelectPiece(PieceColor.BLACK, blackPlayer.getTouchPosition()));
                System.out.println("Black piece selected!");
                if (gameBoard.isMoveValid(blackPlayer.getSelectedPiece().getPieceNumber(), blackPlayer.getTouchPosition(), PieceColor.BLACK)) {
                    if (gameBoard.isMill(blackPlayer.getSelectedPiece().getPieceNumber(), PieceColor.BLACK)) {
                        gamePhaseStack.pop();
                        gamePhaseStack.push(Phase.CAPTURE);
                        System.out.println("Black player Mill!");
                    } else {
                        gamePhaseStack.pop();
                        whitePlayerTurn = true;
                        blackPlayer.clearSelectedPiece();
                        System.out.println("Black piece moved!");
                    }
                }
            }
        }
    }

    private void firstPhase(float delta) {
        if (whitePlayerTurn) {
            gameBoard.update(delta, whitePlayer.getMousePosition());
            if (whitePlayer.isPlay() && !whitePlayer.maxNumberPlayed()) {
                if (gameBoard.addPiece(PieceColor.WHITE, whitePlayer.getTouchPosition())) {
                    whitePlayer.addPiece();
                    System.out.println("ADD BLUE");
                    if (gameBoard.isMill(whitePlayer.getTouchPosition(), PieceColor.WHITE)) {
                        System.out.println("IS MILLLLL BLUE");
                        gamePhaseStack.push(Phase.CAPTURE);
                    } else {
                        whitePlayerTurn = false;
                    }
                }
            }
        } else {
            gameBoard.update(delta, blackPlayer.getMousePosition());
            if (blackPlayer.isPlay() && !blackPlayer.maxNumberPlayed()) {
                if (gameBoard.addPiece(PieceColor.BLACK, blackPlayer.getTouchPosition())) {
                    blackPlayer.addPiece();
                    System.out.println("ADD RED");
                    if (gameBoard.isMill(blackPlayer.getTouchPosition(), PieceColor.BLACK)) {
                        System.out.println("IS MILLLLL RED");
                        gamePhaseStack.push(Phase.CAPTURE);
                    } else {
                        whitePlayerTurn = true;
                    }
                }
            }
        }

        if (whitePlayer.maxNumberPlayed() && blackPlayer.maxNumberPlayed()) {
            gamePhaseStack.clear();
            gamePhaseStack.push(Phase.SECOND_PHASE);
        }
    }

    private void secondPhase(float delta) {
        if (whitePlayerTurn) {
            gameBoard.update(delta, whitePlayer.getMousePosition());
            if (whitePlayer.isPlay()) {
                whitePlayer.setSelectedPiece(gameBoard.getSelectPiece(PieceColor.WHITE, whitePlayer.getTouchPosition()));
                if (whitePlayer.getSelectedPiece() != null) {
                    System.out.println("White piece selected!");
                    gamePhaseStack.push(Phase.MOVE);
                    whitePlayer.setSelectedPiece(whitePlayer.getSelectedPiece());
                }
            }
        } else {
            gameBoard.update(delta, blackPlayer.getMousePosition());
            if (blackPlayer.isPlay()) {
                blackPlayer.setSelectedPiece(gameBoard.getSelectPiece(PieceColor.BLACK, blackPlayer.getTouchPosition()));
                if (blackPlayer.getSelectedPiece() != null) {
                    System.out.println("Black piece selected!");
                    gamePhaseStack.push(Phase.MOVE);
                    blackPlayer.setSelectedPiece(blackPlayer.getSelectedPiece());
                }
            }
        }
    }
}
