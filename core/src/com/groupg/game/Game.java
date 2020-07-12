package com.groupg.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.groupg.game.ai.Evaluation;
import com.groupg.game.ai.MiniMax;
import com.groupg.game.board.Board;
import com.groupg.game.board.PointsPosition;
import com.groupg.game.gameobject.PieceColor;
import com.groupg.game.player.Player;

public class Game {
    private Board gameBoard;
    private GameState gameState;
    private Player whitePlayer;
    private Player blackPlayer;
    private Evaluation evaluationBoard;

    public Game(MyGame myGame) {
        whitePlayer = new Player(myGame.getCamera(), PieceColor.WHITE);
        blackPlayer = new Player(myGame.getCamera(), PieceColor.BLACK);
        gameBoard = new Board();
        gameBoard.initialize();
        gameState = new GameState(whitePlayer, blackPlayer, gameBoard);
        evaluationBoard = new Evaluation();
    }

    public void update(float delta) {

        whitePlayer.update(delta);

        gameBoard.update(delta, whitePlayer.getMousePosition());
        gameState.update(delta);

        // If is the black player turn
        if (!gameState.getCurrentTurn()) {
            MiniMax.getBestOpeningPhaseMove(gameBoard, gameState, blackPlayer);
        }

        if (whitePlayer.isPlay() && gameState.getCurrentTurn()) {
            System.out.println("Current game board: \n" + gameBoard);
            switch (gameState.getGameStates().peek()) {
                case FIRST_PHASE:
                    gameState.firstPhase(whitePlayer);
                    break;

                case SECOND_PHASE:
                    gameState.secondPhase(whitePlayer);
                    break;

                case CAPTURE:
                    gameState.captureState(whitePlayer);
                    break;

                case MOVE:
                    gameState.movingState(whitePlayer, false);
                    break;

                case FINISH:
                    gameState.finishState();
                    break;

                default:
                    throw new IllegalArgumentException("Unknown game state");
            }
            //player.setAction(false);
            System.out.println("Updated game board: \n" + gameBoard + "\n");
        }
    }

    public void render(float delta, SpriteBatch batch) {
        gameState.render(delta, batch);
        gameBoard.render(delta, batch);
    }

    public void dispose() {
        gameState.dispose();
        gameBoard.dispose();
    }
}
