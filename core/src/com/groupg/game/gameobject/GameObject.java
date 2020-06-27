package com.groupg.game.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class GameObject {
    protected Vector3 position;
    protected Texture texture;
    protected Sprite sprite;
    protected int pointNumber;
    protected boolean isMouseClicked;

    public GameObject(Texture texture, Vector3 position, int pointNumber) {
        this.texture = texture;
        this.position = position;
        this.pointNumber = pointNumber;
        sprite = new Sprite(this.texture);
    }

    public abstract void update(double delta, Vector3 vector3);

    public abstract void render(double delta, SpriteBatch batch);

    public abstract void dispose();

    public void setMouseClickedFalse() {
        isMouseClicked = false;
    }

    public boolean isMouseClicked() {
        return isMouseClicked;
    }

    public int getPointNumber() {
        return pointNumber;
    }
}
