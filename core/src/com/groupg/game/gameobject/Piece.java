package com.groupg.game.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Piece {
    private final float moveSpeed = 500f;

    private Vector3 pieceCurrentPosition;
    private Vector3 nextPosition;
    private Vector3 direction;
    private Texture pieceTexture;
    private Texture selectTexture;
    private Sprite pieceSprite;
    private Sprite selectSprite;
    private PieceColor pieceColor;
    private int pieceNumber;
    private boolean isMouseOver;
    private boolean isSelected;
    private boolean isMoving;

    public Piece(Vector3 position, PieceColor pieceColor, int pieceNumber) {
        this.pieceColor = pieceColor;
        this.pieceNumber = pieceNumber;

        pieceTexture = new Texture(Gdx.files.internal((pieceColor == PieceColor.WHITE) ? "White_Piece.png" : "Black_Piece.png"));
        selectTexture = new Texture(Gdx.files.internal("Select_2.png"));

        pieceSprite = new Sprite(pieceTexture);
        pieceSprite.setPosition(position.x - pieceSprite.getWidth() / 2, position.y - (pieceSprite.getHeight() + 24) / 2);
        selectSprite = new Sprite(selectTexture);
        selectSprite.setPosition(position.x - selectSprite.getWidth() / 2, position.y - (selectSprite.getHeight() + 25) / 2);

        pieceCurrentPosition = new Vector3(position.x, position.y, 0);
    }

    public void update(float delta, Vector3 position) {
        if (isMoving) updatePosition(delta);
        if (isCollide(position)) isMouseOver = true; else isMouseOver = false;
    }

    public void render(float delta, SpriteBatch batch) {
        pieceSprite.draw(batch);
        if (isMouseOver || isSelected) {
            selectSprite.draw(batch);
        }
    }

    public void dispose() {
        selectTexture.dispose();
        pieceTexture.dispose();
    }

    public void setNextPosition(Vector3 nextPosition) {
        this.nextPosition = nextPosition;
    }

    public void setMoving(boolean moving) {
        direction = Direction.getDirection(pieceCurrentPosition, nextPosition);
        isMoving = moving;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public PieceColor getPieceColor() {
        return pieceColor;
    }

    public boolean isCollide(Vector3 collidePosition) {
        return pieceSprite.getX() <= collidePosition.x
                && pieceSprite.getX() + pieceSprite.getWidth() >= collidePosition.x
                && pieceSprite.getY() <= collidePosition.y
                && pieceSprite.getY() + pieceSprite.getHeight() >= collidePosition.y;
    }

    public int getPieceNumber() {
        return pieceNumber;
    }

    public void setPieceNumber(int pieceNumber) {
        this.pieceNumber = pieceNumber;
    }

    private void updatePosition(float delta) {
        //System.out.println(pieceCurrentPosition + "->" + nextPosition);

        if (reachedNextPosition(pieceCurrentPosition, nextPosition)) {
            pieceCurrentPosition.set(nextPosition.x, nextPosition.y, 0);
            pieceSprite.setPosition(pieceCurrentPosition.x - pieceSprite.getWidth() / 2, pieceCurrentPosition.y - (pieceSprite.getHeight() + 24) / 2);
            selectSprite.setPosition(pieceCurrentPosition.x - selectSprite.getWidth() / 2, pieceCurrentPosition.y - (selectSprite.getHeight() + 25) / 2);
            isMoving = false;
        } else {
            pieceCurrentPosition.x += ((moveSpeed * direction.x) * delta);
            pieceCurrentPosition.y += ((moveSpeed * direction.y) * delta);
            pieceSprite.setPosition(pieceCurrentPosition.x - pieceSprite.getWidth() / 2, pieceCurrentPosition.y - (pieceSprite.getHeight() + 24) / 2);
            selectSprite.setPosition(pieceCurrentPosition.x - selectSprite.getWidth() / 2, pieceCurrentPosition.y - (selectSprite.getHeight() + 25) / 2);
        }
    }

    // Check if the sprite has reached to the new position
    private boolean reachedNextPosition(Vector3 pieceCurrentPosition, Vector3 nextPosition) {
        if (Direction.up(direction)) return pieceCurrentPosition.y <= nextPosition.y;
        if (Direction.down(direction)) return pieceCurrentPosition.y >= nextPosition.y;
        if (Direction.left(direction)) return pieceCurrentPosition.x <= nextPosition.x;
        if (Direction.right(direction)) return pieceCurrentPosition.x >= nextPosition.x;

        throw new IllegalArgumentException("Piece next direction not found");
    }
}
