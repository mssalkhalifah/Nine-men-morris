package com.groupg.game.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Piece {
    private Vector3 position;
    private Texture pieceTexture;
    private Texture selectTexture;
    private Sprite pieceSprite;
    private Sprite selectSprite;
    private PieceColor pieceColor;
    private int pieceNumber;

    public Piece(Vector3 position, PieceColor pieceColor, int pieceNumber) {
        this.position = position;
        this.pieceColor = pieceColor;
        this.pieceNumber = pieceNumber;

        pieceTexture = new Texture(Gdx.files.internal((pieceColor == PieceColor.WHITE) ? "White_Piece.png" : "Black_Piece.png"));
        selectTexture = new Texture(Gdx.files.internal("Select_2.png"));

        pieceSprite = new Sprite(pieceTexture);
        pieceSprite.setPosition(position.x - pieceSprite.getWidth() / 2, position.y - (pieceSprite.getHeight() + 24) / 2);
        selectSprite = new Sprite(selectTexture);
        selectSprite.setPosition(position.x - selectSprite.getWidth() / 2, position.y - selectSprite.getHeight() / 2);
    }

    public void update(double delta, Vector3 vector3) {

    }

    public void render(double delta, SpriteBatch batch) {
        pieceSprite.draw(batch);
    }

    public void dispose() {
        selectTexture.dispose();
        pieceTexture.dispose();
    }

    public int getPieceNumber() {
        return pieceNumber;
    }
}
