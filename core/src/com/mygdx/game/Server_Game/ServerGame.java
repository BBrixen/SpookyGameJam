package com.mygdx.game.Server_Game;

import com.mygdx.game.Map.Map;
import com.mygdx.game.Networking.Server_Data.NetworkData;
import com.mygdx.game.Networking.Server_Data.ServerNetworker;

import java.io.IOException;

public class ServerGame {

    public GameData gameData;
    private ServerNetworker server;
    private boolean multiplayer;
    private Map map;

    public ServerGame(ServerNetworker server, int maxPlayers, boolean multiplayer) {
        gameData = new GameData(maxPlayers);
        this.server = server;
        this.multiplayer = multiplayer;
    }

    public void playerJoins(Player player) {
        this.gameData.players.add(player);

        if (this.gameData.players.size() == this.gameData.maxPlayers) {
            System.out.println("all clients connected");

            // make map here
            map = new Map();
            map.virus(1f,0.00001f,'f');
            map.rockSummon(20);

            if (multiplayer) { // send out data to all clients
                server.continuallyRecieveData();
                server.addToQueueAndSend(new NetworkData(gameData));
            }
            // begin the game
            mainLoop();
        } else {
            server.addToQueueAndSend(new NetworkData(gameData));
        }
    }

    public void mainLoop() {
        System.out.println("beginning the game");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    // game stuff here
                }
            }
        });
        thread.start();
    }
}
