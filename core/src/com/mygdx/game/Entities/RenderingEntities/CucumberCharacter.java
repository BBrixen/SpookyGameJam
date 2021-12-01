package com.mygdx.game.Entities.RenderingEntities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Entities.GameEntities.Enemies.Cucumber;

public class CucumberCharacter extends EnemyCharacter {

    public CucumberCharacter(int id) {
        super();
        gameEntity = new Cucumber(id);
        sprite = new Sprite(Textures.cucumber);
        sprite.setScale(1.25f); // this might wanna change
    }
}
