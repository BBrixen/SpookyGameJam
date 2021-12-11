package com.mygdx.game.Map.Tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;

public class DefaultTile extends Tile {

    /**
     * This creates a map tile, this is mostly to help with map rendering and sanity.
     * Default Tile represents dirt, grass, cobble
     */
    public DefaultTile(String type, int row, int col,
                       boolean server, HashMap<String, Texture> typeToTexture) {
        super(type, row, col);
        if (server) return; // we dont want to create a sprite on the server side
        updateType(type, typeToTexture);
    }

    public void updateType(String type, HashMap<String, Texture> typeToTexture) {
        // handling all the different types of tiles
        Texture primaryTexture = typeToTexture.get(type);
        sprite = new Sprite(primaryTexture);
        sprite.setScale(primaryScale);
        sprite.setPosition(x, y);
    }

    @Override
    public void render(SpriteBatch batch) {
        // renders the primary and secondary texture, if there are any
        if (sprite == null) return; // texture not implemented yet
        sprite.draw(batch);
    }

}
