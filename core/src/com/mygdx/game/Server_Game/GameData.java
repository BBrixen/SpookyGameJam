package com.mygdx.game.Server_Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameData implements Serializable {

    public List<Player> players;
    public int maxPlayers;
    public long seed;

    public GameData(int maxPlayers) {
        this.maxPlayers = maxPlayers;
        this.players = new ArrayList<>();
        this.seed = System.currentTimeMillis();
    }

    public boolean allPlayersConnected() {
        return players.size() == maxPlayers;
    }
}
