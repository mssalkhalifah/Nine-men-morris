package com.groupg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.groupg.game.board.Board;
import com.groupg.game.gameobject.PieceColor;
import com.groupg.game.player.Player;

public class Game {
    private Texture millTexture;
    private Board gameBoard;
    private Player whitePlayer;
    private Player blackPlayer;
    private boolean whitePlayerTurn = true; // Temporary!
    private boolean capturePhase;

    public Game(MyGame myGame) {
        millTexture = new Texture(Gdx.files.internal("Mill.png"));
        whitePlayer = new Player(myGame.getCamera());
        blackPlayer = new Player(myGame.getCamera());
        gameBoard = new Board();
    }

    public void update(double delta) {
        if (whitePlayerTurn) whitePlayer.update(delta); else blackPlayer.update(delta);
        if (capturePhase) {
            capturePhase(delta);
        } else {
            firstPhase(delta);
        }
    }

    public void render(double delta, SpriteBatch batch) {
        gameBoard.render(delta, batch);
    }

    public void dispose() {
        millTexture.dispose();
        gameBoard.dispose();
    }

    private void capturePhase(double delta) {
        if (whitePlayerTurn) {
            gameBoard.update(delta, whitePlayer.getMousePosition());
            if (whitePlayer.isPlay()) {
                if (gameBoard.isCaptureValid(whitePlayer.getTouchPosition(), PieceColor.WHITE)) {
                    capturePhase = false;
                    whitePlayerTurn = false;
                    System.out.println("CAPTURED!");
                }
            }
        } else {
            gameBoard.update(delta, blackPlayer.getMousePosition());
            if (blackPlayer.isPlay()) {
                if (gameBoard.isCaptureValid(blackPlayer.getTouchPosition(), PieceColor.BLACK)) {
                    capturePhase = false;
                    whitePlayerTurn = true;
                    System.out.println("CAPTURED!");
                }
            }
        }
    }

    private void firstPhase(double delta) {
        if (whitePlayerTurn) {
            gameBoard.update(delta, whitePlayer.getMousePosition());
            if (whitePlayer.isPlay() && whitePlayer.maxNumberPlayed()) {
                if (gameBoard.addPiece(PieceColor.WHITE, whitePlayer.getTouchPosition())) {
                    whitePlayer.addPiece();
                    System.out.println("ADD BLUE");
                    if (gameBoard.isMill(whitePlayer.getTouchPosition(), PieceColor.WHITE)) {
                        System.out.println("IS MILLLLL BLUE");
                        capturePhase = true;
                    } else {
                        whitePlayerTurn = false;
                    }
                }
            }
        } else {
            gameBoard.update(delta, blackPlayer.getMousePosition());
            if (blackPlayer.isPlay() && blackPlayer.maxNumberPlayed()) {
                if (gameBoard.addPiece(PieceColor.BLACK, blackPlayer.getTouchPosition())) {
                    blackPlayer.addPiece();
                    System.out.println("ADD RED");
                    if (gameBoard.isMill(blackPlayer.getTouchPosition(), PieceColor.BLACK)) {
                        System.out.println("IS MILLLLL RED");
                        capturePhase = true;
                    } else {
                        whitePlayerTurn = true;
                    }
                }
            }
        }
    }

    private void secondPhase(double delta) {

    }
}
