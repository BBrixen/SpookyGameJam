package com.mygdx.game.Server_Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.Entities.RenderingEntities.PlayerCharacter;
import com.mygdx.game.Entities.GameEntities.Player;
import com.mygdx.game.Networking.Client_Side.ClientNetworker;
import com.mygdx.game.Networking.Server_Data.NetworkData;
import java.io.IOException;
import java.util.HashMap;

public class InputHandler {
    // server side
    // handles the input from an actor

    public static final float diagonalMultiplier =
            (float) Math.sqrt(0.5f);
    private static HashMap<Integer, Boolean> keyPresses = null;

    public static void handleKeyDown(ClientNetworker sender, GameData gameData,
                                     boolean multiplayer, PlayerCharacter character) {
        Player p = (Player) character.getGameEntity();
        // checks and initializing keypresses
        if (p == null) return;
        if (gameData == null || !gameData.allPlayersConnected()) return;
        if (keyPresses == null) initKeyPresses();
        if (!isKeyChange()) return;

        // mouse button input: Gdx.input.isButtonPressed(Input.Buttons.LEFT)
        float defaultSpeed = p.getDefaultSpeed();
        float deltaX = 0, deltaY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.A))
            deltaX = -defaultSpeed;
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            deltaX = defaultSpeed;
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            deltaY = defaultSpeed;
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            deltaY = -defaultSpeed;

        if (deltaY != 0 && deltaX != 0) {
            deltaY *= diagonalMultiplier;
            deltaX *= diagonalMultiplier;
        }

        p.setSpeedX(deltaX);
        p.setSpeedY(deltaY);
        character.setGameEntity(p);
        gameData.players.set(p.getId(), p); // updates the gamedata to include modified player
        if (multiplayer) {
            try {
//                System.out.println(gameData.players.get(0).getSpeedX());
                sender.sendData(new NetworkData(gameData));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void initKeyPresses() {
        keyPresses = new HashMap<>();
        keyPresses.put(Input.Keys.A, false);
        keyPresses.put(Input.Keys.W, false);
        keyPresses.put(Input.Keys.S, false);
        keyPresses.put(Input.Keys.D, false);
        keyPresses.put(Input.Keys.E, false); // E is attack
    }

    public static boolean isKeyChange() {
        boolean change = false;
        for (Integer key : keyPresses.keySet()) {
            if (Gdx.input.isKeyPressed(key)) {
                if (!keyPresses.get(key)) {
                    // they do not match
                    change = true;
                    // updating new keyPresses
                    keyPresses.replace(key, true);
                }
            } else {
                if (keyPresses.get(key)) {
                    // they do not match
                    change = true;
                    keyPresses.replace(key, false);
                }
            }
        }
        return change;
    }


}
