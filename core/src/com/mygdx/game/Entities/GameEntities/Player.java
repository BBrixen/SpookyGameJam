package com.mygdx.game.Entities.GameEntities;

import java.io.Serializable;

public class Player extends Entity implements Serializable {

    private final int pID;

    public Player(int id) {
        super();
        this.pID = id;
    }

    public int getId() {
        return pID;
    }
}
