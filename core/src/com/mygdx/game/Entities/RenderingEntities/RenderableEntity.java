package com.mygdx.game.Entities.RenderingEntities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Entities.GameEntities.Entity;

public abstract class RenderableEntity extends Actor {

    protected Sprite sprite;
    protected Entity gameEntity;

    public RenderableEntity() {
    }

    @Override
    public void draw(Batch batch, float alpha){
        sprite.draw(batch, alpha);
    }

    @Override
    public void act(float dTime){
        gameEntity.update(dTime);
        sprite.setPosition(this.gameEntity.getX(), this.gameEntity.getY());
    }

    public Entity getGameEntity() {
        return gameEntity;
    }

    public void setGameEntity(Entity gameEntity) {
        this.gameEntity = gameEntity;
    }
}
