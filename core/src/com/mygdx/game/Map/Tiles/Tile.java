package com.mygdx.game.Map.Tiles;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Map.Map;

public abstract class Tile {

    protected String type;
    protected Sprite sprite;
    protected float primaryScale, x, y;
    protected boolean passable;

    public Tile(String type, int row, int col) {
        this.type = type;
        passable = true;
        primaryScale = 1f;
        x = Map.mapIndexToPlayerPosition(col);
        y = Map.mapIndexToPlayerPosition(row);
    }

    public boolean isPassable() {
        return passable;
    }

    public abstract void render(SpriteBatch batch);
}
