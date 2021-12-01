package com.mygdx.game.Server_Game;

import com.mygdx.game.Entities.GameEntities.Enemies.Cucumber;
import com.mygdx.game.Entities.GameEntities.Entity;
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
    private int tick;

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
            map = new Map(gameData.seed, multiplayer);
            // we pass the state of multiplayer bc if its false then
            // we want to fun it as a client (when we would normally pass false)

            if (multiplayer) { // send out data to all clients
                server.continuallyRecieveData();
            }
            // begin the game
            mainLoop();
        }
    }

    public void mainLoop() {
        System.out.println("beginning the game");
        tick = 0;
        final long[] lastTime = {System.currentTimeMillis()};
        final float[] dTime = {System.currentTimeMillis()};
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                dTime[0] = (System.currentTimeMillis() - lastTime[0]) / 1000f;
                lastTime[0] = System.currentTimeMillis();

                for (Player p : gameData.players) {
                    p.updateServer(dTime[0], map);
                }

                for (Entity entity : gameData.entities) {
                    entity.updateServer(dTime[0], map);
                }

                if (tick % 100 == 0) {
                    Cucumber cucumber = new Cucumber(tick + gameData.maxPlayers + 1);
                    cucumber.setSpeedY(cucumber.getDefaultSpeed());
                    gameData.entities.add(cucumber);
                }

                tick ++;
            }
        }, 0, 10, TimeUnit.MILLISECONDS);
    }
}
