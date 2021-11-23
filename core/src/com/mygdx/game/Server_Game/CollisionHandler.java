package com.mygdx.game.Server_Game;

import com.mygdx.game.Client_Display.ClientGame;
import com.mygdx.game.Entities.RenderingEntities.PlayerCharacter;
import com.mygdx.game.Map.Map;
import com.mygdx.game.Map.Tiles.Tile;

public class CollisionHandler {

    /**
     * Modifies playercharacter to not run into an impassable object
     */
    public static void handleCollisions(float dTime, PlayerCharacter character) {
        int rowBefore = Map.playerYToMapRow(character.getPositionY());
        int colBefore = Map.playerXToMapCol(character.getPositionX());

        float x = character.getPositionX();
        float speedX = character.getSpeedX();
        x += speedX * dTime;

        int colAfter = Map.playerXToMapCol(x);
        Tile expectedPosition = ClientGame.map.SML.get(rowBefore).get(colAfter);
        if (expectedPosition.isPassable()) {
            character.setPositionX(x);
        }

        float y = character.getPositionY();
        float speedY = character.getSpeedY();
        y += speedY*dTime;

        int rowAfter = Map.playerYToMapRow(y);
        expectedPosition = ClientGame.map.SML.get(rowAfter).get(colBefore);
        if (expectedPosition.isPassable()) {
            character.setPositionY(y);
        }
    }

}
