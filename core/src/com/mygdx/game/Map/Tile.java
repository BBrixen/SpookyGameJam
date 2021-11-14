package com.mygdx.game.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Tile {

    protected String type;
    protected Texture primaryTexture;
    protected float primaryScale;
    protected boolean passable;

    public Tile(String type) {
        this.type = type;
        passable = true;
        primaryScale = 1f;
    }

    public abstract void updateType(String type);

    public abstract void render(float x, float y, SpriteBatch batch);
}
