package com.mygdx.game.Entities.GameEntities;

import java.io.Serializable;

public class Player extends Entity implements Serializable {

    private int pID;

    public Player(int id) {
        super("player", id);
        this.pID = id;
        this.setDefaultSpeed(120f);
    }

    public int getId() {
        return pID;
    }

    public void setId(int pID) {
        this.pID = pID;
    }

}
