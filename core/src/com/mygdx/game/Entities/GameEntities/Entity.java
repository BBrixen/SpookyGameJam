package com.mygdx.game.Entities.GameEntities;

import com.mygdx.game.Map.Map;
import com.mygdx.game.Server_Game.CollisionHandler;

import java.io.Serializable;

public abstract class Entity implements Serializable {

    protected String type;
    protected int id;
    protected float x, y, speedX, speedY;

    protected float defaultSpeed = 60.0f; // 64 speed should be about 1 tile per second
    public static final float diagonalMultiplier =
            (float) Math.sqrt(0.5f);

    public Entity(String type, int id) {
        this.x = 0;
        this.y = 0;
        this.speedX = 0;
        this.speedY = 0;
        this.type = type;
        this.id = id;
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

    // vec is in the form [x, y]
    public void setSpeedVec(float xMult, float yMult) {
        if (xMult != 0 && yMult != 0) {
            xMult *= diagonalMultiplier;
            yMult *= diagonalMultiplier;
        }
        this.setSpeedX(this.getDefaultSpeed() * xMult);
        this.setSpeedY(this.getDefaultSpeed() * yMult);
    }

    public float getDefaultSpeed() {
        return defaultSpeed;
    }

    public void setDefaultSpeed(float defaultSpeed) {
        this.defaultSpeed = defaultSpeed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return type + " (" + id + ")";
    }
}
