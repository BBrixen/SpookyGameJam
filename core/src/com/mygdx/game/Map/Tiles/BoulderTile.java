package com.mygdx.game.Map.Tiles;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Entities.RenderingEntities.Textures;

public class BoulderTile extends Tile {

    private Sprite background;

    public BoulderTile(String type, int row, int col, boolean server) {
        super(type, row, col);
        passable = false;
        primaryScale = 1.25f;
        float yoffset = 8;
        float xoffset = -6;

        if (server) return;

        // handling background
        background = new Sprite(Textures.grass);
        background.setPosition(x, y);

        // handling sprite
        sprite = new Sprite(Textures.boulder);
        sprite.setScale(primaryScale);
        sprite.setPosition(x + xoffset, y + yoffset);
    }

    @Override
    public void render(SpriteBatch batch) {
        // renders the primary and secondary texture, if there are any
        if (sprite == null) return; // texture not implemented yet

        background.draw(batch);
        sprite.draw(batch);
    }
}
