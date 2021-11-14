package com.mygdx.game.Server_Game;

import com.mygdx.game.Entities.RenderingEntities.PlayerCharacter;
import com.mygdx.game.Map.Map;

public class CollisionHandler {

    /**
     * Modifies playercharacter to not run into an impassable object
     */
    public static void handleCollisions(float dTime, Map map, PlayerCharacter character) {
        float x = character.getPositionX();
        float speedX = character.getSpeedX();
        float y = character.getPositionY();
        float speedY = character.getSpeedY();

        x += speedX * dTime;
        y += speedY * dTime;


    }

}
