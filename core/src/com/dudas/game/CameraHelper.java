package com.dudas.game;

import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by OLO on 24. 1. 2015
 */
public class CameraHelper {
    private OrthographicCamera camera;

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
