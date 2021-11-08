package com.mygdx.game.Networking.Server_Data;

import com.mygdx.game.Server_Game.GameData;
import java.io.Serializable;

public class NetworkData implements Serializable {

    private GameData gameData;

    public NetworkData(GameData data) {
        this.gameData = data;
    }

    public GameData getGameData() {
        return gameData;
    }

    public void setGameData(GameData data) {
        this.gameData = data;
    }

    @Override
    public String toString() {
        return "" + gameData;
    }
}


