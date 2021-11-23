package com.mygdx.game.Map.Tiles;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Entities.RenderingEntities.Textures;

public class BoulderTile extends Tile {

    private final Sprite background;

    public BoulderTile(String type, int row, int col) {
        super(type, row, col);
        passable = false;
        primaryScale = 1.25f;
        float yoffset = 8;
        float xoffset = -6;

        // handling background
        background = new Sprite(Textures.grass);
        background.setPosition(x, y);

        // handling sprite
        sprite = new Sprite(Textures.boulder);
        sprite.setScale(primaryScale);
        sprite.setPosition(x + xoffset, y + yoffset);
    }

    @Override
    public void updateType(String type) {
        // empty bc boulder cannot have different type
    }

    @Override
    public void render(SpriteBatch batch) {
        // renders the primary and secondary texture, if there are any
        if (sprite == null) return; // texture not implemented yet

        background.draw(batch);
        sprite.draw(batch);
    }
}
