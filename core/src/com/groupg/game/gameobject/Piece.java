package com.groupg.game.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Piece extends GameObject {
    private PieceColor pieceColor;

    public Piece(PieceColor pieceColor, Vector3 position, int pointNumber) {
        super((pieceColor == PieceColor.BLUE)
                ? new Texture(Gdx.files.internal("Player.png"))
                : new Texture(Gdx.files.internal("Enemy.png")),
                position, pointNumber);

        this.pieceColor = pieceColor;
        sprite.setPosition(position.x - 6 / 2, position.y - 6 / 2);
    }

    @Override
    public void update(double delta, Vector3 vector3) {

    }

    @Override
    public void render(double delta, SpriteBatch batch) {
        sprite.draw(batch);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
