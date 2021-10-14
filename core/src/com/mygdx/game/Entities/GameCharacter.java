package com.mygdx.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GameCharacter extends Actor {

    Texture texture = new Texture(Gdx.files.internal("cat2.png"));
    Sprite characterSprite = new Sprite(texture);
    OrthographicCamera camera;
    float positionX, positionY, speedX, speedY;

    public GameCharacter() {
        positionX = 0;
        positionY = 0;
        speedX = 0;
        speedY = 0;
    }

    public GameCharacter(OrthographicCamera camera) {
        this();
        this.camera = camera;
    }

    @Override
    public void draw(Batch batch, float alpha){
        characterSprite.draw(batch, alpha);
    }

    @Override
    public void act(float dTime){
        this.positionX += speedX*dTime;
        this.positionY += speedY*dTime;
        characterSprite.setPosition(this.positionX, this.positionY);
        if (this.camera != null)
            this.camera.position.set(this.positionX, this.positionY, 0);
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }
}
