package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Tile extends Actor {

    Texture tileTexture;
    Sprite tileSprite;

    public Tile(Texture texture, float x, float y) {
        tileTexture = texture;
        tileSprite = new Sprite(tileTexture);
        tileSprite.setPosition(x, y); // might have to change it to row, col and convert here
    }

    @Override
    public void draw(Batch batch, float alpha) {
        tileSprite.draw(batch, alpha);
    }
}
