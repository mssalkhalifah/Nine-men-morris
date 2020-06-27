package com.groupg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.groupg.game.board.Board;
import com.groupg.game.board.PointsPosition;
import com.groupg.game.gameobject.GameObjectManager;
import com.groupg.game.gameobject.Piece;
import com.groupg.game.gameobject.PieceColor;
import com.groupg.game.gameobject.Point;
import com.groupg.game.player.Player;

public class Game {
    private Texture millTexture;
    private Board gameBoard;
    private GameObjectManager gameObjectManager;
    private Player player;
    private boolean playerTurn = true; // Temporary!
    private boolean isMill;

    public Game() {
        millTexture = new Texture(Gdx.files.internal("Mill.png"));
        player = new Player();
        gameBoard = new Board();
        gameObjectManager = new GameObjectManager(PointsPosition.NUMBER_OF_POINTS + Player.NUMBER_OF_PLAYER_PIECES * 2);
        for (int i = 0; i < PointsPosition.NUMBER_OF_POINTS; i++) {
            gameObjectManager.addObject(new Point(gameBoard, gameBoard.getPointPosition(i), i));
        }
    }

    public void update(double delta, Vector3 vector3) {
        if (playerTurn) {
            gameObjectManager.update(delta, vector3);
            if (Gdx.input.isTouched()) {
                int pointClicked = gameObjectManager.getPointClicked();
                if (pointClicked >= 0) {
                    if (gameBoard.addPiece(PieceColor.BLUE, pointClicked) && player.maxNumberPlayed()) {
                        gameObjectManager.addObject(new Piece(PieceColor.BLUE, gameBoard.getPointPosition(pointClicked), pointClicked));
                        System.out.println(pointClicked);
                        if (gameBoard.checkHorizontalMill(pointClicked, PieceColor.BLUE) || gameBoard.checkVerticalMill(pointClicked, PieceColor.BLUE)) {
                            System.out.println("MILL!!");
                            isMill = true;
                        }
                    }
                }
            }
        }
    }

    public void render(double delta, SpriteBatch batch) {
        gameObjectManager.render(delta, batch);
        if (isMill) {
            batch.draw(millTexture,
                    MyGame.BOARD_WIDTH / 2 - millTexture.getWidth() / 2,
                    MyGame.BOARD_HEIGHT / 2 - millTexture.getHeight() / 2);
        }
    }

    public void dispose() {
        gameObjectManager.dispose();
        millTexture.dispose();
    }
}
