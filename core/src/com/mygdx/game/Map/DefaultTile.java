package com.mygdx.game.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Entities.RenderingEntities.Textures;

public class DefaultTile extends Tile {

    /**
     * This creates a map tile, this is mostly to help with map rendering and sanity.
     * Default Tile represents dirt, grass, cobble
     */
    public DefaultTile(String type) {
        super(type);
        updateType(type);
    }

    public void updateType(String type) {
        // handling all the different types of tiles
        this.type = type;
        switch (type) {
            case "dirt":
                primaryTexture = Textures.dirt;
                break;
            case "grass":
                primaryTexture = Textures.grass;
                break;
            case "cobble":
                primaryTexture = Textures.cobble;
                break;
            case "manmadeCobble":
                primaryTexture = Textures.manmadeCobble;
                break;
        }
    }

    @Override
    public void render(float x, float y, SpriteBatch batch) {
        // renders the primary and secondary texture, if there are any
        if (primaryTexture == null) return; // texture not implemented yet

        Sprite primary = new Sprite(primaryTexture);
        primary.setScale(primaryScale);
        primary.setPosition(x, y);
        primary.draw(batch);
    }

}
