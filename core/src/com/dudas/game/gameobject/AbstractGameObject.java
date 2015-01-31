package com.dudas.game.gameobject;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by OLO on 24. 1. 2015
 */
public abstract class AbstractGameObject {

    public Vector2 position;
    public Vector2 dimension;
    public Vector2 origin;
    public Vector2 scale;
    public float rotation;

    public AbstractGameObject(float positionX, float positionY) {
        position = new Vector2(positionX, positionY);
        dimension = new Vector2(1, 1);
        origin = new Vector2(dimension.x / 2, dimension.y / 2);
        scale = new Vector2(1, 1);
        rotation = 0;
    }

}
