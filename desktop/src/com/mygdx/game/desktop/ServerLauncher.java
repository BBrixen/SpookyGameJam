package com.mygdx.game.desktop;

import com.mygdx.game.Networking.Server_Data.ServerNetworker;

import java.io.IOException;

public class ServerLauncher {

    public static void main(String[] args) {
        int players = 2;
        try {
            ServerNetworker server = new ServerNetworker(players);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
