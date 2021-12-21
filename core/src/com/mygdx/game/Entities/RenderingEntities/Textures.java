package com.mygdx.game.Entities.RenderingEntities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Textures {

    // map textures
    public static Texture grass, dirt, wood, boulder, cobble, manmadeCobble, tree1, tree2, tree3;

    // entity textures
    public static Texture player, cucumber;

    // item textures
    // these are class orb items
    public static Texture Item_wizard, Item_strategist, Item_ninja, Item_priest, Item_fighter;
    // these are other, less important items
    public static Texture item_coin, item_fish, item_milk;

    public static void loadTextures() {
        try {
            // tiles
            grass = new Texture(Gdx.files.internal("Sprites/GroundTiles/grass.png"));
            dirt = new Texture(Gdx.files.internal("Sprites/GroundTiles/dirt.png"));
            wood = new Texture(Gdx.files.internal("Sprites/GroundTiles/wood.png"));
            boulder = new Texture(Gdx.files.internal("Sprites/boulder.png"));
            cobble = new Texture(Gdx.files.internal("Sprites/GroundTiles/cobble.png"));
            manmadeCobble = new Texture(Gdx.files.internal("Sprites/GroundTiles/cobble_premade.png"));
            tree1 = new Texture(Gdx.files.internal("Sprites/tree1.png"));
            tree2 = new Texture(Gdx.files.internal("Sprites/tree2.png"));
            tree3 = new Texture(Gdx.files.internal("Sprites/tree3.png"));

            // entities
            player = new Texture(Gdx.files.internal("Sprites/cat.png"));
            cucumber = new Texture(Gdx.files.internal("Sprites/Enemies/cucumber.png"));

            // items
            Item_wizard = new Texture(Gdx.files.internal("Sprites/Items/item_wizard.png"));
            Item_strategist = new Texture(Gdx.files.internal("Sprites/Items/item_strategist.png"));
            Item_ninja = new Texture(Gdx.files.internal("Sprites/Items/item_ninja.png"));
            Item_priest = new Texture(Gdx.files.internal("Sprites/Items/item_priest.png"));
            Item_fighter = new Texture(Gdx.files.internal("Sprites/Items/item_fighter.png"));

            item_coin = new Texture(Gdx.files.internal("Sprites/Items/item_coin.png"));
            item_fish = new Texture(Gdx.files.internal("Sprites/Items/item_fish.png"));
            item_milk = new Texture(Gdx.files.internal("Sprites/Items/item_milk.png"));
        } catch (NullPointerException e) {
            System.out.println("the server failed to initialize the textures");
        }
    }

}
