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
import com.mygdx.game.Map.Map;
import com.mygdx.game.Networking.Client_Side.ClientNetworker;
import com.mygdx.game.Server_Game.GameData;
import com.mygdx.game.Server_Game.InputHandler;
import com.mygdx.game.Server_Game.Player;
import com.mygdx.game.Server_Game.ServerGame;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import com.mygdx.game.Entities.Textures;

public class ClientGame extends ApplicationAdapter {
	// the display for the user
	// inputs will be sent to the server for processing
	// the server then sends new game information to be displayed

	// rendering stuff
	private SpriteBatch batch;
	private Sprite backgroundSprite;
	private OrthographicCamera camera;
	private Stage stage;
	private GameCharacter character;
	private HashMap<Integer, GameCharacter> playerToSprite = new HashMap<>();
	private Music nightMusic;
	private float w, h;
	private Player thisPlayer;
	private Map map;

	// multiplayer stuff
	private final boolean multiplayer = false;
	private ClientNetworker clientNetworker;
	private GameData currentGameData;
	private int pID = -1;
	private int previousLength = -1;
	
	@Override
	public void create () {
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

		if (multiplayer) {
			// creating network connection
			try {
				clientNetworker = new ClientNetworker(this);
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			ServerGame game = new ServerGame(null, 1, false);
			currentGameData = game.gameData;
			game.playerJoins(new Player(0));
			this.map = game.map;
		}
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
			if (curPlayer.getId() == pID) continue; // dont affect current player bc the server gets all confused
			GameCharacter sprite = playerToSprite.get(curPlayer.getId());
			sprite.setSpeedX(curPlayer.getSpeedX());
			sprite.setSpeedY(curPlayer.getSpeedY());
			if (curPlayer.getSpeedX() == 0 && curPlayer.getSpeedY() == 0) {
				sprite.setPositionX(curPlayer.getX());
				sprite.setPositionY(curPlayer.getY());
			}

			playerToSprite.replace(curPlayer.getId(), sprite);
		}
	}

	@Override
	public void render () {
		// pre-render interpretation
		interpretGameData();

		// reseting background
		ScreenUtils.clear(90/255f, 230/255f, 80/255f, 1);

		// handling inputs
		InputHandler.handleKeyDown(thisPlayer, clientNetworker, currentGameData,
					multiplayer, character);

		// rendering stuff
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		renderMap();
		stage.act(Gdx.graphics.getDeltaTime());
		batch.end();
		stage.draw();
	}

	public void renderMap() {
		if (this.map == null) return;

		int row = (int) (character.getPositionY()/64) + (this.map.size/2);
		int col = (int) (character.getPositionX()/64) + (this.map.size/2);

		for (int r = row + 20; r > row - 20 && row > 0; r --) {
			if (r >= this.map.size) continue;
			float y = (r - this.map.size/2) * 64;
			for (int c = col - 20; c < col + 20 && c < this.map.size; c ++) {
				if (c < 0) continue;
				char type = this.map.SML.get(r).get(c);
				float x = (c - this.map.size/2) * 64;

				Texture tile = Textures.dirt; // the default for now
				if (type == 'g') tile = Textures.grass;
				if (type == 'f') tile = Textures.tree;
				if (type == 'F') tile = Textures.tree;
				if (type == 't') tile = Textures.tree2;
				if (type == 'T') tile = Textures.tree2;
				if (type == 'c') tile = Textures.cobble;

				Sprite s = new Sprite(tile);
				s.setPosition(x, y);

				if (type == 'f' || type == 't' || type == 'T' || type == 'F') { // special stuff needed for trees
					s.setScale(3);
					s.setPosition(x+16, y+64);
					if (type == 'F' || type == 'T') s.flip(true, false);
					if (type == 't' || type == 'T') s.setScale(2);

					// tree flooring
					Sprite treeFlooring = new Sprite(Textures.grass); // might wanna change this later on to match the sourrounding terrain
					treeFlooring.setPosition(x, y);
					treeFlooring.draw(batch);

				}

				s.draw(batch);
			}
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		nightMusic.dispose();
	}

	public void updateGameData(GameData gameData) {
		this.currentGameData = gameData;
		if (this.map == null) this.map = new Map(gameData.seed);
	}
}
