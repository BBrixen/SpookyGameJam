package com.mygdx.game.Entities.GameEntities;

import java.io.Serializable;

public class Player extends Entity implements Serializable {

    private int pID;
    private float health, maxHealth;

    public Player(int id) {
        super("player", id);
        this.pID = id;
        this.setDefaultSpeed(120f);
        this.health = maxHealth = 200.0f;
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

    public float getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void takeDamage(float damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }
}
