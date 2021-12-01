package com.mygdx.game.Entities.RenderingEntities;

import com.mygdx.game.Entities.GameEntities.Enemies.Enemy;

public class EnemyCharacter extends RenderableEntity {


    public EnemyCharacter() {}

    @Override
    public void act(float dTime) {
        ((Enemy) this.gameEntity).move();
        super.act(dTime);
    }
}
