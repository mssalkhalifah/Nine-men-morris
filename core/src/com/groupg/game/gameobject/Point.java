package com.groupg.game.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.groupg.game.board.Board;

public class Point {
    public static final int WIDTH = 24;
    public static final int HEIGHT = WIDTH;

    private Rectangle highlightRectangle;
    private Texture highlightTexture;
    private Sprite highlightSprite;
    private Board gameBoard;
    private int pointNumber;
    private boolean isMouseOver;

    public Point(Vector3 position, Board gameBoard,int pointNumber) {
        this.gameBoard = gameBoard;
        this.pointNumber = pointNumber;

        highlightRectangle = new Rectangle();
        highlightRectangle.setSize(WIDTH, HEIGHT);
        highlightRectangle.setPosition(position.x - WIDTH / 2, position.y - (HEIGHT + 15) / 2);

        highlightTexture = new Texture(Gdx.files.internal("Arrow.png"));

        highlightSprite = new Sprite(highlightTexture);
        highlightSprite.setPosition(position.x - highlightSprite.getWidth() / 2,
                position.y - highlightSprite.getHeight() / 2);
    }

    public void update(double delta, Vector3 position) {
        if (gameBoard.isEmptyPoint(pointNumber) && highlightRectangle.x <= position.x
                && highlightRectangle.x + highlightRectangle.width >= position.x
                && highlightRectangle.y <= position.y
                && highlightRectangle.y + highlightRectangle.height >= position.y) {
            isMouseOver = true;
        } else {
            isMouseOver = false;
        }
    }

    public void render(double delta, SpriteBatch batch) {
        if (isMouseOver) {
            highlightSprite.draw(batch);
        }
    }

    public void dispose() {
        highlightTexture.dispose();
    }

    public boolean isCollide(Vector3 collidePosition) {
        return highlightRectangle.x <= collidePosition.x
                && highlightRectangle.x + highlightRectangle.width >= collidePosition.x
                && highlightRectangle.y <= collidePosition.y
                && highlightRectangle.y + highlightRectangle.height >= collidePosition.y;
    }

    public int getPointNumber() {
        return pointNumber;
    }
}
