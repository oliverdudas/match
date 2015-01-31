package com.dudas.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class MatchGame extends ApplicationAdapter {

    private GameRenderer gameRenderer;
    private GameController gameController;

    @Override
    public void create() {
        gameController = new GameController();
        gameRenderer = new GameRenderer(gameController);
    }

    @Override
    public void render() {

        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        // Update game world by the time that has passed
        // since last rendered frame.
        gameController.update(Gdx.graphics.getDeltaTime());

        // Clears the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Render game world to screen
        gameRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
//		camera.viewportWidth = BOARD_WIDTH;
//		camera.viewportHeight = BOARD_HEIGHT * height / width;
//		camera.update();
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
    }

    @Override
    public void pause() {
    }
}
