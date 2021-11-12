package com.mygdx.game.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Entities.RenderingEntities.Textures;

public class Tile {

    /**
     * This creates a map tile, this is mostly to help with map rendering and sanity.
     */
    private String type;
    private Texture primaryTexture, secondaryTexture;
    private float primaryScale, yOffset;
    private boolean passable, flipped;

    public Tile(String type, boolean flipped) {
        // all the default values
        this.primaryTexture = Textures.grass;
        this.secondaryTexture =  null;
        this.primaryScale = 1f;
        this.yOffset = 0;
        this.passable = true;
        this.type = type;
        this.flipped = flipped;

        updateType(type);
    }

    public void updateType(String type) {
        // handling all the different types of tiles
        this.type = type;
        switch (type) {
            case "tree1":
                primaryTexture = Textures.tree;
                secondaryTexture = Textures.dirt;
                primaryScale = 1.5f;
                break;
            case "tree2":
                primaryTexture = Textures.tree2;
                secondaryTexture = Textures.dirt;
                primaryScale = 1.5f;
                break;
            case "tree3":
                primaryTexture = Textures.tree3;
                secondaryTexture = Textures.dirt;
                primaryScale = 1.5f;
                break;
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
            case "boulder":
                primaryTexture = null; // still need a boulder texture
                passable = false;
                break;
        }
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped; // flipped for tree variance
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset; // yoffset used for trees so they are above the tile they live on
    }

    public void render(float x, float y, SpriteBatch batch) {
        // renders the primary and secondary texture, if there are any
        if (primaryTexture == null) return; // texture not implemented yet

        if (secondaryTexture != null) {
            Sprite secondary = new Sprite(this.secondaryTexture);
            secondary.setPosition(x, y);
            secondary.draw(batch);
        }

        Sprite primary = new Sprite(primaryTexture);
        if (flipped) primary.flip(true, false); // flip over x
        primary.setScale(primaryScale);
        primary.setPosition(x, y + this.yOffset);
        primary.draw(batch);
    }

}
