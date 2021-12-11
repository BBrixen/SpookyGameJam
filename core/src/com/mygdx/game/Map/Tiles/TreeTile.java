package com.mygdx.game.Map.Tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Entities.RenderingEntities.Textures;

import java.util.HashMap;

public class TreeTile extends Tile {

    private Sprite background;
    private final float yoffset;
    private final boolean flipped;

    public TreeTile(String type, int row, int col,
                    boolean flipped, boolean server, HashMap<String, Texture> typeToTexture) {
        super(type, row, col);
        this.flipped = flipped;
        yoffset = 32;
        primaryScale = 1.5f;

        if (server) return;

        // handling background
        background = new Sprite(Textures.dirt);
        background.setPosition(x, y);

        // handles sprite too
        updateType(type, typeToTexture);
    }

    public void updateType(String type, HashMap<String, Texture> typeToTexture) {
        Texture primaryTexture = typeToTexture.get(type);
        sprite = new Sprite(primaryTexture);
        sprite.setScale(primaryScale);
        sprite.setPosition(x, y + yoffset);
        sprite.setFlip(flipped, false);
    }

    @Override
    public void render(SpriteBatch batch) {
        // renders the primary and secondary texture, if there are any
        if (sprite == null) return; // texture not implemented yet

        background.draw(batch);
        sprite.draw(batch);
    }
}
