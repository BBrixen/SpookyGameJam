package com.mygdx.game.Server_Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameData implements Serializable {

    public List<Player> players;
    public int maxPlayers;

    public GameData(int maxPlayers) {
        this.maxPlayers = maxPlayers;
        this.players = new ArrayList<>();
    }

    public boolean allPlayersConnected() {
        return players.size() == maxPlayers;
    }
}
