package com.mygdx.game.Entities.GameEntities;

import com.mygdx.game.Map.Map;
import com.mygdx.game.Server_Game.CollisionHandler;

import java.io.Serializable;

public abstract class Entity implements Serializable {

    protected float x, y;
    protected float speedX, speedY;

    public Entity() {
        this.x = 0;
        this.y = 0;
        this.speedX = 0;
        this.speedY = 0;
    }

    public void update(float dTime) {
        CollisionHandler.handleCollisions(dTime, this); // updates x and y values
    }

    public void updateServer(float dTime, Map map) {
        CollisionHandler.handleCollisionsServer(dTime, this, map);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

}
