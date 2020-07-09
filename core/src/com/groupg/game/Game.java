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
    private GameState gameState;
    private Player whitePlayer;
    private Player blackPlayer;

    public Game(MyGame myGame) {
        millTexture = new Texture(Gdx.files.internal("Mill.png"));
        whitePlayer = new Player(myGame.getCamera(), PieceColor.WHITE);
        blackPlayer = new Player(myGame.getCamera(), PieceColor.BLACK);
        gameBoard = new Board();
        gameState = new GameState(whitePlayer, blackPlayer, gameBoard);
    }

    public void update(float delta) {
        gameState.update(delta);

        Player player = (gameState.getCurrentTurn()) ? whitePlayer : blackPlayer;
        player.update(delta);
        gameBoard.update(delta, player.getMousePosition());

        if (player.isPlay()) {
            switch (gameState.getGameStates().peek()) {
                case FIRST_PHASE:
                    gameState.firstPhase(player);
                    break;

                case SECOND_PHASE:
                    gameState.secondPhase(player);
                    break;

                case CAPTURE:
                    gameState.captureState(player);
                    break;

                case MOVE:
                    gameState.movingState(player);
                    break;

                default:
                    throw new IllegalArgumentException("Unknown game state");
            }
        }
    }

    public void render(float delta, SpriteBatch batch) {
        gameState.render(delta, batch);
        gameBoard.render(delta, batch);
    }

    public void dispose() {
        gameState.dispose();
        millTexture.dispose();
        gameBoard.dispose();
    }
}
