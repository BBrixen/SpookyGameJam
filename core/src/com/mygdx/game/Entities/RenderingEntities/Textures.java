package com.mygdx.game.Entities.RenderingEntities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Textures {

    // map textures
    public static Texture grass = new Texture(Gdx.files.internal("Sprites/GroundTiles/grass.png"));
    public static Texture dirt = new Texture(Gdx.files.internal("Sprites/GroundTiles/dirt.png"));
    public static Texture wood = new Texture(Gdx.files.internal("Sprites/GroundTiles/wood.png"));
    public static Texture boulder = new Texture(Gdx.files.internal("Sprites/boulder.png"));
    public static Texture cobble = new Texture(Gdx.files.internal("Sprites/GroundTiles/cobble.png"));
    public static Texture manmadeCobble = new Texture(Gdx.files.internal("Sprites/GroundTiles/cobble_premade.png"));
    public static Texture tree = new Texture(Gdx.files.internal("Sprites/tree.png"));
    public static Texture tree2 = new Texture(Gdx.files.internal("Sprites/tree2.png"));
    public static Texture tree3 = new Texture(Gdx.files.internal("Sprites/tree3.png"));

    // entity textures
    public static Texture player = new Texture(Gdx.files.internal("Sprites/cat.png"));

}
