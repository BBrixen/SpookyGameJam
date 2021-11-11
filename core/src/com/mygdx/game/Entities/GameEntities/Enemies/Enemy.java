package com.mygdx.game.Entities.GameEntities.Enemies;

import com.mygdx.game.Entities.GameEntities.Entity;

import java.io.Serializable;

public abstract class Enemy extends Entity implements Serializable {

    protected int health, money; // money for if they drop gold and we add shops
    protected float speed, range;
    //range can be used for both melee and ranged attacks


}
