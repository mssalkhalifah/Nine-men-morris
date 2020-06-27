package com.groupg.game.gameobject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class GameObjectManager {
    private Array<GameObject> gameObjects;

    public GameObjectManager(int size) {
        gameObjects = new Array<>(size);
    }

    public void update(double delta, Vector3 vector3) {
        for (GameObject object : gameObjects) {
            object.update(delta, vector3);
        }
    }

    public void render(double delta, SpriteBatch batch) {
        for (GameObject object : gameObjects) {
            object.render(delta, batch);
        }
    }

    public void dispose() {
        for (GameObject object : gameObjects) {
            object.dispose();
        }
    }

    public int getPointClicked() {
        for (GameObject object : gameObjects) {
            if (object.isMouseClicked) {
                object.setMouseClickedFalse();
                assert object.isMouseClicked() == false;
                return object.getPointNumber();
            }
        }

        return -1;
    }

    public void addObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }
}
