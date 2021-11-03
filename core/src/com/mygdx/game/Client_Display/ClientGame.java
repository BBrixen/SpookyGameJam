package com.mygdx.game.Client_Display;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Entities.GameCharacter;
import com.mygdx.game.Networking.Client_Side.ClientNetworker;
import com.mygdx.game.Server_Game.GameData;
import com.mygdx.game.Server_Game.InputHandler;
import com.mygdx.game.Server_Game.Player;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ClientGame extends ApplicationAdapter {
	// the display for the user
	// inputs will be sent to the server for processing
	// the server then sends new game information to be displayed

	// rendering stuff
	private SpriteBatch batch;
	private Texture background;
	private Sprite backgroundSprite;
	private OrthographicCamera camera;
	private Stage stage;
	private GameCharacter character;
	private HashMap<Integer, GameCharacter> playerToSprite = new HashMap<>();
	private Music nightMusic;
	private float w, h;
	private Player thisPlayer;

	// multiplayer stuff
	private boolean multiplayer = false;
	private ClientNetworker clientNetworker;
	private GameData currentGameData;
	private int pID = -1;
	private int previousLength = -1;
	
	@Override
	public void create () {
		if (multiplayer) {
			// creating network connection
			try {
				clientNetworker = new ClientNetworker(this);
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
				return;
			}
		} else {
			currentGameData = new GameData(1);
			currentGameData.players.add(new Player(0));
		}

		// making camera
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(w, h);

		// setting view
		FitViewport viewport = new FitViewport(w, h, camera);
		batch = new SpriteBatch();
		stage = new Stage(viewport, batch);

		// adding character sprites
		character = new GameCharacter(camera);
		stage.addActor(character);

		// loading sound
		nightMusic = Gdx.audio.newMusic(Gdx.files.internal("night.mp3"));
		nightMusic.setLooping(true);
		nightMusic.setVolume(0.2f);
		// TURNED OFF FOR TESTING
//		nightMusic.play();

		// background
		background = new Texture(Gdx.files.internal("badlogic.jpg"));
		backgroundSprite = new Sprite(background);
	}

	public void interpretGameData() {
		if (this.currentGameData == null) return;
		List<Player> players = currentGameData.players;
		int numPlayers = players.size();

		if (pID == -1) pID = numPlayers - 1;
		thisPlayer = players.get(pID);

		if (! this.currentGameData.allPlayersConnected()) return;

		if (previousLength != numPlayers) {
			previousLength = numPlayers;
			// there has been a new player added, time to add things to the sprite list
			playerToSprite = new HashMap<>();
			for (Player player : players) {
				if (player.getId() == pID) {
					playerToSprite.put(pID, character);
				} else {
					GameCharacter otherCharacter = new GameCharacter();
					playerToSprite.put(player.getId(), otherCharacter);
					stage.addActor(otherCharacter);
				}
			}
		}

		// update the position of each sprite
		for (Player curPlayer : players) {
			GameCharacter sprite = playerToSprite.get(curPlayer.getId());
			sprite.setSpeedX(curPlayer.getSpeedX());
			sprite.setSpeedY(curPlayer.getSpeedY());

			playerToSprite.replace(curPlayer.getId(), sprite);
		}
	}

	@Override
	public void render () {
		// pre-render interpretation
		interpretGameData();

		// reseting background
		ScreenUtils.clear(90/255f, 230/255f, 80/255f, 1);

		if (this.currentGameData != null && this.currentGameData.allPlayersConnected())
			// handling inputs
			InputHandler.handleKeyDown(thisPlayer,
					clientNetworker, currentGameData, multiplayer);

		// rendering stuff
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, 0,0, w, h);
		stage.act(Gdx.graphics.getDeltaTime());
		batch.end();
		stage.draw();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		nightMusic.dispose();
	}

	public void updateGameData(GameData gameData) {
		this.currentGameData = gameData;
	}

}
