package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Entities.GameCharacter;

public class Game extends ApplicationAdapter {

	SpriteBatch batch;
	OrthographicCamera camera;
	Stage stage;
	GameCharacter character;
	GameCharacter secondary;
	
	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(w, h);

		FitViewport viewport = new FitViewport(w, h, camera);
		batch = new SpriteBatch();
		stage = new Stage(viewport, batch);

		character = new GameCharacter(camera);
		secondary = new GameCharacter();
		stage.addActor(secondary);
		stage.addActor(character);

	}

	@Override
	public void render () {
		// reseting background
		ScreenUtils.clear(90/255f, 230/255f, 80/255f, 1);

		// handling inputs
		InputHandler.handleKeyDown(character);

		// rendering stuff
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		stage.act(Gdx.graphics.getDeltaTime());
		batch.end();
		stage.draw();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

}
