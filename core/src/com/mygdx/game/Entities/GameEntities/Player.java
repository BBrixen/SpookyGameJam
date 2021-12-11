package com.mygdx.game.Entities.GameEntities;

import java.io.Serializable;

public class Player extends Entity implements Serializable {

    private int pID;
    private float health;

    public Player(int id) {
        super("player", id);
        this.pID = id;
        this.setDefaultSpeed(120f);
        this.health = 100.0f;
    }

    public int getId() {
        return pID;
    }

    public void setId(int pID) {
        this.pID = pID;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }
}
