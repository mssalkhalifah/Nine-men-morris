package com.groupg.game.player;

public class Player {
    public static final int NUMBER_OF_PLAYER_PIECES = 9;

    private int numberOfPieces;
    private int currentNumberOfPieces;

    public Player() {
        numberOfPieces = NUMBER_OF_PLAYER_PIECES;
    }

    public boolean maxNumberPlayed() {
        return currentNumberOfPieces++ < NUMBER_OF_PLAYER_PIECES;
    }
}
