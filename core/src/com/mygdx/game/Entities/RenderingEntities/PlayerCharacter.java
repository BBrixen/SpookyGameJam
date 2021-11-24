package com.mygdx.game.Entities.RenderingEntities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Map.Map;
import com.mygdx.game.Server_Game.CollisionHandler;

public class PlayerCharacter extends Actor {

    private Sprite characterSprite;
    private OrthographicCamera camera;
    private float positionX, positionY, speedX, speedY;

    public PlayerCharacter() {
        positionX = 0;
        positionY = 0;
        speedX = 0;
        speedY = 0;

        characterSprite = new Sprite(Textures.player);
        characterSprite.setScale(1.25f);
    }

    public PlayerCharacter(OrthographicCamera camera) {
        this();
        this.camera = camera;
    }

    @Override
    public void draw(Batch batch, float alpha){
        characterSprite.draw(batch, alpha);
    }

    @Override
    public void act(float dTime){
        CollisionHandler.handleCollisions(dTime, this);
        characterSprite.setPosition(this.positionX, this.positionY);
        if (this.camera != null)
            this.camera.position.set(this.positionX, this.positionY, 0);
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
        if (speedX < 0) characterSprite.setFlip(true, false);
        if (speedX > 0) characterSprite.setFlip(false, false);
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public float getSpeedX() {
        return speedX;
    }

    public float getSpeedY() {
        return speedY;
    }
}
