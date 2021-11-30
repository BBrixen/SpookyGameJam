package com.mygdx.game.Server_Game;

import com.mygdx.game.Client_Display.ClientGame;
import com.mygdx.game.Entities.GameEntities.Entity;
import com.mygdx.game.Map.Map;
import com.mygdx.game.Map.Tiles.Tile;

public class CollisionHandler {

    /**
     * Modifies playerentity to not run into an impassable object
     */
    public static void handleCollisions(float dTime, Entity entity) {
        if (ClientGame.map == null) return;

        // we handle the row/col separately so that the player can slide along the edge of a collision boundary
        int rowBefore = Map.playerYToMapRow(entity.getY());
        int colBefore = Map.playerXToMapCol(entity.getX());

        // handling col (x)
        float x = entity.getX();
        float speedX = entity.getSpeedX();
        x += speedX * dTime;

        int colAfter = Map.playerXToMapCol(x);
        Tile expectedPosition = ClientGame.map.SML.get(rowBefore).get(colAfter);
        if (expectedPosition.isPassable()) {
            entity.setX(x);
        }

        // handling row (y)
        float y = entity.getY();
        float speedY = entity.getSpeedY();
        y += speedY*dTime;

        int rowAfter = Map.playerYToMapRow(y);
        expectedPosition = ClientGame.map.SML.get(rowAfter).get(colBefore);
        if (expectedPosition.isPassable()) {
            entity.setY(y);
        }
    }

}
