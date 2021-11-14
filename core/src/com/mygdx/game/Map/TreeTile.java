package com.mygdx.game.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Entities.RenderingEntities.Textures;

public class TreeTile extends Tile {

    private Texture secondaryTexture;
    private float yoffset;
    private boolean flipped;

    public TreeTile(String type, boolean flipped) {
        super(type);
        this.flipped = flipped;
        yoffset = 32;
        secondaryTexture = Textures.dirt;
        primaryScale = 1.5f;
        updateType(type);
    }

    public void updateType(String type) {
        switch (type) {
            case "tree1":
                primaryTexture = Textures.tree;
                break;
            case "tree2":
                primaryTexture = Textures.tree2;
                break;
            case "tree3":
                primaryTexture = Textures.tree3;
                break;
        }
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
