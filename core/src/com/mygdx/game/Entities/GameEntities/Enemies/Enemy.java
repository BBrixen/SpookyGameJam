package com.mygdx.game.Entities.GameEntities.Enemies;

import com.mygdx.game.Entities.GameEntities.Entity;

import java.io.Serializable;

public abstract class Enemy extends Entity implements Serializable {

    protected int health, money; // money for if they drop gold and we add shops
    protected float speed, range;
    //range can be used for both melee and ranged attacks


    public Enemy(String type, int id, int health, int money, float speed, float range) {
        super(type, 0);
        this.health = health;
        this.money = money;
        this.speed = speed;
        this.range = range;
    }

    public Enemy(String type, int id, int health, int money, float range) {
        super(type, id);
        this.health = health;
        this.money = money;
        this.range = range;
        this.speed = defaultSpeed;
    }

    public abstract void move(); // this will set the speeds for the enemy, not actually changing its x and y
    public abstract void attack(); // determine if it will attack or not, maybe return boolean?
}
