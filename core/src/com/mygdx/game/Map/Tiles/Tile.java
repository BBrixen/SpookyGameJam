package com.mygdx.game.Map.Tiles;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Tile {

    protected String type;
    protected Sprite sprite;
    protected float primaryScale, x, y;
    protected boolean passable;

    public Tile(String type, int row, int col, int size) {
        this.type = type;
        passable = true;
        primaryScale = 1f;
        x = (col - size / 2f) * 64;
        y = (row - size / 2f) * 64;
    }

    public abstract void updateType(String type);

    public abstract void render(SpriteBatch batch);
}
