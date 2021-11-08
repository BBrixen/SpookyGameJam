package com.mygdx.game.Networking.Server_Data;

import com.mygdx.game.Map.Map;
import com.mygdx.game.Networking.Client_Side.ClientNetworker;
import com.mygdx.game.Server_Game.GameData;

import java.io.Serializable;

public class NetworkData implements Serializable {

    private GameData gameData;
    private Map map; // this is not always used

    public NetworkData(GameData data) {
        this.gameData = data;
    }

    public NetworkData(GameData data, Map map) {
        this.gameData = data;
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
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


