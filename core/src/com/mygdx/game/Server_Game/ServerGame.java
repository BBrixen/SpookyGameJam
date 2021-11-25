package com.mygdx.game.Server_Game;

import com.mygdx.game.Entities.GameEntities.Player;
import com.mygdx.game.Map.Map;
import com.mygdx.game.Networking.Server_Data.ServerNetworker;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerGame {

    public GameData gameData;
    private final ServerNetworker server;
    private final boolean multiplayer;
    public Map map;

    public ServerGame(ServerNetworker server, int maxPlayers, boolean multiplayer) {
        gameData = new GameData(maxPlayers);
        this.server = server;
        this.multiplayer = multiplayer;
    }

    public void playerJoins(Player player) {
        this.gameData.players.add(player);
        if (multiplayer) server.continuallySendData();

        if (this.gameData.players.size() == this.gameData.maxPlayers) {
            System.out.println("all clients connected");

            // make map here
            map = new Map(gameData.seed, true);

            if (multiplayer) { // send out data to all clients
                server.continuallyRecieveData();
            }
            // begin the game
            mainLoop();
        }
    }

    public void mainLoop() {
        System.out.println("beginning the game");
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // this is where the server will keep track of all the enemies and move them

            }
        }, 0, 10, TimeUnit.MILLISECONDS);
    }
}
