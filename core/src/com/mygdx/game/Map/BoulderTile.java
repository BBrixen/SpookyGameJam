package com.mygdx.game.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Entities.RenderingEntities.Textures;

public class BoulderTile extends Tile{

    private Texture secondaryTexture;
    private float yoffset;

    public BoulderTile(String type) {
        super(type);
        passable = false;
        secondaryTexture = Textures.grass;
        primaryTexture = Textures.boulder;
        primaryScale = 1.25f;
        yoffset = 32;
    }

    @Override
    public void updateType(String type) {
        // empty bc boulder cannot have different type
    }

    @Override
    public void render(float x, float y, SpriteBatch batch) {
        // renders the primary and secondary texture, if there are any
        if (primaryTexture == null) return; // texture not implemented yet

        Sprite secondary = new Sprite(secondaryTexture);
        secondary.setPosition(x, y);
        secondary.draw(batch);

        Sprite primary = new Sprite(primaryTexture);
        primary.setScale(primaryScale);
        primary.setPosition(x, y + yoffset);
        primary.draw(batch);
    }
}
