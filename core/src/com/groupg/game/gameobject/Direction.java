package com.groupg.game.gameobject;

import com.badlogic.gdx.math.Vector3;

public class Direction {
    public static Vector3 getDirection(Vector3 currentPosition, Vector3 nextPosition) {
        Vector3 direction = new Vector3(0, 0, 0);

        if (currentPosition.x > nextPosition.x) direction.x = -1;
        if (currentPosition.x < nextPosition.x) direction.x = 1;
        if (currentPosition.y > nextPosition.y) direction.y = -1;
        if (currentPosition.y < nextPosition.y) direction.y = 1;

        return direction;
    }

    public static boolean up(Vector3 direction) {
        //System.out.println("up:" + direction);
        return (direction.x == 0 && direction.y < 0);
    }

    public static boolean down(Vector3 direction) {
        //System.out.println("down:" + direction);
        return (direction.x == 0 && direction.y > 0);
    }

    public static boolean left(Vector3 direction) {
        //System.out.println("left:" + direction);
        return (direction.x < 0 && direction.y == 0);
    }

    public static boolean right(Vector3 direction) {
        //System.out.println("right:" + direction);
        return (direction.x > 0 && direction.y == 0);
    }
}
