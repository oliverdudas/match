package com.dudas.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.dudas.game.gameobject.board.Board;

/**
 * Created by OLO on 24. 1. 2015
 */
public class GameRenderer implements Disposable {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private GameController gameController;
    private BoardRenderer boardRenderer;
    private ShaderProgram shaderMonochrome;
    private ShaderProgram shaderBarel;

    public GameRenderer(GameController gameController) {
        this.gameController = gameController;
        this.boardRenderer = new BoardRenderer();
        init();
    }

    private void init() {
//		float w = Gdx.graphics.getWidth();
//		float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(9, 9);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        batch = new SpriteBatch();
//        initTestShaders();
    }

    private void initTestShaders() {
        ShaderProgram.pedantic = false;
        shaderMonochrome = new ShaderProgram(
                Gdx.files.internal("shaders/monochrome.vs"),
                Gdx.files.internal("shaders/monochrome.fs")
        );

        shaderBarel = new ShaderProgram(
                Gdx.files.internal("shaders/barel.vs"),
                Gdx.files.internal("shaders/barel.fs")
        );

        if (!shaderMonochrome.isCompiled()) {
            String msg = "Could not compile shader program: " + shaderMonochrome.getLog();
            throw new GdxRuntimeException(msg);
        }

        if (!shaderBarel.isCompiled()) {
            String msg = "Could not compile shader program: " + shaderBarel.getLog();
            throw new GdxRuntimeException(msg);
        }
    }

    public void render() {
        renderGame(batch);
    }

    private void renderGame(SpriteBatch batch) {
        gameController.cameraHelper.setCamera(camera);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

//		Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
//        batch.setShader(shaderMonochrome);
//        shaderMonochrome.setUniformf("u_amount", 1.0f);
//        batch.setShader(shaderBarel);
//        shaderBarel.setUniformf("u_projTrans", 1.0f);
//        gameController.board.render(batch);
        boardRenderer.renderBoard(batch, gameController.board);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        boardRenderer.dispose();
//        shaderMonochrome.dispose();
    }
}
