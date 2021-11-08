package com.mygdx.game.Server_Game;

import com.mygdx.game.Map.Map;
import com.mygdx.game.Networking.Server_Data.NetworkData;
import com.mygdx.game.Networking.Server_Data.ServerNetworker;

import java.io.IOException;


public class ServerGame {

    public GameData gameData;
    private ServerNetworker server;
    private boolean multiplayer;
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

            this.map = new Map();
            if (multiplayer) { // send out data to all clients
                try {
                    if (server.isSending) Thread.sleep(20);
                    System.out.println("about to send out the map");
//                    server.sendDataToOtherClients(new NetworkData(this.gameData, this.map));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("sent the map");
                server.continuallyRecieveData();
            }
            // begin the game
            mainLoop();
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
