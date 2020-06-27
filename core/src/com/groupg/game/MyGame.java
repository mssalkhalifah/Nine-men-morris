package com.groupg.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class MyGame extends ApplicationAdapter {
	public static final int BOARD_HEIGHT = 112;
	public static final int BOARD_WIDTH = BOARD_HEIGHT;

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture boardImg;
	private Game game;

	@Override
	public void create () {
		batch = new SpriteBatch();
		boardImg = new Texture("Board.png");
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 112, 112);
		game = new Game();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		update();
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

	private void update() {
		Vector3 mousePosition = new Vector3();
		mousePosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(mousePosition);
		game.update(Gdx.graphics.getDeltaTime(), mousePosition);
	}
}
