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
    public DefaultTile(String type, int row, int col, int size) {
        super(type, row, col, size);
        updateType(type);
    }

    public void updateType(String type) {
        // handling all the different types of tiles
        Texture primaryTexture = Textures.grass;
        this.type = type;
        switch (type) {
            case "dirt":
                primaryTexture = Textures.dirt;
                break;
            case "cobble":
                primaryTexture = Textures.cobble;
                break;
            case "manmadeCobble":
                primaryTexture = Textures.manmadeCobble;
                break;
        }
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
