package com.mygdx.game.Entities.RenderingEntities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Textures {

    // map textures
    public static Texture grass, dirt, wood, boulder, cobble, manmadeCobble, tree, tree2, tree3;

    // entity textures
    public static Texture player;

    public static void loadTextures() {
        try {
            grass = new Texture(Gdx.files.internal("Sprites/GroundTiles/grass.png"));
            dirt = new Texture(Gdx.files.internal("Sprites/GroundTiles/dirt.png"));
            wood = new Texture(Gdx.files.internal("Sprites/GroundTiles/wood.png"));
            boulder = new Texture(Gdx.files.internal("Sprites/boulder.png"));
            cobble = new Texture(Gdx.files.internal("Sprites/GroundTiles/cobble.png"));
            manmadeCobble = new Texture(Gdx.files.internal("Sprites/GroundTiles/cobble_premade.png"));
            tree = new Texture(Gdx.files.internal("Sprites/tree.png"));
            tree2 = new Texture(Gdx.files.internal("Sprites/tree2.png"));
            tree3 = new Texture(Gdx.files.internal("Sprites/tree3.png"));
            player = new Texture(Gdx.files.internal("Sprites/cat.png"));
        } catch (NullPointerException e) {
            System.out.println("the server failed to initialize the textures");
        }
    }

}
