package com.groupg.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class MyGame extends ApplicationAdapter {
	public static final int BOARD_HEIGHT = 542;
	public static final int BOARD_WIDTH = 782;

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture boardImg;
	private Game game;

	@Override
	public void create () {
		batch = new SpriteBatch();
		boardImg = new Texture("Board_2.png");
		camera = new OrthographicCamera();
		camera.setToOrtho(false, BOARD_WIDTH, BOARD_HEIGHT);
		game = new Game(this);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.update(Gdx.graphics.getDeltaTime());
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(boardImg, 0, 0);
		game.render(Gdx.graphics.getDeltaTime(), batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		boardImg.dispose();
		game.dispose();
	}

	public OrthographicCamera getCamera() {
		return camera;
	}
}
