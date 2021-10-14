package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.Entities.GameCharacter;

public class InputHandler {

    public static final float defaultSpeed = 100.0f;
    public static final float diagonalMultiplier =
            (float) Math.sqrt(0.5f);

    public static void handleKeyDown(GameCharacter actor) {
        // may need to group all the game data together in a single class

        // mouse button input: Gdx.input.isButtonPressed(Input.Buttons.LEFT)
        float deltaX = 0, deltaY = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            deltaX = -defaultSpeed;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            deltaX = defaultSpeed;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            deltaY = defaultSpeed;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            deltaY = -defaultSpeed;

        if (deltaY != 0 && deltaX != 0) {
            deltaY *= diagonalMultiplier;
            deltaX *= diagonalMultiplier;
        }

        actor.setSpeedX(deltaX);
        actor.setSpeedY(deltaY);
    }

}
