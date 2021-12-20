package com.mygdx.game.Server_Game;

import com.mygdx.game.Entities.GameEntities.Enemies.Cucumber;
import com.mygdx.game.Entities.GameEntities.Enemies.Enemy;
import com.mygdx.game.Entities.GameEntities.Entity;
import com.mygdx.game.Entities.GameEntities.Player;
import com.mygdx.game.Map.Map;
import com.mygdx.game.Networking.Server_Data.ServerNetworker;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerGame {

    public GameData gameData;
    private final ServerNetworker server;
    private final boolean multiplayer;
    private Random random;
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
            random = new Random(gameData.seed);

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
                // -------------------------------------
                // updating the current state of the game
                dTime[0] = (System.currentTimeMillis() - lastTime[0]) / 1000f;
                lastTime[0] = System.currentTimeMillis();

                for (Player p : gameData.players) {
                    p.updateServer(dTime[0], map);
                }

                for (Entity entity : gameData.entities) {
                    entity.updateServer(dTime[0], map);
                }

                handleDamage();
                // done updating basic things
                // -------------------------------------

                // spawning enemies randomly
                if (tick % 100 == 0) {
                    Cucumber cucumber = new Cucumber(tick + gameData.maxPlayers + 1);
                    cucumber.setX((random.nextFloat()-0.5f)*200);
                    cucumber.setY((random.nextFloat()-0.5f)*200);
                    gameData.entities.add(cucumber);
                }

                // increase tick counter at end of cycle, this keeps track of time in the game
                tick ++;
            }
        }, 0, 10, TimeUnit.MILLISECONDS);
    }

    private void handleDamage() {
        for (Entity entity : gameData.entities) {
            if (entity.getDamage() == 0) continue;
            if (entity instanceof Enemy) {
                Enemy enemy = (Enemy) entity; // cast it to an enemy so we can access its range

                for (Player p : gameData.players) {
                    float dist = Math.abs(p.getX() - enemy.getX()) + Math.abs(p.getY() - enemy.getY());
                    if (dist <= enemy.getRange()) {
                        // player is within range, inflict damage
                        p.takeDamage(enemy.getDamage());
                        // TODO possibly add some boolean so that a player does not take damage from
                        //  multiple enemies in the same turn, because that can just insta kill someone
                    }
                }

            }
        }
    }
}
