package com.groupg.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.groupg.game.gameobject.Piece;
import com.groupg.game.gameobject.PieceColor;

public class Player {
    public static final int NUMBER_OF_PLAYER_PIECES = 9;

    private boolean action;
    private boolean isAI;

    private int totalNumberOfPieces;
    private int currentNumberOfPieces;
    private int numberOfPiecesPlayed;

    private Vector3 touchPosition;
    private Vector3 mousePosition;

    private Piece selectedPiece;

    private OrthographicCamera camera;

    private PieceColor pieceColor;

    public Player(OrthographicCamera camera, PieceColor pieceColor) {
        this.pieceColor = pieceColor;
        this.camera = camera;
        touchPosition = new Vector3();
        mousePosition = new Vector3();
        totalNumberOfPieces = NUMBER_OF_PLAYER_PIECES;
    }

    public void update(double delta) {
        if (!isAI) {
            mousePosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(mousePosition);

            if (isPlay() || action) {
                touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touchPosition);
            }
        }
    }

    public void addPiece() {
        currentNumberOfPieces++;
        numberOfPiecesPlayed++;
    }

    public PieceColor getPieceColor() {
        return pieceColor;
    }

    public void setPieceColor(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }

    public void setTouchPosition(Vector3 touchPosition) {
        this.touchPosition = touchPosition;
        //camera.unproject(touchPosition);
    }

    public Vector3 getTouchPosition() {
        return touchPosition;
    }

    public Vector3 getMousePosition() {
        return mousePosition;
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public void setSelectedPiece(Piece selectedPiece) {
        if (selectedPiece != null) this.selectedPiece = selectedPiece;
    }

    public void clearSelectedPiece() {
        selectedPiece = null;
    }

    public boolean isPlay() {
        return Gdx.input.justTouched();
    }

    public boolean maxNumberPlayed() {
        return numberOfPiecesPlayed >= NUMBER_OF_PLAYER_PIECES;
    }

    public int getTotalNumberOfPieces() {
        return totalNumberOfPieces;
    }

    public int getCurrentNumberOfPieces() {
        return currentNumberOfPieces;
    }

    public void decrementNumberOfPieces() {
        --totalNumberOfPieces;
        --currentNumberOfPieces;
    }

    public boolean isAction() {
        return action;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

    public void setAI(boolean AI) {
        isAI = AI;
    }
}
