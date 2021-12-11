package com.mygdx.game.Entities.RenderingEntities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Entities.GameEntities.Player;

public class PlayerCharacter extends RenderableEntity {

    private OrthographicCamera camera;

    public PlayerCharacter(int pID) {
        super();
        gameEntity = new Player(pID);
        sprite = new Sprite(Textures.player);
        sprite.setScale(1.25f);
    }

    public PlayerCharacter(int pID, OrthographicCamera camera) {
        this(pID);
        this.camera = camera;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, ((Player) this.gameEntity).getHealth() / 100.0f);
    }

    @Override
    public void act(float dTime){
        super.act(dTime);
        if (this.camera != null)
            this.camera.position.set(this.gameEntity.getX(), this.gameEntity.getY(), 0);
    }
}
