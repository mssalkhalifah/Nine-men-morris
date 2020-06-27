package com.groupg.game.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.groupg.game.board.Board;

public class Point extends GameObject {
    private Rectangle highlight;
    private boolean isMouseOver;
    private Board gameBoard;

    public Point(Board board, Vector3 position, int pointNumber) {
        super(new Texture(Gdx.files.internal("Highlight.png")), position, pointNumber);
        gameBoard = board;
        highlight = new Rectangle();
        highlight.setHeight(4f);
        highlight.setWidth(4f);
        highlight.setPosition(position.x - 4 / 2, position.y - 4 / 2);
        sprite.setPosition(highlight.getX(), highlight.getY());
    }

    @Override
    public void update(double delta, Vector3 vector3) {
        if (highlight.x <= vector3.x && highlight.x + highlight.width >= vector3.x && highlight.y <= vector3.y && highlight.y + highlight.height >= vector3.y) {
            isMouseOver = true;
            if (Gdx.input.isTouched()) {
                isMouseClicked = true;
            }
        } else {
            isMouseOver = false;
        }
    }

    @Override
    public void render(double delta, SpriteBatch batch) {
        if (isMouseOver) {
            sprite.draw(batch);
        }
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
