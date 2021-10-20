package com.mygdx.game.Server_Game;

import com.mygdx.game.Networking.Server_Data.NetworkData;
import com.mygdx.game.Networking.Server_Data.ServerNetworker;

public class ServerGame {

    private GameData gameData;
    private ServerNetworker server;

    public ServerGame(ServerNetworker server, int maxPlayers) {
        gameData = new GameData(maxPlayers);
        this.server = server;
    }

    public void playerJoins(Player player) {
        this.gameData.players.add(player);

        if (this.gameData.players.size() == this.gameData.maxPlayers) {
            System.out.println("all clients connected");
            server.continuallyRecieveData();
            // send out data to all clients
            server.addToQueueAndSend(new NetworkData(gameData));
            // begin the game
            mainLoop();
        } else {
            server.addToQueueAndSend(new NetworkData(gameData));
        }
    }

    public void mainLoop() {
        System.out.println("beginning the game");
        while (true) {
            // do nothing
        }
    }
}
