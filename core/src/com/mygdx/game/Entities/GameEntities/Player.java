package com.mygdx.game.Entities.GameEntities;

import java.io.Serializable;

public class Player implements Serializable {

    private float x, y;
    private float speedX, speedY;
    private int id;

    public Player(int pID) {
        this.x = 0;
        this.y = 0;
        this.speedX = 0;
        this.speedY = 0;
        this.id = pID;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
