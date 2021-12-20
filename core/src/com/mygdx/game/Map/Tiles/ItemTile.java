package com.mygdx.game.Map.Tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Entities.RenderingEntities.Textures;

import java.util.HashMap;

public class ItemTile extends Tile {

    private Sprite background;
    private boolean collected;

    public ItemTile(String type, int row, int col, boolean server, HashMap<String, Texture> typeToTexture) {
        super(type, row, col);
        collected = false;
        primaryScale = 0.5f;

        if (server) return;

        background = new Sprite(Textures.manmadeCobble);
        background.setPosition(x, y);
        updateType(type, typeToTexture);
    }

    private void updateType(String type, HashMap<String, Texture> typeToTexture) {
        Texture primaryTexture = typeToTexture.get(type);
        sprite = new Sprite(primaryTexture);
        sprite.setScale(primaryScale);
        sprite.setPosition(x, y);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (sprite == null) return; // texture not implemented yet

        background.draw(batch);
        if (!collected) // only render if it hasnt been collected yet
            sprite.draw(batch);
    }
}
