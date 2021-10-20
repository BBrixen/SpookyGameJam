package com.mygdx.game.Networking.Server_Data;

import com.mygdx.game.Networking.Client_Side.ClientNetworker;
import com.mygdx.game.Server_Game.GameData;

import java.io.Serializable;

public class NetworkData implements Serializable {

    private GameData data;

    public NetworkData(GameData data) {
        this.data = data;
    }

    public GameData getData() {
        return data;
    }

    public void setData(GameData data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "" + data;
    }
}


