package com.groupg.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.groupg.game.gameobject.Piece;

public class Player {
    public static final int NUMBER_OF_PLAYER_PIECES = 9;

    private int currentNumberOfPieces;

    private boolean isPlay;

    private Vector3 touchPosition;
    private Vector3 mousePosition;

    private Piece selectedPiece;

    private OrthographicCamera camera;

    public Player(OrthographicCamera camera) {
        this.camera = camera;
        touchPosition = new Vector3();
        mousePosition = new Vector3();
    }

    public void update(double delta) {
        mousePosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePosition);
        isPlay = false;
        if (Gdx.input.justTouched()) {
            touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPosition);
            isPlay = true;
        }
    }

    public void addPiece() {
        currentNumberOfPieces++;
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
        return isPlay;
    }

    public boolean maxNumberPlayed() {
        return currentNumberOfPieces >= NUMBER_OF_PLAYER_PIECES;
    }

}
