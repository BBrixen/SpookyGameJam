package com.mygdx.game.Client_Display;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Entities.RenderingEntities.Textures;
import com.mygdx.game.Map.Tiles.Tile;
import com.mygdx.game.Networking.Client_Side.ClientNetworker;
import com.mygdx.game.Entities.RenderingEntities.PlayerCharacter;
import com.mygdx.game.Entities.GameEntities.Player;
import com.mygdx.game.Server_Game.GameData;
import com.mygdx.game.Server_Game.InputHandler;
import com.mygdx.game.Server_Game.ServerGame;
import com.mygdx.game.Map.Map;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ClientGame extends ApplicationAdapter {
	// the display for the user
	// inputs will be sent to the server for processing
	// the server then sends new game information to be displayed

	// rendering stuff
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Stage stage;
	private PlayerCharacter character;
	private HashMap<Integer, PlayerCharacter> playerToSprite = new HashMap<>();
	private Music nightMusic;
	private Player thisPlayer;
	public static Map map;

	// multiplayer stuff
	private final boolean multiplayer = false;
	private ClientNetworker clientNetworker;
	private GameData currentGameData;
	private int pID = -1;
	private int previousLength = -1;
	
	@Override
	public void create () {
		Textures.loadTextures(); // we have to load the textures before using any of them inside the map or entities

		// making camera
		float w, h;
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(w, h);
		camera.zoom = 0.5f;

		// setting view
		FitViewport viewport = new FitViewport(w, h, camera);
		batch = new SpriteBatch();
		stage = new Stage(viewport, batch);

		// adding character sprites
		character = new PlayerCharacter(camera);
		stage.addActor(character);

		// loading sound, TURNED OFF FOR TESTING
		// bennett: check dispose() funtion
//		nightMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/night.mp3"));
//		nightMusic.setLooping(true);
//		nightMusic.setVolume(0.2f);
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
			map = game.map;
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
					PlayerCharacter otherCharacter = new PlayerCharacter();
					playerToSprite.put(player.getId(), otherCharacter);
					stage.addActor(otherCharacter);
				}
			}
		}

		// update the position of each sprite
		for (Player curPlayer : players) {
			if (curPlayer.getId() == pID) continue; // dont affect current player bc the server gets all confused
			PlayerCharacter sprite = playerToSprite.get(curPlayer.getId());
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
		float dTime = Gdx.graphics.getDeltaTime();
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
		batch.end();
		stage.act(dTime);
		stage.draw();
	}

	public void renderMap() {
		if (map == null) return;

		int row = Map.playerYToMapRow(character.getPositionY());
		int col = Map.playerXToMapCol(character.getPositionX());

		for (int r = row + 10; r > row - 10 && row > 0; r --) {
			if (r >= Map.size) continue;
			for (int c = col - 10; c < col + 10 && c < Map.size; c ++) {
				if (c < 0) continue;
				Tile tile = map.SML.get(r).get(c);
				tile.render(batch);
			}
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
//		nightMusic.dispose();
	}

	public void updateGameData(GameData gameData) {
		this.currentGameData = gameData;
		if (map == null) map = new Map(gameData.seed, false);
	}
}
