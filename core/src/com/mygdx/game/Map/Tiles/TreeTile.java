package com.mygdx.game.Map.Tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Entities.RenderingEntities.Textures;

public class TreeTile extends Tile {

    private final Sprite background;
    private final float yoffset;
    private final boolean flipped;

    public TreeTile(String type, int row, int col, boolean flipped) {
        super(type, row, col);
        this.flipped = flipped;
        yoffset = 32;
        primaryScale = 1.5f;

        // handling background
        background = new Sprite(Textures.dirt);
        background.setPosition(x, y);

        // handles sprite too
        updateType(type);
    }

    public void updateType(String type) {
        Texture primaryTexture = Textures.tree;
        switch (type) {
            case "tree2":
                primaryTexture = Textures.tree2;
                break;
            case "tree3":
                primaryTexture = Textures.tree3;
                break;
        }
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
